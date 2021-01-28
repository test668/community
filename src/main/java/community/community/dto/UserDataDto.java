package community.community.dto;


import lombok.Data;

/**
 * @Author by wyc
 * @Date 2021/1/26.
 */
@Data
public class UserDataDto {
    private Integer id;
    private Integer userId;
    private Integer userQuestionLikeCount;
    private Integer userQuestionCount;
    private String userBio;
    private Integer userCollectCount;
    private Integer sex;
    private String name;
    private String avatarUrl;
    private String email;
}
