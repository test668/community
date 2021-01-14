package community.community.dto;

import community.community.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModifity;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Integer likeStatus;
    private User user;
    private Integer status;
    private Integer collectStatus;
    private Integer collectCount;
}
