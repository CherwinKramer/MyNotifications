package nl.ckramer.mynotifications.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.objectbox.annotation.Entity;

@Entity
public class Notification extends BaseEntity implements Parcelable {

    private String title;
    private String body;
    private String location;
    private Date date;
    private Date notifyDate;
    private boolean finished;

    public Notification() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Date notifyDate) {
        this.notifyDate = notifyDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.location);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeLong(this.notifyDate != null ? this.notifyDate.getTime() : -1);
        dest.writeLong(this.getId());
        dest.writeLong(this.getCreatedDate() != null ? this.getCreatedDate().getTime() : -1);
        dest.writeInt(this.finished ? 1 : 0);
    }

    protected Notification(Parcel in) {
        this.title = in.readString();
        this.body = in.readString();
        this.location = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        long tmpNotifyDate = in.readLong();
        this.notifyDate = tmpNotifyDate == -1 ? null : new Date(tmpNotifyDate);
        this.setId(in.readLong());
        long tmpCreatedDate = in.readLong();
        this.setCreatedDate(tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate));
        this.finished = in.readInt() == 1;
    }

    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel source) {
            return new Notification(source);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
