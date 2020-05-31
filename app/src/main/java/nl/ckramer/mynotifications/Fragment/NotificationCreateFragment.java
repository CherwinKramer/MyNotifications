package nl.ckramer.mynotifications.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import io.objectbox.Box;
import nl.ckramer.mynotifications.Activity.IconStatus;
import nl.ckramer.mynotifications.Entity.Notification;
import nl.ckramer.mynotifications.R;
import nl.ckramer.mynotifications.Util.DateHelper;
import nl.ckramer.mynotifications.Util.ObjectBox;

public class NotificationCreateFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "NotificationCreateFragm";

    TextInputEditText mTitleEditText;
    TextInputEditText mBodyEditText;
    TextInputEditText mLocationEditText;
    TextInputEditText mDateEditText;
    TextInputEditText mReminderEditText;

    TextInputEditText mCurrentEditText;
    Calendar mCurrentCalendar;

    Notification mNotification;

    public NotificationCreateFragment() {

    }

    public NotificationCreateFragment(Notification notification) {
        mNotification = notification;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_create, container, false);
//        ((IconStatus) getActivity()).showBack();
        initializeViews(view);
        initializeData();
        setHasOptionsMenu(true);
        return view;
    }

    private void initializeViews(View view) {
        mTitleEditText = view.findViewById(R.id.notification_title);
        mBodyEditText = view.findViewById(R.id.notification_body);
        mLocationEditText = view.findViewById(R.id.notification_location);

        mDateEditText = view.findViewById(R.id.notification_date);
        initializeDatePicker(mDateEditText);

        mReminderEditText = view.findViewById(R.id.notification_reminder_date);
        initializeDatePicker(mReminderEditText);
    }

    private void initializeData() {
        if(mNotification != null) {
            mTitleEditText.setText(mNotification.getTitle());
            mBodyEditText.setText(mNotification.getBody());
            mLocationEditText.setText(mNotification.getLocation());
            mDateEditText.setText(DateHelper.formatDate(DateHelper.datetimeFormat, mNotification.getDate()));
            mReminderEditText.setText(DateHelper.formatDate(DateHelper.datetimeFormat, mNotification.getNotifyDate()));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_icon) {
            if(validateAction()) {
                saveAction();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateAction() {
        boolean error = false;

        if(mTitleEditText == null || mTitleEditText.getText().toString() == null || mTitleEditText.getText().toString().isEmpty()) {
            mTitleEditText.setError(getResources().getString(R.string.common_validation_empty_error));
        }
        if(mDateEditText == null || !DateHelper.isDate(mDateEditText.getText().toString())) {
            mDateEditText.setError(getResources().getString(R.string.common_validation_empty_error));
        }
        return !error;
    }

    private void saveAction() {
        if(mNotification == null) {
            mNotification = new Notification();
        }

        mNotification.setTitle(mTitleEditText.getText().toString());
        mNotification.setBody(mBodyEditText.getText().toString());
        mNotification.setLocation(mLocationEditText.getText().toString());
        mNotification.setDate(DateHelper.parseDate(DateHelper.datetimeFormat, mDateEditText.getText().toString()));
        mNotification.setNotifyDate(DateHelper.parseDate(DateHelper.datetimeFormat, mReminderEditText.getText().toString()));

        Box<Notification> notificationBox = ObjectBox.get().boxFor(Notification.class);
        if(notificationBox != null) notificationBox.put(mNotification);
        this.getFragmentManager().popBackStack();
    }

    private void initializeDatePicker(final TextInputEditText editText) {
        editText.setOnClickListener(view -> {
            mCurrentCalendar = Calendar.getInstance();
            mCurrentEditText = editText;
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    NotificationCreateFragment.this,
                    mCurrentCalendar.get(Calendar.YEAR),
                    mCurrentCalendar.get(Calendar.MONTH),
                    mCurrentCalendar.get(Calendar.DAY_OF_MONTH));
            dpd.show(getFragmentManager(), "DatePickerDialog");
        });
    }

    private void initializeTimePicker(DatePickerDialog datePickerDialog) {
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                NotificationCreateFragment.this,
                mCurrentCalendar.get(Calendar.HOUR_OF_DAY),
                mCurrentCalendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
        datePickerDialog.dismiss();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mCurrentCalendar.set(Calendar.YEAR, year);
        mCurrentCalendar.set(Calendar.MONTH, monthOfYear);
        mCurrentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        initializeTimePicker(view);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mCurrentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCurrentCalendar.set(Calendar.MINUTE, minute);

        String dateString = DateHelper.formatDate(DateHelper.datetimeFormat, mCurrentCalendar.getTime());
        mCurrentEditText.setText(dateString);
        view.dismiss();
    }

}
