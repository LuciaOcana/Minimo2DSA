package com.eetac.dsa.pixelrushfrontendv10;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.RegisterCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextMail;
    EditText editTextAge;

    ProgressBar progressBarSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_sign_up_dialog);

        // Initialize the text views and buttons here
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextMail = findViewById(R.id.editTextMail);
        editTextAge = findViewById(R.id.editTextAge);
        progressBarSignUp = findViewById(R.id.progressBarRegister);
    }

    public void register(View view){
        progressBarSignUp.setVisibility(View.VISIBLE); // Show progress bar

        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();
        String mail = editTextMail.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String birthDate = editTextAge.getText().toString();

        RegisterCredentials registerCredentials = new RegisterCredentials(username,password,name,surname,mail,birthDate);

        Call<Void> callSingIn = pixelRushService.register(registerCredentials);

        callSingIn.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBarSignUp.setVisibility(View.GONE); // Hide progress bar

                if (response.isSuccessful()) {
                    Log.i("First Version","User register successful");
                    Toast.makeText(RegisterActivity.this,"User register successful",Toast.LENGTH_SHORT).show();
                    finish();
                    //showPrincipalUserPage();
                } else {
                    Log.i("First Version","Error: " + response.code() + " " + response.message());
                    Toast.makeText(RegisterActivity.this,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBarSignUp.setVisibility(View.GONE);
                Log.i("First Version","Error: "+t.getMessage(),t);
                Toast.makeText(RegisterActivity.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelSignUp(View view){
        finish();
    }
}
