package community.community.service;

import community.community.dto.PaginationDto;
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

    public PaginationDto List(Integer page, Integer size) {
        PaginationDto paginationDto = new PaginationDto();

        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if(totalCount % size==0){
            totalPage=totalCount /size;
        }
        else{
            totalPage=totalCount /size+1;
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        paginationDto.setPagination(totalPage,page);

        Integer offset=size*(page-1);

        List<Question> questions = questionMapper.List(offset,size);
        List<QuestionDto> questionDtoList=new ArrayList<>();

        for (Question question : questions) {
           User user=userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);

        }
        paginationDto.setQuestions(questionDtoList);


        return paginationDto;
    }

    public PaginationDto List(Integer userId, Integer page, Integer size) {
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.countByUserId(userId);
        Integer totalPage;
        if(totalCount % size==0){
            totalPage=totalCount /size;
        }
        else{
            totalPage=totalCount /size+1;
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        paginationDto.setPagination(totalPage,page);

        Integer offset=size*(page-1);

        List<Question> questions = questionMapper.ListByUserId(userId,offset,size);
        List<QuestionDto> questionDtoList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);

        }
        paginationDto.setQuestions(questionDtoList);


        return paginationDto;
    }

    public QuestionDto getById(Integer id) {
        Question question= questionMapper.getById(id);
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user=userMapper.findById(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModifity(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            question.setGmtModifity(System.currentTimeMillis());
            questionMapper.update(question);
        }

    }
}
