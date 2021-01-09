package community.community.mapper;

import community.community.model.LikeUserComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author by wyc
 * @Date 2021/1/7.
 */

@Mapper
public interface LikeUserCommentMapper {
    @Insert("Insert into like_user_comment (like_user_id,like_comment_id,status,gmt_create,gmt_modifity) values (#{likeUserId},#{likeCommentId},#{status},#{gmtCreate},#{gmtModifity})")
    void insert(LikeUserComment likeUserComment);


    @Select("Select * from like_user_comment where like_user_id=#{likeUserId} and like_comment_id=#{likeCommentId}")
    LikeUserComment findIsUpdate(LikeUserComment likeUserComment);

    @Update("update like_user_comment set status=#{status},gmt_modifity=#{gmtModifity} where id=#{id}")
    void updateStatus(LikeUserComment likeUserComment);

    @Select("select IFNULL(MAX(status),0) as status from like_user_comment where like_user_id=#{likeUserId} and like_comment_id=#{likeCommentId}")
    int findStatus(LikeUserComment likeUserComment);
}
