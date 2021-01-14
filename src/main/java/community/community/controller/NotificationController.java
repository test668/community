package community.community.controller;

import community.community.dto.NotificationDto;
import community.community.dto.ResultDto;
import community.community.enums.NotificationTypeEnum;
import community.community.model.Notification;
import community.community.model.User;
import community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
/**
 * @Author by wyc
 * @Date 2021/1/11.
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(
            @PathVariable(name = "id") Integer id,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDto notificationDto = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDto.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDto.getType()
                || NotificationTypeEnum.LIKE_COMMENT.getType()==notificationDto.getType()
        ) {
            return "redirect:/question/" + notificationDto.getOuterId();
        } else {
            return "redirect:/";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/notification/deleteUnread",method= RequestMethod.POST)
    public ResultDto deleteUnread(@RequestBody Notification notification){
        notification.setStatus(1);
        try {
            notificationService.deleteAllUnread(notification);
            return ResultDto.ok0f();

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error0f(203,"执行异常");
        }

    }

}
