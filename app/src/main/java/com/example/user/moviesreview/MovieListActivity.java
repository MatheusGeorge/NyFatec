package com.example.user.moviesreview;

/**
 * Created by user on 19/06/2017.
 */
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {
    private ArrayList<Movie> filmeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MovieArrayAdapter movieArrayAdapter;


    private TextView titulo_filme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.filme_rv);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.filme_toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.filme_RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieArrayAdapter = new MovieArrayAdapter(filmeList);
        recyclerView.setAdapter(movieArrayAdapter);


        titulo_filme = (TextView) findViewById(R.id.filme_titulo_Text_view);

        String procurarFilme = getIntent().getStringExtra("nomeFilme");
        new GetFilmesTask(findViewById(android.R.id.content)).execute(procurarFilme);
        String titulo = getIntent().getStringExtra("nomeFilme");

        titulo_filme.setText(getString(R.string.resultado_busca)+titulo);
    }

    //upButton setinha na toolbar para voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetFilmesTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        View rootView;
        GetFilmesTask(View view){
            this.rootView = view;
        }
        protected ArrayList<Movie> doInBackground(String... filmes) {
            URL url = createURL(filmes[0]);

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    StringBuilder builder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } catch (IOException e) {
                        Snackbar.make(findViewById(R.id.moviesLayout), getString(R.string.erro_leitura), Snackbar.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    return convertJSONToArrayList(new JSONObject(builder.toString()));
                }
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.moviesLayout), getString(R.string.erro_conexao), Snackbar.LENGTH_LONG).show();
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return new ArrayList<>();
        }

        protected void onPostExecute(ArrayList<Movie> movies) {
            if(movies.size() == 0){
                Snackbar.make(rootView, getString(R.string.erro_procura), Snackbar.LENGTH_LONG).show();
            }
            else{

                filmeList = movies;
                movieArrayAdapter = new MovieArrayAdapter(filmeList);
                recyclerView.setAdapter(movieArrayAdapter);
                recyclerView.smoothScrollToPosition(0);
            }
        }
    }



    public URL createURL(String filme) {

        String apiKey = getString(R.string.apiKey);
        String baseUrl = getString(R.string.stringURL);
        try {
            String urlString = baseUrl + "?api_key=" + apiKey + "&query=" + URLEncoder.encode(filme, "UTF-8");
            return new URL(urlString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Movie> convertJSONToArrayList (JSONObject filmesJSON){

        ArrayList<Movie> filmeList = new ArrayList<>();

        try{
            JSONArray filmes = filmesJSON.getJSONArray("results");
            for (int i = 0; i < filmes.length(); i++){
                JSONObject filme = filmes.getJSONObject(i);
                String titulo = filme.getString("display_title");
                String data_public = filme.getString("publication_date");
                String sumario = filme.getString("summary_short");
                String link = filme.getJSONObject("link").getString("url");
                String imagem = "";
                if (!filme.isNull("multimedia") && filme.getJSONObject("multimedia") != JSONObject.NULL)
                    imagem = filme.getJSONObject("multimedia").getString("src");

                filmeList.add(new Movie(imagem, titulo, sumario, data_public, link));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return filmeList;
    }
}
