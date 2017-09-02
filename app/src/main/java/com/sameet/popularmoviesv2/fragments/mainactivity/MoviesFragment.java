package com.sameet.popularmoviesv2.fragments.mainactivity;


import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sameet.popularmoviesv2.R;
import com.sameet.popularmoviesv2.adapters.MovieAdapter;
import com.sameet.popularmoviesv2.model.Movie;
import com.sameet.popularmoviesv2.networkutils.NetworkRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends android.support.v4.app.Fragment {
    private static final String API_KEY = "0ec5cc72e1380fbed537bfe349bcc4fb";
    private static final String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular/?api_key=" + API_KEY;
    private static final String HIGHEST_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated/?api_key=" + API_KEY;
    private static final String POPULAR_KEY = "popularKey";
    private static final String HIGHEST_RATED_KEY = "highestRatedKey";
    private Movie mMovie;
    private List<Movie> mMovies = new ArrayList<>();
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mNetworkInfo;
    private NetworkRequest mNetworkRequest;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;


    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_movies, container, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mNetworkRequest = mNetworkRequest.getInstance(getActivity());


        mMovieAdapter = new MovieAdapter(mMovies, getContext());
        mRecyclerView.setAdapter(mMovieAdapter);

        if (isNetworkAvailable()) {
            loadData(POPULAR_URL);
        }

        // Inflate the layout for this fragment
        return mRecyclerView;
    }

    /**
     * This method loads the Movie data in the main UI thread using Volley
     **/
    private void loadData(String url) {
        StringRequest popularRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           jsonParse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        StringRequest topRatedRequest = new StringRequest(Request.Method.GET,
               url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                       jsonParse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mNetworkRequest.addToRequestQueue(popularRequest);
        mNetworkRequest.addToRequestQueue(topRatedRequest);
    }

    private void jsonParse(String response) throws JSONException {
        mMovies.clear(); // Clears the list of movies
        JSONObject jsonObject = new JSONObject(response);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject objects = results.getJSONObject(i);
            mMovie = new Movie(
                    objects.getString("title"),
                    objects.getString("poster_path"),
                    objects.getString("overview"),
                    objects.getDouble("vote_average"),
                    objects.getString("release_date"),
                    objects.getInt(null), // Null for trailerID as we'll get this in the DetailActivity
                    objects.getString(null) // Null for the reviews as we'll get this in the DetailActivity
            );
            mMovies.add(mMovie);
        }
        mMovieAdapter.replaceData(mMovies); // Reloads movie data into adapter
    }

    /**
     * This method checks for internet connectivty before requesting Movies from API
     **/
    private boolean isNetworkAvailable() {
        mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

}
