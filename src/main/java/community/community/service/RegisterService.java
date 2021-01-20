package community.community.service;

import community.community.dto.ResultDto;
import community.community.dto.UserDto;
import community.community.mapper.UserMapper;
import community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author by wyc
 * @Date 2021/1/20.
 */
@Service
public class RegisterService {

    @Autowired
    private UserMapper userMapper;

    public ResultDto registerUser(UserDto userDto) {
        User user = userMapper.findUserByEmail(userDto);
        if (user!=null){
            return ResultDto.error0f(203,"该邮箱已注册");
        }

        return null;
    }
}
