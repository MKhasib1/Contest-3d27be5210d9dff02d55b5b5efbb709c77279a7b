package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    protected EditText email;
    protected EditText password;
    protected Button Login;
    protected Button SignUp;
    protected FirebaseAuth mAuth;
    protected SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    protected ProgressBar pbHeaderProgress;
    private int x=0;
    private  static final String EXTRA_EMAIL="com.example.login.EXTRA_EMAIL";
    private  static final int RC_SIGN_IN=9001;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        Login=findViewById(R.id.Login);
        SignUp=findViewById(R.id.SignUp);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        pbHeaderProgress= findViewById(R.id.spinner);
        pbHeaderProgress.setVisibility(View.INVISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton =  findViewById(R.id.signIN);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Error","before sign in google");
                signIn();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email!=null&&password!=null&& TextUtils.isEmpty(email.getText().toString())==false&&TextUtils.isEmpty(password.getText().toString())==false){
                    mSginIN(email.getText().toString(), password.getText().toString());

                }
                else
                { Toast.makeText(MainActivity.this, "Fill both email with password please!",
                        Toast.LENGTH_SHORT).show();

                }

            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email!=null&&password!=null&&TextUtils.isEmpty(email.getText().toString())==false&&TextUtils.isEmpty(password.getText().toString())==false)
                    mSginUp(email.getText().toString(),password.getText().toString());
                else
                { Toast.makeText(MainActivity.this, "Fill both email with password please!",
                        Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void signIn()
    { Log.e("Error","In sign in");
        Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RC_SIGN_IN){
            Log.e("Error","In onActivityResult");
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }
    private void handleSignInResult(GoogleSignInResult result)
    {
        Log.e("Error","In handleSignInResult");
        if(x==1)
        {updateUIG(result);
            x=0;}
        x=1;
        if(result.isSuccess())
        { updateUIG(result);
            Log.e("Error","In handleSignInResult");

        }
    }

    private void updateUIG(GoogleSignInResult result) {
        Log.e("Error","updateUIG:");
        openNewActivity("majd20171998@gmail.com");
    }

    public void onConnectionFailed(ConnectionResult result) {
        // An unresolvable error has occurred and a connection to Google APIs
        // could not be established. Display an error message, or handle
        // the failure silently
        Log.e("Error","onConnectionFailed:"+result);
        // ...
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("error", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void mSginIN(String toString, String toString1){
        pbHeaderProgress.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(toString, toString1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("success", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            cases(task);

                            updateUI(null);
                        }

                        // ...
                    }
                });
        pbHeaderProgress.setVisibility(View.INVISIBLE);
    }

    private void mSginUp(String toString, String toString1) {
        pbHeaderProgress.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(toString,toString1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            cases(task);

                            updateUI(null);
                        }

                        // ...
                    }
                });
        pbHeaderProgress.setVisibility(View.INVISIBLE);
    }
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null)
        {
            openNewActivity(currentUser.getEmail());
        }

    }


    private void openNewActivity(String email) {
        Intent intent= new Intent(this,AppWindow.class);
        intent.putExtra(EXTRA_EMAIL,email);

        startActivity(intent);
    }
    public void cases(Task<AuthResult> task){
        // If sign in fails, display a message to the user.
        Log.e("error", "createUserWithEmail:failure", task.getException());
        String type=task.getException().toString();

        if(type.contains("The email address is badly formatted"))
            Toast.makeText(MainActivity.this, "The Email isn't formatted correctly",
                    Toast.LENGTH_SHORT).show();
        else if(type.contains("There is no user record corresponding to this identifier"))
            Toast.makeText(MainActivity.this, "No Such Email exists",
                    Toast.LENGTH_SHORT).show();
        else if(type.contains("The password is invalid"))
            Toast.makeText(MainActivity.this, "InCorrect Password",
                    Toast.LENGTH_SHORT).show();
        else if(type.contains("The email address is already in use by another account"))
            Toast.makeText(MainActivity.this, "The Email is already exists",
                    Toast.LENGTH_SHORT).show();
        else if(type.contains("A network error"))
            Toast.makeText(MainActivity.this, "Network is down",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Your password is too little",
                    Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

}
