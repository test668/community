package community.community.controller;

import community.community.dto.CommentDto;
import community.community.dto.QuestionDto;
import community.community.dto.ResultDto;
import community.community.enums.CommentTypeEnum;
import community.community.model.Question;
import community.community.model.User;
import community.community.service.CommentService;
import community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(
            @PathVariable(name = "id") Integer id,
            Model model,
            HttpServletRequest request
    ){
        User user = (User) request.getSession().getAttribute("user");
        QuestionDto questionDto=questionService.getById(id);
        List<QuestionDto> relateQuestions=questionService.selectQuestions(questionDto);
        List<CommentDto> comments=commentService.listByTargetId(user,id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relateQuestions",relateQuestions);
        return "question";
    }

    @ResponseBody
    @RequestMapping(value = "/question/delete",method = RequestMethod.POST)
    public ResultDto delete(@RequestBody Question question){
        try {
            question.setStatus(1);
            questionService.deleteQuestion(question);
            return ResultDto.error0f(200,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error0f(2003, "执行异常");
        }
    }
}
