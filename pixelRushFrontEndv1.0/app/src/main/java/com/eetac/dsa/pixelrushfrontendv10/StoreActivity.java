package com.eetac.dsa.pixelrushfrontendv10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_layout);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        getAllObjectsFromStore();
        coinNumber();
    }

    public void getAllObjectsFromStore () {

        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface
            setContentView(R.layout.recycle_view);

            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            Call<List<StoreObject>> callGetAllStoreObjects = pixelRushService.getAllObjectsFromStore();
            callGetAllStoreObjects.enqueue(new Callback<List<StoreObject>>() {
                @Override
                public void onResponse(Call<List<StoreObject>> call, Response<List<StoreObject>> response) {
                    if (response.isSuccessful()) {
                        List<StoreObject> objects = response.body();

                        // Crear y establecer el adaptador
                        MyAdapter adapter = new MyAdapter(objects,StoreActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(StoreActivity.this));
                        Log.i("FirstVersion_ObjectList", "Showing Store");
                        Toast.makeText(StoreActivity.this, "Showing Store", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                        Toast.makeText(StoreActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<StoreObject>> call, Throwable t) {
                    Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                    Toast.makeText(StoreActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void exitStore(View view){
        finish();
    }

    public void coinNumber(){
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

        TextView coinsTextView = findViewById(R.id.coinNumber);

        Call<JsonObject> callGetPointsFromActiveMatch = pixelRushService.getMatchPointsFromActiveMatch(username);
        callGetPointsFromActiveMatch.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonResponse = response.body();
                    if (jsonResponse != null) {
                        int points = jsonResponse.get("matchPoints").getAsInt();
                        String pointsS = String.valueOf(points);
                        coinsTextView.setText(pointsS);
                    } else {
                        System.out.println("Response body is null");
                    }
                } else {
                    System.out.println("Error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });

    }
}
