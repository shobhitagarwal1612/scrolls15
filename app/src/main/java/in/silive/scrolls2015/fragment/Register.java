

package in.silive.scrolls2015.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import in.silive.scrolls2015.Config;
import in.silive.scrolls2015.R;
import in.silive.scrolls2015.network.FetchDataforLists;
import in.silive.scrolls2015.network.NetworkResponseListener;

/**
 * Created by Shobhit Agarwal on 9/24/2015.
 */
public class Register extends Fragment implements View.OnClickListener, NetworkResponseListener {
    FetchDataforLists fetchDataforLists;
    ArrayList<String> searchList;
    JSONObject jsonObject;
    View rootView;
    ProgressBar progressBar;
    public static String inProgress=Config.CHECK_GET_TEAM_DETAILS;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login_fragment, container, false);
        (rootView.findViewById(R.id.loginButton)).setOnClickListener(this);
        searchList = new ArrayList<>();
        searchList.add(Config.CHECK_IS_TEAM_VALID);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                try {
                    login();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
/*
                TeamProfileFragment fragment = new TeamProfileFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_frame, fragment, "TeamDetails").addToBackStack(null).commit();
                ft = fm.beginTransaction();
*/

                break;
        }
    }

    public void login() throws MalformedURLException, JSONException {
        jsonObject = new JSONObject();
        jsonObject.put("TeamId", ((EditText) rootView.findViewById(R.id.teamIdLogin)).getText().toString());
        jsonObject.put("Password", ((EditText) rootView.findViewById(R.id.passwordLogin)).getText().toString());
        fetchDataforLists = null;
        Log.d("Sending JSON", "0" + jsonObject);
        fetchDataforLists = new FetchDataforLists();
        fetchDataforLists.setNrl(this);
        fetchDataforLists.setJson(jsonObject);
        fetchDataforLists.setURL(Config.IS_TEAM_VALID);
        fetchDataforLists.setSearchList(searchList);
        fetchDataforLists.setType_of_request(Config.POST);
        fetchDataforLists.execute();
    }

    @Override
    public void beforeRequest() throws MalformedURLException {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postRequest(Object result) throws MalformedURLException {
        //      progressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        ArrayList<HashMap<String, String>> results = (ArrayList<HashMap<String, String>>) result;
        if (results.size() > 0) {
            if (results.get(0).get(searchList.get(0)).equalsIgnoreCase("false")) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Sorry your team does not exist")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            } else {
                TeamProfileFragment fragment = new TeamProfileFragment();
                Bundle args = new Bundle();
                args.putString("Id", ((EditText) rootView.findViewById(R.id.teamIdLogin)).getText().toString());
                fragment.setArguments(args);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_frame, fragment, "TeamDetails").addToBackStack(null).commit();
                ft = fm.beginTransaction();
            }
        }

    }
    public void getTeamDetails(){}
}

