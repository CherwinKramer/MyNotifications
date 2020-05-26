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
import nl.ckramer.mynotifications.Entity.Note;
import nl.ckramer.mynotifications.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    private static final String TAG = "NoteAdapter";
    private List<Note> mNotes;
    private Context mContext;

    private OnItemListener mOnItemListener;

    public NoteAdapter(List<Note> notes, OnItemListener onItemListener)  {
        this.mNotes = notes;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_note, parent, false);
        return new ViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Note note = mNotes.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        MaterialTextView title, description;

        public ViewHolder(View v, OnItemListener onItemListener){
            super(v);
            title = v.findViewById(R.id.note_title);
            description = v.findViewById(R.id.note_description);

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
        return mNotes.size();
    }

    public Note getItem(int position) {
        return mNotes.get(position);
    }

    public interface OnItemListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

}