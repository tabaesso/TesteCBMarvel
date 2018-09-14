package br.com.testecbmarvel.testecbmarvel.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.testecbmarvel.testecbmarvel.R;
import br.com.testecbmarvel.testecbmarvel.model.Example;
import br.com.testecbmarvel.testecbmarvel.presenter.RetrofitInitializer;
import br.com.testecbmarvel.testecbmarvel.presenter.Services;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    String url;
    String id;
    String name;
    List<String> personagemList;
    private  ImageView imageView;
    private TextView txt_personagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView = findViewById(R.id.det_img_personagem);
        txt_personagem = findViewById(R.id.det_txt_nome_personagem);

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

        //Configurações:

        txt_personagem.setText(name);

        Glide.with(this)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);


        RetrofitInitializer
                .getGsonComics(id)
                .create(Services.class)
                .getComics()
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {

                        System.out.println("Requisição certa!");

//                        personagemList = new ArrayList<>();
////
//                        int total = Integer.parseInt(response.body().getData().getCount());
//
//                        for (int i = 0; i < (total - 1); i++){


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
//                                recyclerView.setAdapter(new PersonagemAdapter(activity, personagemList));
//                            }


//                        }

                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        System.out.println("Requisição errada!");
                    }
                });


    }
}
