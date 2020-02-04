package community.community.controller;

import community.community.dto.CommentCreateDto;
import community.community.dto.CommentDto;
import community.community.dto.ResultDto;
import community.community.enums.CommentTypeEnum;
import community.community.model.Comment;
import community.community.model.User;
import community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        commentService.insert(comment,user);
        return ResultDto.ok0f();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List> comments(@PathVariable(name = "id") Integer id){
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDto.ok0f(commentDtos);
    }
}
