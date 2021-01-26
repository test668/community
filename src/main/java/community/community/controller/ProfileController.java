package community.community.controller;

import community.community.dto.PaginationDto;
import community.community.model.Notification;
import community.community.model.User;
import community.community.service.NotificationService;
import community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(
            @PathVariable(name = "action") String action, Model model,
            HttpServletRequest request,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size
            ){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的帖子");
            PaginationDto paginationDto = questionService.List(user.getId(), page, size);
            if (paginationDto==null){
                //以后再来补充。。。
            }else{
                model.addAttribute("pagination",paginationDto);
            }
        }else if ("replies".equals(action)){
            PaginationDto paginationDto=notificationService.List(user.getId(), page, size);
            model.addAttribute("section","replies");
            if (paginationDto==null){
                //以后再来补充。。。
            }else{
                model.addAttribute("pagination",paginationDto);
            }
            model.addAttribute("sectionName","最新回复");
        }else if ("collect".equals(action)){
            model.addAttribute("section","collect");
            model.addAttribute("sectionName","我收藏的问题");
            PaginationDto paginationDto=questionService.collectQuestionList(user.getId(),page,size);
            if (paginationDto==null){

            }else {
                model.addAttribute("pagination",paginationDto);
            }
        }
        return "profile";
    }



}
