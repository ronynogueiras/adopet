package br.ufg.inf.adopet.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.util.AppConfig;
import br.ufg.inf.adopet.util.AppController;
import br.ufg.inf.adopet.util.AppData;
import br.ufg.inf.adopet.util.InputTag;
import br.ufg.inf.adopet.util.Routes;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName,mEmail,mConfirmPassword,mPassword;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setUp();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean isValidInputs(){
        return mName.getText().toString().trim().length() > 0 &&
                mEmail.getText().toString().trim().length() > 0 &&
                mConfirmPassword.getText().toString().trim().length() > 0 &&
                mPassword.getText().toString().trim().length()>0 &&
                mConfirmPassword.getText().toString().trim().equals(mPassword.getText().toString().trim());
    }

    private void setUp(){


        this.mName = (EditText) findViewById(R.id.input_name);
        this.mEmail = (EditText) findViewById(R.id.input_email);
        this.mConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        this.mPassword = (EditText) findViewById(R.id.input_password);
        this.mRegister = (Button) findViewById(R.id.action_register);
        this.mRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.action_register){
            if(isValidInputs()){
                register();
            }else{
                Toast.makeText(this,getString(R.string.invalid_register_inputs),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void register(){
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
                    if(response.has("data")){
                        response = response.getJSONObject("data");
                        AppData.setData(RegisterActivity.this,AppData.Preferences.ID,response.getString("id"));
                        AppData.setData(RegisterActivity.this,AppData.Preferences.NAME,response.getString("name"));
                        AppData.setData(RegisterActivity.this,AppData.Preferences.MAIL,response.getString("email"));
                        setResult(RESULT_OK);
                        finish();
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

                String data = new String(volleyError.networkResponse.data);
                try {
                    JSONArray errors = new JSONObject(data).getJSONArray("errors");
                    Toast.makeText(RegisterActivity.this, (errors.isNull(0) ?  getString(R.string.unknown_error): errors.getString(0)),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this,volleyError.getMessage()+"",Toast.LENGTH_LONG).show();
                }
                loading.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, Routes.REGISTER,response,requestError){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>(2);
                params.put(InputTag.NAME_TAG,mName.getText().toString().trim());
                params.put(InputTag.EMAIL_TAG,mEmail.getText().toString().trim());
                params.put(InputTag.PASSWORD_TAG,mPassword.getText().toString().trim());
                params.put(InputTag.PASSWORD_CONFIRMATION_TAG,mConfirmPassword.getText().toString().trim());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String,String> headers = response.headers;
                String accessToken = headers.get("Access-Token");
                String clientToken = headers.get("Client");
                String typeToken = headers.get("Token-Type");
                String uid = headers.get("Uid");

                AppData.setData(RegisterActivity.this,AppData.Preferences.ACCESS_TOKEN,accessToken);
                AppData.setData(RegisterActivity.this,AppData.Preferences.CLIENT_TOKEN,clientToken);
                AppData.setData(RegisterActivity.this,AppData.Preferences.TOKEN_TYPE,typeToken);
                AppData.setData(RegisterActivity.this,AppData.Preferences.UID,uid);

                return super.parseNetworkResponse(response);
            }

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

        AppController.getInstance().addToRequestQueue(request,LoginActivity.class.getSimpleName());
    }

    @Override
    protected void onDestroy() {
        AppController.getInstance().cancelPendingRequests(LoginActivity.class.getSimpleName());
        super.onDestroy();
    }
}
