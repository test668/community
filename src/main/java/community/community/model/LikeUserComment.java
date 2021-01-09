package community.community.model;

import lombok.Data;

/**
 * @Author by wyc
 * @Date 2021/1/7.
 */

@Data
public class LikeUserComment {
    private long id;
    private Integer likeUserId;
    private Integer likeCommentId;
    private Integer status;
    private long gmtCreate;
    private long gmtModifity;
}
