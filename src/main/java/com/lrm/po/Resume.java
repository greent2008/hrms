package com.lrm.po;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jianengxi on 2020/2/19
 */
@Entity
@Table(name = "t_resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornTime;

    private Long workingYears;

    private String degree;
    private String mobile;
    private String email;

    @Lob
    @Column(columnDefinition = "text")
    private String selfEvaluation;

    @Lob
    @Column(columnDefinition = "text")
    private String workExperience;

    @Lob
    @Column(columnDefinition = "text")
    private String projectExperience;

    @Lob
    @Column(columnDefinition = "text")
    private String educationExperience;

    @Lob
    @Column(columnDefinition = "text")
    private String certificate;

    @Lob
    @Column(columnDefinition = "text")
    private String languageSkill;

    @Lob
    @Column(columnDefinition = "text")
    private String professionalSkill;

    @Lob
    @Column(columnDefinition = "text")
    private String honor;

    @Lob
    @Column(columnDefinition = "text")
    private String careerGoal;

    @OneToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Resume() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBornTime() {
        return bornTime;
    }

    public void setBornTime(Date bornTime) {
        this.bornTime = bornTime;
    }

    public Long getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(Long workingYears) {
        this.workingYears = workingYears;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getProjectExperience() {
        return projectExperience;
    }

    public void setProjectExperience(String projectExperience) {
        this.projectExperience = projectExperience;
    }

    public String getEducationExperience() {
        return educationExperience;
    }

    public void setEducationExperience(String educationExperience) {
        this.educationExperience = educationExperience;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getLanguageSkill() {
        return languageSkill;
    }

    public void setLanguageSkill(String languageSkill) {
        this.languageSkill = languageSkill;
    }

    public String getProfessionalSkill() {
        return professionalSkill;
    }

    public void setProfessionalSkill(String professionalSkill) {
        this.professionalSkill = professionalSkill;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getCareerGoal() {
        return careerGoal;
    }

    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
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
        return "Resume{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", bornTime=" + bornTime +
                ", workingYears=" + workingYears +
                ", degree='" + degree + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", selfEvaluation='" + selfEvaluation + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", projectExperience='" + projectExperience + '\'' +
                ", educationExperience='" + educationExperience + '\'' +
                ", certificate='" + certificate + '\'' +
                ", languageSkill='" + languageSkill + '\'' +
                ", professionalSkill='" + professionalSkill + '\'' +
                ", honor='" + honor + '\'' +
                ", careerGoal='" + careerGoal + '\'' +
                ", user=" + user +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
