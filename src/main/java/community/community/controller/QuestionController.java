package community.community.controller;

import community.community.dto.CommentCreateDto;
import community.community.dto.CommentDto;
import community.community.dto.QuestionDto;
import community.community.service.CommentService;
import community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
            Model model
    ){

        QuestionDto questionDto=questionService.getById(id);
        List<CommentDto> comments=commentService.listByQuestionId(id);
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        return "question";
    }
}
