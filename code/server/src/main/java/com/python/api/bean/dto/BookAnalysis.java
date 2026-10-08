package com.python.api.bean.dto;

import lombok.Data;

import java.util.List;

/**
 * 书籍分析返回的对象数据
 */
@Data
public class BookAnalysis {

    /**
     * 词云图
     */
    @Data
    public static class WordCloud {
        /**
         * 书籍昵称
         */
        private String name;
        /**
         * 权重，就是各数
         */
        private Integer value;

        /**
         * 权重，就是各数
         */
        private Integer clicks;

        public WordCloud(String name, Integer value, Integer clicks) {
            this.name = name;
            this.value = value;
            this.clicks = clicks;
        }

        public WordCloud(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 受欢迎排行折线图
     */
    @Data
    public static class line {
        /**
         * 书籍昵称
         */
        private List<String> names;
        /**
         * 权重，就是总的推荐数
         */
        private List<Integer> values;

        public line(List<String> names, List<Integer> values) {
            this.names = names;
            this.values = values;
        }
    }

    /**
     * 点击与字数关系
     */
    @Data
    public static class lines {
        /**
         * 书籍昵称
         */
        private List<String> names;
        /**
         * 总字数
         */
        private List<Integer> values;

        /**
         * 总点击数
         */
        private List<Integer> clicks;

        public lines(List<String> names, List<Integer> values, List<Integer> clicks) {
            this.names = names;
            this.values = values;
            this.clicks = clicks;
        }
    }


    /**
     * 受欢迎排行折线图
     */
    private line line;

    /**
     * 字数与点击数关系
     */
    private lines lines;

    /**
     * 粉丝
     */
    private List<WordCloud> fans;

    /**
     * 字数
     */
    private List<WordCloud> words;



    public BookAnalysis(List<WordCloud> fans, List<WordCloud> words,line line,lines lines) {
        this.fans = fans;
        this.words = words;
        this.line = line;
        this.lines = lines;
    }

    public BookAnalysis(List<WordCloud> words) {
        this.words = words;
    }

    public BookAnalysis(line line,List<WordCloud> words,List<WordCloud> fans) {
        this.line = line;
        this.words = words;
        this.fans = fans;
    }
}
