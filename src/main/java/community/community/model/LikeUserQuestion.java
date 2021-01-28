package community.community.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author by wyc
 * @Date 2021/1/14.
 */
@Data
public class LikeUserQuestion implements Serializable {
    private long id;
    private Integer likeUserId;
    private Integer likeQuestionId;
    private Integer status;
    private long gmtCreate;
    private long gmtModifity;
}
