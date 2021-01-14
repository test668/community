package community.community.mapper;

import community.community.model.CollectUserQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author by wyc
 * @Date 2021/1/12.
 */

@Mapper
public interface CollectUserQuestionMapper {

    @Select("Select * from collect_user_question where collect_user_id=#{collectUserId} and collect_question_id=#{collectQuestionId}")
    CollectUserQuestion findIsUpdate(CollectUserQuestion collectUserQuestion);

    @Update("update collect_user_question set status=#{status},gmt_modifity=#{gmtModifity} where id=#{id}")
    void updateStatus(CollectUserQuestion collectUserQuestion);


    @Insert("Insert into collect_user_question (collect_user_id,collect_question_id,status,gmt_create,gmt_modifity) values (#{collectUserId},#{collectQuestionId},#{status},#{gmtCreate},#{gmtModifity})")
    void insert(CollectUserQuestion collectUserQuestion);

    @Select("select IFNULL(MAX(status),0) as status from collect_user_question where collect_user_id=#{collectUserId} and collect_question_id=#{collectQuestionId}")
    int findStatus(CollectUserQuestion collectUserQuestion);

    @Select("Select count(1) from collect_user_question where collect_user_id=#{userId} and status=1")
    Integer collectCount(@Param(value = "userId") Integer userId);

    @Select("select * from collect_user_question where collect_user_id=#{userId} and status=1 order by gmt_create desc limit #{offset},#{size}")
    List<CollectUserQuestion> listByCollectUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,@Param(value = "size") Integer size);
}
