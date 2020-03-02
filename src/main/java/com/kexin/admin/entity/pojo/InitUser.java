package com.kexin.admin.entity.pojo;

/**
 * 系统初始化用户,用户登陆信息
 */
public class InitUser {


//    private int _id=1;
    private String Name;//用户名

    private String avatar;//图片

    private String email;//邮件

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public int get_id() {
//        return _id;
//    }
//
//    public void set_id(int _id) {
//        this._id = _id;
//    }



    @Override
    public String toString() {
        return "InitUser{" +
                "Name:'" + Name + '\'' +
                ", avatar:'" + avatar + '\'' +
                ", email:'" + email + '\'' +
                '}';
    }
}
