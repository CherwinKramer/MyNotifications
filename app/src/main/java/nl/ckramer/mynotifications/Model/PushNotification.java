package nl.ckramer.mynotifications.Model;

import android.app.PendingIntent;
import android.content.Context;

public class PushNotification {
    private String title;
    private String content;
    private Context context;
    private int importance;
    private String channelId;

    PendingIntent pendingIntent;

    public PushNotification(String title, String content, Context context, int importance, String channelId) {
        this.title = title;
        this.content =  content;
        this.context = context;
        this.importance = importance;
        this.channelId = channelId;
    }

    public PushNotification(String title, String content, Context context, String channelId) {
        this.title = title;
        this.content =  content;
        this.context = context;
        this.channelId = channelId;
    }

    public PushNotification(String title, String content, Context context, String channelId, PendingIntent pendingIntent) {
        this.title = title;
        this.content =  content;
        this.context = context;
        this.channelId = channelId;
        this.pendingIntent = pendingIntent;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }
}


