package community.community.service;

import community.community.dto.ResultDto;
import community.community.mapper.UserMapper;
import community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author by wyc
 * @Date 2021/1/20.
 */
@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    public ResultDto verify(User user) {
        int count=userMapper.findEmail(user);
        if (count!=1){
            return ResultDto.error0f(201,"该用户未注册");
        }else{
            int count1=userMapper.verifyPassword(user);
            if (count1!=1){
                return ResultDto.error0f(202,"密码错误");
            }else{
                user.setGmtModified(System.currentTimeMillis());
                userMapper.updateToken(user);
                return ResultDto.ok0f();
            }
        }
    }

}
