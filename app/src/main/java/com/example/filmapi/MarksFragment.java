package com.example.filmapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmapi.models.Film;
import com.example.filmapi.models.Root;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarksFragment extends Fragment {

    public final static String PATH_MARKS_LOCATION = "marks";
    RecyclerView recyclerView;
    ProgressDialog dialog;
    FilmInterface apiInterface;
    private List<Film> listItems;
    ArrayList<String> marksM = new ArrayList<String>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MarksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarksFragment newInstance(String param1, String param2) {
        MarksFragment fragment = new MarksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_marks, container, false);
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading...");
        dialog.show();
        SharedPreferences uri = getActivity().getSharedPreferences(PATH_MARKS_LOCATION, Context.MODE_PRIVATE);
        int size = uri.getInt("marksSize", 0);
        listItems = new ArrayList<>();

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

        for(int i=0; i < size; i++)
            marksM.add(uri.getString("marks" + i, ""));

        recyclerView = view.findViewById(R.id.recycler_marks);

        page.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    recyclerView.setHasFixedSize(true);
                    Root page = response.body();
                    for(int i=0; i < size; i++)
                        System.out.println(Integer.parseInt(marksM.get(i))+"SDFGTBH");
                    RecyclerAdapter adapter = new RecyclerAdapter(view.getContext(), page.getFilms(), stateClickListener);
                    recyclerView.setAdapter(adapter);
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