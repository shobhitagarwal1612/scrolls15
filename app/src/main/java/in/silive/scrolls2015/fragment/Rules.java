package in.silive.scrolls2015.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.silive.scrolls2015.R;

/**
 * Created by Shobhit Agarwal on 9/25/2015.
 */
public class Rules extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rules_fragment, container, false);
    }
}
