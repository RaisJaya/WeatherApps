package com.raisjayadevelop.weatherapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.xml.transform.OutputKeys;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    TextView tvCity, tvTempt, tvDesc;
    ImageView imgWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvCity = (TextView) findViewById(R.id.tv_city);
        tvTempt = (TextView) findViewById(R.id.tv_temp);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        imgWeather = (ImageView) findViewById(R.id.image_icon_weather);

        String city = getIntent().getStringExtra(MainActivity.EXTRA_CITY);
        tvCity.setText(city);

        String url = "https://api.openweathermap.org/data/2.5/weather?" +
                "q=" + city + "&appid=2b93bff163273bab99783c218f9e932a";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText( WeatherActivity.this, getResources().getString(R.string.failur_connect),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final  String responseData = response.body().string();
                try {
                    JSONObject objData = new JSONObject(responseData);
                    final JSONArray arrayWeather = objData.getJSONArray("weather");
                    final JSONObject objWeather = new JSONObject(arrayWeather.get(0).toString());

                    final JSONObject objTemp = new JSONObject(objData.get("main").toString());
                    double temp = (objTemp.getDouble("temp"))-273.15;
                    final String tempCel = String.valueOf(String.format("%.2f", temp));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tvTempt.setText(tempCel+"°C");
                                tvDesc.setText(objWeather.get("main").toString());

                                String urlIcon = "http://openweather.org/img/w/" +objWeather.get("icon")+".png";
                                Glide.with(WeatherActivity.this).load(urlIcon).into(imgWeather);

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });

                }catch  (JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }
}