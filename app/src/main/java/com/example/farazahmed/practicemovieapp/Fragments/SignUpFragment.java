package com.example.farazahmed.practicemovieapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class SignUpFragment extends Fragment {

    String email;
    String password;
    String firstname;
    String lastname;
    private EditText sign_up_firtname;
    private EditText sign_up_lastname;
    private EditText sign_up_email;
    private EditText sign_up_password;
    private Button sign_up_button;
    private LinearLayout rootlayout;
    private ProgressDialog pd;
    private FirebaseAuth mFirebaseAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //setting up views
        pd = new ProgressDialog(getContext());
        rootlayout = (LinearLayout) view.findViewById(R.id.root_signup);
        sign_up_firtname = (EditText) view.findViewById(R.id.signup_firstname);
        sign_up_lastname = (EditText) view.findViewById(R.id.signup_lastname);
        sign_up_email = (EditText) view.findViewById(R.id.signup_email);
        sign_up_password = (EditText) view.findViewById(R.id.signup_password);
        sign_up_button = (Button) view.findViewById(R.id.signup_register);

        //seeting up Firebase Auth instance
        mFirebaseAuth = FirebaseAuth.getInstance();


        //setting up signup button action
        sign_up_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //getting the values from input text fields
                firstname = sign_up_firtname.getText().toString();
                lastname = sign_up_lastname.getText().toString();
                email = sign_up_email.getText().toString();
                password = sign_up_password.getText().toString();

                // make checks
                if (TextUtils.isEmpty(firstname)) {

                    sign_up_firtname.setError("fill the required field");
                    return;
                } else {
                    sign_up_firtname.setError(null);
                }
                if (TextUtils.isEmpty(lastname)) {
                    sign_up_lastname.setError("fill the required field");
                    return;
                } else {
                    sign_up_lastname.setError(null);
                }
                if (TextUtils.isEmpty(email) && isEmailValid(email)) {
                    sign_up_email.setError("fill the required field");
                    return;
                } else {
                    sign_up_email.setError(null);
                }
                if (TextUtils.isEmpty(password)) {
                    sign_up_password.setError("fill the required field");
                    return;
                } else {
                    sign_up_password.setError(null);
                }


                pd.setMessage("Signing Up");
                pd.show();

                signin_EmailPassword();

            }
        });


        return view;


    }

    public void signin_EmailPassword() {


//setting up the registration via firebase auth
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        pd.dismiss();
                        if (!task.isSuccessful()) {

                            Snackbar.make(rootlayout, "Authentication Fail", BaseTransientBottomBar.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Snackbar.make(rootlayout, "The email address is already registered", BaseTransientBottomBar.LENGTH_SHORT).show();
                            }

                        } else {

                            sign_up_firtname.setText("");
                            sign_up_lastname.setText("");
                            sign_up_email.setText("");
                            sign_up_password.setText("");

                            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        Snackbar.make(rootlayout, "Authentication Fail", BaseTransientBottomBar.LENGTH_SHORT).show();
                                        if (password.length() < 6) {
                                            Snackbar.make(rootlayout, "Minimum password should be of 6 characters", BaseTransientBottomBar.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }

                                }
                            });
                        }
                    }
                });

    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

}
