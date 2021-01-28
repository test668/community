package community.community.model;

import lombok.Data;

import java.awt.*;
import java.io.Serializable;

/**
 * @Author by wyc
 * @Date 2021/1/11.
 */
@Data
public class DislikeUserComment implements Serializable {
    private Integer id;
    private Integer dislikeUserId;
    private Integer dislikeCommentId;
    private Integer status;
    private Long gmtCreate;
    private Long gmtModifity;
}
