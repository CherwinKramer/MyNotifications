package nl.ckramer.mynotifications.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.objectbox.Box;
import nl.ckramer.mynotifications.Adapter.NoteAdapter;
import nl.ckramer.mynotifications.Entity.Note;
import nl.ckramer.mynotifications.R;
import nl.ckramer.mynotifications.Util.ObjectBox;

public class NoteFragment extends Fragment implements NoteAdapter.OnItemListener{

    private static final String TAG = "NoteFragment";

    Context mContext;
    NoteAdapter mNoteAdapter;
    RecyclerView mRecyclerView;

    public NoteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        mContext = view.getContext();

        Box<Note> noteBox = ObjectBox.get().boxFor(Note.class);
        List<Note> noteList = noteBox.getAll();

        RecyclerView recyclerView = view.findViewById(R.id.note_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NoteAdapter(noteList, this));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, layoutManager.getOrientation()));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: Item clicked with position " + position);
    }

    @Override
    public void onItemLongClick(int position) {
        Log.d(TAG, "onItemClick: Item long clicked with position " + position);
    }

}
