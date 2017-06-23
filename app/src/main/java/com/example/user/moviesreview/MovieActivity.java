package com.example.user.moviesreview;

/**
 * Created by user on 19/06/2017.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieActivity extends AppCompatActivity {

    TextView titulo;
    TextView sumario;
    TextView data;
    ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filme_completo);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.filme_toolBar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titulo = (TextView) findViewById(R.id.filme_titulo_textView);
        sumario = (TextView) findViewById(R.id.filme_sumario_textView);
        data = (TextView) findViewById(R.id.dateTextView);
        imagem = (ImageView) findViewById(R.id.filme_image_imageView);


        titulo.setText(getIntent().getStringExtra("titulo"));
        sumario.setText(getIntent().getStringExtra("sumario"));
        data.setText(getIntent().getStringExtra("data"));
        String imageURL = getIntent().getStringExtra("imagem");

        if (TextUtils.isEmpty(imageURL)){
            imagem.setImageDrawable(getDrawable(R.drawable.ic_block_black_24dp));
        }
        else {
            new LoadImageTask(imagem).execute(imageURL);
        }


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


    private class LoadImageTask extends AsyncTask<String,Void,Bitmap> {
        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;

            HttpURLConnection connection = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                try (InputStream inputStream = connection.getInputStream()) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
