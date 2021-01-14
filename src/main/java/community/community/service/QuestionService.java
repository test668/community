package community.community.service;

import com.mysql.cj.util.StringUtils;
import community.community.dto.PaginationDto;
import community.community.dto.QuestionDto;
import community.community.enums.NotificationTypeEnum;
import community.community.exception.CustomizeException;
import community.community.mapper.CollectUserQuestionMapper;
import community.community.mapper.LikeUserQuestionMapper;
import community.community.mapper.QuestionMapper;
import community.community.mapper.UserMapper;
import community.community.model.CollectUserQuestion;
import community.community.model.LikeUserQuestion;
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

    @Autowired
    private CollectUserQuestionMapper collectUserQuestionMapper;

    @Autowired
    private LikeUserQuestionMapper likeUserQuestionMapper;

    @Autowired
    private NotificationService notificationService;

    public PaginationDto List(String search, Integer page, Integer size,String type) {
        if (!(StringUtils.isNullOrEmpty(search))) {
            String[] searchs = search.split(" ");
            search = Arrays.stream(searchs).collect(Collectors.joining("|"));
        } else {
            search = ".";
        }
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.count(search);
        if (totalCount == 0) {
            return new PaginationDto();
        } else {
            Integer totalPage;
            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }
            if (page < 1) {
                page = 1;
            }
            if (page > totalPage) {
                page = totalPage;
            }
            paginationDto.setPagination(totalPage, page);
            Integer offset = size * (page - 1);


            List<Question> questions = questionMapper.List(offset, size, search);

            if("2".equals(type)){
                Collections.sort(questions, new Comparator<Question>() {
                    @Override
                    public int compare(Question o1, Question o2) {

                        return (o2.getCommentCount()-o1.getCommentCount());
                    }
                });
            }else if ("3".equals(type)){
                Collections.sort(questions, new Comparator<Question>() {
                    @Override
                    public int compare(Question o1, Question o2) {
                        return (o2.getCollectCount()-o1.getCollectCount());
                    }
                });
            }

            List<QuestionDto> questionDtoList = new ArrayList<>();

            for (Question question : questions) {
                User user = userMapper.findById(question.getCreator());
                QuestionDto questionDto = new QuestionDto();
                BeanUtils.copyProperties(question, questionDto);
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
        if (totalCount == 0) {
            return new PaginationDto();
        } else {
            Integer totalPage;
            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }
            if (page < 1) {
                page = 1;
            }
            if (page > totalPage) {
                page = totalPage;
            }
            paginationDto.setPagination(totalPage, page);

            Integer offset = size * (page - 1);

            List<Question> questions = questionMapper.ListByUserId(userId, offset, size);
            List<QuestionDto> questionDtoList = new ArrayList<>();

            for (Question question : questions) {
                User user = userMapper.findById(question.getCreator());
                QuestionDto questionDto = new QuestionDto();
                BeanUtils.copyProperties(question, questionDto);
                questionDto.setUser(user);
                questionDtoList.add(questionDto);

            }
            paginationDto.setData(questionDtoList);


            return paginationDto;
        }
    }

    public QuestionDto getById(Integer id, User user) {
        Question question = questionMapper.getById(id);
        if (question == null) {
            throw new CustomizeException("问题不存在");
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        if(user!=null){
            CollectUserQuestion collectUserQuestion = new CollectUserQuestion();
            collectUserQuestion.setCollectQuestionId(id);
            collectUserQuestion.setCollectUserId(user.getId());
            int collectStatus = collectUserQuestionMapper.findStatus(collectUserQuestion);
            questionDto.setCollectStatus(collectStatus);

            LikeUserQuestion likeUserQuestion=new LikeUserQuestion();
            likeUserQuestion.setLikeQuestionId(id);
            likeUserQuestion.setLikeUserId(user.getId());
            int likeStatus=likeUserQuestionMapper.findStatus(likeUserQuestion);
            questionDto.setLikeStatus(likeStatus);
        }
        User user1 = userMapper.findById(question.getCreator());
        questionDto.setUser(user1);
        return questionDto;
    }





    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModifity(question.getGmtCreate());
            questionMapper.create(question);
        } else {
            question.setGmtModifity(System.currentTimeMillis());
            questionMapper.update(question);
        }

    }

    public void incView(Integer id) {
        questionMapper.updateView(id);
    }

    public List<QuestionDto> selectQuestions(QuestionDto questionDto) {
        if (StringUtils.isNullOrEmpty(questionDto.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = questionDto.getTag().split(",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTag(regexTag);

        List<Question> questions = questionMapper.selectRelatedQuestion(question);
        List<QuestionDto> questionDtos = questions.stream().map(q -> {
            QuestionDto questionDto1 = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto1);
            return questionDto1;
        }).collect(Collectors.toList());

        return questionDtos;
    }

    public void deleteQuestion(Question question) {
        questionMapper.deleteQuestion(question);
    }

    public List<QuestionDto> selectLeastQuestions(QuestionDto questionDto) {
        User user = new User();
        user.setId(questionDto.getUser().getId());
        List<Question> questions = questionMapper.findLeastQuestion(user);
        List<QuestionDto> questionDtos = questions.stream().map(question -> {
            QuestionDto questionDto1 = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto1);
            return questionDto1;
        }).collect(Collectors.toList());
        return questionDtos;
    }

    public void collectQuestion(QuestionDto questionDto) {
        CollectUserQuestion collectUserQuestion = new CollectUserQuestion();
        collectUserQuestion.setCollectUserId(questionDto.getUser().getId());
        collectUserQuestion.setCollectQuestionId(questionDto.getId());
        collectUserQuestion.setStatus(questionDto.getCollectStatus());
        CollectUserQuestion collectUserQuestion1 = collectUserQuestionMapper.findIsUpdate(collectUserQuestion);
        if (collectUserQuestion1 != null) {
            collectUserQuestion.setId(collectUserQuestion1.getId());
            collectUserQuestion.setGmtModifity(System.currentTimeMillis());
            collectUserQuestionMapper.updateStatus(collectUserQuestion);
        } else {
            collectUserQuestion.setGmtCreate(System.currentTimeMillis());
            collectUserQuestion.setGmtModifity(System.currentTimeMillis());
            collectUserQuestionMapper.insert(collectUserQuestion);
        }

        Question question = new Question();
        question.setId(questionDto.getId());
        question.setCollectCount(questionDto.getCollectCount());
        questionMapper.updateCollectCount(question);
    }

    public PaginationDto collectQuestionList(Integer userId, Integer page, Integer size) {
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = collectUserQuestionMapper.collectCount(userId);
        if (totalCount == 0) {
            return new PaginationDto();
        } else {
            Integer totalPage;
            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }
            if (page < 1) {
                page = 1;
            }
            if (page > totalPage) {
                page = totalPage;
            }
            paginationDto.setPagination(totalPage, page);

            Integer offset = size * (page - 1);

            List<CollectUserQuestion> listByCollectUserIds=collectUserQuestionMapper.listByCollectUserId(userId,offset,size);

            List<Question> questions = new ArrayList<>();
            listByCollectUserIds.forEach(listByCollectUserId->{
                Question question;
                question=questionMapper.getById(listByCollectUserId.getCollectQuestionId());
                questions.add(question);
            });
            List<QuestionDto> questionDtoList = new ArrayList<>();

            for (Question question : questions) {
                User user = userMapper.findById(question.getCreator());
                QuestionDto questionDto = new QuestionDto();
                BeanUtils.copyProperties(question, questionDto);
                questionDto.setUser(user);
                questionDtoList.add(questionDto);

            }
            paginationDto.setData(questionDtoList);


            return paginationDto;
        }
    }

    public void likeQuestion(QuestionDto questionDto) {
        LikeUserQuestion likeUserQuestion=new LikeUserQuestion();
        likeUserQuestion.setStatus(questionDto.getLikeStatus());
        likeUserQuestion.setLikeQuestionId(questionDto.getId());
        likeUserQuestion.setLikeUserId(questionDto.getUser().getId());
        LikeUserQuestion likeUserQuestion1=likeUserQuestionMapper.findIsUpdate(likeUserQuestion);
        if(likeUserQuestion1!=null){
            likeUserQuestion.setId(likeUserQuestion1.getId());
            likeUserQuestion.setGmtModifity(System.currentTimeMillis());
            likeUserQuestionMapper.updateStatus(likeUserQuestion);
        }else{
            likeUserQuestion.setGmtCreate(System.currentTimeMillis());
            likeUserQuestion.setGmtModifity(System.currentTimeMillis());
            likeUserQuestionMapper.insert(likeUserQuestion);
        }

        Question question=new Question();
        question.setLikeCount(questionDto.getLikeCount());
        question.setId(questionDto.getId());
        question.setGmtModifity(System.currentTimeMillis());
        questionMapper.updateLikeCount(question);

        Question questionById=questionMapper.getById(question.getId());
        String notifierName=questionDto.getUser().getName();
        String outerTitle = null;
        if (questionById.getTitle().length() > 10) {
            outerTitle = questionById.getTitle().substring(0, 10) + "...";
        } else {
            outerTitle = questionById.getTitle();
        }

        if (questionDto.getLikeStatus() != 0) {
            notificationService.createNotification(likeUserQuestion.getLikeUserId(), questionById.getCreator(), notifierName, outerTitle, NotificationTypeEnum.LIKE_QUESTION, question.getId(), questionById.getId());
        } else {
            notificationService.deleteNotification(likeUserQuestion.getLikeUserId(), questionById.getCreator(), NotificationTypeEnum.LIKE_QUESTION, questionById.getId());
        }


    }
}
