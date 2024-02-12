package com.example.filmapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.filmapi.models.Film;
import com.example.filmapi.models.Root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularFragment extends Fragment {
    FilmInterface apiInterface;
    RecyclerView recycler;
    ProgressDialog dialog;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PopularFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        if (!isConnected()){
            Fragment fr = new ConnectionErrorFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fr);
            fragmentTransaction.commit();
        }else {

            dialog = new ProgressDialog(getContext());
            dialog.setTitle("Loading...");
            dialog.show();
            recycler = (RecyclerView) view.findViewById(R.id.recyclerView);

            apiInterface = RequestBuilder.buildRequest().create(FilmInterface.class);
            RecyclerAdapter.OnStateClickListener stateClickListener = new RecyclerAdapter.OnStateClickListener() {
                @Override
                public void onStateClick(Film film, int position, View v) {
                    Intent intent = new Intent(view.getContext(), FilmDetails.class);
                    intent.putExtra("id", film.getFilmId());
                    Toast.makeText(view.getContext(), "Был выбран пункт " + film.getNameRu(),
                            Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            };

            Call<Root> page = apiInterface.getPagesId();

            page.enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        recycler.setHasFixedSize(true);
                        Root page = response.body();
                        RecyclerAdapter adapter = new RecyclerAdapter(view.getContext(), page.getFilms(), stateClickListener);
                        recycler.setAdapter(adapter);
                    } else {
                        Log.e("errLog", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {
                    if (!isConnected()){
                        Fragment fr = new ConnectionErrorFragment();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fr);
                        fragmentTransaction.commit();
                    }
                    Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }
    boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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