package ru.gdgkazan.simpleweather.screen.weatherlist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdgkazan.simpleweather.R;
import ru.gdgkazan.simpleweather.model.City;
import ru.gdgkazan.simpleweather.model.SetCity;
import ru.gdgkazan.simpleweather.screen.general.LoadingDialog;
import ru.gdgkazan.simpleweather.screen.general.LoadingView;
import ru.gdgkazan.simpleweather.screen.general.SimpleDividerItemDecoration;
import ru.gdgkazan.simpleweather.screen.weather.RetrofitWeatherLoader;
import ru.gdgkazan.simpleweather.screen.weather.RetrofitWeatherLoaderSeveral;
import ru.gdgkazan.simpleweather.screen.weather.WeatherActivity;

/**
 * @author Artur Vasilov
 */
public class WeatherListActivity extends AppCompatActivity implements CitiesAdapter.OnItemClick, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty)
    View mEmptyView;


    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private CitiesAdapter mAdapter;

    private LoadingView mLoadingView;

    private String[] initialCities;
    private String[] initialCitiesID;
    private List<City> cities;

    private  SetCity mSetCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, false));
        mAdapter = new CitiesAdapter(getInitialCities(), this);
        mRecyclerView.setAdapter(mAdapter);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        loadWeather(false);

        /**
         * TODO : task
         *
         * 1) Load all cities forecast using one or multiple loaders
         * 2) Try to run these requests as most parallel as possible
         * or better do as less requests as possible
         * 3) Show loading indicator during loading process
         * 4) Allow to update forecasts with SwipeRefreshLayout
         * 5) Handle configuration changes
         *
         * Note that for the start point you only have cities names, not ids,
         * so you can't load multiple cities in one request.
         *
         * But you should think how to manage this case. I suggest you to start from reading docs mindfully.
         */
    }

    @Override
    public void onItemClick(@NonNull City city) {

        startActivity(WeatherActivity.makeIntent(this, city.getName(), city));
    }

    @NonNull
    private List<City> getInitialCities() {
        cities = new ArrayList<>();
        initialCities   = getResources().getStringArray(R.array.initial_cities);
        initialCitiesID = getResources().getStringArray(R.array.initial_cites_id);

        /*for (String city : initialCities) {
            cities.add(new City(city));
        }*/

        for (int i=0; i<initialCities.length;i++){
            cities.add(new City(initialCities[i], initialCitiesID[i]));
        }
        return cities;
    }

    @NonNull
    private  String getCitiesIDonLine(){

        String ID="";

        for (String i:initialCitiesID) {

            if (!TextUtils.isEmpty(ID)){
                ID=ID+",";
            }

            ID = ID+i;
        }

        return ID;
    }

    private void loadWeather(boolean restart) {
        mLoadingView.showLoadingIndicator();
        LoaderManager.LoaderCallbacks<SetCity> callbacks = new WeatherCallbacks();
        if (restart) {
            getSupportLoaderManager().restartLoader(R.id.weather_loader_id, Bundle.EMPTY, callbacks);
        } else {
            getSupportLoaderManager().initLoader(R.id.weather_loader_id, Bundle.EMPTY, callbacks);
        }
    }

    @Override
    public void onRefresh() {
        loadWeather(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 4000);
    }

    private  class  WeatherCallbacks implements LoaderManager.LoaderCallbacks<SetCity>{

        @Override
        public Loader<SetCity> onCreateLoader(int id, Bundle args) {
            return new RetrofitWeatherLoaderSeveral(WeatherListActivity.this, getCitiesIDonLine());
        }

        @Override
        public void onLoadFinished(Loader<SetCity> loader, SetCity data) {
                saveWeather(data);

        }

        @Override
        public void onLoaderReset(Loader<SetCity> loader) {

        }
    }

    private void saveWeather(@Nullable SetCity setCity) {

        if (setCity == null) {
            showError();
            return;
        }

        mLoadingView.hideLoadingIndicator();

            mSetCity = setCity;
            changeCity(setCity);

    }

    private void changeCity(SetCity setCity) {

       for(ru.gdgkazan.simpleweather.model.List l:setCity.getList()){

           for (City city:cities){
               if(l.getId().equals(city.getID())){

                   city.setWind(l.getWind());
                   city.setWeather(l.getWeather());
                   city.setMain(l.getMain());
                   break;

               }
           }

       }

    }

    private void showError() {
        mLoadingView.hideLoadingIndicator();
    }


}
