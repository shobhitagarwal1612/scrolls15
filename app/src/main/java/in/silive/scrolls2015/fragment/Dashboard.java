package in.silive.scrolls2015.fragment;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import in.silive.scrolls2015.adapter.DashboardAdapter;
import in.silive.scrolls2015.R;
import in.silive.scrolls2015.models.ImageItem;

/**
 * Created by Shobhit Agarwal on 9/23/2015.
 */
public class Dashboard extends Fragment {

    public static ImageView arrow;
    public static TextView tvSwipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard_fragment, container, false);
        arrow = (ImageView) rootView.findViewById(R.id.arrow);
        tvSwipe = (TextView) rootView.findViewById(R.id.tv_swipe);
        ListView dashboard = (ListView) rootView.findViewById(R.id.grid_menu);
        DashboardAdapter adapter = new DashboardAdapter(getActivity(), R.layout.grid_item_layout, getData());
        dashboard.setAdapter(adapter);
        dashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                CategoryPaper frag = new CategoryPaper();
                frag.setArguments(bundle);
                getParentFragment().getFragmentManager().beginTransaction().replace(R.id.content_frame, frag, "category paper")
                        .addToBackStack(null).setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).commit();
            }
        });
        SlidingUpPanelLayout slidingUpPanelLayout = Home.slidingPaneLayout;
        slidingUpPanelLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {
                arrow.animate().rotation(v * 180);
            }

            @Override
            public void onPanelCollapsed(View view) {
                tvSwipe.setText("Swipe up to view the topics");
            }

            @Override
            public void onPanelExpanded(View view) {
                tvSwipe.setText("Swipe down to collapse");
            }

            @Override
            public void onPanelAnchored(View view) {

            }

            @Override
            public void onPanelHidden(View view) {

            }
        });

        return rootView;

    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.category_icons);
        String[] titles = getResources().getStringArray(R.array.category_titles);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, titles[i]));
        }
        return imageItems;
    }
}
