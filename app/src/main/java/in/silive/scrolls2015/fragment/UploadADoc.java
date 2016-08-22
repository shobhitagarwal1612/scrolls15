package in.silive.scrolls2015.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.silive.scrolls2015.R;

/**
 * Created by Shobhit Agarwal on 9/24/2015.
 */
public class UploadADoc extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.upload_doc_fragment, container, false);
        rootView.findViewById(R.id.upload_docbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register fragment = new Register();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_frame, fragment, "TeamDetails").addToBackStack(null).commit();
            }
        });
        return rootView;
    }
}
