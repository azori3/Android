package com.example.alimethnani.pidev;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.alimethnani.pidev.api.GMailSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String URL_JOBS = "http://10.0.2.2:18080/pidev-web/api/jobs";
    private Context mContext;
    private Button mButtonDo;
    ListView listjobs;
    private ListaAdaptorJobs listaAdaptorJobs;
    ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getApplicationContext();

        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddJobActivity.class);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(intent);
            }
        });




     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    */



//        BackgroundMail.newBuilder(this)
//                .withUsername("hichem.alouis123@gmail.com")
//                .withPassword("tunis123456")
//                .withMailto("ali.methnani@esprit.tn")
//                .withType(BackgroundMail.TYPE_PLAIN)
//                .withSubject("this is the subjectfffffff")
//                .withBody("this is the bodyfffffffff")
//                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
//                    @Override
//                    public void onSuccess() {
//                        //do some magic
//                    }
//                })
//                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
//                    @Override
//                    public void onFail() {
//                        //do some magic
//                    }
//                })
//                .send();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        listjobs = findViewById(R.id.listjobs);
        // Initialize a new JsonArrayRequest instance

        final ArrayList<Jobs> jobs= new ArrayList<>();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_JOBS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count=0;
                        while (count<response.length()){
                            try {
                                // Loop through the array elements

                                // Get current json object
                                JSONObject student = response.getJSONObject(count);
                                Log.i("1","4");
                                // Get the current student (json object) data
                                String firstName = student.getString("titre");
                                String names = student.getString("contenutJob");
                                String mimo = student.getString("nbrPOste");
                                String id = student.getString("id");
                                Jobs e = new Jobs(firstName,Integer.parseInt(mimo),names , Integer.parseInt(id));


                                jobs.add(e);

                                String lastName = student.getString("nbrPOste");


                              //  String age = student.getString("nbrPoste");

                                // Display the formatted json data in text view
                              /*  mTextView.append(firstName +" " + lastName +"\nAge : " + age);
                                mTextView.append("\n\n");*/
                                count++;
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("","azdkal"+jobs);
                        listaAdaptorJobs = new ListaAdaptorJobs(getApplicationContext(),jobs);
                        listjobs.setAdapter(listaAdaptorJobs);
                        listaAdaptorJobs.notifyDataSetChanged();

                    }

                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                // Do something when error occurred
                Log.i("1","*****");

            }
        }
        );
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);



        listjobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("1", String.valueOf(jobs.get(i).getId()));
                Intent intent = new Intent(getBaseContext(), ViewJobActivity.class);
                intent.putExtra("EXTRA_ID", String.valueOf(jobs.get(i).getId()));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
