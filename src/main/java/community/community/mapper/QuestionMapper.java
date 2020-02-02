package community.community.mapper;

import community.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("Insert into question (title,description,gmt_create,gmt_modifity,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModifity},#{creator},#{tag})")
    void create(Question question);

    @Select("Select * from question order by gmt_create desc limit #{offset},#{size} ")
    List<Question> List(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("Select count(1) from question")
    Integer count();

    @Select("Select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> ListByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("Select count(1) from question where creator=#{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    @Select("Select * from question where id=#{id}")
    Question getById(@Param(value = "id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modifity=#{gmtModifity},tag=#{tag} where id=#{id}")
    void update(Question question);

    @Update("update question set view_count=view_count+1 where id=#{id}")
    void updateView(@Param(value = "id") Integer id);

    @Update("update question set comment_count=comment_count+1 where id=#{id}")
    void updateCommentCount(@Param(value = "id") Integer id);

    @Select("select id,title,tag from question where tag regexp #{tag} and id !=#{id}")
    List<Question> selectRelatedQuestion(Question question);
}
