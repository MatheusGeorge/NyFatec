package com.example.user.moviesreview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 19/06/2017.
 */

public class MovieArrayAdapter extends RecyclerView.Adapter<MovieArrayAdapter.ViewHolder>{

private ArrayList<Movie> myFilmes;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView data_public;
        ImageView img;
        private final Context context;

        public ViewHolder(final View itemView) {
            super(itemView);

            img = (ImageButton)itemView.findViewById(R.id.imagemFilme);
            titulo = (TextView)itemView.findViewById(R.id.filme_tituloTextView);
            data_public = (TextView)itemView.findViewById(R.id.data_public_textView);
            context = itemView.getContext();
        }
    }

    public MovieArrayAdapter(ArrayList<Movie> myItens){
        this.myFilmes = myItens;
    }

    @Override
    public MovieArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder ViewHolder, int position) {

        final Movie movie = myFilmes.get(position);

        ViewHolder.titulo.setText(movie.titulo);
        ViewHolder.data_public.setText(movie.data_public);
        if (movie.imagem == ""){
            ViewHolder.img.setImageResource(R.drawable.ic_block_black_24dp);
        }
        else{
            new LoadImageTask(ViewHolder.img).execute(movie.imagem);
        }

        ViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilmeDetails(ViewHolder.context, movie);

            }
        });

    }

    @Override
    public int getItemCount() {
        return myFilmes.size();
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

    public void openFilmeDetails(Context context, Movie movie){
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra("titulo", movie.titulo);
        intent.putExtra("sumario", movie.sumario);
        intent.putExtra("data", movie.data_public);
        intent.putExtra("imagem", movie.imagem);
        context.startActivity(intent);
    }

}


//*****************************************************************************************************
//TESTE COM LISTVIEW


/*import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmeArrayAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> myFilmes;
    private Map<String, Bitmap> bitmaps = new HashMap<>();

    public FilmeArrayAdapter(Context context, List<Movie> forecast) {
        super(context, -1, forecast);
    }



    public static class ViewHolder {
        TextView titulo;
        TextView data_public;
        ImageButton img;

    }

    public FilmeArrayAdapter(ArrayList<Movie> myItens){
        this.myFilmes = myItens;
    }

 @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie filme = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.img = (ImageButton) convertView.findViewById(R.id.imagemFilme);
            viewHolder.titulo = (TextView) convertView.findViewById(R.id.filme_tituloTextView);
            viewHolder.data_public = (TextView) convertView.findViewById(R.id.data_public_textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (bitmaps.containsKey(filme.link)) {
            viewHolder.img.setImageBitmap(bitmaps.get(filme.link));
        } else {
            new LoadImageTask(viewHolder.img).execute(filme.link);
        }
        Context context = getContext();
        viewHolder.img = (ImageButton) convertView.findViewById(R.id.imagemFilme);
        viewHolder.titulo = (TextView) convertView.findViewById(R.id.filme_tituloTextView);
        viewHolder.data_public = (TextView) convertView.findViewById(R.id.data_public_textView);
        return convertView;
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
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
    }
}*/


