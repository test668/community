package community.community.controller;

import community.community.dto.PaginationDto;
import community.community.dto.ResultDto;
import community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "6") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "type", defaultValue = "1") String type
    ) {
        PaginationDto pagination = questionService.List(search, page, size, type);
        if (pagination == null) {
            //以后修改
        } else {
            model.addAttribute("pagination", pagination);
            model.addAttribute("search", search);
            model.addAttribute("type",type);
        }
        return "index";
    }

//    @ResponseBody
//    @PostMapping("/index/selectType")
//    public ResultDto selectType(Model model, @RequestBody HashMap<String,String> type) {
//        try {
//            String search="";
//            int page=1,size=5;
//
//            String selectType=type.get("type");
//            PaginationDto pagination = questionService.List(search, page, size, selectType);
//            if (pagination == null) {
//                //以后修改
//            } else {
//                model.addAttribute("pagination", pagination);
//                model.addAttribute("search", search);
//                model.addAttribute("type",type);
//            }
//            return ResultDto.ok0f();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultDto.error0f(203,"执行异常");
//        }
//    }


}
