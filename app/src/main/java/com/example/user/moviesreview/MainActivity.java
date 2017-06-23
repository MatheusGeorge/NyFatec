package com.example.user.moviesreview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText procurarET;
    String verifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        procurarET = (EditText) findViewById(R.id.procurarEditText);
        verifica = procurarET.getText().toString();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.filme_toolBar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab_procurar);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializarListActivity();
            }
        });

    }

    public void inicializarListActivity() {
        Intent intent = new Intent(this, MovieListActivity.class);
        intent.putExtra("nomeFilme", procurarET.getText().toString());
        startActivity(intent);
    }
}

