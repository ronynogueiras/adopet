package br.ufg.inf.adopet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.model.Post;
import br.ufg.inf.adopet.util.AppConfig;
import br.ufg.inf.adopet.util.AppController;
import br.ufg.inf.adopet.util.InputTag;
import br.ufg.inf.adopet.util.Routes;

public class PostDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mDescription,mAuthor,mDate;
    private ImageView mPicture;
    private FloatingActionButton mInterest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();

        if(intent != null){
            int id = intent.getIntExtra(InputTag.POST_DETAIL_ID,0);
            if(id!=0){
                this.setUpLayout();

                this.getPost(String.valueOf(id));

            }else{
                Toast.makeText(this,R.string.invalid_id_post,Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this,R.string.invalid_id_post,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPost(final String id){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage(getString(R.string.loading_message));
        loading.show();

        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(AppConfig.DEV_MODE)
                    Log.i(AppConfig.DEBUG_TAG,s);
                try {
                    JSONObject response = new JSONObject(s);
                    renderPostDetail(response);
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
                if(AppConfig.DEV_MODE)
                    Log.e(AppConfig.DEBUG_TAG,volleyError.getMessage()+" ");
                loading.dismiss();
            }
        };

        StringRequest request = new StringRequest(Request.Method.GET, Routes.ANIMAL + id,response,requestError){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>(3);
                headers.put("accept","*/*");
                headers.put("content-type","multipart/form-data");
                headers.put("cache-control","no-cache");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(Routes.MAX_TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(request, PostDetailActivity.class.getSimpleName());
    }

    private void renderPostDetail(JSONObject postResponse) throws JSONException{

        this.mToolbar.setTitle(postResponse.getString("name"));
        String description = postResponse.has("description") ? postResponse.getString("description") : getString(R.string.description_not_avaliable) ;
        this.mDescription.setText(description);
        this.mAuthor.setText(postResponse.getJSONObject("user").getString("name"));
        Picasso.with(this).load("https://cdn.pixabay.com/photo/2014/03/05/19/23/dog-280332_960_720.jpg").fit().centerCrop().into(this.mPicture);
        this.mDate.setText(postResponse.getString("created_at"));

        this.mInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setPostInterest();
            }
        });


    }

    private void setUpLayout(){

        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mDescription = (TextView) findViewById(R.id.post_detail_description);
        this.mDate = (TextView) findViewById(R.id.post_detail_date);
        this.mAuthor = (TextView) findViewById(R.id.post_detail_author);
        this.mPicture = (ImageView) findViewById(R.id.post_detail_picture);
        this.mInterest = (FloatingActionButton) findViewById(R.id.post_detail_interest);
    }
}
