package com.sameet.popularmoviesv2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sameet.popularmoviesv2.fragments.mainactivity.FavoritesFragment;
import com.sameet.popularmoviesv2.fragments.mainactivity.MoviesFragment;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
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
                case 0: return getResources().getText(R.string.title_movie);
                case 1: return getResources().getText(R.string.title_favorites);
            }
            return super.getPageTitle(position);
        }
    }
}
