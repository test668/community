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

 Date: 08/01/2021 17:57:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 2, 2, 1589089834502, 1589089834502, 0, 'ffa', 1, 1, 0, 0);
INSERT INTO `comment` VALUES (2, 1, 2, 1589089873488, 1589089873488, 0, 'oi', 2, 0, 0, 0);
INSERT INTO `comment` VALUES (3, 4, 2, 1589092433480, 1610097130679, 0, 'ff', 1, 0, 0, 0);
INSERT INTO `comment` VALUES (4, 7, 3, 1589181458825, 1610072126378, 0, 'fdsf', 1, 2, 0, 0);
INSERT INTO `comment` VALUES (5, 4, 2, 1589181597210, 1589181597210, 0, 'fsdfadf', 2, 0, 0, 0);
INSERT INTO `comment` VALUES (6, 8, 2, 1589181880246, 1610072560331, 2, 'fsdfs', 1, 0, 0, 0);
INSERT INTO `comment` VALUES (7, 8, 2, 1589461413183, 1610072549070, 1, '你好', 1, 0, 0, 0);
INSERT INTO `comment` VALUES (8, 4, 2, 1610001721230, 1610001721230, 0, 'eff', 2, 0, 0, 0);
INSERT INTO `comment` VALUES (9, 6, 2, 1610087625574, 1610087625574, 0, '15656', 1, 0, 1, 0);
INSERT INTO `comment` VALUES (10, 6, 2, 1610089379742, 1610089379742, 0, '啊实打实大大', 1, 0, 1, 0);
INSERT INTO `comment` VALUES (11, 6, 2, 1610089389692, 1610089389692, 0, 'dada', 1, 0, 1, 0);
INSERT INTO `comment` VALUES (12, 4, 2, 1610093817095, 1610095472232, 0, '2222565656', 1, 0, 0, 0);
INSERT INTO `comment` VALUES (13, 4, 2, 1610093828096, 1610093828096, 0, 'dsdsad', 1, 0, 0, 0);
INSERT INTO `comment` VALUES (14, 4, 2, 1610093843310, 1610093855480, 0, '1111222', 1, 0, 0, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of like_user_comment
-- ----------------------------
INSERT INTO `like_user_comment` VALUES (1, 2, 6, 1, 1610005639512, 1610072560328);
INSERT INTO `like_user_comment` VALUES (2, 2, 7, 0, 1610005678778, 1610072549064);
INSERT INTO `like_user_comment` VALUES (3, 2, 4, 0, 1610005940608, 1610072126374);

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
  `flag_id` bigint(20) NULL DEFAULT NULL COMMENT '记录被评论的问题，评论，以及被点赞的评论的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (2, 'duidui', 'private UserMapper userMapper;\r\n\r\n    @Autowired\r\n    private NotificationMapper notificationMapper;\r\n\r\n    @Transactional\r\n    public void insert(Comment comment, User commentator) {\r\n        if (comment.getParentId()==0){\r\n            throw new CustomizeException(\"回复的问题不存在\");\r\n        }\r\n        if (comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){\r\n            throw new CustomizeException(\"评论类型不允许\");\r\n        }\r\n        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){\r\n         Comment dbComment= commentMapper.getById((int)comment.getParentId());\r\n         if (dbComment==null){\r\n             throw new CustomizeException(\"评论不存在\");', 'java,spring', 1589089701819, 1589089701819, 2, 10, 1, 0, 1);
INSERT INTO `question` VALUES (3, 'ada', 'dad', 'java', 1589089817051, 1589089817051, 2, 1, 0, 0, 1);
INSERT INTO `question` VALUES (4, 'hdiadja', 'fdf', 'java', 1589092419446, 1589092419446, 2, 92, 4, 0, 0);
INSERT INTO `question` VALUES (5, 'daddad', 'asdas', 'java', 1589093023437, 1589093023437, 2, 10, 0, 0, 0);
INSERT INTO `question` VALUES (6, 'ada', 'ada', 'java', 1589093031429, 1589093031429, 2, 13, 0, 0, 0);
INSERT INTO `question` VALUES (7, 'dad', 'aa', 'java', 1589093042667, 1589093042667, 2, 48, 1, 0, 0);
INSERT INTO `question` VALUES (8, 'dasdaf', 'dfdafadfasd', 'java', 1589181860905, 1589181860905, 3, 172, 2, 0, 0);
INSERT INTO `question` VALUES (9, '                            <li><a href=\"#\" onclick=\"deleteComment(this)\" th:comment-id=\"${comment.id}\" th:parent-id=\"${comment.parentId}\">删除</a></li>', 'public List<CommentDto> listByTargetId(User loginUser,Integer id, CommentTypeEnum type) {\r\n       List<Comment> comments=commentMapper.ListById(id, type.getType());\r\n       if (comments.size()==0){\r\n           return new ArrayList<>();\r\n       }\r\n\r\n        Collections.sort(comments, new Comparator<Comment>() {\r\n            @Override\r\n            public int compare(Comment o1, Comment o2) {\r\n                return (int)(o2.getLikeCount()-o1.getLikeCount());\r\n            }\r\n        });\r\n        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());\r\n        List<Integer> userIds=new ArrayList();\r\n        userIds.addAll(commentators);\r\n\r\n        //修改处\r\n        List<User> users=new ArrayList<>();\r\n        for (Integer userId : userIds) {\r\n            User user = userMapper.findById(userId);\r\n            users.add(user);\r\n        }\r\n\r\n\r\n\r\n        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));\r\n\r\n\r\n        List<CommentDto> commentDtos;\r\n        if(loginUser!=null&&type.getType()==1){\r\n            LikeUserComment likeUserComment=new LikeUserComment();\r\n            likeUserComment.setLikeUserId(loginUser.getId());\r\n            commentDtos = comments.stream().map(comment -> {\r\n                CommentDto commentDto = new CommentDto();\r\n                BeanUtils.copyProperties(comment,commentDto);\r\n                commentDto.setUser(userMap.get(comment.getCommentator()));\r\n                likeUserComment.setLikeCommentId(comment.getId());\r\n                int status = likeUserCommentMapper.findStatus(likeUserComment);\r\n                commentDto.setLikeStatus(status);\r\n                return commentDto;\r\n            }).collect(Collectors.toList());\r\n        }else{\r\n            commentDtos = comments.stream().map(comment -> {\r\n                CommentDto commentDto = new CommentDto();\r\n                BeanUtils.copyProperties(comment,commentDto);\r\n                commentDto.setUser(userMap.get(comment.getCommentator()));\r\n                return commentDto;\r\n            }).collect(Collectors.toList());\r\n        }\r\n        List<CommentDto> commentDtoList=new ArrayList<>();\r\n        List<CommentDto> topCommentDtos=new ArrayList<>();\r\n        List<CommentDto> notTopCommentDtos=new ArrayList<>();\r\n        commentDtos.forEach(commentDto -> {\r\n            if (commentDto.getIsTop()==1){\r\n                topCommentDtos.add(commentDto);\r\n            }else{\r\n                notTopCommentDtos.add(commentDto);\r\n            }\r\n        });\r\n        if (!topCommentDtos.isEmpty()){\r\n            commentDtoList.addAll(topCommentDtos);\r\n            commentDtoList.addAll(notTopCommentDtos);\r\n        }else{\r\n            commentDtoList.addAll(notTopCommentDtos);\r\n        }\r\n        return commentDtoList;\r\n\r\n    }', 'java', 1610097988924, 1610097988924, 2, 2, 0, 0, 1);
INSERT INTO `question` VALUES (10, '2.点赞评论，问题，评论中加上作者标识（完成）', 'public List<CommentDto> listByTargetId(User loginUser,Integer id, CommentTypeEnum type) {\r\n       List<Comment> comments=commentMapper.ListById(id, type.getType());\r\n       if (comments.size()==0){\r\n           return new ArrayList<>();\r\n       }\r\n\r\n        Collections.sort(comments, new Comparator<Comment>() {\r\n            @Override\r\n            public int compare(Comment o1, Comment o2) {\r\n                return (int)(o2.getLikeCount()-o1.getLikeCount());\r\n            }\r\n        });\r\n        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());\r\n        List<Integer> userIds=new ArrayList();\r\n        userIds.addAll(commentators);\r\n\r\n        //修改处\r\n        List<User> users=new ArrayList<>();\r\n        for (Integer userId : userIds) {\r\n            User user = userMapper.findById(userId);\r\n            users.add(user);\r\n        }\r\n\r\n\r\n\r\n        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));\r\n\r\n\r\n        List<CommentDto> commentDtos;\r\n        if(loginUser!=null&&type.getType()==1){\r\n            LikeUserComment likeUserComment=new LikeUserComment();\r\n            likeUserComment.setLikeUserId(loginUser.getId());\r\n            commentDtos = comments.stream().map(comment -> {\r\n                CommentDto commentDto = new CommentDto();\r\n                BeanUtils.copyProperties(comment,commentDto);\r\n                commentDto.setUser(userMap.get(comment.getCommentator()));\r\n                likeUserComment.setLikeCommentId(comment.getId());\r\n                int status = likeUserCommentMapper.findStatus(likeUserComment);\r\n                commentDto.setLikeStatus(status);\r\n                return commentDto;\r\n            }).collect(Collectors.toList());\r\n        }else{\r\n            commentDtos = comments.stream().map(comment -> {\r\n                CommentDto commentDto = new CommentDto();\r\n                BeanUtils.copyProperties(comment,commentDto);\r\n                commentDto.setUser(userMap.get(comment.getCommentator()));\r\n                return commentDto;\r\n            }).collect(Collectors.toList());\r\n        }\r\n        List<CommentDto> commentDtoList=new ArrayList<>();\r\n        List<CommentDto> topCommentDtos=new ArrayList<>();\r\n        List<CommentDto> notTopCommentDtos=new ArrayList<>();\r\n        commentDtos.forEach(commentDto -> {\r\n            if (commentDto.getIsTop()==1){\r\n                topCommentDtos.add(commentDto);\r\n            }else{\r\n                notTopCommentDtos.add(commentDto);\r\n            }\r\n        });\r\n        if (!topCommentDtos.isEmpty()){\r\n            commentDtoList.addAll(topCommentDtos);\r\n            commentDtoList.addAll(notTopCommentDtos);\r\n        }else{\r\n            commentDtoList.addAll(notTopCommentDtos);\r\n        }\r\n        return commentDtoList;\r\n\r\n    }', 'java', 1610098261767, 1610098261767, 2, 3, 0, 0, 0);
INSERT INTO `question` VALUES (11, '3.用户删除自己的评论以及问题（完成）', 'if (comment.getParentId()==0){\r\n            throw new CustomizeException(\"回复的问题不存在\");\r\n        }\r\n        if (comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){\r\n            throw new CustomizeException(\"评论类型不允许\");\r\n        }\r\n        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){\r\n         Comment dbComment= commentMapper.getById((int)comment.getParentId());\r\n         if (dbComment==null){\r\n             throw new CustomizeException(\"评论不存在\");\r\n         }\r\n         else {\r\n             Question question = questionMapper.getById((int) dbComment.getParentId());\r\n             if (question==null){\r\n                 throw new CustomizeException(\"问题不存在22\");\r\n             }\r\n             commentMapper.insert(comment);\r\n             commentMapper.updateCommentCount((int)comment.getParentId());\r\n             createNotification(comment.getCommentator(), dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT,question.getId(),dbComment.getId());\r\n         }\r\n        }\r\n        else{\r\n            Question question = questionMapper.getById((int) comment.getParentId());\r\n            if (question==null){\r\n                throw new CustomizeException(\"问题不存在\");\r\n            }\r\n            else{\r\n                commentMapper.insert(comment);\r\n                questionMapper.updateCommentCount(question.getId());\r\n\r\n                createNotification(comment.getCommentator(),question.getCreator(),commentator.getName(),question.getTitle(), NotificationTypeEnum.REPLY_QUESTION,question.getId(),question.getId());\r\n            }\r\n\r\n        }', 'java', 1610098749625, 1610098749625, 2, 2, 0, 0, 0);
INSERT INTO `question` VALUES (12, 'dsd', '`Notification notification = notificationMapper.selectById(id);\r\n        if (notification == null) {\r\n            throw new CustomizeException(\"无消息\");\r\n        }\r\n        if (notification.getReceiver() != (long) user.getId()) {\r\n            throw new CustomizeException(\"错误\");\r\n        }\r\n        notification.setStatus(NotificationStatusEnum.READ.getStatus());\r\n        notificationMapper.updateById(notification);\r\n        NotificationDto notificationDto = new NotificationDto();\r\n        BeanUtils.copyProperties(notification, notificationDto);\r\n        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));\r\n        return notificationDto;`', 'java', 1610099128801, 1610099128801, 2, 1, 0, 0, 1);

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'Github', '59522523', '54b353d8-0869-47c2-a517-738762b1c16e', 1589089664276, 1610086583238, 'https://avatars2.githubusercontent.com/u/59522523?v=4');
INSERT INTO `user` VALUES (3, 'ano', '65154691', 'a5eb956e-7146-4477-b230-5c5a8c1205b1', 1589181439845, 1589538893601, 'https://avatars3.githubusercontent.com/u/65154691?v=4');

SET FOREIGN_KEY_CHECKS = 1;
