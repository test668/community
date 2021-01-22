package community.community.mapper;

import community.community.model.LoginBlackList;
import community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author by wyc
 * @Date 2021/1/21.
 */
@Mapper
public interface LoginBlackListMapper {

    @Select("select count(1) from login_black_list where email=#{email} and status=1")
    int isBlackList(User user);

    @Select("select login_time from login_black_list where email=#{email}")
    int findLoginTime(User user);

    @Select("select count(1) from login_black_list where email=#{email}")
    int updateOrCreat(User user);

    @Insert("insert into login_black_list (email,gmt_create,gmt_modifity,status) values (#{email},#{gmtCreate},#{gmtModifity},#{status})")
    void create(LoginBlackList loginBlackList);

    @Update("update login_black_list set login_time=login_time+1 where email=#{email}")
    void incTime(User user);

    @Update("update login_black_list set status=1 where email=#{email}")
    void updateStatus(User user);

    @Update("update login_black_list set status=0,login_time=0 where email=#{email}")
    void removeBlackList(LoginBlackList loginBlackList);
}
