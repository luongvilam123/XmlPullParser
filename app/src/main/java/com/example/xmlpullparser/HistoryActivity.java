package com.example.xmlpullparser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

TextView txthis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<History> list = (ArrayList<History>) args.getSerializable("ARRAYLIST");
      
        txthis=(TextView)findViewById(R.id.txthis);
        for (int i=0; i<list.size();i++) {
            txthis.setText(list.get(i).toString());
        }
    }

}