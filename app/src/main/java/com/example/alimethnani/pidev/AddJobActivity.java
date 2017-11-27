package com.example.alimethnani.pidev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AddJobActivity extends AppCompatActivity {
    EditText emailBox;
    EditText titre;
    EditText NbrPoste;
    EditText Profil;
    EditText Datess;
    EditText JobLocation;
    EditText mission;
    android.support.v7.widget.AppCompatEditText  passwordBox;
    Button registerButton;
    TextView loginLink;
    String URL = "http://10.0.2.2:18080/pidev-web/api/jobs/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        emailBox = (EditText)findViewById(R.id.emailBox);
        titre = findViewById(R.id.titleBox);
        mission = findViewById(R.id.contenutBox);
        NbrPoste = findViewById(R.id.NbrPosteBox);
        Profil = findViewById(R.id.ProfilBox);
        Datess=findViewById(R.id.dateDebutBox);
        JobLocation = findViewById(R.id.passwordBox);

        passwordBox = (android.support.v7.widget.AppCompatEditText)findViewById(R.id.passwordBox);
        registerButton = (Button)findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(AddJobActivity.this);

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("titre", titre.getText());
                    jsonBody.put("contenutJob", mission.getText());
                    jsonBody.put("emailJob", emailBox.getText());
                    jsonBody.put("indexationJob",JobLocation.getText());
                    jsonBody.put("profil", Profil.getText());
                    jsonBody.put("infosCompl", Datess.getText());

                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (ExceptionInInitializerError e) {
                    e.printStackTrace();
                }
            }
        });


    }
    }

