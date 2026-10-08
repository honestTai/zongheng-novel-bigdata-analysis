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
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 论坛的ID
     */
    @TableField("forumsId")
    private Integer forumsId;

    /**
     * 帖子的ID
     */
    @TableField("threadId")
    private Integer threadId;

    /**
     * 用户的ID
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 评论的类型
     */
    private Integer type;

    /**
     * 评论的标题
     */
    private String title;

    /**
     * 作者的状态
     */
    @TableField("authorStatus")
    private Integer authorStatus;

    /**
     * 评论的点赞数
     */
    @TableField("upvoteNum")
    private Integer upvoteNum;

    /**
     * 评论的审核状态
     */
    @TableField("checkStatus")
    private Integer checkStatus;

    /**
     * 评论是否置顶
     */
    private Integer sticky;

    /**
     * 评论的rsuv值
     */
    private Integer rsuv;

    /**
     * 评论的锁定状态
     */
    @TableField("lockStatus")
    private Integer lockStatus;

    /**
     * 评论的创建时间
     */
    @TableField("createTime")
    private Long createTime;

    /**
     * 评论的引用帖子ID
     */
    @TableField("refThreadId")
    private Integer refThreadId;

    /**
     * 评论的图片地址
     */
    @TableField("imageUrl")
    private String imageUrl;

    /**
     * 评论的最后回复时间
     */
    @TableField("lastPostTime")
    private Long lastPostTime;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 评论的排序号
     */
    @TableField("orderNum")
    private Integer orderNum;

    /**
     * 评论的操作状态
     */
    @TableField("opStatus")
    private Integer opStatus;

    /**
     * 评论的引用评论ID
     */
    @TableField("refPostId")
    private Integer refPostId;

    /**
     * 用户的昵称
     */
    @TableField("nickName")
    private String nickName;

    /**
     * 用户的头像地址
     */
    @TableField("userImgUrl")
    private String userImgUrl;

    /**
     * 评论的回复数
     */
    @TableField("postNum")
    private Integer postNum;

    /**
     * 评论的内容类型
     */
    @TableField("contentType")
    private Integer contentType;

    /**
     * 评论的打赏单位
     */
    @TableField("donateUnit")
    private Integer donateUnit;

    /**
     * 评论的回复父评论ID
     */
    @TableField("replyPostParentId")
    private Integer replyPostParentId;

    /**
     * 评论的被回复用户ID
     */
    @TableField("beRepliedUserId")
    private Integer beRepliedUserId;

    /**
     * 评论的被回复用户昵称
     */
    @TableField("beRepliedNickName")
    private String beRepliedNickName;

    /**
     * 评论的回复列表
     */
    @TableField("rpList")
    private String rpList;

    /**
     * 评论的被引用评论
     */
    @TableField("beRefPost")
    private String beRefPost;

    /**
     * 评论的帖子打赏类型
     */
    @TableField("threadDonateType")
    private Integer threadDonateType;

    /**
     * 评论的提及用户
     */
    @TableField("mentionedUsers")
    private String mentionedUsers;

    /**
     * 评论的提及用户昵称
     */
    @TableField("mentionedNickNames")
    private String mentionedNickNames;

    /**
     * 用户的粉丝评分等级
     */
    @TableField("fansScoreLevel")
    private Integer fansScoreLevel;

    /**
     * 用户的评分等级昵称
     */
    @TableField("scoreLevelNickName")
    private String scoreLevelNickName;

    /**
     * 用户的论坛领导者状态
     */
    @TableField("forumLeaderStatus")
    private Integer forumLeaderStatus;

    /**
     * 用户的等级
     */
    @TableField("userLevel")
    private Integer userLevel;

    /**
     * 用户是否点击支持
     */
    @TableField("isClickSupport")
    private Integer isClickSupport;

    /**
     *  用户是否被禁言
     */
    @TableField("speakForbid")
    private Boolean speakForbid;

    /**
     * 评论是否标红
     */
    @TableField("markRed")
    private Boolean markRed;

    /**
     * 评论的引用章节名称
     */
    @TableField("refChapterName")
    private String refChapterName;

    /**
     * 评论的引用章节内容
     */
    @TableField("refChapterContent")
    private String refChapterContent;

    /**
     * 评论的热度数
     */
    @TableField("heatNumber")
    private Integer heatNumber;

    /**
     * 评论的热度忽略
     */
    @TableField("heatIgnore")
    private Integer heatIgnore;

    /**
     * 评论的热度标记
     */
    @TableField("heatNumMark")
    private Long heatNumMark;

    /**
     * 评论的红包ID
     */
    @TableField("redPacketId")
    private Integer redPacketId;

    /**
     * 评论的包含帖子列表
     */
    @TableField("includeThreadList")
    private String includeThreadList;

    /**
     * 评论的趋势ID
     */
    @TableField("trendIds")
    private String trendIds;

    /**
     * 评论的趋势视图
     */
    @TableField("trendViews")
    private String trendViews;

    /**
     * 用户的IP地区
     */
    @TableField("ipRegion")
    private String ipRegion;

    /**
     * 评论的书籍
     */
    @TableField("bookId")
    private Integer bookId;

    @TableField(exist = false)
    public String bookName;


}
