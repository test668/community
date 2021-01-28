package community.community.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author by wyc
 * @Date 2021/1/21.
 */
@Data
public class LoginBlackList implements Serializable {
    private Integer id;
    private String email;
    private Integer loginTime;
    private Integer status;
    private long gmtCreate;
    private long gmtModifity;
}
