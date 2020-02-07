package community.community.service;

import com.mysql.cj.util.StringUtils;
import community.community.dto.PaginationDto;
import community.community.dto.QuestionDto;
import community.community.exception.CustomizeException;
import community.community.mapper.QuestionMapper;
import community.community.mapper.UserMapper;
import community.community.model.Question;
import community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDto List(String search,Integer page, Integer size) {
        if (!(StringUtils.isNullOrEmpty(search))){
            String[] searchs=search.split(" ");
            search=Arrays.stream(searchs).collect(Collectors.joining("|"));
        }else {
            search=".";
        }
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.count(search);
        if (totalCount==0){
            return new PaginationDto();
        }else {
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

        

        List<Question> questions = questionMapper.List(offset,size,search);
        List<QuestionDto> questionDtoList=new ArrayList<>();

        for (Question question : questions) {
           User user=userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);

        }
        paginationDto.setData(questionDtoList);


        return paginationDto;
        }
    }

    public PaginationDto List(Integer userId, Integer page, Integer size) {
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.countByUserId(userId);
        if(totalCount==0){
            return new PaginationDto();
        }else {
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
        paginationDto.setData(questionDtoList);


        return paginationDto;
        }
    }

    public QuestionDto getById(Integer id) {
        Question question= questionMapper.getById(id);
        if (question==null){
            throw new CustomizeException("问题不存在");
        }
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

    public void incView(Integer id) {
        questionMapper.updateView(id);
    }

    public List<QuestionDto> selectQuestions(QuestionDto questionDto) {
        if(StringUtils.isNullOrEmpty(questionDto.getTag())){
            return new ArrayList<>();
        }
        String[] tags = questionDto.getTag().split(",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question=new Question();
        question.setId(questionDto.getId());
        question.setTag(regexTag);

        List<Question> questions=questionMapper.selectRelatedQuestion(question);
        List<QuestionDto> questionDtos = questions.stream().map(q -> {
            QuestionDto questionDto1 = new QuestionDto();
            BeanUtils.copyProperties(q,questionDto1);
            return questionDto1;
        }).collect(Collectors.toList());

        return questionDtos;
    }

}
