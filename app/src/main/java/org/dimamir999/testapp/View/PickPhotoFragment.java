package org.dimamir999.testapp.View;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dimamir999.testapp.R;

/**
 * Created by dimamir999 on 19.01.16.
 */
public class PickPhotoFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_picker, container, false);
    }
}
