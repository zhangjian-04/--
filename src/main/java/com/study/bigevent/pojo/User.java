package com.study.bigevent.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class User {
    @NotNull
    private Integer id;// 主键ID
    private String username;// 用户名
    @JsonIgnore // 这个就是当输出json格式的时候，就会忽略掉，不会输出这个密码
    private String password;// 密码
    @NotEmpty(groups = { Update.class })
    @Pattern(regexp = "^\\S{1,10}", groups = { Update.class }) // 这个就是表示一字符开头，匹配一到十里面的一个
    private String nickname;// 昵称
    @Email(groups = { Update.class })
    private String email;// 邮箱
    private String userPic;// 用户头像地址
    private LocalDateTime createTime;// 创建时间
    private LocalDateTime updateTime;// 更新时间

    // 定义验证分组
    public interface Update {
    }

    public User() {
    }

    public User(Integer id, String username, String password, String nickname, String email, String userPic,
            LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.userPic = userPic;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 获取
     * 
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * 
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置
     * 
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取
     * 
     * @return userPic
     */
    public String getUserPic() {
        return userPic;
    }

    /**
     * 设置
     * 
     * @param userPic
     */
    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    /**
     * 获取
     * 
     * @return createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     * 
     * @param createTime
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     * 
     * @return updateTime
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置
     * 
     * @param updateTime
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return "User{id = " + id + ", username = " + username + ", password = " + password + ", nickname = " + nickname
                + ", email = " + email + ", userPic = " + userPic + ", createTime = " + createTime + ", updateTime = "
                + updateTime + "}";
    }
}
