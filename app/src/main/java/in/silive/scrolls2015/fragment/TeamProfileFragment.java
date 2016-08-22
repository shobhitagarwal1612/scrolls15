package in.silive.scrolls2015.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nononsenseapps.filepicker.FilePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import in.silive.scrolls2015.Config;
import in.silive.scrolls2015.R;
import in.silive.scrolls2015.network.FetchDataforLists;
import in.silive.scrolls2015.network.NetworkResponseListener;

/**
 * Created by kone on 25/9/15.
 */
public class TeamProfileFragment extends Fragment implements NetworkResponseListener {
    View rootView;
    ArrayList<String> searchList = new ArrayList<>();
    FetchDataforLists fetchDataforLists;
    String filearray = "";
    JSONObject jsonObject = new JSONObject();
    ProgressBar progressBar;
    public String TAG = this.getClass().getName();

    public static String inProgress = Config.CHECK_GET_TEAM_DETAILS;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.team_details_fragment, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        Bundle args = getArguments();
        if (args != null) {
            try {
                searchList.clear();
                searchList.add("TotalMembers");
                searchList.add("Member1RegId");
                searchList.add("Member2RegId");
                searchList.add("Member3RegId");
                searchList.add("Mem1Name");
                searchList.add("Mem2Name");
                searchList.add("Mem3Name");
                searchList.add("TeamId");
                searchList.add("TeamName");
                searchList.add("SynopsisName");
                searchList.add("SynopsisAvailable");
                String id = args.getString("Id");
                Log.d("Id", "" + id);
                jsonObject = new JSONObject();
                fetchDataforLists = new FetchDataforLists();
                fetchDataforLists.setNrl(this);
                fetchDataforLists.setURL(Config.GET_TEAM_STUDENTS + id);
                fetchDataforLists.setType_of_request(Config.GET);
                fetchDataforLists.setJson(jsonObject);
                fetchDataforLists.setSearchList(searchList);
                fetchDataforLists.execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        return rootView;
    }

    private void showFileChooser() {
        Intent i = new Intent(getActivity(), FilePickerActivity.class);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, Config.FILE_SELECT_CODE);

    }

    /* @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (requestCode) {
             case Config.FILE_SELECT_CODE:
                 // Get the Uri of the selected file
                 Uri uri = data.getData();
                 Log.d(TAG, "File Uri: " + data.getDataString());
                 // Get the path
                    byte[] filearray= getByteArray(data.getDataString());
                 Log.d("ByteaAraay",filearray.toString());
                 break;
         }
         super.onActivityResult(requestCode, resultCode, data);
     }
    */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.FILE_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                // For JellyBean and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                            // Do something with the URI
                            Log.d("URI in JB", uri.toString());
                        }
                    }
                    // For Ice Cream Sandwich
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra
                            (FilePickerActivity.EXTRA_PATHS);

                    if (paths != null) {
                        for (String path : paths) {
                            Uri uri = Uri.parse(path);
                            // Do something with the URI
                            Log.d("URI in KITKAT", path);
                        }
                    }
                }

            } else {
                Uri uri = data.getData();
                // Do something with the URI
                Log.d("URI Outside Kitkat", uri.toString());
                filearray = getByteArray(uri.getPath());
                try {
                    uploadfile();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Log.d("FileArray",filearray.toString());
            }
        }
    }
    //1045 1046 new 1037
    private void uploadfile() throws MalformedURLException, JSONException {
        Log.d("ByteArray", filearray);
        inProgress = "UPLOAD_FILE";
        ArrayList<String> searchList = new ArrayList<>();
        fetchDataforLists = new FetchDataforLists();
        fetchDataforLists.setURL(Config.UPLOAD_FILE);
        jsonObject.put("DomainName", "Civil Engineering");
        jsonObject.put("TopicName", "Evolution of Skycrapers");
        jsonObject.put("TeamId", "1036");
        jsonObject.put("FileName", "");
        jsonObject.put("FileArray", filearray);
        fetchDataforLists.setJson(jsonObject);
        fetchDataforLists.setType_of_request(Config.POST);
        fetchDataforLists.setNrl(this);
        fetchDataforLists.setSearchList(searchList);
        fetchDataforLists.execute();
    }
    //1007
    public String getByteArray(String path) {

        FileInputStream fileInputStream = null;

        File file = new File(path);
        String bytearray = "";
        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String filesdata=Base64.encodeToString(bFile,Base64.DEFAULT);;
        return (filesdata);
    }

    @Override
    public void beforeRequest() throws MalformedURLException {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postRequest(Object result) throws MalformedURLException {
        progressBar.setVisibility(View.GONE);
        ArrayList<HashMap<String, String>> resultsList = (ArrayList<HashMap<String, String>>) result;

        if (inProgress.equals(Config.CHECK_GET_TEAM_DETAILS)) {
            if (resultsList.size() > 0) {
                Log.d("Came iside", "Came inside" + resultsList.get(0).get(searchList.get(1)));
                ((TextView) rootView.findViewById(R.id.team)).setText(resultsList.get(0).get(searchList.get(8)));
                ((TextView) rootView.findViewById(R.id.id)).setText(resultsList.get(0).get(searchList.get(7)));
                ((TextView) rootView.findViewById(R.id.teamleaderName)).setText(resultsList.get(0).get(searchList.get(1)));
                ((TextView) rootView.findViewById(R.id.teamleaderNameid)).setText(resultsList.get(0).get(searchList.get(4)));

                ((TextView) rootView.findViewById(R.id.otherMembersmembertwo)).setText(resultsList.get(0).get(searchList.get(2)));
                ((TextView) rootView.findViewById(R.id.otherMembersmembertwo)).setText(resultsList.get(0).get(searchList.get(5)));
                if (resultsList.get(0).get(searchList.get(0)).equals("3")) {

                    rootView.findViewById(R.id.otherMembersmemberthree).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.otherMembersmemberthreeid).setVisibility(View.VISIBLE);

                    ((TextView) rootView.findViewById(R.id.otherMembersmemberthree)).setText(resultsList.get(0).get(searchList.get(3)));
                    ((TextView) rootView.findViewById(R.id.otherMembersmemberthreeid)).setText(resultsList.get(0).get(searchList.get(6)));
                } else if (resultsList.get(0).get(searchList.get(0)).equals("2")) {
                    rootView.findViewById(R.id.otherMembersmemberthree).setVisibility(View.GONE);
                    rootView.findViewById(R.id.otherMembersmemberthreeid).setVisibility(View.GONE);

                }

            }
        } else if (inProgress.equals("UPLOAD_FILE")) {
            Toast.makeText(getActivity(), "" + fetchDataforLists.getResponsecode(), Toast.LENGTH_SHORT).show();
        }
    }
}
