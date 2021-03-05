package community.community.mapper;

import community.community.model.Question;
import community.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("Insert into question (title,description,gmt_create,gmt_modifity,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModifity},#{creator},#{tag})")
    void create(Question question);

    @Select("Select * from question where title regexp #{search} and status=0 order by gmt_create desc limit #{offset},#{size} ")
    List<Question> List(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size,@Param(value = "search") String search);

    @Select("Select count(1) from question where title regexp #{search} and status=0")
    Integer count(@Param(value = "search")String search);

    @Select("Select * from question where creator=#{userId} and status=0 order by gmt_create desc limit #{offset},#{size}")
    List<Question> ListByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("Select count(1) from question where creator=#{userId} and status=0")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    @Select("Select * from question where id=#{id} and status=0")
    Question getById(@Param(value = "id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modifity=#{gmtModifity},tag=#{tag} where id=#{id}")
    void update(Question question);

    @Update("update question set view_count=#{viewCount} where id=#{id}")
    void updateView(Question question);

    @Update("update question set comment_count=comment_count+1 where id=#{id}")
    void updateCommentCount(@Param(value = "id") Integer id);

    @Select("select id,title,tag from question where tag regexp #{tag} and id !=#{id} and status=0 limit 5")
    List<Question> selectRelatedQuestion(Question question);

    @Update("update question set status=#{status} where id=#{id}")
    void deleteQuestion(Question question);

    @Update("update question set comment_count=comment_count-1 where id=#{id}")
    void decCommentCount(Question question);

    @Select("select id,title,tag from question where  creator=#{id} and status=0 order by gmt_create desc limit 5 ")
    List<Question> findLeastQuestion(User user);

    @Update("update question set collect_count=#{collectCount} where id=#{id} and status=0")
    void updateCollectCount(Question question);

    @Update("update question set like_count=#{likeCount} where id=#{id} and status=0")
    void updateLikeCount(Question question);

}
