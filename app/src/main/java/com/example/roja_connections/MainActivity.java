package com.example.roja_connections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText name1,name2;
    private static String URL_REGIST = "http://192.168.43.201/Android_connection/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
    }

    String TAG = MainActivity.class.getSimpleName();

    public void register(View v){
        final String name1 = this.name1.getText().toString().trim();
        final String name2 = this.name2.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST,URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "before json declaration");
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "after json declaration");
                            String success = jsonObject.getString("success");
                            Log.d(TAG, "after success declaration");
                            if(success.equals("1")){
                                Toast.makeText(MainActivity.this, "Data inserted successful", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Data not inserted cos of 0 return ", Toast.LENGTH_LONG).show();

                            }
                    } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Data inserted error "+e.toString(), Toast.LENGTH_LONG).show();
                        }}
                    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Data inserted error on responce "+error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d(TAG, "Before map declaration");
                Map<String,String> params = new HashMap<>();
                params.put("name1",name1);
                Log.d(TAG, "after name1 declaration");
                params.put("name2",name2);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
