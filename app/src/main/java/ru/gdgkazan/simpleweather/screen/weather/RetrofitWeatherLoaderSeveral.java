package ru.gdgkazan.simpleweather.screen.weather;

import android.content.Context;
import android.support.v4.content.Loader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.gdgkazan.simpleweather.model.City;
import ru.gdgkazan.simpleweather.model.SetCity;
import ru.gdgkazan.simpleweather.network.ApiFactory;

public class RetrofitWeatherLoaderSeveral extends Loader<SetCity> {

    private final Call<SetCity> mCall;

    @Nullable
    private SetCity mCity;

    public RetrofitWeatherLoaderSeveral(Context context, @NonNull String cityID) {
        super(context);
        mCall = ApiFactory.getWeatherService().getWeatherSeveral(cityID);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mCity != null) {
            deliverResult(mCity);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        mCall.enqueue(new Callback<SetCity>() {
            @Override
            public void onResponse(Call<SetCity> call, Response<SetCity> response) {
                mCity = response.body();
                deliverResult(mCity);
            }

            @Override
            public void onFailure(Call<SetCity> call, Throwable t) {
                deliverResult(null);
            }
        });
    }

    @Override
    protected void onStopLoading() {
        mCall.cancel();
        super.onStopLoading();
    }

}
