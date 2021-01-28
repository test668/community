package community.community.controller;

import community.community.dto.ResultDto;
import community.community.dto.UserDto;
import community.community.enums.UserTypeEnum;
import community.community.mapper.UserMapper;
import community.community.model.User;
import community.community.service.LoginService;
import community.community.util.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

/**
 * @Author by wyc
 * @Date 2021/1/15.
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VerifyCodeUtil verifyCodeUtil;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultDto login(@RequestBody UserDto userDto, HttpServletResponse response){
        User user=new User();
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setType(UserTypeEnum.LOCAL_USER.getType());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        ResultDto resultDto=loginService.verify(user);
        if (resultDto.getCode()==200){
            Cookie cookie=new Cookie("token",token);
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
        }
        return resultDto;
    }

    @ResponseBody
    @PostMapping("/login/password")
    public ResultDto password(@RequestBody UserDto userDto, HttpServletRequest request){
        userDto.setType(UserTypeEnum.LOCAL_USER.getType());
        String verCode=userDto.getVerifyCode();
        ResultDto resultDto = verifyCodeUtil.verifyCode(verCode, userDto.getEmail(), request);
        if (resultDto.getCode()==200){
            User user = userMapper.findUserByEmail(userDto);
            //邮箱是否可用
            if (user==null){
                return ResultDto.error0f(203,"该邮箱未注册");
            }else {
                userDto.setGmtModified(System.currentTimeMillis());
                try {
                    userMapper.updatePassword(userDto);
                    return ResultDto.ok0f();
                } catch (Exception e) {
                    return ResultDto.error0f(204,"执行异常");
                }
            }
        }else {
            return resultDto;
        }
    }
}
