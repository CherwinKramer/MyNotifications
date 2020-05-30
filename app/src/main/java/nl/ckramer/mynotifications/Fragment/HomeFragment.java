package nl.ckramer.mynotifications.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.objectbox.Box;
import nl.ckramer.mynotifications.Adapter.NoteAdapter;
import nl.ckramer.mynotifications.Adapter.NotificationAdapter;
import nl.ckramer.mynotifications.Entity.Note;
import nl.ckramer.mynotifications.Entity.Notification;
import nl.ckramer.mynotifications.R;
import nl.ckramer.mynotifications.Util.ObjectBox;

public class HomeFragment extends Fragment implements NotificationAdapter.OnItemListener{
    private static final String TAG = "HomeFragment";
    
    Context mContext;
    NotificationAdapter mNotificationAdapter;
    RecyclerView mNotificationRecyclerView;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        mContext = view.getContext();

        Box<Notification> notificationBox = ObjectBox.get().boxFor(Notification.class);
        List<Notification> notificationList = notificationBox.getAll();

        mNotificationRecyclerView = view.findViewById(R.id.note_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mNotificationRecyclerView.setLayoutManager(layoutManager);
        mNotificationAdapter = new NotificationAdapter(notificationList, this);
        mNotificationRecyclerView.setAdapter(mNotificationAdapter);
        mNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, layoutManager.getOrientation()));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: Item clicked with position " + position);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment newFragment = new NotificationCreateFragment(mNotificationAdapter.getItem(position));
        transaction.replace(R.id.fragment_content, newFragment);
        transaction.addToBackStack("notificationCreateFragment");
        transaction.commit();
    }

    @Override
    public void onItemLongClick(int position) {
        Log.d(TAG, "onItemClick: Item long clicked with position " + position);
    }

}
