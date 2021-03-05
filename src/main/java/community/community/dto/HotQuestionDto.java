package community.community.dto;

import community.community.model.Question;
import lombok.Data;

/**
 * @Author by wyc
 * @Date 2021/3/5.
 */

@Data
public class HotQuestionDto {
    private Question question;
    private int rank;
}
