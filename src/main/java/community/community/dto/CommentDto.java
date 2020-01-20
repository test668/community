package community.community.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CommentDto {
    private long parentId;
    private String content;
    private Integer type;
}
