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

    @Autowired
    private DislikeUserCommentMapper dislikeUserCommentMapper;

    @Autowired
    private NotificationService notificationService;

    /**
     *
     * @param comment
     * @param commentator
     */
    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == 0) {
            throw new CustomizeException("回复的问题不存在");
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException("评论类型不允许");
        }
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            Comment dbComment = commentMapper.getById((int) comment.getParentId());
            String outerTitle;

            if (comment.getParentId()!=comment.getParentId2()){
                Comment dbComment2=commentMapper.getById((int)comment.getParentId2());
                if(dbComment2==null){
                    throw new CustomizeException("评论不存在");
                }else {
                    if (dbComment2.getContent().length()>10){
                        outerTitle=dbComment2.getContent().substring(0,10)+"...";
                    }else{
                        outerTitle=dbComment2.getContent();
                    }
                    String name=userMapper.findById(dbComment2.getCommentator()).getName();
                    String content="@"+name+"："+comment.getContent();
                    comment.setContent(content);
                    Question question = questionMapper.getById((int) dbComment.getParentId());
                    if (question == null) {
                        throw new CustomizeException("问题不存在22");
                    }
                    notificationService.createNotification(comment.getCommentator(), dbComment2.getCommentator(), commentator.getName(), outerTitle, NotificationTypeEnum.REPLY_COMMENT, question.getId(), dbComment2.getId());
                }
            }
            if (dbComment == null) {
                throw new CustomizeException("评论不存在");
            } else {
                Question question = questionMapper.getById((int) dbComment.getParentId());
                if (question == null) {
                    throw new CustomizeException("问题不存在22");
                }
                if (dbComment.getContent().length()>10){
                    outerTitle=dbComment.getContent().substring(0,10)+"...";
                }else{
                    outerTitle=dbComment.getContent();
                }
                commentMapper.insert(comment);
                commentMapper.updateCommentCount((int) comment.getParentId());
                notificationService.createNotification(comment.getCommentator(), dbComment.getCommentator(), commentator.getName(), outerTitle, NotificationTypeEnum.REPLY_COMMENT, question.getId(), dbComment.getId());
            }

        } else {
            Question question = questionMapper.getById((int) comment.getParentId());
            if (question == null) {
                throw new CustomizeException("问题不存在");
            } else {
                commentMapper.insert(comment);
                questionMapper.updateCommentCount(question.getId());
                notificationService.createNotification(comment.getCommentator(), question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId(), question.getId());
            }
        }
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
            DislikeUserComment dislikeUserComment=new DislikeUserComment();
            dislikeUserComment.setDislikeUserId(loginUser.getId());
            commentDtos = comments.stream().map(comment -> {
                CommentDto commentDto = new CommentDto();
                BeanUtils.copyProperties(comment, commentDto);
                commentDto.setUser(userMap.get(comment.getCommentator()));
                likeUserComment.setLikeCommentId(comment.getId());
                dislikeUserComment.setDislikeCommentId(comment.getId());
                int likeStatus = likeUserCommentMapper.findStatus(likeUserComment);
                int dislikeStatus=dislikeUserCommentMapper.findStatus(dislikeUserComment);
                commentDto.setLikeStatus(likeStatus);
                commentDto.setDislikeStatus(dislikeStatus);
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
        if (commentById.getContent().length() > 10) {
            outerTitle = commentById.getContent().substring(0, 10) + "...";
        } else {
            outerTitle = commentById.getContent();
        }
        if (commentDto.getLikeStatus() != 0) {
            notificationService.createNotification(commentDto.getLikeUserId(), commentById.getCommentator(), notifityName, outerTitle, NotificationTypeEnum.LIKE_COMMENT, question.getId(), commentById.getId());
        } else {
            notificationService.deleteNotification(commentDto.getLikeUserId(), commentById.getCommentator(), NotificationTypeEnum.LIKE_COMMENT, commentById.getId());
        }

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

    public void dislikeComment(CommentDto commentDto) {
        DislikeUserComment dislikeUserComment=new DislikeUserComment();
        dislikeUserComment.setDislikeUserId(commentDto.getDislikeUserId());
        dislikeUserComment.setDislikeCommentId(commentDto.getId());
        dislikeUserComment.setStatus(commentDto.getDislikeStatus());
        DislikeUserComment dislikeUserComment1=dislikeUserCommentMapper.findIsUpdate(dislikeUserComment);
        if(dislikeUserComment1!=null){
            dislikeUserComment.setId(dislikeUserComment1.getId());
            dislikeUserComment.setGmtModifity(System.currentTimeMillis());
            dislikeUserCommentMapper.updateStatus(dislikeUserComment);
        }else{
            dislikeUserComment.setGmtCreate(System.currentTimeMillis());
            dislikeUserComment.setGmtModifity(System.currentTimeMillis());
            dislikeUserCommentMapper.insert(dislikeUserComment);
        }

        Comment comment=new Comment();
        comment.setId(commentDto.getId());
        comment.setDislikeCount(commentDto.getDislikeCount());
        comment.setGmtModifity(System.currentTimeMillis());
        commentMapper.updateDislikeCount(comment);
    }

}
