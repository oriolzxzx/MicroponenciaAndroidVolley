package com.example.microponenciaandroidvolley;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class RecyckerView_ImageAdapter extends RecyclerView.Adapter<RecyckerView_ImageAdapter.ViewHolderImages> {
    ArrayList<ImageInfo> ListaUrl;
    View.OnClickListener listener;

    public RecyckerView_ImageAdapter(ArrayList<ImageInfo> listaUrl) {
        ListaUrl = listaUrl;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderImages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_picture, parent, false);
    v.setOnClickListener(listener);
    return new ViewHolderImages(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImages holder, int position) {
        holder.AsignarUrl(ListaUrl.get(position).Url);
    }

    @Override
    public int getItemCount() {
        return ListaUrl.size();
    }

    public class ViewHolderImages extends RecyclerView.ViewHolder {

        NetworkImageView imageview;

        public ViewHolderImages(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageViewImagen);
            imageview.setDefaultImageResId(R.drawable.ic_sync_problem_black_24dp);
        }

        public void AsignarUrl(String s) {

            imageview.setImageUrl(s, MySingleton.getInstance(itemView.getContext()).getImageLoader());

        }
    }
}
