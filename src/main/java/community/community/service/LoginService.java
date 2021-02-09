package community.community.service;

import community.community.dto.ResultDto;
import community.community.mapper.LoginBlackListMapper;
import community.community.mapper.UserMapper;
import community.community.model.LoginBlackList;
import community.community.model.User;
import community.community.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author by wyc
 * @Date 2021/1/20.
 */
@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginBlackListMapper loginBlackListMapper;

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

    public ResultDto verify(User user) {
        int isBlackList=loginBlackListMapper.isBlackList(user);
        if (isBlackList!=0){
            return ResultDto.error0f(203,"密码输入错误次数到达上限，3分钟后重试");
        }
        int count=userMapper.findEmail(user);
        if (count!=1){
            return ResultDto.error0f(201,"该用户未注册");
        }else{
            int count1=userMapper.verifyPassword(user);
            LoginBlackList loginBlackList=new LoginBlackList();
            if (count1!=1){
                int updateOrCreate=loginBlackListMapper.updateOrCreat(user);
                if (updateOrCreate==0){
                    loginBlackList.setStatus(0);
                    loginBlackList.setEmail(user.getEmail());
                    loginBlackList.setGmtCreate(System.currentTimeMillis());
                    loginBlackList.setGmtModifity(System.currentTimeMillis());
                    loginBlackListMapper.create(loginBlackList);
                }else {
                    //防止暴力破解
                    loginBlackListMapper.incTime(user);
                    int timeCount=loginBlackListMapper.findLoginTime(user);
                    if (timeCount==5){
                        loginBlackListMapper.updateStatus(user);
                        try {
                            executorService.schedule(new Thread(()->{
                                LoginBlackList loginBlackList1=new LoginBlackList();
                                loginBlackList1.setEmail(user.getEmail());
                                loginBlackList1.setGmtModifity(System.currentTimeMillis());
                                loginBlackListMapper.removeBlackList(loginBlackList1);
                            }), 3*60, TimeUnit.SECONDS);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return ResultDto.error0f(202,"密码错误");
            }else{
                user.setGmtModified(System.currentTimeMillis());
                loginBlackList.setEmail(user.getEmail());
                loginBlackList.setGmtModifity(System.currentTimeMillis());
                loginBlackListMapper.removeBlackList(loginBlackList);
                userMapper.updateToken(user);
                return ResultDto.ok0f();
            }
        }
    }
}
