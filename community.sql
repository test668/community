/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 28/01/2021 17:35:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collect_user_question
-- ----------------------------
DROP TABLE IF EXISTS `collect_user_question`;
CREATE TABLE `collect_user_question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `collect_user_id` int(11) NULL DEFAULT NULL,
  `collect_question_id` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT 0 COMMENT '0表示该问题未被收藏，1表示被收藏',
  `gmt_create` bigint(255) NULL DEFAULT NULL,
  `gmt_modifity` bigint(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect_user_question
-- ----------------------------
INSERT INTO `collect_user_question` VALUES (1, 2, 8, 1, 1610437902155, 1610445637580);
INSERT INTO `collect_user_question` VALUES (2, 2, 6, 1, 1610444639618, 1610444639618);
INSERT INTO `collect_user_question` VALUES (3, 2, 11, 1, 1610444644456, 1610500410435);
INSERT INTO `collect_user_question` VALUES (4, 2, 13, 1, 1611565180998, 1611565180998);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `commentator` int(11) NULL DEFAULT NULL,
  `gmt_create` bigint(20) NULL DEFAULT NULL,
  `gmt_modifity` bigint(20) NULL DEFAULT NULL,
  `like_count` bigint(20) NULL DEFAULT 0,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `comment_count` int(11) NULL DEFAULT 0,
  `status` int(11) NULL DEFAULT 0 COMMENT '0为正常，1为已删除',
  `is_top` int(11) NULL DEFAULT 0 COMMENT '0为正常，1为置顶',
  `dislike_count` bigint(20) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 2, 2, 1589089834502, 1589089834502, 0, 'ffa', 1, 1, 0, 0, 0);
INSERT INTO `comment` VALUES (2, 1, 2, 1589089873488, 1589089873488, 0, 'oi', 2, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (3, 4, 2, 1589092433480, 1610609737020, 0, 'ff', 1, 0, 0, 0, 1);
INSERT INTO `comment` VALUES (4, 7, 3, 1589181458825, 1610609561913, 0, 'fdsf', 1, 2, 0, 1, 1);
INSERT INTO `comment` VALUES (5, 4, 2, 1589181597210, 1589181597210, 0, 'fsdfadf', 2, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (6, 8, 2, 1589181880246, 1610421568661, 1, 'fsdfs', 1, 2, 0, 0, 1);
INSERT INTO `comment` VALUES (7, 8, 2, 1589461413183, 1610358428959, 1, '你好', 1, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (8, 4, 2, 1610001721230, 1610418134093, 0, 'eff', 2, 0, 0, 0, -1);
INSERT INTO `comment` VALUES (9, 6, 2, 1610087625574, 1610087625574, 0, '15656', 1, 0, 1, 0, 0);
INSERT INTO `comment` VALUES (10, 6, 2, 1610089379742, 1610089379742, 0, '啊实打实大大', 1, 0, 1, 0, 0);
INSERT INTO `comment` VALUES (11, 6, 2, 1610089389692, 1610089389692, 0, 'dada', 1, 0, 1, 0, 0);
INSERT INTO `comment` VALUES (12, 4, 2, 1610093817095, 1610095472232, 0, '2222565656', 1, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (13, 4, 2, 1610093828096, 1610093828096, 0, 'dsdsad', 1, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (14, 4, 2, 1610093843310, 1610620985717, 1, '1111222', 1, 2, 0, 1, 1);
INSERT INTO `comment` VALUES (15, 11, 2, 1610519146513, 1610519146513, 0, '22', 1, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (16, 6, 2, 1610618857685, 1610618857685, 0, '225', 2, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (17, 6, 2, 1610618879188, 1610618879188, 0, '@Github：ddadd', 2, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (18, 14, 2, 1610621007318, 1610621007318, 0, '16154', 2, 0, 0, 0, 0);
INSERT INTO `comment` VALUES (19, 14, 2, 1610621029570, 1610621029570, 0, '@Github：test', 2, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for dislike_user_comment
-- ----------------------------
DROP TABLE IF EXISTS `dislike_user_comment`;
CREATE TABLE `dislike_user_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dislike_user_id` int(11) NULL DEFAULT NULL,
  `dislike_comment_id` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '0表示未被点踩，1表示点踩',
  `gmt_create` bigint(255) NULL DEFAULT NULL,
  `gmt_modifity` bigint(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dislike_user_comment
-- ----------------------------
INSERT INTO `dislike_user_comment` VALUES (1, 2, 6, 1, 1610333300076, 1610333300076);
INSERT INTO `dislike_user_comment` VALUES (2, 2, 7, 0, 1610333485099, 1610358428956);
INSERT INTO `dislike_user_comment` VALUES (3, 2, 4, 1, 1610356687401, 1610609561906);
INSERT INTO `dislike_user_comment` VALUES (4, 2, 8, 0, 1610418134083, 1610418134083);
INSERT INTO `dislike_user_comment` VALUES (5, 2, 3, 1, 1610609737012, 1610609737012);
INSERT INTO `dislike_user_comment` VALUES (6, 2, 14, 1, 1610620985711, 1610620985711);

-- ----------------------------
-- Table structure for like_user_comment
-- ----------------------------
DROP TABLE IF EXISTS `like_user_comment`;
CREATE TABLE `like_user_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `like_user_id` int(11) NOT NULL,
  `like_comment_id` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0表示该评论未被点赞，1表示已被点赞',
  `gmt_create` bigint(20) NULL DEFAULT NULL,
  `gmt_modifity` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of like_user_comment
-- ----------------------------
INSERT INTO `like_user_comment` VALUES (1, 2, 6, 0, 1610005639512, 1610421568652);
INSERT INTO `like_user_comment` VALUES (2, 2, 7, 0, 1610005678778, 1610072549064);
INSERT INTO `like_user_comment` VALUES (3, 2, 4, 0, 1610005940608, 1610417577251);
INSERT INTO `like_user_comment` VALUES (4, 2, 14, 1, 1610620984906, 1610620984906);

-- ----------------------------
-- Table structure for like_user_question
-- ----------------------------
DROP TABLE IF EXISTS `like_user_question`;
CREATE TABLE `like_user_question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `like_user_id` int(11) NULL DEFAULT NULL,
  `like_question_id` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT 0 COMMENT '0表示该问题未被点赞，1表示该问题被点赞',
  `gmt_create` bigint(20) NULL DEFAULT NULL,
  `gmt_modifity` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of like_user_question
-- ----------------------------
INSERT INTO `like_user_question` VALUES (1, 2, 11, 0, 1610595005157, 1610595537391);
INSERT INTO `like_user_question` VALUES (2, 2, 7, 0, 1610595551904, 1610595620248);
INSERT INTO `like_user_question` VALUES (3, 2, 5, 0, 1610595606624, 1610595610193);
INSERT INTO `like_user_question` VALUES (4, 2, 13, 1, 1611565178206, 1611565178206);
INSERT INTO `like_user_question` VALUES (5, 6, 7, 1, 1611650499738, 1611650499738);
INSERT INTO `like_user_question` VALUES (6, 6, 6, 1, 1611651591952, 1611651591952);

-- ----------------------------
-- Table structure for login_black_list
-- ----------------------------
DROP TABLE IF EXISTS `login_black_list`;
CREATE TABLE `login_black_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gmt_create` bigint(20) NULL DEFAULT NULL,
  `gmt_modifity` bigint(20) NULL DEFAULT NULL,
  `login_time` int(11) NULL DEFAULT 1 COMMENT '登录次数',
  `status` int(11) NULL DEFAULT 0 COMMENT '0表示正常，1表示冻结状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_black_list
-- ----------------------------
INSERT INTO `login_black_list` VALUES (1, '2208700170@qq.com', 1611220826012, 1611220826012, 0, 0);

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notifier` bigint(20) NULL DEFAULT NULL,
  `receiver` bigint(20) NULL DEFAULT NULL,
  `outer_id` bigint(20) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `gmt_create` bigint(20) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '0未读，1已读，2删除',
  `notifier_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `outer_title` varchar(502) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `flag_id` bigint(20) NULL DEFAULT NULL COMMENT '记录被评论的问题，评论，以及被点赞的评论或者问题的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES (1, 3, 2, 7, 1, 1589181458839, 1, 'ano', 'dad', 0);
INSERT INTO `notification` VALUES (2, 2, 3, 7, 2, 1589181597217, 1, 'Github', 'dad', 0);
INSERT INTO `notification` VALUES (3, 2, 3, 8, 1, 1589181880265, 1, 'Github', 'dasdaf', 0);
INSERT INTO `notification` VALUES (4, 2, 3, 8, 1, 1589461413197, 1, 'Github', 'dasdaf', 0);
INSERT INTO `notification` VALUES (5, 2, 3, 7, 2, 1610001721255, 0, 'Github', 'dad', 0);
INSERT INTO `notification` VALUES (6, 2, 3, 7, 3, 1610005940623, 0, 'Github', 'fds...', 0);
INSERT INTO `notification` VALUES (7, 2, 3, 7, 3, 1610005942400, 0, 'Github', 'fds...', 0);
INSERT INTO `notification` VALUES (8, 2, 3, 7, 3, 1610008013766, 0, 'Github', 'fds...', 0);
INSERT INTO `notification` VALUES (9, 2, 3, 7, 3, 1610008023753, 0, 'Github', 'fds...', 0);
INSERT INTO `notification` VALUES (10, 2, 3, 7, 3, 1610008182124, 0, 'Github', 'fds...', 0);
INSERT INTO `notification` VALUES (11, 2, 2, 8, 3, 1610008269821, 1, 'Github', 'fsd...', 0);
INSERT INTO `notification` VALUES (12, 2, 2, 8, 3, 1610008872090, 1, 'Github', 'fsd...', 0);
INSERT INTO `notification` VALUES (13, 2, 2, 8, 3, 1610012168784, 2, 'Github', '你好', 7);
INSERT INTO `notification` VALUES (14, 2, 2, 8, 3, 1610012346139, 2, 'Github', '你好', 7);
INSERT INTO `notification` VALUES (15, 2, 3, 7, 3, 1610356689500, 2, 'Github', 'fds...', 4);
INSERT INTO `notification` VALUES (16, 2, 3, 7, 3, 1610417573573, 2, 'Github', 'fds...', 4);
INSERT INTO `notification` VALUES (17, 2, 2, 11, 4, 1610595168019, 2, 'Github', '3.用户删除自己的评...', 11);
INSERT INTO `notification` VALUES (18, 2, 2, 11, 4, 1610595192435, 2, 'Github', '3.用户删除自己的评...', 11);
INSERT INTO `notification` VALUES (19, 2, 2, 7, 4, 1610595551914, 2, 'Github', 'dad', 7);
INSERT INTO `notification` VALUES (20, 2, 2, 7, 4, 1610595594101, 2, 'Github', 'dad', 7);
INSERT INTO `notification` VALUES (21, 2, 2, 5, 4, 1610595606631, 2, 'Github', 'daddad', 5);
INSERT INTO `notification` VALUES (22, 2, 2, 8, 2, 1610618857706, 1, 'Github', 'dasdaf', 6);
INSERT INTO `notification` VALUES (23, 2, 2, 6, 2, 1610618879192, 1, 'Github', 'ada', 16);
INSERT INTO `notification` VALUES (24, 2, 2, 8, 2, 1610618879199, 1, 'Github', 'dasdaf', 6);
INSERT INTO `notification` VALUES (25, 2, 2, 4, 3, 1610620984919, 1, 'Github', '1111222', 14);
INSERT INTO `notification` VALUES (26, 2, 2, 4, 2, 1610621007339, 1, 'Github', '1111222', 14);
INSERT INTO `notification` VALUES (27, 2, 2, 4, 2, 1610621029574, 1, 'Github', '16154', 18);
INSERT INTO `notification` VALUES (28, 2, 2, 4, 2, 1610621029580, 1, 'Github', '1111222', 14);
INSERT INTO `notification` VALUES (29, 2, 6, 13, 4, 1611565178216, 1, 'Github', '这是一个新问题', 13);
INSERT INTO `notification` VALUES (30, 6, 2, 7, 4, 1611650499753, 1, '1376969', 'dad', 7);
INSERT INTO `notification` VALUES (31, 6, 2, 6, 4, 1611651591964, 1, '1376969', 'ada', 6);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(5012) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gmt_create` bigint(20) NULL DEFAULT NULL,
  `gmt_modifity` bigint(20) NULL DEFAULT NULL,
  `creator` int(11) NULL DEFAULT NULL,
  `view_count` int(11) NULL DEFAULT 0,
  `comment_count` int(11) NULL DEFAULT 0,
  `like_count` int(11) NULL DEFAULT 0,
  `status` int(11) NULL DEFAULT 0 COMMENT '0为正常，1表示已删除',
  `collect_count` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (2, 'duidui', 'private UserMapper userMapper;\r\n\r\n    @Autowired\r\n    private NotificationMapper notificationMapper;\r\n\r\n    @Transactional\r\n    public void insert(Comment comment, User commentator) {\r\n        if (comment.getParentId()==0){\r\n            throw new CustomizeException(\"回复的问题不存在\");\r\n        }\r\n        if (comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){\r\n            throw new CustomizeException(\"评论类型不允许\");\r\n        }\r\n        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){\r\n         Comment dbComment= commentMapper.getById((int)comment.getParentId());\r\n         if (dbComment==null){\r\n             throw new CustomizeException(\"评论不存在\");', 'java,spring', 1589089701819, 1589089701819, 2, 10, 1, 0, 1, 0);
INSERT INTO `question` VALUES (3, 'ada', 'dad', 'java', 1589089817051, 1589089817051, 2, 1, 0, 0, 1, 0);
INSERT INTO `question` VALUES (4, 'hdiadja', 'fdf', 'java', 1589092419446, 1589092419446, 2, 119, 4, 0, 0, 0);
INSERT INTO `question` VALUES (5, 'daddad', 'asdas', 'java', 1589093023437, 1589093023437, 2, 14, 0, 0, 1, 0);
INSERT INTO `question` VALUES (6, 'ada', 'ada', 'java', 1589093031429, 1589093031429, 2, 26, 0, 1, 0, 1);
INSERT INTO `question` VALUES (7, 'dad', 'aa', 'java', 1589093042667, 1589093042667, 2, 145, 1, 1, 0, 0);
INSERT INTO `question` VALUES (8, 'dasdaf', 'dfdafadfasd', 'java', 1589181860905, 1589181860905, 3, 332, 2, 0, 0, 1);
INSERT INTO `question` VALUES (9, '                            <li><a href=\"#\" onclick=\"deleteComment(this)\" th:comment-id=\"${comment.id}\" th:parent-id=\"${comment.parentId}\">删除</a></li>', 'public List<CommentDto> listByTargetId(User loginUser,Integer id, CommentTypeEnum type) {\r\n       List<Comment> comments=commentMapper.ListById(id, type.getType());\r\n       if (comments.size()==0){\r\n           return new ArrayList<>();\r\n       }\r\n\r\n        Collections.sort(comments, new Comparator<Comment>() {\r\n            @Override\r\n            public int compare(Comment o1, Comment o2) {\r\n                return (int)(o2.getLikeCount()-o1.getLikeCount());\r\n            }\r\n        });\r\n        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());\r\n        List<Integer> userIds=new ArrayList();\r\n        userIds.addAll(commentators);\r\n\r\n        //修改处\r\n        List<User> users=new ArrayList<>();\r\n        for (Integer userId : userIds) {\r\n            User user = userMapper.findById(userId);\r\n            users.add(user);\r\n        }\r\n\r\n\r\n\r\n        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));\r\n\r\n\r\n        List<CommentDto> commentDtos;\r\n        if(loginUser!=null&&type.getType()==1){\r\n            LikeUserComment likeUserComment=new LikeUserComment();\r\n            likeUserComment.setLikeUserId(loginUser.getId());\r\n            commentDtos = comments.stream().map(comment -> {\r\n                CommentDto commentDto = new CommentDto();\r\n                BeanUtils.copyProperties(comment,commentDto);\r\n                commentDto.setUser(userMap.get(comment.getCommentator()));\r\n                likeUserComment.setLikeCommentId(comment.getId());\r\n                int status = likeUserCommentMapper.findStatus(likeUserComment);\r\n                commentDto.setLikeStatus(status);\r\n                return commentDto;\r\n            }).collect(Collectors.toList());\r\n        }else{\r\n            commentDtos = comments.stream().map(comment -> {\r\n                CommentDto commentDto = new CommentDto();\r\n                BeanUtils.copyProperties(comment,commentDto);\r\n                commentDto.setUser(userMap.get(comment.getCommentator()));\r\n                return commentDto;\r\n            }).collect(Collectors.toList());\r\n        }\r\n        List<CommentDto> commentDtoList=new ArrayList<>();\r\n        List<CommentDto> topCommentDtos=new ArrayList<>();\r\n        List<CommentDto> notTopCommentDtos=new ArrayList<>();\r\n        commentDtos.forEach(commentDto -> {\r\n            if (commentDto.getIsTop()==1){\r\n                topCommentDtos.add(commentDto);\r\n            }else{\r\n                notTopCommentDtos.add(commentDto);\r\n            }\r\n        });\r\n        if (!topCommentDtos.isEmpty()){\r\n            commentDtoList.addAll(topCommentDtos);\r\n            commentDtoList.addAll(notTopCommentDtos);\r\n        }else{\r\n            commentDtoList.addAll(notTopCommentDtos);\r\n        }\r\n        return commentDtoList;\r\n\r\n    }', 'java', 1610097988924, 1610097988924, 2, 2, 0, 0, 1, 0);
INSERT INTO `question` VALUES (10, '2.点赞评论，问题，评论中加上作者标识（完成）', 'public List<CommentDto> listByTargetId(User loginUser,Integer id, CommentTypeEnum type) {\r\n       List<Comment> comments=commentMapper.ListById(id, type.getType());\r\n       if (comments.size()==0){\r\n           return new ArrayList<>();\r\n       }\r\n\r\n        Collections.sort(comments, new Comparator<Comment>() {\r\n            @Override\r\n            public int compare(Comment o1, Comment o2) {\r\n                return (int)(o2.getLikeCount()-o1.getLikeCount());\r\n            }\r\n        });\r\n        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());\r\n        List<Integer> userIds=new ArrayList();\r\n        userIds.addAll(commentators);\r\n\r\n        //修改处\r\n        List<User> users=new ArrayList<>();\r\n        for (Integer userId : userIds) {\r\n            User user = userMapper.findById(userId);\r\n            users.add(user);\r\n        }\r\n\r\n\r\n\r\n        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));\r\n\r\n\r\n        List<CommentDto> commentDtos;\r\n        if(loginUser!=null&&type.getType()==1){\r\n            LikeUserComment likeUserComment=new LikeUserComment();\r\n            likeUserComment.setLikeUserId(loginUser.getId());\r\n            commentDtos = comments.stream().map(comment -> {\r\n                CommentDto commentDto = new CommentDto();\r\n                BeanUtils.copyProperties(comment,commentDto);\r\n                commentDto.setUser(userMap.get(comment.getCommentator()));\r\n                likeUserComment.setLikeCommentId(comment.getId());\r\n                int status = likeUserCommentMapper.findStatus(likeUserComment);\r\n                commentDto.setLikeStatus(status);\r\n                return commentDto;\r\n            }).collect(Collectors.toList());\r\n        }else{\r\n            commentDtos = comments.stream().map(comment -> {\r\n                CommentDto commentDto = new CommentDto();\r\n                BeanUtils.copyProperties(comment,commentDto);\r\n                commentDto.setUser(userMap.get(comment.getCommentator()));\r\n                return commentDto;\r\n            }).collect(Collectors.toList());\r\n        }\r\n        List<CommentDto> commentDtoList=new ArrayList<>();\r\n        List<CommentDto> topCommentDtos=new ArrayList<>();\r\n        List<CommentDto> notTopCommentDtos=new ArrayList<>();\r\n        commentDtos.forEach(commentDto -> {\r\n            if (commentDto.getIsTop()==1){\r\n                topCommentDtos.add(commentDto);\r\n            }else{\r\n                notTopCommentDtos.add(commentDto);\r\n            }\r\n        });\r\n        if (!topCommentDtos.isEmpty()){\r\n            commentDtoList.addAll(topCommentDtos);\r\n            commentDtoList.addAll(notTopCommentDtos);\r\n        }else{\r\n            commentDtoList.addAll(notTopCommentDtos);\r\n        }\r\n        return commentDtoList;\r\n\r\n    }', 'java', 1610098261767, 1610098261767, 2, 25, 0, 0, 0, 0);
INSERT INTO `question` VALUES (11, '3.用户删除自己的评论以及问题（完成）', 'if (comment.getParentId()==0){\r\n            throw new CustomizeException(\"回复的问题不存在\");\r\n        }\r\n        if (comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){\r\n            throw new CustomizeException(\"评论类型不允许\");\r\n        }\r\n        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){\r\n         Comment dbComment= commentMapper.getById((int)comment.getParentId());\r\n         if (dbComment==null){\r\n             throw new CustomizeException(\"评论不存在\");\r\n         }\r\n         else {\r\n             Question question = questionMapper.getById((int) dbComment.getParentId());\r\n             if (question==null){\r\n                 throw new CustomizeException(\"问题不存在22\");\r\n             }\r\n             commentMapper.insert(comment);\r\n             commentMapper.updateCommentCount((int)comment.getParentId());\r\n             createNotification(comment.getCommentator(), dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT,question.getId(),dbComment.getId());\r\n         }\r\n        }\r\n        else{\r\n            Question question = questionMapper.getById((int) comment.getParentId());\r\n            if (question==null){\r\n                throw new CustomizeException(\"问题不存在\");\r\n            }\r\n            else{\r\n                commentMapper.insert(comment);\r\n                questionMapper.updateCommentCount(question.getId());\r\n\r\n                createNotification(comment.getCommentator(),question.getCreator(),commentator.getName(),question.getTitle(), NotificationTypeEnum.REPLY_QUESTION,question.getId(),question.getId());\r\n            }\r\n\r\n        }', 'java', 1610098749625, 1610098749625, 2, 171, 1, 1, 0, 1);
INSERT INTO `question` VALUES (12, 'dsd', '`Notification notification = notificationMapper.selectById(id);\r\n        if (notification == null) {\r\n            throw new CustomizeException(\"无消息\");\r\n        }\r\n        if (notification.getReceiver() != (long) user.getId()) {\r\n            throw new CustomizeException(\"错误\");\r\n        }\r\n        notification.setStatus(NotificationStatusEnum.READ.getStatus());\r\n        notificationMapper.updateById(notification);\r\n        NotificationDto notificationDto = new NotificationDto();\r\n        BeanUtils.copyProperties(notification, notificationDto);\r\n        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));\r\n        return notificationDto;`', 'java', 1610099128801, 1610099128801, 2, 1, 0, 0, 1, 0);
INSERT INTO `question` VALUES (13, '这是一个新问题', 'null', 'java', 1611211999448, 1611211999448, 6, 38, 0, 1, 0, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gmt_create` bigint(20) NULL DEFAULT NULL,
  `gmt_modified` bigint(20) NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL COMMENT '1为本地用户，2为Github用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'Github', '59522523', 'ced1bf66-98fa-4918-b04f-6b086c56fcd2', 1589089664276, 1611824306566, 'https://avatars.githubusercontent.com/u/59522523?v=4', NULL, NULL, 2);
INSERT INTO `user` VALUES (3, 'ano', '65154691', 'a5eb956e-7146-4477-b230-5c5a8c1205b1', 1589181439845, 1589538893601, 'https://avatars3.githubusercontent.com/u/65154691?v=4', NULL, NULL, 2);
INSERT INTO `user` VALUES (6, 'test248888', NULL, 'c1c8eb25-97d1-41f3-b28a-d902d17abb1c', 1611211818653, 1611736402865, 'https://avatars.githubusercontent.com/u/59522523?v=4', 'e10adc3949ba59abbe56e057f20f883e', '2208700170@qq.com', 1);

-- ----------------------------
-- Table structure for user_data
-- ----------------------------
DROP TABLE IF EXISTS `user_data`;
CREATE TABLE `user_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `user_question_like_count` int(11) NULL DEFAULT 0 COMMENT '收到的赞',
  `user_question_count` int(11) NULL DEFAULT 0 COMMENT '文章总数',
  `user_bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个签',
  `user_collect_count` int(11) NULL DEFAULT 0 COMMENT '收藏的文章总数',
  `sex` int(11) NULL DEFAULT 3 COMMENT '1为男，2为女，3为保密',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_data
-- ----------------------------
INSERT INTO `user_data` VALUES (2, 2, 3, 5, '1225', 4, 3);
INSERT INTO `user_data` VALUES (3, 3, 0, 1, NULL, 0, 3);
INSERT INTO `user_data` VALUES (4, 6, 1, 1, '1544', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
