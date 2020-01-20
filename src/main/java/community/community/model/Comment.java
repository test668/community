package community.community.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Comment {
    private BigInteger id;
    private long parentId;
    private Integer commentator;
    private long gmtCreate;
    private long gmtModifity;
    private long likeCount;
    private String content;
    private Integer type;
}
