package br.com.testecbmarvel.testecbmarvel.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.testecbmarvel.testecbmarvel.R;
import br.com.testecbmarvel.testecbmarvel.adapter.PersonagemAdapter;
import br.com.testecbmarvel.testecbmarvel.extras.Keys;
import br.com.testecbmarvel.testecbmarvel.extras.Util;
import br.com.testecbmarvel.testecbmarvel.model.Example;
import br.com.testecbmarvel.testecbmarvel.model.Personagem;
import br.com.testecbmarvel.testecbmarvel.presenter.PaginationScrollListener;
import br.com.testecbmarvel.testecbmarvel.presenter.RetrofitInitializer;
import br.com.testecbmarvel.testecbmarvel.presenter.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


//    VIEW: É a Activity ou Fragment, que exibirá os dados. Não deveria conter regra de negócio a não ser
//    disparar eventos que indicam mudança de estado dos dados; ele também possui código a fim de apresentar
//    a activity e/ou fragment


    private android.support.v7.widget.RecyclerView recyclerView;
    private RelativeLayout loading;
    private ProgressBar progressBarRecycle;
//    private TextView txtEmptyMessage;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int PAGE_SIZE = 10;
    private int currentPage = PAGE_START;

    private Util util;
    private long inicio = 0;

    PersonagemAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<Personagem> personagemList;
    SharedPreferences sharedPreferences;
    Activity activity;
    Context context = MainActivity.this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentPage = PAGE_START;
        isLoading = false;
        isLastPage = false;

        recyclerView = findViewById(R.id.recyclerPersonagens);
        loading = findViewById(R.id.main_loading);
        progressBarRecycle = findViewById(R.id.main_ProgressBar_Recycle);
//        txtEmptyMessage = findViewById(R.id.main_txtEmptyMessage);

        activity = MainActivity.this;
        loading.setVisibility(View.VISIBLE);
        util = new Util();

        RelativeLayout item = (RelativeLayout)findViewById(R.id.main_relative);
        final View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        item.addView(view);

        try{

        RetrofitInitializer
                .getGsonListCharacters()
                .create(Services.class)
                .lista(util.timestamp(),inicio, Keys.PUBLIC_KEY, util.md5())
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
//                        fd3669cf0c17cd9ca8ce7576a9ecede3  --  1536976898
                        personagemList = new ArrayList<>();
//
                        int total = Integer.parseInt(response.body().getData().getCount());

                        for (int i = 0; i < total; i++) {
//
                            String id = response.body().getData().getResults().get(i).getId();
                            String nome = response.body().getData().getResults().get(i).getName();
                            String thumbnail_url = response.body().getData().getResults().get(i).getThumbnail().getPath()
                                    + "." + response.body().getData().getResults().get(i).getThumbnail().getExtension();

                            sharedPreferences = MainActivity.this.getSharedPreferences("personagem_information", Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString("personagem_id", id).apply();
                            sharedPreferences.edit().putString("personagem_nome", nome).apply();
                            sharedPreferences.edit().putString("personagem_url", thumbnail_url).apply();
//
                            Personagem personagem = new Personagem(id, nome, thumbnail_url);
//
                            personagemList.add(personagem);
//
//
//                            if (personagemList.size() != 0) {
//                                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//                                recyclerView.setAdapter(new PersonagemAdapter(activity, personagemList));
//                                loading.setVisibility(View.GONE);
//                            }


                        }


                        if (personagemList.size() != 0) {
                            adapter = new PersonagemAdapter(activity, personagemList);
                            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                            loading.setVisibility(View.GONE);
                            System.out.println("TESTE1 = " + currentPage);

                            recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
                                @Override
                                protected void loadMoreItems() {
                                    progressBarRecycle.setVisibility(View.VISIBLE);
                                    isLoading = true;
                                    currentPage += 1;
                                    inicio += 20;
                                    loadPage(view);
                                }

                                @Override
                                public int getTotalPageCount() {
                                    return TOTAL_PAGES;
                                }

                                @Override
                                public boolean isLastPage() {
                                    return isLastPage;
                                }

                                @Override
                                public boolean isLoading() {
                                    return isLoading;
                                }
                            });

                            loadPage(view);


                        } else {
                            Toast.makeText(activity, "Não há personagens!", Toast.LENGTH_SHORT).show();
                            desabilitaProgress();
                        }
                    }



                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Problemas com a conexão!", Toast.LENGTH_SHORT).show();
                        System.out.println("Requisição errada!");
                        desabilitaProgress();
                    }
                });
        } catch (IOError e){
            e.printStackTrace();
            Toast.makeText(activity, "Não foi possível carregar os dados :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void desabilitaProgress() {
        loading.setVisibility(View.GONE);
        progressBarRecycle.setVisibility(View.GONE);
    }

    private void loadPage(View view) {
                    loadData(view, currentPage);
                }

    private void loadData(final View view, final int page) {

        try {
            RetrofitInitializer
                    .getGsonListCharacters()
                    .create(Services.class)
                    .lista(util.timestamp(), inicio, Keys.PUBLIC_KEY, util.md5())
                    .enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {

                            if (response.body() != null) {

                                progressBarRecycle.setVisibility(View.GONE);
                                Toast.makeText(activity, "TESTANDO", Toast.LENGTH_SHORT).show();
                                System.out.println("LOAD = " + inicio);
//
//
//                            Example publications = response.body();
//
//
//                            if (Integer.parseInt(publications.getData().getTotal()) > 0) {
////                                se é a primeira página que está sendo carregada, atualiza o total de publicações
//                                if (page == 0) {
//                                    TOTAL_PAGES = (int) (Math.floor(Integer.parseInt(publications.getData().getTotal()) / PAGE_SIZE));
//                                    if (Integer.parseInt(publications.getData().getTotal()) % PAGE_SIZE != 0) {
//                                        TOTAL_PAGES++;
//                                    }
//                                }
//
////
//                                progressBarRecycle.setVisibility(View.GONE);
////                                adapter.addAll(publications);
////                                adapter.removeLoadingFooter();
//                                isLoading = false;
////
//                                System.out.println("CurrentPage: " + currentPage);
//                                System.out.println("TOTAL_PAGES: " + TOTAL_PAGES);
////
//                                if (currentPage + 1 < TOTAL_PAGES) {
////                                    adapter.addLoadingFooter();
//                                } else {
//                                    isLastPage = true;
//                                }
//                            } else {
//                                progressBarRecycle.setVisibility(View.GONE);
//                                txtEmptyMessage.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            Log.e("ERRO Response Timeline", "Erro ao carregar dados da timeline");
//                        }
//
                            }
                        }

                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Problemas com a conexão!", Toast.LENGTH_SHORT).show();
                            System.out.println("Requisição errada!");
                            desabilitaProgress();
                        }
                    });
        } catch (IOError e){
            e.printStackTrace();
            Toast.makeText(activity, "Não foi possível carregar os dados :(", Toast.LENGTH_SHORT).show();
        }


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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}