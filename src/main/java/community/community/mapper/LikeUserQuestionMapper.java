package community.community.mapper;

import community.community.model.LikeUserQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author by wyc
 * @Date 2021/1/14.
 */
@Mapper
public interface LikeUserQuestionMapper {

    @Insert("Insert into like_user_question (like_user_id,like_question_id,status,gmt_create,gmt_modifity) values (#{likeUserId},#{likeQuestionId},#{status},#{gmtCreate},#{gmtModifity})")
    void insert(LikeUserQuestion likeUserQuestion);


    @Select("Select * from like_user_question where like_user_id=#{likeUserId} and like_question_id=#{likeQuestionId}")
    LikeUserQuestion findIsUpdate(LikeUserQuestion likeUserQuestion);

    @Update("update like_user_question set status=#{status},gmt_modifity=#{gmtModifity} where id=#{id}")
    void updateStatus(LikeUserQuestion likeUserQuestion);

    @Select("select IFNULL(MAX(status),0) as status from like_user_question where like_user_id=#{likeUserId} and like_question_id=#{likeQuestionId}")
    int findStatus(LikeUserQuestion likeUserQuestion);
}
