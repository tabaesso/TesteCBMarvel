package br.com.testecbmarvel.testecbmarvel.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOError;
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

public class SearchActivity extends AppCompatActivity {

    private android.support.design.widget.TextInputEditText editPesquisa;
    private ImageView buttonSearch;
    private RelativeLayout loading;
    private android.support.v7.widget.RecyclerView recyclerView;

    SharedPreferences sharedPreferences;
    List<Personagem> personagemList;
    Context context = SearchActivity.this;
    Activity activity = SearchActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editPesquisa = findViewById(R.id.edtPesquisa);
        buttonSearch = findViewById(R.id.search_button);
        loading = findViewById(R.id.search_loading);
        recyclerView = findViewById(R.id.recyclerSearch);
//        edit = findViewById(R.id.edt_pesquisa1);

        final Util util = new Util();

        editPesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    verificaParaBusca(util);
                    return true;
                }
                return false;
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaParaBusca(util);
            }
        });





    }

    private void verificaParaBusca(Util util) {
        String texto = editPesquisa.getText().toString();
        if (!texto.isEmpty()) {
            buscar(util, texto);
        } else {
            Toast.makeText(context, "Preencha o campo!", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar(Util util, String textoParaBuscar){
        try {
            RetrofitInitializer
                    .getGsonListCharacters()
                    .create(Services.class)
                    .getSearch(util.timestamp(), 100, textoParaBuscar, Keys.PUBLIC_KEY, util.md5())
//                .lista(1491, Keys.PUBLIC_KEY)
                    .enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {
//fd3669cf0c17cd9ca8ce7576a9ecede3  --  1536976898
                            System.out.println("Requisição certa!");

                            personagemList = new ArrayList<>();

                            int total = Integer.parseInt(response.body().getData().getCount());

                            for (int i = 0; i < total; i++) {

                                String id = response.body().getData().getResults().get(i).getId();
                                String nome = response.body().getData().getResults().get(i).getName();
                                String thumbnail_url = response.body().getData().getResults().get(i).getThumbnail().getPath()
                                        + "." + response.body().getData().getResults().get(i).getThumbnail().getExtension();
//                            String thumbnail_extension = response.body().getData().getResults().get(i).getThumbnail().getExtension();


                                sharedPreferences = SearchActivity.this.getSharedPreferences("personagem_information", Context.MODE_PRIVATE);
                                sharedPreferences.edit().putString("personagem_id", id).apply();
                                sharedPreferences.edit().putString("personagem_nome", nome).apply();
                                sharedPreferences.edit().putString("personagem_url", thumbnail_url).apply();

                                Personagem personagem = new Personagem(id, nome, thumbnail_url);

                                personagemList.add(personagem);

//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("chefe_preference", Context.MODE_PRIVATE);
//                            chefeId = sharedPreferences.getString("chefe_id",null);

                                if (personagemList.size() != 0) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                                    recyclerView.setAdapter(new PersonagemAdapter(activity, personagemList));
                                    loading.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(context, "Nenhum personagem encontrado!", Toast.LENGTH_SHORT).show();
                                }


                            }

                        }

                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                            Toast.makeText(SearchActivity.this, "Problemas com a conexão!", Toast.LENGTH_SHORT).show();
                            System.out.println("Requisição errada!");
                            loading.setVisibility(View.GONE);
                        }
                    });
        } catch (IOError e){
            e.printStackTrace();
            Toast.makeText(activity, "Não foi possível carregar os dados :(", Toast.LENGTH_SHORT).show();
        }
    }
}
