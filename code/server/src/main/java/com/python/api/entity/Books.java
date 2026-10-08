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
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 书籍id唯一
     */
      @TableId("bookId")
    private Integer bookId;

    /**
     * 总点击数
     */
    private String totalclick;

    /**
     * 总推荐数
     */
    private String totalrecommend;

    /**
     * 周推荐
     */
    private String weekrecommend;

    /**
     * 总字数
     */
    private String words;

    /**
     * 总粉丝数
     */
    private Integer fans;

    /**
     * 作品介绍
     */
    private String descinfo;

    /**
     * 作者
     */
    private String arthur;

    /**
     * 地址
     */
    private String link;

    /**
     * 作品封面
     */
    private String pic;

    /**
     * 作品名称
     */
    @TableField("bookName")
    private String bookName;

    /**
     * 作品类型
     */
    @TableField("bookType")
    private String bookType;

    /**
     * 状态
     */
    private String status;


}
