package community.community.model;

import lombok.Data;

/**
 * @Author by wyc
 * @Date 2021/1/25.
 */
@Data
public class UserData {
    private Integer id;
    private Integer userId;
    private Integer userQuestionLikeCount;
    private Integer userQuestionCount;
    private String userBio;
    private Integer userCollectCount;
    private Integer sex;
}
