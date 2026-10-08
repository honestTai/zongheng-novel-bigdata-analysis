package com.python.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class Click implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作者的id，整数类型，不可为空
     */
    @TableField("authorId")
    private Integer authorId;

    /**
     * 书籍的细分类别的id，整数类型，不可为空
     */
    @TableField("cateFineId")
    private Integer cateFineId;

    /**
     * 书籍的细分类别的名称，字符串类型
     */
    @TableField("cateFineName")
    private String cateFineName;

    /**
     * 书籍的排名，字符串类型，可为空
     */
    @TableField("rankNo")
    private String rankNo;

    /**
     * 排名的数量
     */
    private Integer number;

    /**
     * 书籍的排序号，整数类型，不可为空
     */
    @TableField("orderNo")
    private Integer orderNo;

    /**
     * 书籍的上下架状态，整数类型，不可为空
     */
    @TableField("updownNumber")
    private Integer updownNumber;

    /**
     * 书籍的打赏金额，整数类型，不可为空
     */
    private Integer reward;

    /**
     * 书籍的打赏类型，整数类型，不可为空
     */
    @TableField("rewardType")
    private Integer rewardType;

    /**
     * 书籍的打赏字符串，字符串类型，可为空
     */
    @TableField("rewardStr")
    private String rewardStr;

    /**
     * 书籍的id，整数类型，主键，不可为空
     */
    @TableField("bookId")
    private Integer bookId;

    /**
     * 书籍的名称，字符串类型，不可为空
     */
    @TableField("bookName")
    private String bookName;

    /**
     * 书籍的封面，字符串类型，不可为空
     */
    @TableField("bookCover")
    private String bookCover;

    /**
     * 书籍的连载状态，整数类型，不可为空
     */
    @TableField("serialStatus")
    private Integer serialStatus;

    /**
     * 书籍的简介，字符串类型，不可为空
     */
    private String description;

    /**
     * 作者的笔名，字符串类型，不可为空
     */
    private String pseudonym;

    /**
     * 作者的头像，字符串类型，可为空
     */
    @TableField("authorCover")
    private String authorCover;

    /**
     * 书籍的最新章节更新时间，字符串类型，不可为空
     */
    @TableField("latestChapterTime")
    private String latestChapterTime;

    /**
     * 书籍的最新章节id，整数类型，不可为空
     */
    @TableField("latestChapterId")
    private Integer latestChapterId;

    /**
     * 书籍的最新章节名称，字符串类型，不可为空
     */
    @TableField("latestChapterName")
    private String latestChapterName;

    /**
     * 书籍是否被收藏，布尔类型，不可为空
     */
    @TableField("isFavorite")
    private Boolean isFavorite;

    /**
     * 书籍的爬取标识
     */
      @TableId("isPython")
    private String isPython;

    /**
     * 0天1周2月
     */
    private Integer type;


}
