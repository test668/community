package community.community.service;

import community.community.dto.QuestionDto;
import community.community.mapper.QuestionMapper;
import community.community.mapper.UserMapper;
import community.community.model.Question;
import community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDto> List() {
        List<Question> questions = questionMapper.List();
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
           User user=userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);

        }
        return questionDtoList;
    }
}
