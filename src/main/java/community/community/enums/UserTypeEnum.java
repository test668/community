package community.community.enums;

/**
 * @Author by wyc
 * @Date 2021/1/18.
 */
public enum UserTypeEnum {
    LOCAL_USER(1,"本地用户"),
    GITHUB_USER(2,"Github用户");
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    UserTypeEnum(int type, String name){
        this.type = type;
        this.name = name;
    }
}
