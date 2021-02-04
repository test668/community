package community.community.util;

import community.community.dto.QuestionWeightDto;
import community.community.mapper.QuestionMapper;
import community.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author by wyc
 * @Date 2021/1/29.
 */
@Component
public class HotQuestionUtil {
    @Autowired
    private QuestionMapper questionMapper;

    public List<Question> getHotQuestion(){
        List<Question> questionList=new ArrayList<>();
        Integer size=100;
        Integer offset=0;
        String search=".";
        Integer count = questionMapper.count(search);
        List<QuestionWeightDto> hotQuestion=new ArrayList<>();
        if (count!=0){
            do {
                List<Question> pageQuestionList;
                pageQuestionList=questionMapper.List(offset,size,search);
                offset=offset+size;
                List<QuestionWeightDto> collect = pageQuestionList.stream().map(question -> {
                    QuestionWeightDto weight=new QuestionWeightDto();
                    Double w = question.getViewCount() * 0.5 + question.getCollectCount() * 0.3 + question.getLikeCount() * 0.2;
                    weight.setQuestionId(question.getId());
                    weight.setWeight(w);
                    return weight;
                }).collect(Collectors.toList());
                Collections.sort(collect, new Comparator<QuestionWeightDto>() {
                    @Override
                    public int compare(QuestionWeightDto o1, QuestionWeightDto o2) {
                        return (int)(o2.getWeight()-o1.getWeight());
                    }
                });
                if (collect.size()<5){
                    hotQuestion.addAll(collect);
                }else {
                    hotQuestion.addAll(collect.subList(0,5));
                }
                Collections.sort(hotQuestion, new Comparator<QuestionWeightDto>() {
                    @Override
                    public int compare(QuestionWeightDto o1, QuestionWeightDto o2) {
                        return (int)(o2.getWeight()-o1.getWeight());
                    }
                });
                if (hotQuestion.size()>5){
                    hotQuestion = hotQuestion.subList(0, 5);
                }
            }while (offset<count);
            if (!hotQuestion.isEmpty()){
                questionList=hotQuestion.stream().map(questionWeightDto -> {
                    Question question=questionMapper.getById(questionWeightDto.getQuestionId());
                    return question;
                }).collect(Collectors.toList());
            }
        }
        return questionList;
    }
}
