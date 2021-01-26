package community.community.dto;

import community.community.model.User;
import community.community.model.UserData;
import lombok.Data;

/**
 * @Author by wyc
 * @Date 2021/1/26.
 */
@Data
public class UserDataDto {
    private UserData userData;
    private User user;
}
