package community.community.mapper;

import community.community.model.DislikeUserComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author by wyc
 * @Date 2021/1/11.
 */
@Mapper
public interface DislikeUserCommentMapper {

    @Select("select IFNULL(MAX(status),0) as status from dislike_user_comment where dislike_user_id=#{dislikeUserId} and dislike_comment_id=#{dislikeCommentId}")
    int findStatus(DislikeUserComment dislikeUserComment);

    @Select("Select * from dislike_user_comment where dislike_user_id=#{dislikeUserId} and dislike_comment_id=#{dislikeCommentId}")
    DislikeUserComment findIsUpdate(DislikeUserComment dislikeUserComment);

    @Update("update dislike_user_comment set status=#{status},gmt_modifity=#{gmtModifity} where id=#{id}")
    void updateStatus(DislikeUserComment dislikeUserComment);

    @Insert("Insert into dislike_user_comment (dislike_user_id,dislike_comment_id,status,gmt_create,gmt_modifity) values (#{dislikeUserId},#{dislikeCommentId},#{status},#{gmtCreate},#{gmtModifity})")
    void insert(DislikeUserComment dislikeUserComment);
}
