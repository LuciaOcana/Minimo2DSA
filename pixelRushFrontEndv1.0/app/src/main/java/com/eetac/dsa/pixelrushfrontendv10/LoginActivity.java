package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.LoginCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    String usernameLogin;
    ProgressBar progressBarLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_log_in_dialog);

        // Initialize the text views and buttons here
        // Initialize the text views and buttons here
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        progressBarLogin = findViewById(R.id.progressBarLogin);

        // Pre-fill the EditText fields with stored username and password
        String storedUsername = SharedPreferencesUtil.getStoredUsername(this);
        if (!storedUsername.isEmpty()) {
            // User is already logged in, navigate to MainUserPageActivity
            Intent intent = new Intent(LoginActivity.this, MainUserPageActivity.class);
            intent.putExtra("username", storedUsername);
            startActivity(intent);
            finish(); // Finish the LoginActivity so the user cannot go back to it
        } else {
            // User is not logged in, proceed with the login process
            String storedPassword = SharedPreferencesUtil.getStoredPassword(this);
            editTextUsername.setText(storedUsername);
            editTextPassword.setText(storedPassword);
        }

    }

    public void login(View view) {
        progressBarLogin.setVisibility(View.VISIBLE); // Show progress bar
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        LoginCredentials loginCredentials = new LoginCredentials(username,password);

        Call<Void> callLogin = pixelRushService.login(loginCredentials);

        callLogin.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBarLogin.setVisibility(View.GONE); // Hide progress bar
                if (response.isSuccessful()) {
                    System.out.println("User login successful");
                    Toast.makeText(LoginActivity.this,"User login successful",Toast.LENGTH_SHORT).show();
                    usernameLogin=username;

                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("username", username).apply();
                    sharedPreferences.edit().putString("password", password).apply();

                    Intent intent = new Intent(LoginActivity.this, MainUserPageActivity.class);
                    String message = usernameLogin;
                    intent.putExtra("username", message);
                    startActivity(intent);
                } else {
                    System.out.println("Error: " + response.code() + " " + response.message());
                    Toast.makeText(LoginActivity.this,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBarLogin.setVisibility(View.GONE); // Hide progress bar
                System.out.println("Error: "+t.getMessage());
                Toast.makeText(LoginActivity.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void cancelLogin(View view){
        finish();
    }
}
