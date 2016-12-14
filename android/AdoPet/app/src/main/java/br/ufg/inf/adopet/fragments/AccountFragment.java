package br.ufg.inf.adopet.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.activities.MainActivity;
import br.ufg.inf.adopet.activities.NewPostActivity;
import br.ufg.inf.adopet.adapters.AccountPostsListAdapter;
import br.ufg.inf.adopet.model.Account;
import br.ufg.inf.adopet.model.Post;
import br.ufg.inf.adopet.util.AppConfig;
import br.ufg.inf.adopet.util.AppController;
import br.ufg.inf.adopet.util.AppData;
import br.ufg.inf.adopet.util.InputTag;
import br.ufg.inf.adopet.util.Routes;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener{

    private View mView;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mView = inflater.inflate(R.layout.fragment_account, container, false);
        setUp();
        return this.mView;
    }

    private void setUp(){
        Button signOut = (Button) mView.findViewById(R.id.action_sign_out);
        signOut.setOnClickListener(this);

        TextView name = (TextView) mView.findViewById(R.id.account_name);
        TextView mail = (TextView) mView.findViewById(R.id.account_mail);

        name.setText(AppData.getData(getActivity(),AppData.Preferences.NAME,getString(R.string.not_avaliable)));
        mail.setText(AppData.getData(getActivity(),AppData.Preferences.MAIL,getString(R.string.not_avaliable)));

        FloatingActionButton newPublish = (FloatingActionButton) mView.findViewById(R.id.action_new_publish);
        newPublish.setOnClickListener(this);

        getPostsUser();
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.action_sign_out){
            AppData.removeData(getActivity(),AppData.Preferences.ACCESS_TOKEN);
            AppData.removeData(getActivity(),AppData.Preferences.TOKEN_TYPE);
            AppData.removeData(getActivity(),AppData.Preferences.CLIENT_TOKEN);
            AppData.removeData(getActivity(),AppData.Preferences.UID);
            AppData.removeData(getActivity(),AppData.Preferences.NAME);
            AppData.removeData(getActivity(),AppData.Preferences.ID);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        if(view.getId() == R.id.action_new_publish){
            Intent intent = new Intent(getActivity(),NewPostActivity.class);
            startActivityForResult(intent, InputTag.REQUEST_NEW_POST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK ){
            if (requestCode == InputTag.REQUEST_NEW_POST){
                getPostsUser();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getPostsUser(){
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage(getString(R.string.loading_message));
        loading.show();
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray response = new JSONArray(s);
                    if(response.length()>0)
                        renderPostsAccount(response);
                    else{
                        TextView status = (TextView) mView.findViewById(R.id.account_publish_status);
                        status.setText(getString(R.string.empty_posts));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    loading.dismiss();
                }

            }
        };

        Response.ErrorListener requestError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),volleyError.getMessage()+" ",Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        };
        String userID = String.valueOf(AppData.getData(getActivity(),AppData.Preferences.ID,"0"));
        StringRequest request = new StringRequest(Request.Method.GET, Routes.POSTS_USER + userID + "/animals",response,requestError){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>(7);
                headers.put("accept","*/*");
                headers.put("content-type","multipart/form-data");
                headers.put("cache-control","no-cache");
                headers.put("access-token", AppData.getData(getActivity(),AppData.Preferences.ACCESS_TOKEN,""));
                headers.put("token-type", AppData.getData(getActivity(),AppData.Preferences.TOKEN_TYPE,""));
                headers.put("client", AppData.getData(getActivity(),AppData.Preferences.CLIENT_TOKEN,""));
                headers.put("uid", AppData.getData(getActivity(),AppData.Preferences.UID,""));
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(Routes.MAX_TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request,AccountFragment.class.getSimpleName());
    }

    private void renderPostsAccount(JSONArray json ) throws JSONException{
        RecyclerView postsAccount = (RecyclerView) mView.findViewById(R.id.recyclerview_list);
        postsAccount.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Post> postsList = new ArrayList<>(json.length());
        Post post;
        JSONObject postJson;
        for(int i=0;i<json.length();i++){
            postJson = json.getJSONObject(i);
            post = new Post(postJson.getInt("id"),postJson.getString("name"),postJson.getJSONObject("user").getString("name"),postJson.getString("id"));
            postsList.add(post);
        }

        AccountPostsListAdapter listAdapter = new AccountPostsListAdapter(getActivity(),postsList);
        postsAccount.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        AppController.getInstance().cancelPendingRequests(AccountFragment.class.getSimpleName());
        super.onDestroyView();
    }
}
