package community.community.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author by wyc
 * @Date 2021/1/19.
 */
@Data
public class UserDto {
    private Integer id;
    private String email;
    private String name;
    private String password;
    private MultipartFile file;
    private String avatarUrl;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String verifyCode;
    private Integer type;
}
