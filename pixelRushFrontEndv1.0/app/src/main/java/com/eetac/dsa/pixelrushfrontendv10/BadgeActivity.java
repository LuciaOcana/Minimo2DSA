package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Badge;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BadgeActivity extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_insignias);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Toast.makeText(BadgeActivity.this, "paso por aqui", Toast.LENGTH_SHORT).show();
        ListBadge();
    }

    public void ListBadge () {
        Toast.makeText(BadgeActivity.this, "llego aqui", Toast.LENGTH_SHORT).show();
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface
        setContentView(R.layout.recycle_view_insignias);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewB);

        Call<List<Badge>> callGetAllBadges = pixelRushService.getListBadge(username);
        Toast.makeText(BadgeActivity.this, "username"+username, Toast.LENGTH_SHORT).show();
        callGetAllBadges.enqueue(new Callback<List<Badge>>() {
            @Override
            public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                Toast.makeText(BadgeActivity.this, "entro por aqui", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    List<Badge> badges = response.body();

                    // Crear y establecer el adaptador
                    MyAdapterBadge adapter = new MyAdapterBadge(badges, BadgeActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(BadgeActivity.this));
                    Log.i("FirstVersion_ObjectList", "estoy aqui");
                    Toast.makeText(BadgeActivity.this, "Showing badge", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BadgeActivity.this, "pero me voy", Toast.LENGTH_SHORT).show();
                    Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                    Toast.makeText(BadgeActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {
                Toast.makeText(BadgeActivity.this, "acabo aqui:(", Toast.LENGTH_SHORT).show();
                Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                Toast.makeText(BadgeActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void exitStore(View view){
        finish();
    }

}
