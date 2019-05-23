package com.itheima.domain;

/**
 * @Auther: sean
 * @Date: 2019/3/21 17:18
 * @Description: 用户实体类
 */
public class User {
    private String uid;         // 用户id
    private String username;    // 用户名
    private String password;    // 密码
    private String name;        // 真实姓名
    private String email;       // 邮箱
    private String birthday;    // 生日
    private String gender;      // 性别
    private int state;          // 激活状态  0未激活 1已激活
    private String code;        // 激活码
    private String remark;      // 扩展字段

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", state=" + state +
                ", code='" + code + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
