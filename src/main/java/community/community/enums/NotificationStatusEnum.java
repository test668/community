package community.community.enums;

public enum  NotificationStatusEnum {
    UNREAD(0),READ(1),delete(2);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
