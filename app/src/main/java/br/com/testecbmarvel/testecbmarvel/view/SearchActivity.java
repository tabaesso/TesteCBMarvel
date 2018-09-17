package br.com.testecbmarvel.testecbmarvel.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import br.com.testecbmarvel.testecbmarvel.R;

public class SearchActivity extends AppCompatActivity {

    private android.support.design.widget.TextInputEditText editPesquisa;
//    private android.support.v7.widget.SearchView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editPesquisa = findViewById(R.id.edtPesquisa);
//        edit = findViewById(R.id.edt_pesquisa1);

        editPesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    Toast.makeText(SearchActivity.this, "OPA PESQUISA", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

//        edit.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                Toast.makeText(SearchActivity.this, "EAE", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        edit.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SearchActivity.this, "CLICOUUUU", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
