package community.community.dto;


import lombok.Data;

@Data
public class NotificationDto {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Long outerId;
    private Integer type;
    private long flagId;
}
