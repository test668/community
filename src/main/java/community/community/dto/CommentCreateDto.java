package community.community.dto;

import lombok.Data;


@Data
public class CommentCreateDto {
    private long parentId;
    private long parentId2;
    private String content;
    private Integer type;
}
