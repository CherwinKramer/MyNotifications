package nl.ckramer.mynotifications.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.ckramer.mynotifications.R;

public class NotificationCreateFragment extends Fragment {

    public NotificationCreateFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_create, container, false);
    }
}
