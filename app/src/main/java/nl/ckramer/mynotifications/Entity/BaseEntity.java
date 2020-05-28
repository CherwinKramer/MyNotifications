package nl.ckramer.mynotifications.Entity;

import java.util.Date;

import io.objectbox.annotation.Id;

@io.objectbox.annotation.BaseEntity
public abstract class BaseEntity {

    @Id(assignable = false)
    private long id;

    private Date createdDate = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
