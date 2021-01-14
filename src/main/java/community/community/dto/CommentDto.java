package community.community.dto;

import community.community.model.User;
import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private long parentId;
    private long parentId2;
    private Integer commentator;
    private long gmtCreate;
    private long gmtModifity;
    private long likeCount;
    private long dislikeCount;
    private String content;
    private Integer type;
    private Integer commentCount;
    private User user;
    private Integer likeStatus;
    private Integer dislikeStatus;
    private Integer likeUserId;
    private Integer dislikeUserId;
    private Integer status;
    private Integer isTop;
}
