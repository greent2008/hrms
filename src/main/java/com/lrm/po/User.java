package com.lrm.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String usernameCN;
    private String password;
    private String email;

    @Lob
    @Column(columnDefinition = "text")
    private String avatar;

    private Integer type;  // 0:超级管理员用户 1:经理用户 2:组长用户 3:外包员工

    @ManyToOne
    private Group group;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameCN() {
        return usernameCN;
    }

    public void setUsernameCN(String usernameCN) {
        this.usernameCN = usernameCN;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        if (group != null) {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", usernameCN='" + usernameCN + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", type=" + type +
                    ", group=" + group.getGroupName() +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", usernameCN='" + usernameCN + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", group=" + "" +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
