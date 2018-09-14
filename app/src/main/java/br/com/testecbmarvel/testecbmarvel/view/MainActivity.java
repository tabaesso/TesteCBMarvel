package br.com.testecbmarvel.testecbmarvel.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.testecbmarvel.testecbmarvel.R;
import br.com.testecbmarvel.testecbmarvel.extras.Keys;
import br.com.testecbmarvel.testecbmarvel.extras.Util;
import br.com.testecbmarvel.testecbmarvel.model.Example;
import br.com.testecbmarvel.testecbmarvel.model.Personagem;
import br.com.testecbmarvel.testecbmarvel.presenter.RetrofitInitializer;
import br.com.testecbmarvel.testecbmarvel.presenter.Services;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recyclerPersonagens)
    RecyclerView recyclerView;


    private android.support.design.widget.TextInputEditText editPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        editPesquisa = findViewById(R.id.edtPesquisa);
        editPesquisa.setVisibility(View.VISIBLE);

        Util util = new Util();
//        util.md5();


//        Call<List<Personagem>> calldata = (Call<List<Personagem>>) new RetrofitInitializer().lista();
//        calldata.enqueue(new Callback<List<Personagem>>() {
//            @Override
//            public void onResponse(Call<List<Personagem>> call, Response<List<Personagem>> response) {
//
//                Personagem objeto = response.body().get(0);
//                Toast.makeText(MainActivity.this, "DEU BOM", Toast.LENGTH_SHORT).show();
//
////                String fileDate = objeto.getFileDate();
////                String fileTime = objeto.getFileTime();
////                String url = objeto.getUrl();
////
////                datahora.setText(fileDate + " - " + fileTime + " GMT") ;
////
////                PhotoView photoView = new PhotoView(getApplicationContext());
////                Picasso.with(getApplication()).load(url).placeholder(R.drawable.au).error(R.drawable.ooo).into(photoView);
////                layout.addView(photoView);
//            }
//
//            @Override
//            public void onFailure(Call<List<Personagem>> call, Throwable t) {
//                Log.e("RETROFIT ERRO", t.getMessage() );
//            }
//        });

//        Services apiService = RetrofitInitializer.getGsonClient().create(Services.class);
//
////        String username = "sarahjean";
//        Response<Personagem> response = apiService.lista(util.timestamp(), Keys.PUBLIC_KEY, util.md5());
//        Call<Personagem> call = apiService.lista(util.timestamp(), Keys.PUBLIC_KEY, util.md5());
//        call.enqueue(new Callback<Personagem>() {
//            @Override
//            public void onResponse(Call<Personagem> call, Response<Personagem> response) {
//                int statusCode = response.code();
////                User user = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Personagem> call, Throwable t) {
//                // Log error here since request failed
//            }
//        });
//
//        try {
//            response.execute();
//        } catch (IOException e ){
//            // handle error
//        }



        RetrofitInitializer
                .getGsonCharacters()
                .create(Services.class)
                .lista(util.timestamp(), Keys.PUBLIC_KEY, util.md5())
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {

                        Toast.makeText(MainActivity.this, "opa deu bom", Toast.LENGTH_SHORT).show();

                        int total = Integer.parseInt(response.body().getData().getCount());

                        for (int i = 0; i < (total - 1); i++){
                            System.out.println(response.body().getData().getResults().get(i).getName());
                        }




                        System.out.println(response.toString());
                        System.out.println(response.body());


//                        long oi = response.body().size();
//                        ArrayList<Personagem> personagemArrayList = (ArrayList<Personagem>) response.body().size();
//                        List<Personagem> personagemList = (List<Personagem>) response.body().listIterator(1);
//                        String objeto = response.body().get(1).getName();

//                        String name = objeto.getName();
//                        System.out.println("TESTANDO " + oi);
//                        String fileTime = objeto.getFileTime();
//                        String url = objeto.getUrl();
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "deu ruim", Toast.LENGTH_SHORT).show();
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

                editPesquisa.setVisibility(View.INVISIBLE);

                Toast.makeText(MainActivity.this, "PESQUISA", Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}