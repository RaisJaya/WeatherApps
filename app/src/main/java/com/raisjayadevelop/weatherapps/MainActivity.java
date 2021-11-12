package com.raisjayadevelop.weatherapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected static final String EXTRA_CITY ="extra_city" ;
    ListView lvCity;
    String[] city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCity = (ListView) findViewById(R.id.lv_city);
        city = new String[]{"Jakarta", "Tokyo", "Moscow", "Mecca", "London", "Chicago"};


        CityAdapter adapter = new CityAdapter();
        lvCity.setAdapter(adapter);
    }

    class CityAdapter extends ArrayAdapter<String>{
        public CityAdapter(){
            super(MainActivity.this, android.R.layout.simple_list_item_1, city);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row;
            if(convertView == null){
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }else {
                row = convertView;
            }

            TextView tv = (TextView) row.findViewById(android.R.id.text1);
            tv.setText(getItem(position));

            row.setOnClickListener(v->{
                Log.i("CLICK", getItem(position));
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                intent.putExtra(EXTRA_CITY, getItem(position));
                startActivity(intent);
            });

            return row;
        }
    }
}