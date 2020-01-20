package community.community.mapper;

import community.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    @Insert("Insert into comment (parent_id,content,type,gmt_create,gmt_modifity,commentator) values (#{parentId},#{content},#{type},#{gmtCreate},#{gmtModifity},#{commentator})")
    void insert(Comment comment);
}
