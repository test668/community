package community.community.mapper;

import community.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("Insert into comment (parent_id,content,type,gmt_create,gmt_modifity,commentator) values (#{parentId},#{content},#{type},#{gmtCreate},#{gmtModifity},#{commentator})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{parentId} and status=0")
    Comment getById(@Param(value = "parentId")Integer parentId);

    @Select("select * from comment where parent_id=#{id} and type=#{type} and status=0")
    List<Comment> ListById(@Param(value = "id")Integer id, @Param(value = "type")Integer type);

    @Update("update comment set comment_count=comment_count+1 where id=#{parentId} and status=0")
    void updateCommentCount(@Param(value = "parentId")Integer parentId);

    @Update("update comment set like_count=#{likeCount},gmt_modifity=#{gmtModifity} where id=#{id} and status=0")
    void updateLikeCount(Comment comment);

    @Select("select * from comment where id=#{id} and status=0")
    Comment getCommentById(Comment comment);

    @Update("update comment set status=#{status},gmt_modifity=#{gmtModifity} where id=#{id}")
    void deleteComment(Comment comment);

    @Update("update comment set is_top=#{isTop},gmt_modifity=#{gmtModifity} where id=#{id} and status=0")
    void topComment(Comment comment);

}
