package com.python.api.service.impl;

import com.python.api.bean.dto.BookAnalysis;
import com.python.api.entity.Books;
import com.python.api.mapper.BooksMapper;
import com.python.api.mapper.MonthMapper;
import com.python.api.service.BooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author arthur
 * @since 2023-12-04
 */
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements BooksService {

    @Autowired
    BooksMapper booksMapper;

    @Autowired
    MonthMapper monthMapper;


    /**
     * 获取图书分析对象
     *
     * @throws ExecutionException 当执行计算结果出现异常时抛出
     * @throws InterruptedException 当线程被中断时抛出
     * @return 图书分析对象
     */
    public BookAnalysis BookAnalysis() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4); // 创建一个含有4个线程的线程池

        // 提交任务：根据粉丝数获取Top图书
        Future<List<BookAnalysis.WordCloud>> fansFuture = executor.submit(() -> booksMapper.selectTopBooksByFans());
        // 提交任务：根据关键词数获取Top图书
        Future<List<BookAnalysis.WordCloud>> wordsFuture = executor.submit(() -> booksMapper.selectTopBooksByWords());
        // 提交任务：根据推荐算法获取Top图书
        Future<List<BookAnalysis.WordCloud>> recommendFuture = executor.submit(() -> booksMapper.selectTopBooksByRecommend());
        // 提交任务：获取所有Top图书
        Future<List<BookAnalysis.WordCloud>> dataFuture = executor.submit(() -> booksMapper.selectTopBooks());
        // 获取根据粉丝数获取Top图书的结果
        List<BookAnalysis.WordCloud> fansData = fansFuture.get();
        // 获取根据关键词数获取Top图书的结果
        List<BookAnalysis.WordCloud> wordsData = wordsFuture.get();
        // 获取根据推荐算法获取Top图书的结果
        List<BookAnalysis.WordCloud> recommendData = recommendFuture.get();
        // 获取所有Top图书的结果
        List<BookAnalysis.WordCloud> data = dataFuture.get();

        // 获取所有Top图书的名称
        List<String> name = data.stream().map(BookAnalysis.WordCloud::getName).collect(Collectors.toList());
        // 获取所有Top图书的值
        List<Integer> value = data.stream().map(BookAnalysis.WordCloud::getValue).collect(Collectors.toList());
        // 获取所有Top图书的点击数
        List<Integer> clicks = data.stream().map(BookAnalysis.WordCloud::getClicks).collect(Collectors.toList());
        // 获取推荐算法推荐的图书名称
        List<String> names = recommendData.stream().map(BookAnalysis.WordCloud::getName).collect(Collectors.toList());
        // 获取推荐算法推荐的图书的值
        List<Integer> values = recommendData.stream().map(BookAnalysis.WordCloud::getValue).collect(Collectors.toList());
        // 创建折线对象
        BookAnalysis.line lineData = new BookAnalysis.line(names, values);
        // 创建柱状图对象
        BookAnalysis.lines lines = new BookAnalysis.lines(name, value, clicks);
        executor.shutdown();

        // 创建并返回图书分析对象
        return new BookAnalysis(fansData, wordsData, lineData, lines);
    }


    /**
     * 月票榜单分析
     * @return 返回月票榜单数据
     */
    public BookAnalysis months() {
        List<BookAnalysis.WordCloud> months = monthMapper.selectTopMonths();
        return new BookAnalysis(months);
    }

    /**
     * 某个书籍的月票走势
     * @return
     */
    public BookAnalysis monthsLine(String bookId) {
        List<BookAnalysis.WordCloud> months = monthMapper.monthsLine(bookId);
        List<String> name = months.stream().map(BookAnalysis.WordCloud::getName).collect(Collectors.toList());
        // 获取所有Top图书的值
        List<Integer> value = months.stream().map(BookAnalysis.WordCloud::getValue).collect(Collectors.toList());
        List<BookAnalysis.WordCloud> clicks = monthMapper.clicksPie(bookId);
        List<BookAnalysis.WordCloud> recommonds = monthMapper.recommondsPie(bookId);
        return new BookAnalysis(new BookAnalysis.line(name, value),clicks,recommonds);
    }
}
