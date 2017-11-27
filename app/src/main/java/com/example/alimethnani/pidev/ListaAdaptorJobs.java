package com.example.alimethnani.pidev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ali methnani on 24/11/2017.
 */

public class ListaAdaptorJobs extends ArrayAdapter {
    private Context context;
    private ArrayList<Jobs> jobs ;


    public ListaAdaptorJobs(Context context, ArrayList<Jobs> jobs) {
        super(context, 0,jobs);
        this.context = context;
        this.jobs = jobs;
    }

    @Override
    public int getCount() {
        return jobs.size();
    }

    @Override
    public Jobs getItem(int i) {

        return jobs.get(i);
    }




    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        View view = View.inflate(context,R.layout.inputjobs,null);
        final TextView name = view.findViewById(R.id.name);
        final TextView firstname = view.findViewById(R.id.firstname);
        final TextView nb = view.findViewById(R.id.nb);

        Log.e("rr","name :"+name);

        String Contnue = "";
        Contnue = jobs.get(position).getNames() ;

        if (Contnue.length() > 50){
            Log.e("rr","contenue :"+Contnue);
            Contnue = Contnue.substring(0,50);
            Log.e("rr","contenue :"+Contnue);

        }

        name.setText(Contnue +"...");
        firstname.setText(jobs.get(position).getFirstName());
        nb.setText(""+jobs.get(position).getMimo());

        return view;

    }
}


