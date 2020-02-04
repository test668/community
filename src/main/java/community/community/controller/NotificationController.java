package community.community.controller;

import community.community.dto.NotificationDto;
import community.community.dto.PaginationDto;
import community.community.enums.NotificationTypeEnum;
import community.community.model.User;
import community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(
            @PathVariable(name = "id") Integer id,
            HttpServletRequest request)
    {
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
       NotificationDto notificationDto= notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDto.getType()|| NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDto.getType()) {
            return "redirect:/question/" + notificationDto.getOuterId();
        }else {
            return "redirect:/";
        }
    }

}
