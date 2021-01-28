package community.community.task;

import community.community.mapper.QuestionMapper;
import community.community.model.Question;
import community.community.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author by wyc
 * @Date 2021/1/28.
 */
@Slf4j
public class ViewCountTask extends QuartzJobBean {

    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RedisService redisService;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("ViewCountTask-------",simpleDateFormat.format(new Date()));

        HashMap<Integer, Integer> allViewForRedis = redisService.getAllViewForRedis();
        if (!allViewForRedis.isEmpty()){
            Set<Map.Entry<Integer, Integer>> entries = allViewForRedis.entrySet();
            Iterator iterator=entries.iterator();
            while(iterator.hasNext()){
                Map.Entry entry= (Map.Entry) iterator.next();
                Question question=new Question();
                question.setId((int)entry.getKey());
                question.setViewCount((int)entry.getValue());
                questionMapper.updateView(question);
            }
        }


    }
}
