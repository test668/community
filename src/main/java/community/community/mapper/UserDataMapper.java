package community.community.mapper;

import community.community.model.UserData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author by wyc
 * @Date 2021/1/25.
 */
@Mapper
public interface UserDataMapper {

    @Insert("insert into user_data(user_id) values (#{userId})")
    void insert(UserData userData);

    @Select("select * from user_data where user_id=#{userId}")
    UserData findByUserId(UserData userData);

    @Update("update user_data set user_question_like_count=user_question_like_count+1 where user_id=#{userId}")
    void incUserQuestionLikeCount(UserData userData);

    @Update("update user_data set user_question_count=user_question_count+1 where user_id=#{userId}")
    void incUserQuestionCount(UserData userData);

    @Update("update user_data set user_collect_count=user_collect_count+1 where user_id=#{userId}")
    void incUserCollectCount(UserData userData);

    @Update("update user_data set user_question_like_count=user_question_like_count-1 where user_id=#{userId}")
    void decUserQuestionLikeCount(UserData userData);

    @Update("update user_data set user_question_count=user_question_count-1 where user_id=#{userId}")
    void decUserQuestionCount(UserData userData);

    @Update("update user_data set user_collect_count=user_collect_count-1 where user_id=#{userId}")
    void decUserCollectCount(UserData userData);

    @Update("update user_data set user_bio=#{userBio},sex=#{sex} where user_id=#{userId}")
    void updateData(UserData userData);
}
