package br.com.testecbmarvel.testecbmarvel.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView = findViewById(R.id.det_img_personagem);
        txt_personagem = findViewById(R.id.det_txt_nome_personagem);
        recyclerView = findViewById(R.id.recyclerComics);

        activity = DetailsActivity.this;

        Intent intent = getIntent();
        String idAdapter = intent.getStringExtra("id");
        String urlAdapter = intent.getStringExtra("url");
        String nameAdapter = intent.getStringExtra("name");

        if (idAdapter != null && urlAdapter != null && nameAdapter != null){
            id = idAdapter;
            url = urlAdapter;
            name = nameAdapter;
//            Toast.makeText(this, "OLHA MEU ID " + id, Toast.LENGTH_SHORT).show();
        }

        Util util = new Util();

        //Configurações:

        txt_personagem.setText(name);

        Drawable drawable = Drawable.createFromPath("carregando");

        Glide.with(this)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);


        RetrofitInitializer
                .getGsonComics(id)
                .create(Services.class)
                .getComics(util.timestamp(), Keys.PUBLIC_KEY, util.md5())
                .enqueue(new Callback<Exemplo>() {
                    @Override
                    public void onResponse(Call<Exemplo> call, Response<Exemplo> response) {

                        System.out.println("Requisição certa!");
                        Toast.makeText(DetailsActivity.this, "Deu bom", Toast.LENGTH_SHORT).show();

//                        personagemList = new ArrayList<>();

                        response.body().getData().getCount();
                        int total = Integer.parseInt(response.body().getData().getCount());

                        comicsList = new ArrayList<>();
                        for (int i = 0; i < (total - 1); i++){

//                              String s = response.body().getData().getResults().get(i).getThumbnail();
                            String extension = response.body().getData().getResults().get(i).getThumbnail().getExtension();
                            String url = response.body().getData().getResults().get(i).getThumbnail().getPath() + "." + extension;

//                            Personagem personagem = new Personagem(id, nome, thumbnail_url);

//                            personagemList.add(personagem);


                            comicsList.add(url);

                            if (comicsList.size() != 0) {
                                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                                recyclerView.setAdapter(new ComicsAdapter(activity, comicsList));
                            }


                            System.out.println(comicsList);

//                            String thumbnail_extension = response.body().getData().getResults().get(i).getThumbnail().getExtension();


//                            sharedPreferences = MainActivity.this.getSharedPreferences("personagem_information", Context.MODE_PRIVATE);
//                            sharedPreferences.edit().putString("personagem_id", id).apply();
//                            sharedPreferences.edit().putString("personagem_nome", nome).apply();
//                            sharedPreferences.edit().putString("personagem_url", thumbnail_url).apply();

//                             personagem = new Personagem(id, nome, thumbnail_url);

//                            personagemList.add(personagem);

//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("chefe_preference", Context.MODE_PRIVATE);
//                            chefeId = sharedPreferences.getString("chefe_id",null);

//                            if(personagemList.size() != 0){
//                                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//                                recyclerView.setAdapter(new ComicsAdapter(activity, personagemList));
//                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<Exemplo> call, Throwable t) {
                        System.out.println("Requisição errada!");
                    }
                });


    }
}
