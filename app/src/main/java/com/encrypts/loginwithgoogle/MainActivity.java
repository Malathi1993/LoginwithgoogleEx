package com.encrypts.loginwithgoogle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
private LinearLayout Prof_Section;
private Button SignOut;
private SignInButton SignIn;
private TextView Name;
private TextView Email;
private ImageView prof_pic;
private GoogleApiClient apiClient;
private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Prof_Section = (LinearLayout)findViewById(R.id.pro_section);
        SignOut = (Button) findViewById(R.id.btn_logout);
        SignIn = (SignInButton) findViewById(R.id.sign_in_button);
        Name = (TextView) findViewById(R.id.Name);
        Email = (TextView) findViewById(R.id.Email);
        prof_pic = (ImageView) findViewById(R.id.prof_pic);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        Prof_Section.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
        GoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

    }

    @Override
    public void onClick(View v) {
      switch  (v.getId())
      {
          case:R.id.sign_in_button;
          signIn();
          break;
          case:R.id.btn_logout;
          signout();
          break;
      }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn()
    {
        Intent intent = Auth.GOOGLE_SIGN_IN_API.getSignInIntent(GoogleApiClient);
         startActivityForResult(intent,REQ_CODE );
    }

    private void signout()
      {
             Auth.GoogleSignInApi.signOut(GoogleApiClient).setResultCallback(new ResultCallback<Status>() {
     @Override
         public void onResult(@NonNull Status status) {
           }
         });
      }
    private void handleResult(GoogleSignInResult result)
    {
      if(result.isSuccess())
      {
          GoogleSignInAccount account = result.getSignInAccount();
          String name = account.getDisplayName();
          String Email = account.getEmail();
          String img_url = account.getPhotoUrl().toString();
          Name.setText(name);
          Email.setText(Email);
          img_url(this).into(prof_pic);
          updateUI(true);
      }
      else
          {
              updateUI(false);
          }

    }
    private void updateUI(boolean isLogin) {
        if (isLogin) {
            prof_section.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
        } else {
            prof_section.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected  void  onActivityResult (int requestCode,resultCode,data);
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
      }
    }

