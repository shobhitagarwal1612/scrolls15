package in.silive.scrolls2015.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import in.silive.scrolls2015.Config;
import in.silive.scrolls2015.R;
import in.silive.scrolls2015.network.FetchDataforLists;
import in.silive.scrolls2015.network.NetworkResponseListener;

/**
 * Created by kone on 14/9/15.
 */
public class QueryUsFragment extends Fragment implements NetworkResponseListener {
    FetchDataforLists fetchDataforLists;
    View rootView;
    ArrayList<String> searchList = new ArrayList<>();
    JSONObject jsonObject=new JSONObject();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.query_us, container, false);
        ((Button)rootView.findViewById(R.id.submit_query)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Email", ((EditText) rootView.findViewById(R.id.email_query)).getText().toString());
                    jsonObject.put("Body", ((EditText) rootView.findViewById(R.id.message_queryus)).getText().toString());
                    fetchDataforLists = new FetchDataforLists();
                    fetchDataforLists.setURL(Config.QUERY_US);
                    fetchDataforLists.setType_of_request(Config.POST);
                    fetchDataforLists.setNrl(QueryUsFragment.this);
                    fetchDataforLists.setJson(jsonObject);
                    fetchDataforLists.setSearchList(searchList);
                    fetchDataforLists.execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    @Override
    public void beforeRequest() throws MalformedURLException {
        ((ProgressBar) rootView.findViewById(R.id.progress_bar)).setVisibility(View.VISIBLE);
    }

    @Override
    public void postRequest(Object result) throws MalformedURLException {
        ((ProgressBar) rootView.findViewById(R.id.progress_bar)).setVisibility(View.GONE);
        Toast.makeText(getActivity(),"Query Sent. We will contact as soon as possible "+fetchDataforLists.getResponsecode(),Toast.LENGTH_SHORT).show();
    }
}
