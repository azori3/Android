package com.example.alimethnani.pidev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alimethnani.pidev.api.GMailSender;

public class PostulerActivity extends AppCompatActivity {


    EditText emailsender;
    EditText emailpassword;
    EditText covercontenu;

    Button sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postuler);

        emailsender = findViewById(R.id.Maildest);
        emailpassword = findViewById(R.id.passwordemail);
        covercontenu = findViewById(R.id.subject);
        sendButton = findViewById(R.id.sendcover);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
                sendEmail2(emailsender.toString(),emailpassword.toString(),covercontenu.toString());

            }
        });


    }

    protected void sendEmail2(String user , String password , String subject) {

        try {
            GMailSender sender = new GMailSender(user, password );
            sender.sendMail("This is Subject",
                    "subject",
                    user,
                    "ali.methnani@esprit.tn");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
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

}

