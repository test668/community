package community.community.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Question implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModifity;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Integer status;
    private Integer collectCount;
}
