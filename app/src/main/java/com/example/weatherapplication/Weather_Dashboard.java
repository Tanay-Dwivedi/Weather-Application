package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Weather_Dashboard extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;
    TextView countryName, cityName, tempShow, dating, latView, longView, humView, presView, riseView, setView, windView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_weather_dashboard);

        editText = findViewById(R.id.enterCity);
        button = findViewById(R.id.searchButton);
        countryName = findViewById(R.id.countryDisplay);
        cityName = findViewById(R.id.cityDisplay);
        tempShow = findViewById(R.id.tempDisplay);
        imageView = findViewById(R.id.weatherImageDisplay);
        dating = findViewById(R.id.dateAndTimeDisplay);
        latView = findViewById(R.id.latitudeView);
        longView = findViewById(R.id.longitudeView);
        humView = findViewById(R.id.humidityView);
        riseView = findViewById(R.id.sunriseView);
        setView = findViewById(R.id.sunsetView);
        presView = findViewById(R.id.pressureView);
        windView = findViewById(R.id.windSpeedView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findWeather();
            }
        });

    }

    public void findWeather() {

        final String city = editText.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=e2a31b14bf416d1c81effe80ebdf47de";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Calling the API
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Fetching the Country
                    JSONObject object1 = jsonObject.getJSONObject("sys");
                    String countryFetch = object1.getString("country");
                    countryName.setText(countryFetch+" :");

                    //Fetching the City
                    String cityFetch = jsonObject.getString("name");
                    cityName.setText(cityFetch);

                    // Finding the Temperature
                    JSONObject object2 = jsonObject.getJSONObject("main");
                    String tempFetch = object2.getString("temp");
                    tempShow.setText(tempFetch+" °K");

                    // Fetching the Temperature related Image
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject object3 = jsonArray.getJSONObject(0);
                    String img = object3.getString("icon");
                    Picasso.get().load("http://openweathermap.org/img/wn/"+img+"@2x.png").into(imageView);

                    // Fetching the date and time
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat std = new SimpleDateFormat("dd/MM/yyyy \nHH:mm:ss");
                    String date = std.format(calendar.getTime());
                    dating.setText(date);

                    // Fetching the Latitude of the City
                    JSONObject object4 = jsonObject.getJSONObject("coord");
                    String latitude = object4.getString("lat");
                    latView.setText(latitude + "° N");

                    // Fetching the Longitude of the City
                    JSONObject object5 = jsonObject.getJSONObject("coord");
                    String longitude = object5.getString("lon");
                    longView.setText(longitude + "° E");

                    // Fetching the Humidity of the City
                    JSONObject object6 = jsonObject.getJSONObject("main");
                    String humidity = object6.getString("humidity");
                    humView.setText(humidity + "%");

                    // Fetching the Sun-rise of the City
                    JSONObject object7 = jsonObject.getJSONObject("sys");
                    String sunRise = object7.getString("sunrise");
                    riseView.setText(sunRise);

                    // Fetching the Sun-set of the City
                    JSONObject object8 = jsonObject.getJSONObject("sys");
                    String sunSet = object8.getString("sunset");
                    setView.setText(sunSet);

                    // Fetching the Pressure of the City
                    JSONObject object9 = jsonObject.getJSONObject("main");
                    String pressure = object9.getString("pressure");
                    presView.setText(pressure +" hPa");

                    // Fetching the Wind Speed of the City
                    JSONObject object10 = jsonObject.getJSONObject("wind");
                    String windSpeed = object10.getString("speed");
                    windView.setText(windSpeed +" km/h");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Weather_Dashboard.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue1 = Volley.newRequestQueue(Weather_Dashboard.this);
        requestQueue1.add(stringRequest);

    }

}