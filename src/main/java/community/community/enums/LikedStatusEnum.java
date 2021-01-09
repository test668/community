package community.community.enums;

import lombok.Getter;

/**
 * @Author by wyc
 * @Date 2021/1/7.
 */

@Getter
public enum LikedStatusEnum {
    LIKE(1, "点赞"),
    UNLIKE(0, "取消点赞/未点赞"),
    ;

    private Integer status;

    private String msg;

    LikedStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}

