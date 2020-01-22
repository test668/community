package community.community.controller;

import community.community.dto.CommentCreateDto;
import community.community.dto.ResultDto;
import community.community.exception.CustomizeException;
import community.community.model.Comment;
import community.community.model.User;
import community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request
                       ){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDto.error0f(2002,"未登录");
        }
        if (commentCreateDto==null||commentCreateDto.getContent().isEmpty()){
            return ResultDto.error0f(2003,"评论不能为空");
        }
        Comment comment=new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModifity(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDto.ok0f();
    }
}
