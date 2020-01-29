package community.community.mapper;

import community.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("Insert into comment (parent_id,content,type,gmt_create,gmt_modifity,commentator) values (#{parentId},#{content},#{type},#{gmtCreate},#{gmtModifity},#{commentator})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{parentId}")
    Comment getById(@Param(value = "parentId")Integer parentId);

    @Select("select * from comment where parent_id=#{id} and type=#{type}")
    List<Comment> ListById(@Param(value = "id")Integer id, @Param(value = "type")Integer type);

    @Update("update comment set comment_count=comment_count+1 where id=#{parentId}")
    void updateCommentCount(@Param(value = "parentId")Integer parentId);
}
