package community.community.util;

import community.community.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author by wyc
 * @Date 2021/1/20.
 */
@Component
public class VerifyCodeUtil {

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

    @Value("${spring.mail.username}")
    private String myEmail;

    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 发送验证码邮件
     * @param email
     * @param request
     * @return
     */
    public ResultDto sendEmail(String email , HttpServletRequest request){

        HttpSession session = request.getSession();
        //验证码
        String verCode = VerCodeGenerateUtil.getVerCode();
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(date);
        Map<String,String> map = new HashMap<>();
        map.put("code",verCode);
        map.put("email",email);
        //验证码和邮箱，一起放入session中
        session.setAttribute("verCode",map);
        Map<String,String> codeMap = (Map<String,String>) session.getAttribute("verCode");
        //创建计时线程池，到时间自动移除验证码
        try {
            scheduledExecutorService.schedule(new Thread(()->{
                if(email.equals(codeMap.get("email"))){
                    session.removeAttribute("verCode");
                }
            }), 5*60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //以下为发送邮件部分
        MimeMessage mimeMessage = null;
        MimeMessageHelper helper = null;
        try {
            mimeMessage = mailSender.createMimeMessage();
            helper= new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("账号验证码");
            //后面的ture为支持识别html标签
            helper.setText("<h3>\n" +
                    "\t<span style=\"font-size:16px;\">亲爱的用户：</span> \n" +
                    "</h3>\n" +
                    "<p>\n" +
                    "\t<span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:14px;\">&nbsp; <span style=\"font-size:16px;\">&nbsp;&nbsp;您好！您正在进行邮箱验证，本次请求的验证码为：<span style=\"font-size:24px;color:#FF0000;\"> "+verCode+"</span>,本验证码5分钟内有效，请在5分钟内完成验证。（请勿泄露此验证码）如非本人操作，请忽略该邮件。(这是一封自动发送的邮件，请不要直接回复）</span></span>\n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "\t<span style=\"background-color:#FFFFFF;font-size:16px;color:#000000;\"><span style=\"color:#000000;font-size:16px;background-color:#FFFFFF;\"> \n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "\t<span style=\"background-color:#FFFFFF;font-size:14px;\"><span style=\"color:#FF9900;font-size:18px;\"><span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;\"><span style=\"font-size:16px;color:#000000;background-color:#FFFFFF;\">"+time+"</span><span style=\"font-size:18px;color:#000000;background-color:#FFFFFF;\"></span></span></span></span> \n" +
                    "</p>",true);
            //收件人
            helper.setTo(email);
            //发送方
            helper.setFrom(myEmail);//发送邮件
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResultDto.error0f(201,"发送失败");
        }

        return  ResultDto.error0f(200,"发送成功");
    }

    /**
     * 校验验证码是否正确
     * @param verCode
     * @param email1
     * @param request
     * @return
     */
    public ResultDto verifyCode(String verCode,String email1, HttpServletRequest request){
        HttpSession session=request.getSession();
        Map<String,String> codeMap = (Map<String,String>)session.getAttribute("verCode");
        String code = null;
        String email = null;
        try {
            code = codeMap.get("code");
            email = codeMap.get("email");
        } catch (Exception e) {
            //验证码过期，或未找到  ---验证码无效
            return  ResultDto.error0f(201,"验证码无效");
        }
        //验证码判断
        if (!verCode.toUpperCase().equals(code) || !email1.equals(email)) {
            return ResultDto.error0f(202,"验证码错误");
        }
        //验证码使用完后session删除
        session.removeAttribute("verCode");
        return ResultDto.ok0f();

    }
}
