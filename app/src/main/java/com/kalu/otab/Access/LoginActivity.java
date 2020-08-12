package com.kalu.otab.Access;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kalu.otab.MainActivity;
import com.kalu.otab.R;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends AppCompatActivity {

    private TextView linkregister;
    private AppCompatButton login;
    //firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private AppCompatEditText mLoginEmail;
    private AppCompatEditText mLoginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginEmail = findViewById(R.id.login_email);
        mLoginPassword = findViewById(R.id.login_password);

        setUpFirebaseAuth();



         linkregister = (TextView) findViewById(R.id.create_account);
         linkregister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 openRegisterPage();
             }
         });
         login = findViewById(R.id.btn_login);
         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //check if the fields are filled out
                 if(!isEmpty(mLoginEmail.getText().toString())
                         && !isEmpty(mLoginPassword.getText().toString())){
                     FirebaseAuth.getInstance().signInWithEmailAndPassword(mLoginEmail.getText().toString(),mLoginPassword.getText().toString())
                             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {

                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {

                         }
                     });

                 }else{
                     Toast.makeText(LoginActivity.this, "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
                 }

             }
         });


        }



    private void openRegisterPage() {
        Intent registration = new Intent(this, Register.class);
        startActivity(registration);
    }

    private void setUpFirebaseAuth(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    if (user.isEmailVerified()){
                        Log.d(":signed in ",user.getUid());
                        Toast.makeText(LoginActivity.this,"Authenticated with:" + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Check email inbox for verification link with:" + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                }
                else {
                    Log.d("signed out","yeah");
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
}



