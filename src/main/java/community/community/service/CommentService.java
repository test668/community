package community.community.service;


import community.community.dto.CommentDto;
import community.community.enums.CommentTypeEnum;
import community.community.exception.CustomizeException;
import community.community.mapper.CommentMapper;
import community.community.mapper.QuestionMapper;
import community.community.mapper.UserMapper;
import community.community.model.Comment;
import community.community.model.Question;
import community.community.model.User;
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

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId()==0){
            throw new CustomizeException("回复的问题不存在");
        }
        if (comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException("评论类型不允许");
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
         Comment dbComment= commentMapper.getById((int)comment.getParentId());
         if (dbComment==null){
             throw new CustomizeException("评论不存在");
         }
         else {
             commentMapper.insert(comment);
             commentMapper.updateCommentCount((int)comment.getParentId());
         }
        }
        else{
            Question question = questionMapper.getById((int) comment.getParentId());
            if (question==null){
                throw new CustomizeException("问题不存在");
            }
            else{
                commentMapper.insert(comment);
                questionMapper.updateCommentCount(question.getId());
            }

        }
    }


    public List<CommentDto> listByTargetId(Integer id, CommentTypeEnum type) {
       List<Comment> comments=commentMapper.ListById(id, type.getType());

       if (comments.size()==0){
           return new ArrayList<>();
       }

        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return (int)(o2.getLikeCount()-o1.getLikeCount());
            }
        });
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList();
        userIds.addAll(commentators);

        //修改处
        List<User> users=new ArrayList<>();
        for (Integer userId : userIds) {
            User user = userMapper.findById(userId);
            users.add(user);
        }



        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;

    }
}
