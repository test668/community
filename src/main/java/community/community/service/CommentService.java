package community.community.service;


import community.community.dto.CommentDto;
import community.community.enums.CommentTypeEnum;
import community.community.enums.NotificationStatusEnum;
import community.community.enums.NotificationTypeEnum;
import community.community.exception.CustomizeException;
import community.community.mapper.*;
import community.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private LikeUserCommentMapper likeUserCommentMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == 0) {
            throw new CustomizeException("回复的问题不存在");
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException("评论类型不允许");
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.getById((int) comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException("评论不存在");
            } else {
                Question question = questionMapper.getById((int) dbComment.getParentId());
                if (question == null) {
                    throw new CustomizeException("问题不存在22");
                }
                commentMapper.insert(comment);
                commentMapper.updateCommentCount((int) comment.getParentId());
                createNotification(comment.getCommentator(), dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId(), dbComment.getId());
            }
        } else {
            Question question = questionMapper.getById((int) comment.getParentId());
            if (question == null) {
                throw new CustomizeException("问题不存在");
            } else {
                commentMapper.insert(comment);
                questionMapper.updateCommentCount(question.getId());

                createNotification(comment.getCommentator(), question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId(), question.getId());
            }

        }
    }

    private void createNotification(long notifier, long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, long outerId, long flagId) {
        if (receiver == notifier) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(notifier);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notification.setFlagId(flagId);
        notificationMapper.insert(notification);
    }


    public List<CommentDto> listByTargetId(User loginUser, Integer id, CommentTypeEnum type) {
        List<Comment> comments = commentMapper.ListById(id, type.getType());
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return (int) (o2.getLikeCount() - o1.getLikeCount());
            }
        });
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList();
        userIds.addAll(commentators);

        //修改处
        List<User> users = new ArrayList<>();
        for (Integer userId : userIds) {
            User user = userMapper.findById(userId);
            users.add(user);
        }


        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        List<CommentDto> commentDtos;
        if (loginUser != null && type.getType() == 1) {
            LikeUserComment likeUserComment = new LikeUserComment();
            likeUserComment.setLikeUserId(loginUser.getId());
            commentDtos = comments.stream().map(comment -> {
                CommentDto commentDto = new CommentDto();
                BeanUtils.copyProperties(comment, commentDto);
                commentDto.setUser(userMap.get(comment.getCommentator()));
                likeUserComment.setLikeCommentId(comment.getId());
                int status = likeUserCommentMapper.findStatus(likeUserComment);
                commentDto.setLikeStatus(status);
                return commentDto;
            }).collect(Collectors.toList());
        } else {
            commentDtos = comments.stream().map(comment -> {
                CommentDto commentDto = new CommentDto();
                BeanUtils.copyProperties(comment, commentDto);
                commentDto.setUser(userMap.get(comment.getCommentator()));
                return commentDto;
            }).collect(Collectors.toList());
        }
        List<CommentDto> commentDtoList = new ArrayList<>();
        List<CommentDto> topCommentDtos = new ArrayList<>();
        List<CommentDto> notTopCommentDtos = new ArrayList<>();
        commentDtos.forEach(commentDto -> {
            if (commentDto.getIsTop() == 1) {
                topCommentDtos.add(commentDto);
            } else {
                notTopCommentDtos.add(commentDto);
            }
        });
        if (!topCommentDtos.isEmpty()) {
            commentDtoList.addAll(topCommentDtos);
            commentDtoList.addAll(notTopCommentDtos);
        } else {
            commentDtoList.addAll(notTopCommentDtos);
        }
        return commentDtoList;

    }


    public void likeComment(CommentDto commentDto) {
        LikeUserComment likeUserComment = new LikeUserComment();
        likeUserComment.setLikeUserId(commentDto.getLikeUserId());
        likeUserComment.setLikeCommentId(commentDto.getId());
        likeUserComment.setStatus(commentDto.getLikeStatus());
        LikeUserComment likeUserComment1 = likeUserCommentMapper.findIsUpdate(likeUserComment);
        if (likeUserComment1 != null) {
            likeUserComment.setId(likeUserComment1.getId());
            likeUserComment.setGmtModifity(System.currentTimeMillis());
            likeUserCommentMapper.updateStatus(likeUserComment);
        } else {
            likeUserComment.setGmtCreate(System.currentTimeMillis());
            likeUserComment.setGmtModifity(System.currentTimeMillis());
            likeUserCommentMapper.insert(likeUserComment);
        }

        Comment comment = new Comment();
        comment.setLikeCount(commentDto.getLikeCount());
        comment.setId(commentDto.getId());
        comment.setGmtModifity(System.currentTimeMillis());
        commentMapper.updateLikeCount(comment);

        Comment commentById = commentMapper.getCommentById(comment);
        Question question = questionMapper.getById((int) commentById.getParentId());
        String notifityName = commentDto.getUser().getName();
        String outerTitle = null;
        if (commentById.getContent().length() > 3) {
            outerTitle = commentById.getContent().substring(0, 3) + "...";
        } else {
            outerTitle = commentById.getContent();
        }
        if (commentDto.getLikeStatus() != 0) {
            createNotification(commentDto.getLikeUserId(), commentById.getCommentator(), notifityName, outerTitle, NotificationTypeEnum.LIKE_COMMENT, question.getId(), commentById.getId());
        } else {
            deleteNotification(commentDto.getLikeUserId(), commentById.getCommentator(), NotificationTypeEnum.LIKE_COMMENT, commentById.getId());
        }

    }

    public void deleteNotification(long notifier, long receiver, NotificationTypeEnum notificationType, long flagId) {
        Notification notification = new Notification();
        notification.setFlagId(flagId);
        notification.setNotifier(notifier);
        notification.setReceiver(receiver);
        notification.setType(notificationType.getType());
        notification.setStatus(NotificationStatusEnum.delete.getStatus());
        notificationMapper.deleteNotification(notification);
    }

    public void deleteComment(Comment comment) {
        Question question = new Question();
        question.setId((int) comment.getParentId());
        questionMapper.decCommentCount(question);
        commentMapper.deleteComment(comment);
    }

    public void topComment(Comment comment) {
        commentMapper.topComment(comment);
    }
}
