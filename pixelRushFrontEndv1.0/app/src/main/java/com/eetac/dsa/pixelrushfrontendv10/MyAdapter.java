package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<StoreObject> storeObjects;
    private Context context;
    ProgressBar progressBarBuy;

    // Constructor para recibir la lista de objetos
    public MyAdapter(List<StoreObject> storeObjects, Context context) {
        this.storeObjects = storeObjects;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StoreObject object = storeObjects.get(position);

        holder.lineName.setText(object.articleName);
        holder.lineID.setText("ID: "+object.objectID);
        holder.lineDescription.setText(object.description);
        holder.linePrice.setText("$"+object.price);
        holder.buttonBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarBuy.setVisibility(View.VISIBLE);

                int position = holder.getAdapterPosition();
                Log.i("POSICION DEL ADAPTER", "----------------------------"+position);

                // Retrieve the username from SharedPreferences
                String storedUsername = SharedPreferencesUtil.getStoredUsername(context);
                PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

                Call<Void> callAddItemToUser = pixelRushService.addItemToUser(storedUsername,storeObjects.get(position).getObjectID());
                callAddItemToUser.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        progressBarBuy.setVisibility(View.GONE);

                        if (response.isSuccessful()) {
                            System.out.println("Purchase Successful");
                            Toast.makeText(context, "Purchase Successful", Toast.LENGTH_SHORT).show();


                        } else {
                            System.out.println("Error: " + response.code() + " " + response.message());
                            Toast.makeText(context,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        progressBarBuy.setVisibility(View.GONE);

                        System.out.println("Error: "+t.getMessage());
                        Toast.makeText(context,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lineName;
        TextView lineDescription;
        TextView lineID;
        TextView linePrice;
        Button buttonBuy;

        public ViewHolder(View itemView) {
            super(itemView);
            lineName = itemView.findViewById(R.id.textViewArticleName);
            lineDescription = itemView.findViewById(R.id.textViewDescription);
            lineID = itemView.findViewById(R.id.textViewID);
            linePrice = itemView.findViewById(R.id.textViewPrice);
            buttonBuy = itemView.findViewById(R.id.buttonBuy);
            progressBarBuy = itemView.findViewById(R.id.progressBarBuy);
        }
    }
}
