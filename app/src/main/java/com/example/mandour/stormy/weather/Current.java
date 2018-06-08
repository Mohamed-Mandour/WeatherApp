package com.example.mandour.stormy.weather;

import android.graphics.drawable.Drawable;

import com.example.mandour.stormy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
// this class represent the current weather object.
public class Current {
    private String mIcon;
    private Long mTime;
    private Double mTemperture;
    private Double mHumiditry;
    private Double mPreesure;
    private Double mPricepChannce;
    private String mSummary;
    private String mTimeZome;
    private Double mWindSpeed;
    private Double mWindGust;
    private Double mCloudCover;
    private Double mVisibility;



    public String getTimeZome() {
        return mTimeZome;
    }

    public void setTimeZome(String timeZome) {
        mTimeZome = timeZome;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getIconImage(){
        return Forecast.getIconId(mIcon);
    }



    public void setIcon(String icon) {
        mIcon = icon;
    }

    public Long getTime() {

        return mTime;
    }

    // use the time formated object to custom the time
    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZome()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);


        return timeString;
    }


    public void setTime(Long time) {
        mTime = time;
    }
    // get the temperture and converted to Celsius
    public int getTemperture() {
        return (int) Math.round(mTemperture - 32) * 5 / 9;
    }

    public void setTemperture(Double temperture) {
        mTemperture = temperture;
    }

    public Double getHumiditry() {
        return mHumiditry;
    }

    public void setHumiditry(Double humiditry) {
        mHumiditry = humiditry;
    }

    public Double getPricepChannce() {
        return mPricepChannce;
    }

    public void setPricepChannce(Double pricepChannce) {
        mPricepChannce = pricepChannce;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getPreesure() {
        return (int) Math.round(mPreesure) ;
    }

    public void setPreesure(Double preesure) {
        mPreesure = preesure;
    }

    public Double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public Double getWindGust() {
        return mWindGust;
    }

    public void setWindGust(Double windGust) {
        mWindGust = windGust;
    }

    public Double getCloudCover() {
        return mCloudCover;
    }

    public void setCloudCover(Double cloudCover) {
        mCloudCover = cloudCover;
    }

    public Double getVisibility() {
        return mVisibility;
    }

    public void setVisibility(Double visibility) {
        mVisibility = visibility;
    }
}
