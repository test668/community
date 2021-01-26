package community.community.service;

import community.community.mapper.UserDataMapper;
import community.community.mapper.UserMapper;
import community.community.model.User;
import community.community.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDataMapper userDataMapper;

    public void createOrUpdate(User user) {
        User dbUser=userMapper.findByAccountId(user.getAccountId());
        if (dbUser==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            UserData userData=new UserData();
            dbUser=userMapper.findByAccountId(user.getAccountId());
            userData.setUserId(dbUser.getId());
            userDataMapper.insert(userData);
        }else {
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
