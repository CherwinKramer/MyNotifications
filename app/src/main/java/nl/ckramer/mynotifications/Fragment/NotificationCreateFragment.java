package nl.ckramer.mynotifications.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import nl.ckramer.mynotifications.R;

public class NotificationCreateFragment extends Fragment {

    public NotificationCreateFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_create, container, false);

        String[] reminders = new String[] {"15 Minutes", "1 Hour", "6 Hours", "1 Day"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, reminders);

        AutoCompleteTextView editTextFilledExposedDropdown = view.findViewById(R.id.notification_reminderSpinner);
        editTextFilledExposedDropdown.setAdapter(adapter);

        return view;
    }
}
