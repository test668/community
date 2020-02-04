package community.community.model;

import lombok.Data;

@Data
public class Notification {
    private Integer id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}
