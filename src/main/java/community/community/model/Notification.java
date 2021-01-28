package community.community.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Notification implements Serializable {
    private Integer id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private long flagId;
}
