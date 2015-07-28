package jorgereina1986.c4q.nyc.feedster.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jorgereina1986.c4q.nyc.feedster.R;

/**
 * Created by c4q-jorgereina on 7/27/15.
 */
public class MusicFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_music, container, false);
        return fragmentView;

    }
}
