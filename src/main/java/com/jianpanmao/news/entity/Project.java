package com.jianpanmao.news.entity;

import com.jianpanmao.common.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;

public class Project extends BaseEntity implements Serializable {
    //
    private Integer projectId;

    //项目名称
    private String projectName;

    //所属区域
    private Integer areaId;

    //项目联系人
    private Integer projectContactUserId;

    //线索创建人
    private Integer projectCreateUserId;

    //业主联系人
    private String projectOwnerContact;

    //业主电话
    private String projectOwnerContactTel;

    //状态：0线索，1项目
    private Byte projectStatus;

    //创建时间
    private Date projectCreateTime;

    //更新时间
    private Date projectUpdateTime;

    private static final long serialVersionUID = 1L;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getProjectContactUserId() {
        return projectContactUserId;
    }

    public void setProjectContactUserId(Integer projectContactUserId) {
        this.projectContactUserId = projectContactUserId;
    }

    public Integer getProjectCreateUserId() {
        return projectCreateUserId;
    }

    public void setProjectCreateUserId(Integer projectCreateUserId) {
        this.projectCreateUserId = projectCreateUserId;
    }

    public String getProjectOwnerContact() {
        return projectOwnerContact;
    }

    public void setProjectOwnerContact(String projectOwnerContact) {
        this.projectOwnerContact = projectOwnerContact == null ? null : projectOwnerContact.trim();
    }

    public String getProjectOwnerContactTel() {
        return projectOwnerContactTel;
    }

    public void setProjectOwnerContactTel(String projectOwnerContactTel) {
        this.projectOwnerContactTel = projectOwnerContactTel == null ? null : projectOwnerContactTel.trim();
    }

    public Byte getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Byte projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getProjectCreateTime() {
        return projectCreateTime;
    }

    public void setProjectCreateTime(Date projectCreateTime) {
        this.projectCreateTime = projectCreateTime;
    }

    public Date getProjectUpdateTime() {
        return projectUpdateTime;
    }

    public void setProjectUpdateTime(Date projectUpdateTime) {
        this.projectUpdateTime = projectUpdateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", projectName=").append(projectName);
        sb.append(", areaId=").append(areaId);
        sb.append(", projectContactUserId=").append(projectContactUserId);
        sb.append(", projectCreateUserId=").append(projectCreateUserId);
        sb.append(", projectOwnerContact=").append(projectOwnerContact);
        sb.append(", projectOwnerContactTel=").append(projectOwnerContactTel);
        sb.append(", projectStatus=").append(projectStatus);
        sb.append(", projectCreateTime=").append(projectCreateTime);
        sb.append(", projectUpdateTime=").append(projectUpdateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}