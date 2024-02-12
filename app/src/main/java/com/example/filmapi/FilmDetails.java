package com.example.filmapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.filmapi.models.FilmInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmDetails extends AppCompatActivity {
    ProgressDialog dialog;
    ImageButton backBtn;

    TextView details, name;
    ImageView imageView;
    FilmInterface apiInterface;
    RecyclerView recycler;
    Animation scale, scaleout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.show();

        recycler = findViewById(R.id.recycleDet);
        int id = getIntent().getIntExtra("id", 0);

        apiInterface = RequestBuilder.buildRequest().create(FilmInterface.class);

        Log.i("WTF", String.valueOf(id));
        Call<FilmInfo> getFilm = apiInterface.getFid(id);
        getFilm.enqueue(new Callback<FilmInfo>() {
            @Override
            public void onResponse(Call<FilmInfo> call, Response<FilmInfo> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler.setHasFixedSize(true);
                    FilmInfo filmInfo = response.body();
                    System.out.println(filmInfo + "SDCFVGB");
                    DateilsAdapter filmAdapter = new DateilsAdapter(FilmDetails.this, filmInfo);
                    recycler.setAdapter(filmAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FilmInfo> call, Throwable t) {
                if (!isConnected()){
                    Intent first_intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(first_intent);
                }
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null){
            if(networkInfo.isConnected())
                return true;
            else
                return false;

        }else{
            return false;
        }
    }


}