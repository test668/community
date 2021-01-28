package community.community.config;

import community.community.task.ViewCountTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author by wyc
 * @Date 2021/1/13.
 */
@Configuration
public class QuartzConfig {

    private static String VIEW_COUNT="ViewCountTask";

    @Bean
    public JobDetail saveViewCountDetail(){
        return JobBuilder.newJob(ViewCountTask.class)
                .withIdentity(VIEW_COUNT)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger saveViewCountTrigger(){
        SimpleScheduleBuilder simpleScheduleBuilder=SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(2)
//                .withIntervalInSeconds(60*2)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(saveViewCountDetail())
                .withIdentity(VIEW_COUNT)
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
