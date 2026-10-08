-- Public initialization: schema only, no original users or business records.

-- Create and select your database before import.

SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS `books`  (
  `bookId` int NOT NULL COMMENT '书籍id唯一',
  `totalclick` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '总点击数',
  `totalrecommend` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '总推荐数',
  `weekrecommend` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '周推荐',
  `words` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '总字数',
  `fans` int NOT NULL COMMENT '总粉丝数',
  `descinfo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作品介绍',
  `arthur` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地址',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作品封面',
  `bookName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作品名称',
  `bookType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作品类型',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态',
  PRIMARY KEY (`bookId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `click`  (
  `authorId` int NOT NULL COMMENT '作者的id，整数类型，不可为空',
  `cateFineId` int NOT NULL COMMENT '书籍的细分类别的id，整数类型，不可为空',
  `cateFineName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的细分类别的名称，字符串类型',
  `rankNo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '书籍的排名，字符串类型，可为空',
  `number` int NOT NULL COMMENT '排名的数量',
  `orderNo` int NOT NULL COMMENT '书籍的排序号，整数类型，不可为空',
  `updownNumber` int NOT NULL COMMENT '书籍的上下架状态，整数类型，不可为空',
  `reward` int NOT NULL COMMENT '书籍的打赏金额，整数类型，不可为空',
  `rewardType` int NOT NULL COMMENT '书籍的打赏类型，整数类型，不可为空',
  `rewardStr` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '书籍的打赏字符串，字符串类型，可为空',
  `bookId` int NOT NULL COMMENT '书籍的id，整数类型，主键，不可为空',
  `bookName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的名称，字符串类型，不可为空',
  `bookCover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的封面，字符串类型，不可为空',
  `serialStatus` int NOT NULL COMMENT '书籍的连载状态，整数类型，不可为空',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的简介，字符串类型，不可为空',
  `pseudonym` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者的笔名，字符串类型，不可为空',
  `authorCover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '作者的头像，字符串类型，可为空',
  `latestChapterTime` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的最新章节更新时间，字符串类型，不可为空',
  `latestChapterId` int NOT NULL COMMENT '书籍的最新章节id，整数类型，不可为空',
  `latestChapterName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的最新章节名称，字符串类型，不可为空',
  `isFavorite` tinyint(1) NOT NULL COMMENT '书籍是否被收藏，布尔类型，不可为空',
  `isPython` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的爬取标识',
  `type` int NOT NULL COMMENT '0天1周2月',
  PRIMARY KEY (`isPython`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `comments`  (
  `forumsId` int NOT NULL COMMENT '论坛的ID',
  `threadId` int NOT NULL COMMENT '帖子的ID',
  `userId` int NOT NULL COMMENT '用户的ID',
  `type` int NOT NULL COMMENT '评论的类型',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的标题',
  `authorStatus` int NOT NULL COMMENT '作者的状态',
  `upvoteNum` int NOT NULL COMMENT '评论的点赞数',
  `checkStatus` int NOT NULL COMMENT '评论的审核状态',
  `sticky` int NOT NULL COMMENT '评论是否置顶',
  `rsuv` int NOT NULL COMMENT '评论的rsuv值',
  `lockStatus` int NOT NULL COMMENT '评论的锁定状态',
  `createTime` bigint NOT NULL COMMENT '评论的创建时间',
  `refThreadId` int NOT NULL COMMENT '评论的引用帖子ID',
  `imageUrl` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论的图片地址',
  `lastPostTime` bigint NOT NULL COMMENT '评论的最后回复时间',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论的内容',
  `orderNum` int NOT NULL COMMENT '评论的排序号',
  `opStatus` int NOT NULL COMMENT '评论的操作状态',
  `refPostId` int NOT NULL COMMENT '评论的引用评论ID',
  `nickName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户的昵称',
  `userImgUrl` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户的头像地址',
  `postNum` int NOT NULL COMMENT '评论的回复数',
  `contentType` int NOT NULL COMMENT '评论的内容类型',
  `donateUnit` int NOT NULL COMMENT '评论的打赏单位',
  `replyPostParentId` int NOT NULL COMMENT '评论的回复父评论ID',
  `beRepliedUserId` int NOT NULL COMMENT '评论的被回复用户ID',
  `beRepliedNickName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的被回复用户昵称',
  `rpList` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的回复列表',
  `beRefPost` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的被引用评论',
  `threadDonateType` int NOT NULL COMMENT '评论的帖子打赏类型',
  `mentionedUsers` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的提及用户',
  `mentionedNickNames` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的提及用户昵称',
  `fansScoreLevel` int NOT NULL COMMENT '用户的粉丝评分等级',
  `scoreLevelNickName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户的评分等级昵称',
  `forumLeaderStatus` int NOT NULL COMMENT '用户的论坛领导者状态',
  `userLevel` int NOT NULL COMMENT '用户的等级',
  `isClickSupport` int NOT NULL COMMENT '用户是否点击支持',
  `speakForbid` tinyint(1) NOT NULL COMMENT ' 用户是否被禁言',
  `markRed` tinyint(1) NOT NULL COMMENT '评论是否标红',
  `refChapterName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的引用章节名称',
  `refChapterContent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的引用章节内容',
  `heatNumber` int NOT NULL COMMENT '评论的热度数',
  `heatIgnore` int NOT NULL COMMENT '评论的热度忽略',
  `heatNumMark` bigint NOT NULL COMMENT '评论的热度标记',
  `redPacketId` int NOT NULL COMMENT '评论的红包ID',
  `includeThreadList` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的包含帖子列表',
  `trendIds` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的趋势ID',
  `trendViews` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的趋势视图',
  `ipRegion` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户的IP地区',
  `bookId` int NULL DEFAULT NULL COMMENT '评论的书籍'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `month`  (
  `authorId` int NULL DEFAULT NULL COMMENT '作者的 ID',
  `cateFineId` int NULL DEFAULT NULL COMMENT '书籍的细分类别 ID',
  `cateFineName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '书籍的细分类别名称',
  `number` int NULL DEFAULT NULL COMMENT '月票数',
  `orderNo` int NULL DEFAULT NULL COMMENT '排序',
  `updownNumber` int NULL DEFAULT NULL COMMENT '书籍的上下架状态',
  `reward` int NULL DEFAULT NULL COMMENT '奖励',
  `rewardType` int NULL DEFAULT NULL COMMENT '奖励类型',
  `rewardStr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '奖励说明',
  `bookId` int NULL DEFAULT NULL COMMENT '书籍的 ID',
  `bookName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '书籍的名称',
  `bookCover` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '书籍的封面图片地址',
  `serialStatus` int NULL DEFAULT NULL COMMENT '书籍的连载状态',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '书籍的简介',
  `pseudonym` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者的笔名',
  `authorCover` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者的头像图片地址',
  `latestChapterTime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '书籍的最新章节更新时间',
  `latestChapterId` int NULL DEFAULT NULL COMMENT '书籍的最新章节 ID',
  `latestChapterName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '书籍的最新章节名称',
  `isFavorite` tinyint(1) NULL DEFAULT NULL COMMENT '书籍是否被收藏',
  `is_python` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的爬虫标识',
  `rankNo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '爬取的月票年份与月份',
  PRIMARY KEY (`is_python`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `recommend`  (
  `authorId` int NOT NULL COMMENT '作者的id，整数类型，主键，不可为空',
  `cateFineId` int NOT NULL COMMENT '书籍的细分类别的id，整数类型，不可为空',
  `cateFineName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的细分类别的名称，字符串类型，不可为空',
  `rankNo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '书籍的排名，字符串类型，可为空',
  `number` int NOT NULL COMMENT '书籍的阅读量，整数类型，不可为空',
  `orderNo` int NOT NULL COMMENT '书籍的排序号，整数类型，不可为空',
  `updownNumber` int NOT NULL COMMENT '书籍的上下架状态，整数类型，不可为空',
  `reward` int NOT NULL COMMENT '书籍的打赏金额，整数类型，不可为空',
  `rewardType` int NOT NULL COMMENT '书籍的打赏类型，整数类型，不可为空',
  `rewardStr` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '书籍的打赏字符串，字符串类型，可为空',
  `bookId` int NOT NULL COMMENT '书籍的id，整数类型，主键，不可为空',
  `bookName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的名称，字符串类型，不可为空',
  `bookCover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的封面，字符串类型，不可为空',
  `serialStatus` int NOT NULL COMMENT '书籍的连载状态，整数类型，不可为空',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的简介，字符串类型，不可为空',
  `pseudonym` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者的笔名，字符串类型，不可为空',
  `authorCover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '作者的头像，字符串类型，可为空',
  `latestChapterTime` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的最新章节更新时间，字符串类型，不可为空',
  `latestChapterId` int NOT NULL COMMENT '书籍的最新章节id，整数类型，不可为空',
  `latestChapterName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍的最新章节名称，字符串类型，不可为空',
  `isFavorite` tinyint(1) NOT NULL COMMENT '书籍是否被收藏，布尔类型，不可为空',
  `isPython` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一标识',
  `type` int NOT NULL COMMENT '0天1周2月',
  PRIMARY KEY (`isPython`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `script`  (
  `script_id` int NOT NULL AUTO_INCREMENT,
  `script_name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `script_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `script_file_path` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`script_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `task`  (
  `task_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '任务名称',
  `task_script_id` int NULL DEFAULT NULL COMMENT '脚本信息',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行信息',
  `task_start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `task_end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `task_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `password` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `type` int NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS=1;
