package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Badge;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterBadge extends RecyclerView.Adapter<MyAdapterBadge.ViewHolder> {

    private List<Badge> listBadge;
    private Context context;

    // Constructor para recibir la lista de objetos
    public MyAdapterBadge(List<Badge> badges, Context context) {
        this.listBadge = badges;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_insignias, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapterBadge.ViewHolder holder, int position) {
        Badge object = listBadge.get(position);

        holder.name.setText(object.name);

        //new DownloadImageTask(holder.avatar).execute(object.avatar);

    }

    @Override
    public int getItemCount() {
        return listBadge.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewBadgeName);
          //  avatar = itemView.findViewById(R.id.imageBadge);

        }
    }
}
