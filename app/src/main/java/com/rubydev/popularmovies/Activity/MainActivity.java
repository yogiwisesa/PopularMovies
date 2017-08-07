package com.rubydev.popularmovies.Activity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.rubydev.popularmovies.Helper.EndlessScrollListener;
import com.rubydev.popularmovies.Adapter.MoviesAdapter;
import com.rubydev.popularmovies.Internet.MoviesClient;
import com.rubydev.popularmovies.Model.MoviesDAO;
import com.rubydev.popularmovies.R;
import com.rubydev.popularmovies.Internet.Service;
import com.rubydev.popularmovies.data.MovieContract;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int FAVORITE_MOVIES_LOADER = 0;
    RecyclerView rvPoster;
    ArrayList<MoviesDAO.ResultsBean> listMovies = new ArrayList<>();
    MoviesAdapter adapter;
    int pageApi = 1;
    ProgressDialog loading;
    Boolean popular = true;
    Boolean favorite = false;
    CoordinatorLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvPoster = (RecyclerView) findViewById(R.id.rvPoster);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvPoster.setHasFixedSize(true);
        rvPoster.setLayoutManager(layoutManager);
        cl = (CoordinatorLayout) findViewById(R.id.cl);
        rvPoster.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int totalItemsCount) {
                pageApi = pageApi +1;
                loadData();
            }
        });

        if(savedInstanceState == null || !savedInstanceState.containsKey("popularmovies") || !savedInstanceState.containsKey("favorite")){
            loading = ProgressDialog.show(MainActivity.this, "",
                    "Loading data...", true);
            loadData();
            favorite = false;
        } else {
            listMovies = savedInstanceState.getParcelableArrayList("popularmovies");
            favorite = savedInstanceState.getBoolean("favorite");
        }

        adapter = new MoviesAdapter(this, listMovies);
        rvPoster.setAdapter(adapter);
    }

    public void loadData(){
        MoviesClient client = Service.createService(MoviesClient.class);
        Call<MoviesDAO> call;
        if (popular){
            call = client.getMovie("popular", pageApi);
        } else {
            call = client.getMovie("top_rated", pageApi);
        }

        if (!favorite) {
            call.enqueue(new Callback<MoviesDAO>() {
                @Override
                public void onResponse(Call<MoviesDAO> call, Response<MoviesDAO> response) {
                    MoviesDAO movieDao = response.body();
                    listMovies.addAll(movieDao.getResults());
                    adapter.notifyItemInserted(listMovies.size());
                    if (loading != null) {
                        loading.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<MoviesDAO> call, Throwable t) {
                    if (loading != null) {
                        loading.dismiss();
                        Snackbar snackbar = Snackbar.make(cl, "Loading Failed! \n" + t.toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sortmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        pageApi  = 1;
        listMovies.clear();
        adapter.notifyDataSetChanged();
        switch (item.getItemId()) {
            case R.id.toprated:
                popular = false;
                loadData();
                favorite = false;
                return true;
            case R.id.popular:
                popular = true;
                loadData();
                favorite = false;
                return true;
            case R.id.favorites:
                favorite = true;
                getSupportLoaderManager().initLoader(FAVORITE_MOVIES_LOADER, null, this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("popularmovies", listMovies);
        outState.putBoolean("favorite", favorite);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry.MOVIE_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listMovies.clear();
        adapter.add(data);
        Log.i("Favotite: ", "" + adapter.getSize());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        // Not used
    }

    @Override
    public void onResume() {
        super.onResume();
        if (favorite){
            getSupportLoaderManager().initLoader(FAVORITE_MOVIES_LOADER, null, this);
        }
    }
}
