package br.ufg.inf.adopet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.util.AppConfig;
import br.ufg.inf.adopet.util.AppController;
import br.ufg.inf.adopet.util.AppData;
import br.ufg.inf.adopet.util.InputTag;
import br.ufg.inf.adopet.util.Routes;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTitle,mDescription;
    private ImageView mPicture;
    private Spinner mType;
    private Button mPickImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_post_title);

        this.setUp();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            setResult(RESULT_CANCELED);
            finish();
        }
        if(item.getItemId() == R.id.save_post){
            if(isValidInputs()){
                createPost();
            }else{
                Toast.makeText(this,getString(R.string.invalid_new_post_inputs),Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUp(){
        this.mTitle = (EditText) findViewById(R.id.new_post_title);
        this.mDescription = (EditText) findViewById(R.id.new_post_description);
        this.mPicture = (ImageView) findViewById(R.id.new_post_picture);
        this.mPickImage = (Button) findViewById(R.id.new_post_pick_image);
        this.mType = (Spinner) findViewById(R.id.new_post_type);

        this.mPickImage.setOnClickListener(this);

    }

    private void createPost(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage(getString(R.string.loading_message));
        loading.show();
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(AppConfig.DEBUG_TAG,s);
                loading.dismiss();
                Toast.makeText(NewPostActivity.this,"Publicado com sucesso!",Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        };

        Response.ErrorListener requestError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(AppConfig.DEBUG_TAG,volleyError.getMessage()+"  ");
                loading.dismiss();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST,Routes.CREATE_POST,response,requestError){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>(4);
                params.put(InputTag.TITLE_POST_TAG,mTitle.getText().toString().trim());
                params.put(InputTag.DESCRIPTION_POST_TAG,mDescription.getText().toString().trim());
                params.put(InputTag.CATEGORIE_POST_TAG,mType.getSelectedItem().toString().toLowerCase());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>(7);
                headers.put("accept","*/*");
                headers.put("content-type","multipart/form-data");
                headers.put("cache-control","no-cache");
                headers.put("access-token", AppData.getData(NewPostActivity.this,AppData.Preferences.ACCESS_TOKEN,""));
                headers.put("token-type", AppData.getData(NewPostActivity.this,AppData.Preferences.TOKEN_TYPE,""));
                headers.put("client", AppData.getData(NewPostActivity.this,AppData.Preferences.CLIENT_TOKEN,""));
                headers.put("uid", AppData.getData(NewPostActivity.this,AppData.Preferences.UID,""));
                return headers;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(Routes.MAX_TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request,NewPostActivity.class.getSimpleName());
    }

    private boolean isValidInputs(){
        return true;
    }

    @Override
    protected void onDestroy() {
        AppController.getInstance().cancelPendingRequests(NewPostActivity.class.getSimpleName());
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.new_post_pick_image){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,getString(R.string.pick_image)),InputTag.REQUEST_PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == InputTag.REQUEST_PICK_IMAGE ){
                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    mPicture.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
