package community.community.mapper;

import community.community.dto.UserDto;
import community.community.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("Insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url,type) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{type})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);

    @Select("select count(1) from user where email=#{email} and type=#{type}")
    int findEmail(User user);

    @Select("select count(1) from user where email=#{email} and password=md5(#{password}) and type=#{type}")
    int verifyPassword(User user);

    @Update("update user set token=#{token},gmt_modified=#{gmtModified} where email=#{email} and password=md5(#{password}) and type=#{type} ")
    void updateToken(User user);

    @Select("select * from user where email=#{email} and type=#{type}")
    User findUserByEmail(UserDto userDto);

    @Update("update user set password=md5(#{password}),gmt_modified=#{gmtModified} where email=#{email} and type=#{type}")
    void updatePassword(UserDto userDto);

    @Insert("Insert into user (name,email,password,token,gmt_create,gmt_modified,avatar_url,type) values(#{name},#{email},md5(#{password}),#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{type})")
    void insertLocalUser(User userRegister);
}
