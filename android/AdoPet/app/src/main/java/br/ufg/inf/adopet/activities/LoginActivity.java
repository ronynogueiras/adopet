package br.ufg.inf.adopet.activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.util.UserDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, UserDialog{

    private EditText mEmail, mPassword;
    private Button mAuthUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);

        mAuthUser = (Button) findViewById(R.id.action_login);

        mAuthUser.setOnClickListener(this);

    }


    private boolean isCredentials(){
        return mEmail.getText().toString().trim().length() > 0 && mPassword.getText().toString().trim().length() > 0;
    }

    @Override
    public void onClick(View view) {
        if ( view.getId() == R.id.action_login ){
            if ( isCredentials() ){
                //Request login - API
            }else{
                showDialogToUser(null,null,null);
            }
        }
    }

    @Override
    public void showDialogToUser(String[] args, Dialog.OnClickListener positiveAction, Dialog.OnClickListener negativeAction) {
        //Dialog Implementation
    }
}
