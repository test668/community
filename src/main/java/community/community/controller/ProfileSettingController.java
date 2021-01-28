package community.community.controller;

import community.community.dto.ResultDto;
import community.community.dto.UserDataDto;
import community.community.dto.UserDto;
import community.community.mapper.UserDataMapper;
import community.community.mapper.UserMapper;
import community.community.model.User;
import community.community.model.UserData;
import community.community.util.VerifyCodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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

    @Autowired
    private VerifyCodeUtil verifyCodeUtil;

    @GetMapping("/profileSetting")
    public String getProfileSetting(HttpServletRequest request, Model model){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null||user.getType()==2){
            return "redirect:/";
        }
        UserDataDto userDataDto=new UserDataDto();
        User user1=userMapper.findById(user.getId());
        userDataDto.setAvatarUrl(user1.getAvatarUrl());
        userDataDto.setName(user1.getName());
        userDataDto.setEmail(user1.getEmail());
        UserData userData=new UserData();
        userData.setUserId(user.getId());
        UserData userData1=userDataMapper.findByUserId(userData);
        userDataDto.setSex(userData1.getSex());
        userDataDto.setUserBio(userData1.getUserBio());
        model.addAttribute("userDataDto",userDataDto);
        return "profileSetting";
    }


    @PostMapping("/profileSetting/data")
    @ResponseBody
    public ResultDto saveData(@RequestBody UserDataDto userDataDto,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null||user.getType()==2){
            return ResultDto.error0f(201,"未登录");
        }else {
            User user1=new User();
            user1.setAvatarUrl(userDataDto.getAvatarUrl());
            user1.setId(user.getId());
            user1.setName(userDataDto.getName());
            userMapper.updateAvatarUrlAndName(user1);
            UserData userData=new UserData();
            userData.setSex(userDataDto.getSex());
            userData.setUserBio(userDataDto.getUserBio());
            userData.setUserId(user.getId());
            userDataMapper.updateData(userData);
        }
        return ResultDto.ok0f();
    }

    @PostMapping("/profileSetting/verifyOld")
    @ResponseBody
    public ResultDto verifyOldEmail(@RequestBody UserDto userDto,HttpServletRequest request){
        ResultDto resultDto = verifyCodeUtil.verifyCode(userDto.getVerifyCode(), userDto.getEmail(), request);
        return resultDto;
    }

    @PostMapping("//profileSetting/updateEmail")
    @ResponseBody
    public ResultDto updateEmail(@RequestBody UserDto userDto,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        user.setEmail(userDto.getEmail());
        int i = userMapper.verifyEmail(user);
        if (i!=0){
            return ResultDto.error0f(201,"该邮箱已被其他账号绑定");
        }else {
            userMapper.updateEmail(user);
            return ResultDto.ok0f();
        }
    }

    @PostMapping("/profileSetting/updatePassword")
    @ResponseBody
    public ResultDto updatePassword(@RequestBody HashMap<String,Object> password,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return ResultDto.error0f(201,"未登录");
        }
        user.setPassword((String)password.get("oldPassword"));
        int i = userMapper.verifyPassword(user);
        if (i==0){
            return ResultDto.error0f(202,"原密码错误");
        }else {
            user.setPassword((String)password.get("newPassword"));
            UserDto userDto=new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userMapper.updatePassword(userDto);
            return ResultDto.ok0f();
        }
    }

}
