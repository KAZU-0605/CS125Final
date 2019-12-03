package com.example.cs125final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Countryelements> acountrylist;
    private CountryAdapter aAdapter;
    private static RequestQueue requestQueue;
    private static final String TAG = "cs125final:Main";
    //private TextView startView;
    //private TextView endView;
    private TextView amountView;
    private TextView outputView;
    private String startCurrency;
    private String endCurrency;
    private double exchangeAmount;
    private Spinner dropDown1;
    private Spinner dropDown2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startView = findViewById(R.id.start);
        //endView = findViewById(R.id.end);
        amountView = findViewById(R.id.amount);
        outputView = findViewById(R.id.result);
        requestQueue = Volley.newRequestQueue(this);

        Button convert = findViewById(R.id.convert);
        firstList();
        dropDown1 = findViewById(R.id.spinner_start);
        aAdapter = new CountryAdapter(this, acountrylist);
        dropDown1.setAdapter(aAdapter);
        dropDown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Countryelements clicked = (Countryelements) parent.getItemAtPosition(position);
                String clickedName = clicked.getCountryName();
                Toast.makeText(MainActivity.this, clickedName + " selected", Toast.LENGTH_SHORT).show();
                startCurrency = clickedName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dropDown2 = findViewById(R.id.spinner_end);
        aAdapter = new CountryAdapter(this, acountrylist);
        dropDown2.setAdapter(aAdapter);
        dropDown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Countryelements clicked = (Countryelements) parent.getItemAtPosition(position);
                String clickedName = clicked.getCountryName();
                Toast.makeText(MainActivity.this, clickedName + " selected", Toast.LENGTH_SHORT).show();
                endCurrency = clickedName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Generate Button was pressed.");
                try {
                    //startCurrency = startView.getText().toString();
                    startAPICall(startCurrency);
                } catch (Exception ignored) { }
            }
        });
    }
    private void firstList() {
        acountrylist = new ArrayList<>();
        acountrylist.add(new Countryelements("CNY", R.drawable.china));
        acountrylist.add(new Countryelements("USD", R.drawable.usa));
        acountrylist.add(new Countryelements("R", R.drawable.russia));
        acountrylist.add(new Countryelements("CAD", R.drawable.canada));
        acountrylist.add(new Countryelements("JPY", R.drawable.japan));
    }

    private void startAPICall(final String start) {
        try {
            String URL = "https://api.exchangeratesapi.io/latest?base=" + start;
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            finishAPICall(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    outputView.setText("error with web request");
                }
            }
            );
            requestQueue.add(objectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finishAPICall (JSONObject response) {
        try {
            JSONObject result = response.getJSONObject("rates");
            exchangeAmount = Double.valueOf(amountView.getText().toString());
            double rate = result.getDouble(endCurrency);
            outputView.setText(Double.toString(exchangeAmount * rate));
        } catch (JSONException ignored) { }
    }
}
