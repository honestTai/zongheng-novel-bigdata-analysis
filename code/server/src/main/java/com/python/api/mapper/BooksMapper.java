package com.python.api.mapper;

import com.python.api.bean.dto.BookAnalysis;
import com.python.api.entity.Books;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author arthur
 * @since 2023-12-04
 */
@Mapper
public interface BooksMapper extends BaseMapper<Books> {

    /**
     * 根据粉丝数选择前几本热门图书
     *
     * @return 图书分析的词云列表
     */
    List<BookAnalysis.WordCloud> selectTopBooksByFans();

    /**
     * 根据词频选择前几本热门图书
     *
     * @return 图书分析的词云列表
     */
    List<BookAnalysis.WordCloud> selectTopBooksByWords();


    /**
     * 根据总推荐进行受欢迎书籍的排行
     *
     * @return 受欢迎的书籍数据
     */
    List<BookAnalysis.WordCloud> selectTopBooksByRecommend();


    List<BookAnalysis.WordCloud> selectTopBooks();
}
