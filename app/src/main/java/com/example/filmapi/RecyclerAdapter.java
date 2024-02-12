package com.example.filmapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmapi.models.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Film> films;

    interface OnStateClickListener{
        void onStateClick(Film film, int position, View v);
    }

    private final OnStateClickListener onClickListener;

    public RecyclerAdapter(Context context, ArrayList<Film> films, OnStateClickListener onClickListener) {
        this.context = context;
        this.films = films;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Film film = films.get(position);
        holder.name.setText(film.getNameRu());
        holder.year.setText(film.getYear());
        Picasso.with(context).load(film.getPosterUrlPreview()).placeholder(R.drawable.ic_baseline_search_24).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onStateClick(film, position, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, year;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.film_name);
            year = itemView.findViewById(R.id.year);
            imageView = (ImageView) itemView.findViewById(R.id.poster);

        }
    }
}
