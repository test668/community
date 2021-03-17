package community.community.controller;

import community.community.dto.HotQuestionDto;
import community.community.dto.PaginationDto;
import community.community.dto.ResultDto;
import community.community.model.Question;
import community.community.service.QuestionService;
import community.community.service.RedisService;
import community.community.util.HotQuestionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotQuestionUtil hotQuestionUtil;

    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "6") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "type", defaultValue = "1") String type
    ) {
        PaginationDto pagination = questionService.List(search, page, size, type);
        List<Question> allHotQuestion = redisService.getAllHotQuestion();
        if (allHotQuestion.isEmpty()){
            allHotQuestion=hotQuestionUtil.getHotQuestion();
            redisService.saveHotQuestion(allHotQuestion);
        }
        List<HotQuestionDto> hotQuestionDtoList=new ArrayList<>();
        for (int i=0; i<allHotQuestion.size();i++){
            HotQuestionDto hotQuestionDto=new HotQuestionDto();
            hotQuestionDto.setQuestion(allHotQuestion.get(i));
            hotQuestionDto.setRank(i);
            hotQuestionDtoList.add(hotQuestionDto);
        }

        if (pagination == null) {
            //以后修改
        } else {
            model.addAttribute("pagination", pagination);
            model.addAttribute("search", search);
            model.addAttribute("type",type);
            model.addAttribute("hotQuestions",hotQuestionDtoList);
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
