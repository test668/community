package community.community.model;

import lombok.Data;

/**
 * @Author by wyc
 * @Date 2021/1/11.
 */
@Data
public class DislikeUserComment {
    private Integer id;
    private Integer dislikeUserId;
    private Integer dislikeCommentId;
    private Integer status;
    private Long gmtCreate;
    private Long gmtModifity;
}
