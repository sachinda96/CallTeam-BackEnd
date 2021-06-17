package com.callteam.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author sachinda
 */
@Entity
@Table(name = "SPORTPOOLDETAILS")
public class SportPoolDetailsEntity {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 10)
    private String status;

    @Column(length = 50)
    private String createBy;

    private Date createDate;

    @Column(length = 50)
    private String updateBy;

    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "sportpool_id")
    private SportPoolEntity sportPoolEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public SportPoolEntity getSportPoolEntity() {
        return sportPoolEntity;
    }

    public void setSportPoolEntity(SportPoolEntity sportPoolEntity) {
        this.sportPoolEntity = sportPoolEntity;
    }
}
