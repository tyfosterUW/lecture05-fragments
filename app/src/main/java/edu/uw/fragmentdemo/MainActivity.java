package edu.uw.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieSelectedListener,
        SearchFragment.OnSearchListener {

    private static final String TAG = "MainActivity";
    private SearchFragment searchFragment;
    private MoviesFragment moviesFragment;
    private DetailFragment detailFragment;

    private ViewPager vPager;

    private PagerAdapter vPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vPager = (ViewPager) findViewById(R.id.pageViewer);
        vPagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(vPagerAdapter);
    }


    //respond to search button clicking
    public void handleSearchClick(View v){
        EditText text = (EditText)findViewById(R.id.txtSearch);
        String searchTerm = text.getText().toString();

        //add a new results fragment to the page
        MoviesFragment fragment = MoviesFragment.newInstance(searchTerm);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, "MoviesFragment");
        ft.addToBackStack(null); //remember for the back button
        ft.commit();
    }

    @Override
    public void onMovieSelected(Movie movie) {
        DetailFragment detailFragment = DetailFragment.newInstance(movie.toString(), movie.imdbId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailFragment, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSearchSubmitted(String searchTerm) {

    }

    private class MoviePagerAdapter extends FragmentStatePagerAdapter {

        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            if(position == 0) return searchFragment;
            if(position == 1) return moviesFragment;
            if(position == 2) return detailFragment;
            return null;
        }

        public int getCount() {
            if(moviesFragment == null) {
                return 1;
            } else if (detailFragment == null) {
                return 2;
            } else {
                return 3;
            }
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
