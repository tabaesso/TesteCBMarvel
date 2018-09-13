package br.com.testecbmarvel.testecbmarvel.view;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import br.com.testecbmarvel.testecbmarvel.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerPersonagens)
    RecyclerView recyclerView;


    private android.support.design.widget.TextInputEditText editPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        ActionBar actionBar = getSupportActionBar();
//
//
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        }

        ButterKnife.bind(this);

        editPesquisa = findViewById(R.id.edtPesquisa);
        editPesquisa.setVisibility(View.VISIBLE);
//        editPesquisa.setVisibility(View.VISIBLE);
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