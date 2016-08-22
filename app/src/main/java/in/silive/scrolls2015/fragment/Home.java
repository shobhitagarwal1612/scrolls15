package in.silive.scrolls2015.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import in.silive.scrolls2015.sensor.SimulationView;
import in.silive.scrolls2015.R;

/**
 * Created by Shobhit Agarwal on 9/23/2015.
 */
public class Home extends Fragment {

    private SimulationView mSimulationView;

    Dashboard fragment;

    public static SlidingUpPanelLayout slidingPaneLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mSimulationView = new SimulationView(getActivity());

        WebView home = (WebView) rootView.findViewById(R.id.home);
        String text = "<html><body>"
                + "<p align=\"justify\">"
                + getString(R.string.about_scrolls)
                + "</p> "
                + "</body></html>";

        home.loadData(text, "text/html", "utf-8");


        fragment = new Dashboard();
        slidingPaneLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        FrameLayout main_content = (FrameLayout) rootView.findViewById(R.id.main);
        main_content.removeAllViews();
        main_content.addView(mSimulationView);
        getChildFragmentManager().beginTransaction().add(R.id.sliding_up_panel, fragment, "").commit();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimulationView.startSimulation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSimulationView.stopSimulation();
    }
}
