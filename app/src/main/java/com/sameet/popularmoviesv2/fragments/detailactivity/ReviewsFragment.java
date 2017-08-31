package com.sameet.popularmoviesv2.fragments.detailactivity;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sameet.popularmoviesv2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends android.support.v4.app.Fragment {


    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

}
