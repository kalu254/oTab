package com.kalu.otab.Access;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kalu.otab.MainActivity;
import com.kalu.otab.R;
import com.kalu.otab.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private static final String DOMAIN_NAME = "gmail.com";

    //widgets
    private EditText mEmail, mPassword, mConfirmPassword, mName, mRegNo, mDepartment;
    private Button mRegister;
    private ProgressBar mProgressBar;
    private TextView resendLink;
    private FirebaseUser mUser;
    private ImageView mProfilPic;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private int RESULT_IMAGE = 1;
    private Uri mProfilePath;
    private Uri mSelectedImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        mEmail =  findViewById(R.id.input_email);
        mPassword =  findViewById(R.id.input_password);
        mConfirmPassword =  findViewById(R.id.input_confirm_password);
        mRegister =  findViewById(R.id.btn_register);
        mProgressBar =  findViewById(R.id.progressBar);
        mName = findViewById(R.id.input_name);
        mDepartment = findViewById(R.id.input_department);
        mRegNo = findViewById(R.id.input_reg_no);
        mProfilPic = findViewById(R.id.mProfilePic);

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("Users" + UUID.randomUUID().toString());

        mProfilPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfile();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())
                        && !isEmpty(mName.getText().toString())
                        && !isEmpty(mDepartment.getText().toString())
                        && !isEmpty(mRegNo.getText().toString())
                ){

                    //check if user has a company email address
                    if(isValidDomain(mEmail.getText().toString())){

                        //check if passwords match
                        if(doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())){
                            registerNewEmail(mEmail.getText().toString(),mPassword.getText().toString());
                        }else{
                            Toast.makeText(Register.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Register.this, "Please Register with Company Email", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Register.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendLink = findViewById(R.id.resend_link);
        resendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();

            }
        });

        hideDialog();
        hideSoftKeyboard();

    }

    private void getProfile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "select image"), RESULT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RESULT_IMAGE && data != null && null != data.getData() && isStoragePermissionGranted()){
            mProfilePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mProfilePath);
                mProfilPic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "You have not selected any image", Toast.LENGTH_SHORT).show();
        }
    }

    private void reset() {
            ResendVerificationDialog dialog = new ResendVerificationDialog();
            dialog.show(getSupportFragmentManager(), "dialog_resend_email_verification");

    }

    private void sendVerificationEmail(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mUser != null) {
    mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Toast.makeText(Register.this, "sent verification email", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Register.this, "Couldn't Send Verification Email",
                        Toast.LENGTH_SHORT).show();
            }
        }
    });
}

    }
    private void registerNewEmail(String email, String password){

        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"on complete: Auth State:" + FirebaseAuth.getInstance().getCurrentUser()
                            .getUid());
                            sendVerificationEmail();
                            savePersonalDetails();
                            FirebaseAuth.getInstance().signOut();
                            finish();
                        }
                        else {
                            Toast.makeText(Register.this,"Unable to register,try again",Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();
                    }
                }
        );
    }

    private void savePersonalDetails() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users");

        Query addUser = databaseReference.orderByKey();
        addUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saveImage();
                String name = mName.getText().toString();
                String depart = mDepartment.getText().toString();
                String reg_no = mRegNo.getText().toString();
                String uri = mProfilePath.toString();

                User user = new User(name,depart,reg_no,uri);
                databaseReference.push().setValue(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void saveImage() {
        if(mProfilePath != null){
            mStorageReference.putFile(mProfilePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Register.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    }
                });
        }
    }


    private boolean isValidDomain(String email){
        Log.d(TAG, "isValidDomain: verifying email has correct domain: " + email);
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        Log.d(TAG, "isValidDomain: users domain: " + domain);
        return domain.equals(DOMAIN_NAME);
    }


    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(Register.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }
    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG2","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG3","Permission is granted");
            return true;
        }
    }

}
