package com.python.api.mapper;

import com.python.api.bean.dto.BookAnalysis;
import com.python.api.entity.Month;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author arthur
 * @since 2023-12-04
 */
@Mapper
public interface MonthMapper extends BaseMapper<Month> {

    /**
     * 获取书籍所有月份的月票总数
     * name是书籍，value是月票总数
     *
     * @return
     */
    List<BookAnalysis.WordCloud> selectTopMonths();

    @Select("SELECT rankNo as name,number as value FROM month where bookId = #{bookId}")
    List<BookAnalysis.WordCloud> monthsLine(String bookId);

    @Select("SELECT\n" +
            "CASE\n" +
            "\n" +
            "WHEN\n" +
            "type = 0 THEN\n" +
            "'天' \n" +
            "WHEN type = 1 THEN\n" +
            "'周' \n" +
            "WHEN type = 2 THEN\n" +
            "'月' \n" +
            "END AS name,\n" +
            "SUM( number ) AS value \n" +
            "FROM\n" +
            "click \n" +
            "WHERE\n" +
            "bookId = #{bookId} \n" +
            "GROUP BY\n" +
            "type \n" +
            "ORDER BY\n" +
            "type;")
    List<BookAnalysis.WordCloud> clicksPie(String bookId);
    @Select("SELECT\n" +
            "CASE\n" +
            "\n" +
            "WHEN\n" +
            "type = 0 THEN\n" +
            "'天' \n" +
            "WHEN type = 1 THEN\n" +
            "'周' \n" +
            "WHEN type = 2 THEN\n" +
            "'月' \n" +
            "END AS name,\n" +
            "SUM( number ) AS value \n" +
            "FROM\n" +
            "recommend \n" +
            "WHERE\n" +
            "bookId = #{bookId} \n" +
            "GROUP BY\n" +
            "type \n" +
            "ORDER BY\n" +
            "type;")
    List<BookAnalysis.WordCloud> recommondsPie(String bookId);
}
