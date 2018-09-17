package br.com.testecbmarvel.testecbmarvel.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOError;
import java.util.ArrayList;
import java.util.List;

import br.com.testecbmarvel.testecbmarvel.R;
import br.com.testecbmarvel.testecbmarvel.adapter.ComicsAdapter;
import br.com.testecbmarvel.testecbmarvel.extras.Keys;
import br.com.testecbmarvel.testecbmarvel.extras.Util;
import br.com.testecbmarvel.testecbmarvel.model.comics_.Exemplo;
import br.com.testecbmarvel.testecbmarvel.presenter.RetrofitInitializer;
import br.com.testecbmarvel.testecbmarvel.presenter.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    String url;
    String id;
    String name;
    List<String> comicsList;
    Context context = DetailsActivity.this;
    Activity activity;
    private  ImageView imageView;
    private TextView txt_personagem;
    private android.support.v7.widget.RecyclerView recyclerView;
    private LinearLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView = findViewById(R.id.det_img_personagem);
        txt_personagem = findViewById(R.id.det_txt_nome_personagem);
        recyclerView = findViewById(R.id.recyclerComics);
        loading = findViewById(R.id.details_loading);

        activity = DetailsActivity.this;

        Intent intent = getIntent();
        String idAdapter = intent.getStringExtra("id");
        String urlAdapter = intent.getStringExtra("url");
        String nameAdapter = intent.getStringExtra("name");

        if (idAdapter != null && urlAdapter != null && nameAdapter != null){
            id = idAdapter;
            url = urlAdapter;
            name = nameAdapter;
        }

        Util util = new Util();

        txt_personagem.setText(name);



            Glide.with(this)
                    .load(url)
                    .apply(RequestOptions.centerCropTransform())
                    .into(imageView);

        
        try {
            RetrofitInitializer
                    .getGsonComics(id)
                    .create(Services.class)
                    .getComics(util.timestamp(), 100, Keys.PUBLIC_KEY, util.md5())
                    .enqueue(new Callback<Exemplo>() {
                        @Override
                        public void onResponse(Call<Exemplo> call, Response<Exemplo> response) {



                            response.body().getData().getCount();
                            int total = Integer.parseInt(response.body().getData().getCount());

                            comicsList = new ArrayList<>();
                            for (int i = 0; i < (total - 1); i++) {

                                String extension = response.body().getData().getResults().get(i).getThumbnail().getExtension();
                                String url = response.body().getData().getResults().get(i).getThumbnail().getPath() + "." + extension;


                                comicsList.add(url);

                                if (comicsList.size() != 0) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                                    recyclerView.setAdapter(new ComicsAdapter(activity, comicsList));
                                    loading.setVisibility(View.GONE);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<Exemplo> call, Throwable t) {
                            System.out.println("Requisição errada!");
                            Toast.makeText(DetailsActivity.this, "Problemas com a conexão!", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                        }
                    });

        } catch (IOError e){
            e.printStackTrace();
            Toast.makeText(activity, "Não foi possível carregar os dados :(", Toast.LENGTH_SHORT).show();
        }


    }
}
