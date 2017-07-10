package com.example.pawel.usoswat;


import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;


public class MainActivity extends AppCompatActivity
{

    String userName;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // uprawnienie internetowe
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onClickSubmit(View view) throws IOException
    {
        Intent intent = new Intent(this, ReceiveMessageActivity.class);
       // intent.putExtra("stronaOcen", stronaOcen);
        startActivity(intent);
//        EditText userN = (EditText)findViewById(R.id.userN);
//        userName = userN.getText().toString();
//        EditText passw = (EditText)findViewById(R.id.Passwd);
//        password = passw.getText().toString();
//
//        ArrayList<String> stronaOcen = new ArrayList<String>();
//        authenticationProcess auth = new authenticationProcess();
//        stronaOcen = auth.verificationTry(userName,password);

//        if (stronaOcen==null)
//        {
//            Toast.makeText(MainActivity.this, "Nieprawidłowy login lub hasło!",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this, "Udana próba logowania!",Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(this, ReceiveMessageActivity.class);
//            intent.putExtra("stronaOcen", stronaOcen);
//            startActivity(intent);
//        }

    }
}