package community.community.controller;

import community.community.dto.ResultDto;
import community.community.dto.UserDto;
import community.community.enums.UserTypeEnum;
import community.community.mapper.UserMapper;
import community.community.model.User;
import community.community.service.RegisterService;
import community.community.util.VerifyCodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Author by wyc
 * @Date 2021/1/18.
 */
@Controller
public class RegisterController {

    @Autowired
    private VerifyCodeUtil verifyCodeUtil;

    @Autowired
    private RegisterService registerService;


    @ResponseBody
    @PostMapping("/register/sendEmail")
    public ResultDto sendEmail(@RequestBody HashMap<String,Object> email, HttpServletRequest request){
        String toEmail=(String)email.get("email");
        ResultDto resultDto = verifyCodeUtil.sendEmail(toEmail, request);
        return resultDto;
    }

    @ResponseBody
    @PostMapping("/register")
    public ResultDto register(@RequestBody UserDto userDto, HttpServletRequest request){
        userDto.setType(UserTypeEnum.LOCAL_USER.getType());
        ResultDto resultDto = verifyCodeUtil.verifyCode(userDto.getVerifyCode(), userDto.getEmail(), request);
        if (resultDto.getCode()==200){
                userDto.setGmtCreate(System.currentTimeMillis());
                userDto.setGmtModified(System.currentTimeMillis());
                String token = UUID.randomUUID().toString();
                userDto.setToken(token);
                ResultDto resultDto1=registerService.registerUser(userDto);
                return resultDto1;
        }else {
            return resultDto;
        }
    }

}
