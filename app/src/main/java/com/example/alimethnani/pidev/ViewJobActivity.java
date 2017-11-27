package com.example.alimethnani.pidev;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.alimethnani.pidev.api.GMailSender;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class ViewJobActivity extends AppCompatActivity {

    String id = "";
    private  String URL_JOBS = "http://10.0.2.2:18080/pidev-web/api/jobs/";
    private Context mContext;
    private TextView textView ;
    private TextView textViewContenu ;
    private TextView textViewperiode  ;
    private TextView textViewProfile ;
    private Button PostulerBtn;
    private Button SignalerBtn ;
    private Button SendMailBtn ;
    private Button envoiAb;


    private String ReportMail = "";
    private String ReportMsg = "";
    String URL = "http://10.0.2.2:18080/pidev-web/api/sign/";

    EditText textMailAb;
    EditText textCausAb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        textView = findViewById(R.id.ali);
        textViewContenu = findViewById(R.id.product_name);
        textViewperiode = findViewById(R.id.product_description);
        textViewProfile = findViewById(R.id.product_price);
         PostulerBtn = findViewById(R.id.SMS);
        SignalerBtn = findViewById(R.id.signaler);
        SendMailBtn = findViewById(R.id.Sendmail);
      //  envoiAb = findViewById(R.id.signalerAb);










        String s = getIntent().getStringExtra("EXTRA_ID");
        System.out.println("AAAA");
        System.out.println(s);
        id= s ;
        URL_JOBS = URL_JOBS + id ;

        System.out.println(URL_JOBS);
        new DownloadTask2().execute();

        SendMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getBaseContext(), PostulerActivity.class);
                startActivity(intent);


              sendEmail();

            }
        });

      PostulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Intent intent = new Intent(getBaseContext(), AddJobActivity.class);
              //  startActivity(intent);


                //sendEmail();

            }
        });

        SignalerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // setContentView(R.layout.dialog_main);


                AlertDialog.Builder builder = new AlertDialog.Builder(ViewJobActivity.this);
                LayoutInflater inflater = (LayoutInflater)ViewJobActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             final   View dialogView = inflater.inflate(R.layout.dialog_main,null);
                builder.setTitle("Abusive");
                builder.setMessage("Send cause of abuse");
                builder.setView(R.layout.dialog_main);
                textMailAb = (EditText)dialogView.findViewById(R.id.editText);
                textCausAb = (EditText)dialogView.findViewById(R.id.editText2);
                //In case it gives you an error for setView(View) try
                builder.setView(inflater.inflate(R.layout.dialog_main, null));
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setNegativeButton(android.R.string.cancel, null);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            textMailAb = (EditText)dialogView.findViewById(R.id.editText);
                            textCausAb = (EditText)dialogView.findViewById(R.id.editText2);
                            Log.e("ziwziw","sld"+textCausAb.getText().toString());
                            RequestQueue requestQueue = Volley.newRequestQueue(ViewJobActivity.this);

                            JSONObject jsonBody = new JSONObject();

                            jsonBody.put("contenu","validation"+textCausAb.getText());
                            jsonBody.put("email","validation"+textMailAb.getText());



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
                         //   dialog.cancel();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }













                    }
                });
                builder.show();


            }
        });




    }



    public void sendSmS (){

        String myURL="https://rest.nexmo.com/sms/json?api_key=5cda4d36&api_secret=975ddd6c0d079a0d&to=216"+
                "50866087"+"&from=CGA&text=SR+You+Can+Consult+our+Policy+For+Makecontract+du+message+:+"+1523;
        System.out.println(myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }


    }


    protected void sendEmail() {

     try {
            GMailSender sender = new GMailSender("hichem.alouis123@gmail.com", "tunis123456");
            sender.sendMail("This is Subject",
                    "This is Body",
                    "hichem.alouis123@gmail.com",
                    "ali.methnani@esprit.tn");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }



    }


    private StringBuffer request(String urlString){

        StringBuffer result = new StringBuffer("");
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent","");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";

            while ((line = rd.readLine()) != null){
                result.append(line);
            }

        } catch (IOException e) {
            // e.printStackTrace();

            ViewJobActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(ViewJobActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });



        }
        return result ;

    }


    public class DownloadTask2 extends AsyncTask<Void, Void, String> {



        @Override
        protected String doInBackground(Void... voids) {
            String resultatCheck = request(URL_JOBS).toString();
            //   getDownloadsFromJson( feedsList, jsonStream);



            return resultatCheck;
        }



        @Override
        protected void onPostExecute(String result) {



            JSONObject myJson = null;
            try {
                myJson = new JSONObject(result);
                // use myJson as needed, for example
                String name = myJson.optString("titre");
                textView.setText("Title : "+name);
                String nameContenue = myJson.optString("contenutJob");
                textViewContenu.setText("contenu : "+nameContenue);
                String namePeriode = myJson.optString("profil");
                textViewperiode.setText("Profil : "+namePeriode);
                String nameProfile = myJson.optString("dateDebJob");
                textViewProfile.setText("Date : "+nameProfile);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
