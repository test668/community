package community.community.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author by wyc
 * @Date 2021/1/12.
 */
@Data
public class CollectUserQuestion implements Serializable {
    private Integer id;
    private Integer collectUserId;
    private Integer collectQuestionId;
    private Integer status;
    private long gmtCreate;
    private long gmtModifity;
}
