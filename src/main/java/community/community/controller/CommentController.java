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
import javax.xml.ws.Response;
import java.util.List;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.error0f(2002, "未登录");
        }
        if (commentCreateDto == null || commentCreateDto.getContent().isEmpty()) {
            return ResultDto.error0f(2003, "评论不能为空");
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModifity(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment, user);
        return ResultDto.ok0f();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDto<List> comments(@PathVariable(name = "id") Integer id,HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<CommentDto> commentDtos = commentService.listByTargetId(user,id, CommentTypeEnum.COMMENT);
        return ResultDto.ok0f(commentDtos);
    }

    @ResponseBody
    @RequestMapping(value = "/comment/likeComment",method = RequestMethod.POST)
    public ResultDto likeComment(@RequestBody CommentDto commentDto,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.error0f(2002, "未登录");
        }
        try {
            commentDto.setUser(user);
            commentDto.setLikeUserId(user.getId());
            commentService.likeComment(commentDto);
            return ResultDto.ok0f();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error0f(2003, "执行异常");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/comment/deleteComment",method = RequestMethod.POST)
    public ResultDto deleteComment(@RequestBody Comment comment){
        comment.setStatus(1);
        comment.setGmtModifity(System.currentTimeMillis());
        try {
            commentService.deleteComment(comment);
            return ResultDto.ok0f();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error0f(203,"执行异常");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/comment/topComment",method = RequestMethod.POST)
    public ResultDto topComment(@RequestBody Comment comment){
        comment.setIsTop(1);
        comment.setGmtModifity(System.currentTimeMillis());
        try {
            commentService.topComment(comment);
            return ResultDto.ok0f();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error0f(203,"执行异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/comment/cancelTopComment",method = RequestMethod.POST)
    public ResultDto cancelTopComment(@RequestBody Comment comment){
        comment.setIsTop(0);
        comment.setGmtModifity(System.currentTimeMillis());
        try {
            commentService.topComment(comment);
            return ResultDto.ok0f();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error0f(203,"执行异常");
        }
    }
}
