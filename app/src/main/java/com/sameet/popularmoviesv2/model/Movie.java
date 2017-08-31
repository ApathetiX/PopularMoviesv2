package com.sameet.popularmoviesv2.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private double mVoteAverage;
    private String mReleaseDate;
    private int mTrailerPath;
    private String mReviews;


    public Movie(String title, String posterPath, String overview, double voteAverage, String releaseDate, int trailerPath, String reviews) {
        mTitle = title;
        mPosterPath = posterPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
        mTrailerPath = trailerPath;
        mReviews = reviews;
    }


    public String getTitle() {
        return mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public int getTrailerPath() {
        return mTrailerPath;
    }

    public String getReviews() {
        return mReviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeDouble(mVoteAverage);
        dest.writeString(mReleaseDate);
        dest.writeInt(mTrailerPath);
        dest.writeString(mReviews);
    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readDouble();
        mReleaseDate = in.readString();
        mTrailerPath = in.readInt();
        mReviews = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

