package ru.gdgkazan.simpleweather.screen.weather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.gdgkazan.simpleweather.R;
import ru.gdgkazan.simpleweather.model.City;
import ru.gdgkazan.simpleweather.screen.general.LoadingDialog;
import ru.gdgkazan.simpleweather.screen.general.LoadingView;

public class WeatherActivity extends AppCompatActivity  {

    private static final String CITY_NAME_KEY = "city_name";
    public static final String CITY_OBJ       = "city_object";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.weather_layout)
    View mWeatherLayout;

    @BindView(R.id.weather_main)
    TextView mWeatherMain;

    @BindView(R.id.temperature)
    TextView mTemperature;

    @BindView(R.id.pressure)
    TextView mPressure;

    @BindView(R.id.humidity)
    TextView mHumidity;

    @BindView(R.id.wind_speed)
    TextView mWindSpeed;

    @BindView(R.id.error_layout)
    TextView mErrorLayout;



    private LoadingView mLoadingView;

    private String mCityName;
    private  City mCity;

    @NonNull
    public static Intent makeIntent(@NonNull Activity activity, @NonNull String cityName, @NonNull City city) {
        Intent intent = new Intent(activity, WeatherActivity.class);
        intent.putExtra(CITY_NAME_KEY, cityName);
        intent.putExtra(CITY_OBJ, city);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        mCityName = getIntent().getStringExtra(CITY_NAME_KEY);
        mToolbarTitle.setText(mCityName);

        mCity = (City) getIntent().getSerializableExtra(CITY_OBJ);

        mLoadingView = LoadingDialog.view(getSupportFragmentManager());



        showWeather(mCity);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.error_layout)
    public void onErrorLayoutClick() {
        showWeather(mCity);
    }

   /* private void loadWeather(boolean restart) {
        mWeatherLayout.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.GONE);
        mLoadingView.showLoadingIndicator();
        *//*LoaderManager.LoaderCallbacks<City> callbacks = new WeatherCallbacks();
        if (restart) {
            getSupportLoaderManager().restartLoader(R.id.weather_loader_id, Bundle.EMPTY, callbacks);
        } else {
            getSupportLoaderManager().initLoader(R.id.weather_loader_id, Bundle.EMPTY, callbacks);
        }*//*
    }*/

    private void showWeather(@Nullable City city) {
        if (city == null || city.getMain() == null || city.getWeather() == null
                || city.getWind() == null) {
            showError();
            return;
        }
        mLoadingView.hideLoadingIndicator();

        mWeatherLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);

        mToolbarTitle.setText(city.getName());
        mWeatherMain.setText(city.getWeather().getMain());
        mTemperature.setText(getString(R.string.f_temperature, city.getMain().getTemp()));
        mPressure.setText(getString(R.string.f_pressure, city.getMain().getPressure()));
        mHumidity.setText(getString(R.string.f_humidity, city.getMain().getHumidity()));
        mWindSpeed.setText(getString(R.string.f_wind_speed, city.getWind().getSpeed()));
    }

    private void showError() {
        mLoadingView.hideLoadingIndicator();
        mWeatherLayout.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }


    /*private class WeatherCallbacks implements LoaderManager.LoaderCallbacks<City> {

        @Override
        public Loader<City> onCreateLoader(int id, Bundle args) {
            return new RetrofitWeatherLoader(WeatherActivity.this, mCityName);
        }

        @Override
        public void onLoadFinished(Loader<City> loader, City city) {
            showWeather(city);
        }

        @Override
        public void onLoaderReset(Loader<City> loader) {
            // Do nothing
        }
    }*/
}
