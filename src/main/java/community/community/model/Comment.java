package community.community.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class Comment implements Serializable {
    private Integer id;
    private long parentId;
    private long parentId2;
    private Integer commentator;
    private long gmtCreate;
    private long gmtModifity;
    private long likeCount;
    private String content;
    private Integer type;
    private Integer commentCount;
    private Integer status;
    private Integer isTop;
    private long dislikeCount;
}
