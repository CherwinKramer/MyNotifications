package nl.ckramer.mynotifications.Fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import nl.ckramer.mynotifications.Activity.MainActivity;
import nl.ckramer.mynotifications.Adapter.NotificationAdapter;
import nl.ckramer.mynotifications.Entity.Notification;
import nl.ckramer.mynotifications.Model.PushNotification;
import nl.ckramer.mynotifications.R;
import nl.ckramer.mynotifications.Util.NotificationUtil;
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

        return view;
    }

    private void generateNotification(Notification notification) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("notification", notification);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PushNotification pushNotification = new PushNotification(getString(R.string.notification), getString(R.string.notification_description), mContext, NotificationUtil.NOTIFICATION_CHANNEL, pendingIntent);
        NotificationUtil.generateNotification(pushNotification);
    }

    @Override
    public void onItemClick(int position) {
//        generateNotification(mNotificationAdapter.getItem(position));
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
