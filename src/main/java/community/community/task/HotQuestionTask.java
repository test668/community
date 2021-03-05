package community.community.task;

import community.community.model.Question;
import community.community.service.RedisService;
import community.community.util.HotQuestionUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author by wyc
 * @Date 2021/1/29.
 */
@Slf4j
public class HotQuestionTask extends QuartzJobBean {

    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RedisService redisService;

    @Autowired
    private HotQuestionUtil hotQuestionUtil;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("HotQuestionTask-------",simpleDateFormat.format(new Date()));

        List<Question> hotQuestion = hotQuestionUtil.getHotQuestion();
        if (redisService.getAllHotQuestion().isEmpty()){
            redisService.saveHotQuestion(hotQuestion);
        }else {
            redisService.deleteAllHotQuestion();
            redisService.saveHotQuestion(hotQuestion);
        }
    }
}
