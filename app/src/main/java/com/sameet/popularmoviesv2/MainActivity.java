package com.sameet.popularmoviesv2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sameet.popularmoviesv2.fragments.mainactivity.FavoritesFragment;
import com.sameet.popularmoviesv2.fragments.mainactivity.MoviesFragment;
import com.sameet.popularmoviesv2.model.Movie;
import com.sameet.popularmoviesv2.networkutils.NetworkRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "";
    private static final String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular/?api_key=" + API_KEY;
    private static final String HIGHEST_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated/?api_key=" + API_KEY;
    private static final String POPULAR_KEY = "popularKey";
    private static final String HIGHEST_RATED_KEY = "highestRatedKey";
    private Movie mMovie;
    private Movie popularMovie;
    private Movie highestRatedMovie;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mNetworkInfo;
    private NetworkRequest mNetworkRequest;
    private FragmentManager mFragmentManager;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mFragmentManager = getSupportFragmentManager();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(mFragmentManager); // ViewPager gets info from Fragment Manager

        mViewPager.setAdapter(sectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mNetworkRequest = NetworkRequest.getInstance(this); // Creating an instance of the network which will last lifetime of the activity


        if(isNetworkAvailable()) {
            loadData();
            // Pass popularMovie and highestRatedMovie to the MovieFragment


        }



    }

    /**
     * This method loads the Movie data in the main UI thread using Volley
     **/
    private void loadData() {
        StringRequest popularRequest = new StringRequest(Request.Method.GET,
                POPULAR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            popularMovie = jsonParse(response); // Stores the popular movies in a popular movie object;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        StringRequest topRatedRequest = new StringRequest(Request.Method.GET,
                HIGHEST_RATED_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            highestRatedMovie = jsonParse(response); // Stores the popular movies in a highest rated movie object
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        mNetworkRequest.addToRequestQueue(popularRequest);
        mNetworkRequest.addToRequestQueue(topRatedRequest);
    }

    private Movie jsonParse(String response) throws JSONException {
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
        }
        return mMovie;
    }

    /**
     * This method checks for internet connectivty before requesting Movies from API
     **/
    private boolean isNetworkAvailable() {
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new MoviesFragment();
                case 1: return new FavoritesFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {

            }
            return super.getPageTitle(position);
        }
    }
}
