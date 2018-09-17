package br.com.testecbmarvel.testecbmarvel.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.testecbmarvel.testecbmarvel.R;
import br.com.testecbmarvel.testecbmarvel.adapter.PersonagemAdapter;
import br.com.testecbmarvel.testecbmarvel.extras.Keys;
import br.com.testecbmarvel.testecbmarvel.extras.Util;
import br.com.testecbmarvel.testecbmarvel.model.Example;
import br.com.testecbmarvel.testecbmarvel.model.Personagem;
import br.com.testecbmarvel.testecbmarvel.presenter.RetrofitInitializer;
import br.com.testecbmarvel.testecbmarvel.presenter.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


//    @BindView(R.id.recyclerPersonagens)
//    RecyclerView recyclerView;

    List<Personagem> personagemList;
    List<Personagem> oi;
    SharedPreferences sharedPreferences;
    Activity activity;
    Context context = MainActivity.this;

//    PersonagemAdapter adapter;
//    LinearLayoutManager linearLayoutManager;
//
//    private static final int PAGE_START = 0;
//    private boolean isLoading = false;
//    private boolean isLastPage = false;
//    private int TOTAL_PAGES = 0;
//    private int PAGE_SIZE = 10;
//    private int currentPage = PAGE_START;


//    private android.support.design.widget.TextInputEditText editPesquisa;
    private android.support.v7.widget.RecyclerView recyclerView;
    private RelativeLayout loading;
//    private android.support.v7.widget.RecyclerView recyclerView1;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        currentPage = PAGE_START;
//        isLoading = false;
//        isLastPage = false;

        activity = MainActivity.this;

        recyclerView = findViewById(R.id.recyclerPersonagens);
        loading = findViewById(R.id.main_loading);
//        recyclerView1 = findViewById(R.id.recyclerSearch);
//        editPesquisa = findViewById(R.id.edtPesquisa);
//        editPesquisa.setVisibility(View.VISIBLE);

        util = new Util();

        //TODO: Surround with try catch possibles crachs

        loading.setVisibility(View.VISIBLE);
//        currentPage = PAGE_START;
//        isLoading = false;
//        isLastPage = false;
//        Personagem personagem = new Personagem("1011334", "3-D Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg");
//
//        List<Personagem> opa = new ArrayList<>();
//        opa.add(personagem);
//
//        adapter = new PersonagemAdapter(activity, opa);
//        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//        recyclerView.addOnScrollListener(new PaginationScrollListener() {
//        });


        RetrofitInitializer
                .getGsonListCharacters()
                .create(Services.class)
                .lista(util.timestamp(),270, Keys.PUBLIC_KEY, util.md5())
//                .lista(1491, Keys.PUBLIC_KEY)
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
//fd3669cf0c17cd9ca8ce7576a9ecede3  --  1536976898
                        System.out.println("Requisição certa!");

                        personagemList = new ArrayList<>();

                        int total = Integer.parseInt(response.body().getData().getCount());

                        for (int i = 0; i < total; i++){

                            String id = response.body().getData().getResults().get(i).getId();
                            String nome = response.body().getData().getResults().get(i).getName();
                            String thumbnail_url = response.body().getData().getResults().get(i).getThumbnail().getPath()
                                    + "." + response.body().getData().getResults().get(i).getThumbnail().getExtension();
//                            String thumbnail_extension = response.body().getData().getResults().get(i).getThumbnail().getExtension();


                            sharedPreferences = MainActivity.this.getSharedPreferences("personagem_information", Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString("personagem_id", id).apply();
                            sharedPreferences.edit().putString("personagem_nome", nome).apply();
                            sharedPreferences.edit().putString("personagem_url", thumbnail_url).apply();

                            Personagem personagem = new Personagem(id, nome, thumbnail_url);

                            personagemList.add(personagem);

//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("chefe_preference", Context.MODE_PRIVATE);
//                            chefeId = sharedPreferences.getString("chefe_id",null);

                            if(personagemList.size() != 0){
                                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(new PersonagemAdapter(activity, personagemList));
                                loading.setVisibility(View.GONE);
                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Conexão muito lenta", Toast.LENGTH_SHORT).show();
                        System.out.println("Requisição errada!");
                    }
                });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_pesquisar:

                Intent goSearch = new Intent(MainActivity.this,SearchActivity.class);
                MainActivity.this.startActivity(goSearch);
//                editPesquisa.setVisibility(View.INVISIBLE);
//                nameStartsWith
              /*  RetrofitInitializer
                        .getGsonListCharacters()
                        .create(Services.class)
                        .getSearch(util.timestamp(),1, editPesquisa.getText().toString(), Keys.PUBLIC_KEY, util.md5())
//                .lista(1491, Keys.PUBLIC_KEY)
                        .enqueue(new Callback<Example>() {
                            @Override
                            public void onResponse(Call<Example> call, Response<Example> response) {

                                System.out.println("Requisição certa!");

                                oi = new ArrayList<>();

                                int total = Integer.parseInt(response.body().getData().getCount());

                                for (int i = 0; i < (total - 1); i++){

                                    String id = response.body().getData().getResults().get(i).getId();
                                    String nome = response.body().getData().getResults().get(i).getName();
                                    String thumbnail_url = response.body().getData().getResults().get(i).getThumbnail().getPath()
                                            + "." + response.body().getData().getResults().get(i).getThumbnail().getExtension();
//                            String thumbnail_extension = response.body().getData().getResults().get(i).getThumbnail().getExtension();


                                    sharedPreferences = MainActivity.this.getSharedPreferences("personagem_information", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("personagem_id", id).apply();
                                    sharedPreferences.edit().putString("personagem_nome", nome).apply();
                                    sharedPreferences.edit().putString("personagem_url", thumbnail_url).apply();

                                    Personagem personagem = new Personagem(id, nome, thumbnail_url);

                                    oi.add(personagem);

//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("chefe_preference", Context.MODE_PRIVATE);
//                            chefeId = sharedPreferences.getString("chefe_id",null);

                                    if(oi.size() != 0){
                                        recyclerView.setVisibility(View.GONE);
                                        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                                        recyclerView1.setAdapter(new PersonagemAdapter(activity, oi));
                                    }


                                }

                            }

                            @Override
                            public void onFailure(Call<Example> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Conexão muito lenta", Toast.LENGTH_SHORT).show();
                                System.out.println("Requisição errada!");
                            }
                        });


                Toast.makeText(MainActivity.this, "PESQUISA", Toast.LENGTH_SHORT).show();
*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}