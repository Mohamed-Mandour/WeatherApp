package com.example.mandour.stormy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mandour.stormy.ui.AlertDialogFragment;
import com.example.mandour.stormy.ui.NetworkDialogFragment;
import com.example.mandour.stormy.weather.Current;
import com.example.mandour.stormy.weather.Day;
import com.example.mandour.stormy.weather.Forecast;
import com.example.mandour.stormy.weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    // constant for the daliy forecast
    public static final String DALIY_FORECAST = "DALIY_FORECAST";
    public static final String Hourly_FORECAST = "Hourly_FORECAST";

    private Forecast mForecast;


    @BindView(R.id.temperatureLabel)
    TextView mTemperatureLabel;
    @BindView(R.id.timeLabel)
    TextView mTimeValue;
    @BindView(R.id.humidityValue)
    TextView mHumidityValue;
    @BindView(R.id.summaryLabel)
    TextView mSummaryValue;
    @BindView(R.id.pressureValue)
    TextView mPressureValue;
    @BindView(R.id.iconImageView)
    ImageView mIconImageView;
    @BindView(R.id.refreshImageView)
    ImageView mrefreshImageView;
    @BindView(R.id.progressBar3)
    ProgressBar mProgressBar;
    @BindView(R.id.cloudCoverValue)
    TextView mCloudCoverValue;
    @BindView(R.id.visibilityValue)
    TextView mVisibilityValue;
    @BindView(R.id.windSpeedValue)
    TextView mWindSpeedValue;
    @BindView(R.id.windGustValue)
    TextView mWindGustValue;
    @BindView(R.id.locationLabel)
    TextView mLocationLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        final double latitude = 51.5074;
        final double longtude = 0.1278;

        mProgressBar.setVisibility(View.INVISIBLE);
        mrefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longtude);
            }
        });

        getForecast(latitude, longtude);
    }

    // request to the weather API to get the data
    // check first wheather there is network connection or not
    private void getForecast(double latitude, double longtude) {
        Log.d(TAG, "getForecast called with " + latitude + "and " + longtude);
        String apiValue = "a1fca94d101abf452cf8c428d35f5978";
        String forecastUrl = "https://api.darksky.net/forecast/" + apiValue + "/" + latitude + "," + longtude;

        boolean networkAvailable = isNetworkAvaliable();
        if (networkAvailable) {
            Log.d(TAG, "isNetworkAvailable() " + networkAvailable);
            togglerRefersh();
            OkHttpClient client = new OkHttpClient();
            Log.d(TAG, "client" + client);
            Request request = new Request.Builder().url(forecastUrl).build();
            Log.d(TAG, "request " + request.body());

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure " + call.isExecuted());
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            togglerRefersh();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "onResponse" + call);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            togglerRefersh();
                        }
                    });
                    try {
                        String jsonDate = response.body().string();
                        Log.v(TAG, jsonDate);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonDate);
                            Log.d(TAG, "mForecast" + mForecast);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caugth: " + e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caugth: " + e);

                    }
                }
            });

        } else {
            AlertUserAboutNetworkState();
        }
    }

    private void togglerRefersh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mrefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mrefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    // refresh the data
    private void update() {
        Current current = mForecast.getCurrent();

        mTemperatureLabel.setText(current.getTemperture() + "");
        mTimeValue.setText("At " + current.getFormattedTime());
        mHumidityValue.setText(current.getHumiditry().toString());
        mLocationLabel.setText(current.getTimeZome());
        mSummaryValue.setText(current.getSummary());
        mWindGustValue.setText(current.getWindGust() + "");
        mWindSpeedValue.setText(current.getWindSpeed() + "");
        mVisibilityValue.setText(current.getVisibility() + "");
        mCloudCoverValue.setText(current.getCloudCover() + "");
        mPressureValue.setText(current.getPreesure() + "");


        Drawable drawble = getResources().getDrawable(current.getIconImage());
        mIconImageView.setImageDrawable(drawble);
    }


    private boolean isNetworkAvaliable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvalible = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvalible = true;
        }
        return isAvalible;
    }

    private void alertUserAboutError() {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        alertDialogFragment.show(getFragmentManager(), "error dialog");
    }

    private void AlertUserAboutNetworkState() {
        NetworkDialogFragment networkDialogFragment = new NetworkDialogFragment();
        networkDialogFragment.show(getFragmentManager(), "Network Failure");
    }

    private Forecast parseForecastDetails(String jsonDate) throws JSONException {

        Forecast forecast = new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonDate));
        forecast.setHours(getHourlyForecast(jsonDate));
        forecast.setDays(getDailyForecast(jsonDate));
        return forecast;
    }

    private Day[] getDailyForecast(String jsonDate) throws JSONException {
        // create a new json object
        JSONObject forecast = new JSONObject(jsonDate);
        String timeZone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");
        // create a new daily data array
        Day[] days = new Day[data.length()];
        // iterate through data json array to populate the data
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();
            day.setSummary(jsonDay.getString("summary"));
            day.setTempertureMax(jsonDay.getDouble("temperatureMax"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimeZone(timeZone);

            days[i] = day;
        }
        return days;
    }

    private Hour[] getHourlyForecast(String jsonDate) throws JSONException {
        // get the json object
        JSONObject forecast = new JSONObject(jsonDate);
        String timeZone = forecast.getString("timezone");
        // get the hourly json object
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");
        // create a new hour data array
        Hour[] hours = new Hour[data.length()];
        // iterate through data json array to populate the data
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperture(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timeZone);
            hours[i] = hour;
        }
        return hours;
    }


    private Current getCurrentDetails(String jsonDate) throws JSONException {
        JSONObject forecast = new JSONObject(jsonDate);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "Form Json :" + timezone);

        JSONObject currently = forecast.getJSONObject("currently");
        Current current = new Current();

        current.setHumiditry(currently.getDouble("humidity"));
        current.setPreesure(currently.getDouble("pressure"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPricepChannce(currently.getDouble("precipProbability"));
        current.setTemperture(currently.getDouble("apparentTemperature"));
        current.setSummary(currently.getString("summary"));
        current.setWindSpeed(currently.getDouble("windSpeed"));
        current.setCloudCover(currently.getDouble("cloudCover"));
        current.setVisibility(currently.getDouble("visibility"));
        current.setWindGust(currently.getDouble("windGust"));
        current.setTimeZome(timezone);

        Log.d(TAG, current.getFormattedTime());
        return current;
    }

    @OnClick(R.id.DailyButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DALIY_FORECAST, mForecast.getDays());
        startActivity(intent);
    }

    @OnClick(R.id.HourlyButton)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(Hourly_FORECAST, mForecast.getHours());
        startActivity(intent);
    }
}
