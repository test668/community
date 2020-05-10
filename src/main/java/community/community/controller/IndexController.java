package community.community.controller;

import community.community.dto.PaginationDto;
import community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search
                        ){
        PaginationDto pagination=questionService.List(search,page,size);
        if (pagination==null){
            //以后修改
        }else {
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        }
        return "index";}


}
