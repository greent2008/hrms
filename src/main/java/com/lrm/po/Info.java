package com.lrm.po;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jianengxi on 2020/2/19
 */
@Entity
@Table(name = "t_info")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String responsibleFor;

    private String groupName;
    private String bossName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date onBoardingTime;

    private String companyName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornTime;

    private String degree;
    private Long workingYears;
    private String email;
    private String mobile;
    private String remark;

    @OneToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Info() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsibleFor() {
        return responsibleFor;
    }

    public void setResponsibleFor(String responsibleFor) {
        this.responsibleFor = responsibleFor;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public Date getOnBoardingTime() {
        return onBoardingTime;
    }

    public void setOnBoardingTime(Date onBoardingTime) {
        this.onBoardingTime = onBoardingTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getBornTime() {
        return bornTime;
    }

    public void setBornTime(Date bornTime) {
        this.bornTime = bornTime;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Long getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(Long workingYears) {
        this.workingYears = workingYears;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "Info{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", responsibleFor='" + responsibleFor + '\'' +
                ", groupName='" + groupName + '\'' +
                ", bossName='" + bossName + '\'' +
                ", onBoardingTime=" + onBoardingTime +
                ", companyName='" + companyName + '\'' +
                ", bornTime=" + bornTime +
                ", degree='" + degree + '\'' +
                ", workingYears=" + workingYears +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remark='" + remark + '\'' +
                ", user=" + user.getUsername() +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
