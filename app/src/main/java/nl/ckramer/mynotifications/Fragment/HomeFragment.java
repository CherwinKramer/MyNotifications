package nl.ckramer.mynotifications.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import io.objectbox.Box;
import nl.ckramer.mynotifications.Entity.Note;
import nl.ckramer.mynotifications.R;
import nl.ckramer.mynotifications.Util.ObjectBox;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
