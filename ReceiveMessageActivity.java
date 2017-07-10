package com.example.pawel.usoswat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class ReceiveMessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);
        Intent intent = getIntent();
        ArrayList<String> stronaOcen = new ArrayList<String>();
        stronaOcen = intent.getStringArrayListExtra("stronaOcen");
    }

    public void onClickDegreePage(View view) throws IOException
    {
        Intent intent = new Intent(this, degreePage.class);
        startActivity(intent);
    }
}
