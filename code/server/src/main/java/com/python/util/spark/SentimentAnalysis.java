package com.python.util.spark;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.apache.spark.api.java.function.MapPartitionsFunction;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class SentimentAnalysis implements Serializable {

    @Data
    public static class SentimentRow {
        private String content;
        private String nickName;
        private Long createTime;
        private String ipRegion;
        private String sentiment;
    }

    @Data
    public static class Result {
        private double positive_prob;
        private String sentiment;
        private double neutral_prob;
        private double negative_prob;
    }

    @Data
    public static class JsonResponse  {
        private Result result;
        private boolean success;
        private String tracerId;
    }

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    /**
     * 进行评论的情感分析
     *
     * @param bookId 书籍id
     */
    public List run(String bookId) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Sentiment Analysis")
                .master("local[*]")
                .getOrCreate();
        try {
            // 从数据库读取数据
            Dataset<Row> jdbcDF = spark.read()
                    .format("jdbc")
                    .option("url", url)
                    .option("dbtable", "comments")
                    .option("user", username)
                    .option("password", password)
                    .load();

            // 选择特定列，并根据 bookId 过滤数据
            Dataset<Row> filteredComments = jdbcDF
                    .filter(functions.col("bookId").equalTo(bookId)) // 过滤条件
                    .select("content", "nickName", "createTime", "ipRegion"); // 同时选择多个列

            // 数据清洗
            Dataset<Row> cleanedData = filteredComments
                    .withColumn("content",
                            functions.regexp_replace(functions.col("content"), "<[^>]+>", "")).distinct();

            // 应用情感分析
            Dataset<SentimentRow> withSentiment = cleanedData.mapPartitions(
                    (MapPartitionsFunction<Row, SentimentRow>) iterator -> {
                        List<SentimentRow> results = new ArrayList<>();
                        while (iterator.hasNext()) {
                            Row row = iterator.next();
                            String content = row.getAs("content");
                            // 使用 HanLP 进行情感分析
                            String sentiment = analyzeSentimentWithHanLP(content);
                            SentimentRow sentimentRow = new SentimentRow();
                            sentimentRow.setContent(content);
                            sentimentRow.setNickName(row.getAs("nickName"));
                            sentimentRow.setCreateTime(row.getAs("createTime"));
                            sentimentRow.setIpRegion(row.getAs("ipRegion"));
                            sentimentRow.setSentiment(sentiment);
                            results.add(sentimentRow);
                        }
                        return results.iterator();
                    }, Encoders.bean(SentimentRow.class)
            );

            withSentiment.show();

            return withSentiment.collectAsList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            spark.stop();
        }

    }

    /**
     * 进行情感分析
     *
     * @param content
     * @return
     */
    private static String analyzeSentimentWithHanLP(String content) {
        // 创建DefaultAcsClient实例并初始化
        DefaultProfile defaultProfile = DefaultProfile.getProfile(
                "cn-hangzhou",
                "CHANGE_ME_BEFORE_RUNNING",
                "CHANGE_ME_BEFORE_RUNNING");
        IAcsClient client = new DefaultAcsClient(defaultProfile);
        // 创建API请求并设置参数
        CommonRequest request = new CommonRequest();
        // domain和version是固定值
        request.setDomain("alinlp.cn-hangzhou.aliyuncs.com");
        request.setVersion("2020-06-29");
        //action name可以在API文档里查到
        request.setSysAction("GetSaChGeneral");
        //put的参数可以在API文档查看到
        request.putQueryParameter("ServiceCode", "alinlp");
        request.putQueryParameter("Text", content);
        try {
            CommonResponse response = client.getCommonResponse(request);
            // 创建 JSONObject 对象
            JSONObject jsonObject = new JSONObject(response.getData());
            String resultString = String.valueOf(jsonObject.get("Data"));
            JsonResponse jsonResponse = JSON.parseObject(resultString, JsonResponse.class);
            // 获取 "sentiment" 的值
            String sentiment = jsonResponse.getResult().getSentiment();
            return sentiment;
        } catch (ClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("调用阿里云情感分析接口失败", e);
            return "正面";
        }
    }
}
