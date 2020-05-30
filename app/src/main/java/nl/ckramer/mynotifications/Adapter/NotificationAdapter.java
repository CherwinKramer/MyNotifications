package nl.ckramer.mynotifications.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import nl.ckramer.mynotifications.Entity.Notification;
import nl.ckramer.mynotifications.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private static final String TAG = "NoteAdapter";
    private List<Notification> mResultList;
    private Context mContext;

    private OnItemListener mOnItemListener;

    public NotificationAdapter(List<Notification> notificationList, OnItemListener onItemListener)  {
        this.mResultList = notificationList;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_notification, parent, false);
        return new ViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Notification notification = mResultList.get(position);
        holder.title.setText(notification.getTitle() != null ? notification.getTitle() : "");
        holder.body.setText(notification.getBody() != null ? notification.getBody() : "");
        holder.date.setText(notification.getDate() != null ? notification.getDate().toString() : "");
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        MaterialTextView title, body, date;

        public ViewHolder(View v, OnItemListener onItemListener){
            super(v);
            title = v.findViewById(R.id.notification_title);
            body = v.findViewById(R.id.notification_body);
            date = v.findViewById(R.id.notification_date);

            mOnItemListener = onItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnItemListener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "onLongClick: " + getAdapterPosition());
            mOnItemListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public Notification getItem(int position) {
        return mResultList.get(position);
    }

    public interface OnItemListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

}