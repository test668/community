package community.community.controller;

import community.community.dto.CommentDto;
import community.community.dto.ResultDto;
import community.community.mapper.CommentMapper;
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
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto,
                       HttpServletRequest request
                       ){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDto.error0f(2002,"未登录");
        }
        Comment comment=new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModifity(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentMapper.insert(comment);
        Map<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("message","成功");
        return objectObjectHashMap;
    }
}
