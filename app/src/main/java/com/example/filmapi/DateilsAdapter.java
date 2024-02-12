package com.example.filmapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmapi.models.Country;
import com.example.filmapi.models.FilmInfo;
import com.example.filmapi.models.Genre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DateilsAdapter extends RecyclerView.Adapter<DateilsAdapter.ViewHolder>{
    private final Context context;
    private final FilmInfo film;

    public DateilsAdapter(Context context, FilmInfo film) {
        this.context = context;
        this.film = film;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String listString = "";
        String listString2 = "";

        for (Country s : film.getCountries())
        {
            listString += s.country + ", ";
        }
        for (Genre s2 : film.getGenres())
        {
            listString2 += s2.genre + ", ";
        }
        holder.name.setText(film.getNameRu());
        holder.details.setText(film.getDescription());
        holder.country.setText("Страны: "+ listString);
        holder.genre.setText("Жанры: " + listString2);
        holder.year.setText("Год: " + String.valueOf(film.getYear()));
        holder.rate.setText(String.valueOf("Рейтинг" + film.getRatingImdb()));
        Picasso.with(context).load(film.getPosterUrlPreview()).placeholder(R.drawable.ic_baseline_search_24).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, details, year, rate, country, genre;
        ImageView imageView;
        int id;
        boolean flag = true;
        ArrayList<String> marksM = new ArrayList<String>();
        public final static String PATH_MARKS_LOCATION = "marks";
        ImageButton backBtn, toMarks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = film.getKinopoiskId();
            toMarks = itemView.findViewById(R.id.toMarks);
            SharedPreferences uri = itemView.getContext().getSharedPreferences(PATH_MARKS_LOCATION, Context.MODE_PRIVATE);
            int size = uri.getInt("marksSize", 0);
            for(int i=0; i < size; i++)
                marksM.add(uri.getString("marks" + i, ""));
            Log.d("koijuhygtf", marksM.toString() + "      " + marksM.size());
            Log.d("junhyb", String.valueOf(size));

            if(marksM.contains(String.valueOf(id))){
                toMarks.setImageResource(R.drawable.bookmarknew3);
                flag = false;
            }

            toMarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences uri = itemView.getContext().getSharedPreferences(PATH_MARKS_LOCATION, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = uri.edit();
                    id = film.getKinopoiskId();
                    if (flag){
                        toMarks.setImageResource(R.drawable.bookmarknew3);
                        flag = false;
                        System.out.println(id+"sDTH");
                        marksM.add(String.valueOf(id));
                        editor.putInt("marksSize", uri.getInt("marksSize", 0)+1);
                        for(int i=0; i < marksM.size(); i++)
                            editor.putString("marks"+i, marksM.get(i));
                        editor.apply();
                        Log.d("hjghf", marksM.toString());

                    }else{
                        for(int i=0; i < marksM.size(); i++)
                            editor.remove("marks"+i).apply();
                        marksM.remove(String.valueOf(id));
                        editor.putInt("marksSize", uri.getInt("marksSize", 0)-1);
                        for(int i=0; i < marksM.size(); i++)
                            editor.putString("marks"+i, marksM.get(i));
                        editor.apply();
                        Log.d("ijbh", marksM.toString());
                        toMarks.setImageResource(R.drawable.bookmarknew2);
                        flag=true;}
                }
            });

            backBtn = itemView.findViewById(R.id.backbtn);

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("SDFDFXSA");
                    SharedPreferences uri = v.getContext().getSharedPreferences(PATH_MARKS_LOCATION, Context.MODE_PRIVATE);

                    Intent first_intent = new Intent(v.getContext(), MainActivity.class);
                    v.getContext().startActivity(first_intent);
                }
            });

            name = itemView.findViewById(R.id.name);
            details = itemView.findViewById(R.id.titleDescriptionMovie);
            genre = itemView.findViewById(R.id.titleGenresMovie);
            country = itemView.findViewById(R.id.titlecountryMovie);
            year = itemView.findViewById(R.id.yearMovie);
            rate = itemView.findViewById(R.id.titleRatingMovie);
            imageView = (ImageView) itemView.findViewById(R.id.Photo);

        }
    }
}
