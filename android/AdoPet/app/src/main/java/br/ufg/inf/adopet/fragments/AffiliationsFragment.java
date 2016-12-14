package br.ufg.inf.adopet.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import br.ufg.inf.adopet.activities.PostDetailActivity;
import br.ufg.inf.adopet.adapters.PostFeedListAdapter;
import br.ufg.inf.adopet.model.Post;
import br.ufg.inf.adopet.util.AppConfig;
import br.ufg.inf.adopet.util.AppController;
import br.ufg.inf.adopet.util.InputTag;
import br.ufg.inf.adopet.util.RecyclerItemClickListener;
import br.ufg.inf.adopet.util.Routes;


/**
 * A simple {@link Fragment} subclass.
 */
public class AffiliationsFragment extends Fragment {

    private View mView;
    private RecyclerView mPostFeedList;
    private RecyclerView.Adapter mPostFeedListAdapter;
    private RecyclerView.LayoutManager mPostFeedLayoutManager;
    private List<Post> posts;

    public AffiliationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.mView = inflater.inflate(R.layout.fragment_affiliations, container, false);
        this.setUp();
        this.getPosts();

        return mView;
    }


    private void setUp() {

        mPostFeedList = (RecyclerView) mView.findViewById(R.id.recyclerview_list);
        mPostFeedList.setHasFixedSize(true);

        mPostFeedLayoutManager = new LinearLayoutManager(getActivity());
        mPostFeedList.setLayoutManager(mPostFeedLayoutManager);
    }

    private void getPosts() {
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (AppConfig.DEV_MODE)
                    Log.i(AppConfig.DEBUG_TAG, s);
                try {
                    JSONArray response = new JSONArray(s);
                    renderPosts(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener requestError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (AppConfig.DEV_MODE)
                    Log.i(AppConfig.DEBUG_TAG, volleyError.getMessage()+"");
            }
        };

        StringRequest request = new StringRequest(Request.Method.GET, Routes.ANIMALS, response, requestError){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>(3);
                headers.put("accept","*/*");
                headers.put("content-type","multipart/form-data");
                headers.put("cache-control","no-cache");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(Routes.MAX_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request, PostFeedFragment.class.getSimpleName());
    }

    private void renderPosts(JSONArray json) throws JSONException {
        Post post;
        this.posts = new ArrayList<>(json.length());
        for (int i = 0; i < json.length(); i++) {
            JSONObject postJSON = json.getJSONObject(i);
            post = new Post(postJSON.getInt("id"), postJSON.getString("name"), postJSON.getJSONObject("user").getString("name"), postJSON.getString("id"));
            posts.add(post);
        }
        mPostFeedListAdapter = new PostFeedListAdapter(getActivity(), posts);

        mPostFeedList.setAdapter(mPostFeedListAdapter);

        mPostFeedList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                intent.putExtra(InputTag.POST_DETAIL_ID, posts.get(position).getId());
                startActivity(intent);
            }
        }));

        mPostFeedListAdapter.notifyDataSetChanged();
    }
}

