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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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


    public List<CommentDto> listByQuestionId(Integer id) {
       List<Comment> comments=commentMapper.ListById(id,CommentTypeEnum.QUESTION.getType());
       if (comments.size()==0){
           return new ArrayList<>();
       }
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList();
        userIds.addAll(commentators);


        List<User> users=userMapper.findByCommentators(userIds);
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
