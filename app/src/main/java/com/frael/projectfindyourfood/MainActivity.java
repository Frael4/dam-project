package com.frael.projectfindyourfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.frael.projectfindyourfood.view.SearchInMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToSearchMap(View view){
        Intent searchMap = new Intent(this, SearchInMap.class);
        startActivity(searchMap);
    }
}