package com.python.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author arthur
 * @since 2023-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Month implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作者的 ID
     */
    @TableField("authorId")
    private Integer authorId;

    /**
     * 书籍的细分类别 ID
     */
    @TableField("cateFineId")
    private Integer cateFineId;

    /**
     * 书籍的细分类别名称
     */
    @TableField("cateFineName")
    private String cateFineName;

    /**
     * 月票数
     */
    private Integer number;

    /**
     * 排序
     */
    @TableField("orderNo")
    private Integer orderNo;

    /**
     * 书籍的上下架状态
     */
    @TableField("updownNumber")
    private Integer updownNumber;

    /**
     * 奖励
     */
    private Integer reward;

    /**
     * 奖励类型
     */
    @TableField("rewardType")
    private Integer rewardType;

    /**
     * 奖励说明
     */
    @TableField("rewardStr")
    private String rewardStr;

    /**
     * 书籍的 ID
     */
    @TableField("bookId")
    private Integer bookId;

    /**
     * 书籍的名称
     */
    @TableField("bookName")
    private String bookName;

    /**
     * 书籍的封面图片地址
     */
    @TableField("bookCover")
    private String bookCover;

    /**
     * 书籍的连载状态
     * 0是1不是
     */
    @TableField("serialStatus")
    private Integer serialStatus;

    /**
     * 书籍的简介
     */
    private String description;

    /**
     * 作者的笔名
     */
    private String pseudonym;

    /**
     * 作者的头像图片地址
     */
    @TableField("authorCover")
    private String authorCover;

    /**
     * 书籍的最新章节更新时间
     */
    @TableField("latestChapterTime")
    private String latestChapterTime;

    /**
     * 书籍的最新章节 ID
     */
    @TableField("latestChapterId")
    private Integer latestChapterId;

    /**
     * 书籍的最新章节名称
     */
    @TableField("latestChapterName")
    private String latestChapterName;

    /**
     * 书籍是否被收藏
     */
    @TableField("isFavorite")
    private Boolean isFavorite;

    /**
     * 书籍的爬虫标识
     */
      private String isPython;

    /**
     * 爬取的月票年份与月份
     */
    @TableField("rankNo")
    private String rankNo;


}
