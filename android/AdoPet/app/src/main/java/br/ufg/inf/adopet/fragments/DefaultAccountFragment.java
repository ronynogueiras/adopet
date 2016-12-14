package br.ufg.inf.adopet.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.activities.LoginActivity;
import br.ufg.inf.adopet.activities.MainActivity;
import br.ufg.inf.adopet.activities.RegisterActivity;
import br.ufg.inf.adopet.util.InputTag;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultAccountFragment extends Fragment implements View.OnClickListener{

    private View mView;


    public DefaultAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mView = inflater.inflate(R.layout.fragment_default_account, container, false);

        this.setUp();

        return this.mView;
    }

    private void setUp(){
        Button sigin = (Button) mView.findViewById(R.id.account_sigin);
        Button sigup = (Button) mView.findViewById(R.id.account_sigup);
        sigin.setOnClickListener(this);
        sigup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()){
            case R.id.account_sigin:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, InputTag.REQUEST_LOGIN_TAG);
                break;
            case R.id.account_sigup:
                intent = new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, InputTag.REQUEST_REGISTER_TAG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == InputTag.REQUEST_LOGIN_TAG){
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }else if(requestCode == InputTag.REQUEST_REGISTER_TAG){
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
