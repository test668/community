package community.community.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了你的问题"),
    REPLY_COMMENT(2, "回复了你的评论"),
    LIKE_COMMENT(3,"点赞了你的评论"),
    LIKE_QUESTION(4,"点赞了你的问题");
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
