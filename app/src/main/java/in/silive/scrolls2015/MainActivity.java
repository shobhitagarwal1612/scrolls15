package in.silive.scrolls2015;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import in.silive.scrolls2015.adapter.SlidingPaneDrawerAdapter;
import in.silive.scrolls2015.fragment.AboutUs;
import in.silive.scrolls2015.fragment.Home;
import in.silive.scrolls2015.fragment.ImportantDates;
import in.silive.scrolls2015.fragment.QueryUsFragment;
import in.silive.scrolls2015.fragment.ReachUsFragment;
import in.silive.scrolls2015.fragment.RegistrationFragment;
import in.silive.scrolls2015.fragment.Rules;
import in.silive.scrolls2015.fragment.UploadADoc;
import in.silive.scrolls2015.models.ImageItem;

public class MainActivity extends AppCompatActivity {

    private String[] drawer_option_titles;
    Toolbar toolbar;
    private Fragment fragment;
    private PowerManager.WakeLock mWakeLock;
    private static final String TAG = "com.example.motionsensor";
    private android.support.v4.widget.SlidingPaneLayout slidingPane;
    private ImageView iv_toolbar;
    public static MainActivity ref;
    public static TextView app_title;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        iv_toolbar = (ImageView) toolbar.findViewById(R.id.iv_toolbar);
        app_title = (TextView) toolbar.findViewById(R.id.app_title);
        app_title.setText("Scrolls");
        drawer_option_titles = getResources().getStringArray(R.array.drawer_options);
        slidingPane = (android.support.v4.widget.SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        slidingPane.setParallaxDistance(200);
        ListView slidingDrawer = (ListView) findViewById(R.id.sliding_drawer);
//        selectItem(0);
        fragment = new Home();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

        iv_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingPane.isOpen()) {
                    iv_toolbar.animate().rotation(0);
                    slidingPane.closePane();
                } else {
                    iv_toolbar.animate().rotation(-90);
                    slidingPane.openPane();
                }
            }
        });

        slidingPane.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {
                iv_toolbar.animate().rotation(-90);

            }

            @Override
            public void onPanelClosed(View panel) {
                iv_toolbar.animate().rotation(0);

            }
        });

        SlidingPaneDrawerAdapter slidingPaneDrawerAdapter = new SlidingPaneDrawerAdapter(this, R.layout.drawer_list_item, getData());
        slidingDrawer.setAdapter(slidingPaneDrawerAdapter);
        slidingDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void setTitle(CharSequence title) {
        app_title.setText(title);
    }

    private void selectItem(int position) {
        final Bundle args = new Bundle();
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                fragment = new Home();
                args.putInt("Number", position);
                break;
            case 1:
                fragment = new Rules();
                args.putInt("Number", position);
                break;
            case 2:
                fragment = new ImportantDates();
                args.putInt("Number", position);
                break;
            case 3:
                fragment = new RegistrationFragment();
                args.putInt("Number", position);
                break;
            case 4:
                fragment = new UploadADoc();
                args.putInt("Number", position);
                break;
            case 5:
                fragment = new QueryUsFragment();
                args.putInt("Number", position);
                break;
            case 6:
                fragment = new ReachUsFragment();
                args.putInt("Number", position);
                break;
            case 7:
                fragment = new AboutUs();
                args.putInt("Number", position);
                break;
            default:
                break;
        }
        closeDrawer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragment.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            }
        }, 200);
        setTitle(drawer_option_titles[position]);
    }

    private void closeDrawer() {
        iv_toolbar.animate().rotation(0);
        slidingPane.closePane();

    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.drawer_icons);
        String[] titles = getResources().getStringArray(R.array.drawer_options);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, titles[i]));
        }
        return imageItems;
    }

    @Override
    public void onBackPressed() {
        if (slidingPane.isOpen())
            slidingPane.closePane();
        else {
            if (fragmentManager.getBackStackEntryCount() == 1) {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            } else
                fragmentManager.popBackStack();
        }
    }
}