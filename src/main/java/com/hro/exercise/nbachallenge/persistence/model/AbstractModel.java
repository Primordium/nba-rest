package com.hro.exercise.nbachallenge.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractModel implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Version
    @JsonIgnore
    private Integer version;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonIgnore
    private Date creationTime;
    @UpdateTimestamp
    @JsonIgnore
    private Date updateTime;

    /**
     * @see Model#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @see Model#setId(Integer)
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AbstractModel{" +
                "id=" + id +
                ", version=" + version +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
