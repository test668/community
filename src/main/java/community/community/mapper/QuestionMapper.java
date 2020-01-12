package community.community.mapper;

import community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("Insert into question (title,description,gmt_create,gmt_modifity,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModifity},#{creator},#{tag})")
    void create(Question question);

    @Select("Select * from question")
    List<Question> List();
}
