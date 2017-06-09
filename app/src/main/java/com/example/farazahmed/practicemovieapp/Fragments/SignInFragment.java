package com.example.farazahmed.practicemovieapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.farazahmed.practicemovieapp.Activity.LoginActivity;
import com.example.farazahmed.practicemovieapp.Activity.MainActivity;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Utils.Utils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;


public class SignInFragment extends Fragment {


    private static final String TAG =  "signinfragment";
    private LinearLayout rootlayout ;
    private ProgressBar progresBar;
    private EditText signin_Email;
    private EditText signin_password;
    private Button btn_login;
    private SignInButton btn_google;

    private FirebaseAuth mFirebaseAuth;

    private LoginButton btn_facebook;
    private CallbackManager callbackManager;

     String email = "";
     String password = "";

     ProgressDialog pd;

    public SignInFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
         pd = new ProgressDialog(getContext());

        //setting up the views
        rootlayout = (LinearLayout)view.findViewById(R.id.root_signin);
        signin_Email = (EditText) view.findViewById(R.id.signin_email);
        signin_password = (EditText) view.findViewById(R.id.signin_password);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_google = (SignInButton) view.findViewById(R.id.google_sign_in_button);
        btn_google.setSize(SignInButton.SIZE_WIDE);

        //Facebook sign in
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        btn_facebook = (LoginButton) view.findViewById(R.id.facebook_login_button);
        btn_facebook.setFragment(this);

        //setting up the facebook button action
        btn_facebook.registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                handleFacebookAccessToken(loginResult.getAccessToken());
                Log.v(TAG, "fblogin onSuccess");

            }

            @Override
            public void onCancel() {
                // App code
                Log.v(TAG, "fblogin onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v(TAG, "fblogin onError");
            }
        });


        //getting the firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Email password sign in
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting values to authenticate

                email = signin_Email.getText().toString();
                password = signin_password.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    signin_Email.setError("Fill the required fields");
                    return;
                }
                else if(!isEmailValid(email))
                {
                    signin_Email.setError("Insert Valid Email");
                }
                else
                {
                    signin_Email.setError(null);
                }
                if (TextUtils.isEmpty(password)) {
                    signin_password.setError("fill the required fields");
                    return;
                }
                else
                {
                    signin_password.setError(null);
                }

                pd.setMessage("loading");
                pd.show();
                signin_EmailPassword();


            }
        });

        //google sign in
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).signIn();
            }
        });

        return view;
    }

    public  void signin_EmailPassword()
    {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                pd.dismiss();

                if (!task.isSuccessful()) {
                    // there was an error
                    Snackbar.make(rootlayout, "Authentication Fail", BaseTransientBottomBar.LENGTH_SHORT).show();
                    if (password.length() < 6) {
                        Snackbar.make(rootlayout, "Minimum password should be of 6 characters", BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    signin_Email.setText("");
                    signin_password.setText("");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra(email, "email");
                    startActivity(intent);
                }

            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Utils utils = new Utils(getApplicationContext());

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        }
                         if (utils.isNetworkAvailable() == false) {
                            Toast.makeText(getActivity(), "Oops! no internet connection!", Toast.LENGTH_SHORT).show();
                        }
                         else if(!task.isSuccessful())
                         {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(getContext(), "SomeThing Went Wrong Try Again.",Toast.LENGTH_SHORT).show();

                            }



                    }
                });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
}

