package community.community.controller;

import community.community.dto.UserDataDto;
import community.community.dto.UserDto;
import community.community.mapper.UserDataMapper;
import community.community.mapper.UserMapper;
import community.community.model.User;
import community.community.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author by wyc
 * @Date 2021/1/26.
 */
@Controller
public class ProfileSettingController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDataMapper userDataMapper;

    @GetMapping("/profileSetting")
    public String getProfileSetting(HttpServletRequest request, Model model){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null||user.getType()==2){
            return "redirect:/";
        }
        UserDataDto userDataDto=new UserDataDto();
        User user1=userMapper.findById(user.getId());
        userDataDto.setUser(user1);
        UserData userData=new UserData();
        userData.setUserId(user.getId());
        UserData userData1=userDataMapper.findByUserId(userData);
        userDataDto.setUserData(userData1);
        model.addAttribute("userDataDto",userDataDto);
        return "profileSetting";
    }
}
