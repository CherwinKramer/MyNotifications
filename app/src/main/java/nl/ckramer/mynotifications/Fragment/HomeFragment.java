package nl.ckramer.mynotifications.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.objectbox.Box;
import nl.ckramer.mynotifications.Adapter.NotificationAdapter;
import nl.ckramer.mynotifications.Entity.Notification;
import nl.ckramer.mynotifications.R;
import nl.ckramer.mynotifications.Util.ObjectBox;

public class HomeFragment extends Fragment implements NotificationAdapter.OnItemListener{
    private static final String TAG = "HomeFragment";
    
    Context mContext;
    NotificationAdapter mNotificationAdapter;
    RecyclerView mNotificationRecyclerView;
    Box<Notification> mNotificationBox;

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

        mNotificationBox = ObjectBox.get().boxFor(Notification.class);
        List<Notification> notificationList = mNotificationBox.getAll();

        mNotificationRecyclerView = view.findViewById(R.id.note_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mNotificationRecyclerView.setLayoutManager(layoutManager);
        mNotificationAdapter = new NotificationAdapter(notificationList, this);
        mNotificationRecyclerView.setAdapter(mNotificationAdapter);
        mNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, layoutManager.getOrientation()));

        setHasOptionsMenu(true);

        generateNotification();
        return view;
    }

    private void generateNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "default")
                .setSmallIcon(R.drawable.ic_app)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());



    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: Item clicked with position " + position);
        navigateToCreationFragment(position);
    }

    @Override
    public void onItemLongClick(int position) {
        Notification notification = mNotificationAdapter.getItem(position);
        Log.d(TAG, "onItemClick: Item long clicked with position " + position + " and title " + notification.getTitle());

        new MaterialAlertDialogBuilder(mContext)
            .setTitle(notification.getTitle())
            .setMessage(R.string.common_delete_confirmation)
            .setPositiveButton(R.string.common_yes, (dialog, whichButton) -> {
                mNotificationAdapter.remove(position);
                Snackbar.make(getView(), R.string.common_delete_confirmed, Snackbar.LENGTH_LONG).setAction(R.string.common_undo, view -> mNotificationAdapter.add(position, notification)).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (!mNotificationAdapter.contains(notification)) {
                            mNotificationBox.remove(notification);
                        }
                    }
                }).show();
            })
            .setNegativeButton(R.string.common_no, null)
            .show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_icon) {
            navigateToCreationFragment(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToCreationFragment(Integer position) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_right,R.animator.slide_out_right,R.animator.slide_in_right,R.animator.slide_out_right);
        if(position != null) transaction.replace(R.id.fragment_content, new NotificationCreateFragment(mNotificationAdapter.getItem(position)));
        if(position == null) transaction.replace(R.id.fragment_content, new NotificationCreateFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
