package br.com.testecbmarvel.testecbmarvel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import br.com.testecbmarvel.testecbmarvel.R;
import br.com.testecbmarvel.testecbmarvel.model.Personagem;
import br.com.testecbmarvel.testecbmarvel.view.DetailsActivity;

public class Personagem1Adapter extends RecyclerView.Adapter<Personagem1Adapter.PersonagemViewHolder> {

    private Activity activity;
    private List<Personagem> personagemList;
    private SharedPreferences sharedPreferences;
    private String id;

    public Personagem1Adapter(Activity activity, List<Personagem> personagemList) {

        this.activity = activity;
        this.personagemList = personagemList;
    }


    @Override
    public PersonagemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_personagem, parent, false);

        sharedPreferences = activity.getSharedPreferences("personagem_information", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("personagem_id",null);

        return new PersonagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonagemViewHolder holder, int position) {

        Personagem personagem = personagemList.get(position);
        holder.vincula(personagem);

    }

    @Override
    public int getItemCount() {
        return personagemList.size();
    }


    public class PersonagemViewHolder extends RecyclerView.ViewHolder{

        TextView txt_nome_personagem;
        TextView txt_id_personagem;
        TextView txt_url;
        ImageView img_personagem;

        public PersonagemViewHolder(View itemView) {
            super(itemView);

            txt_nome_personagem = itemView.findViewById(R.id.card_txt_nome_personagem);
            txt_id_personagem = itemView.findViewById(R.id.card_txt_id_personagem);
            txt_url = itemView.findViewById(R.id.card_txt_url);
            img_personagem = itemView.findViewById(R.id.card_img_personagem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (personagemList.get(position).getId() == txt_id_personagem.getText().toString()){
                        Toast.makeText(activity, txt_nome_personagem.getText().toString(), Toast.LENGTH_SHORT).show();

                        Intent goDetails = new Intent(activity,DetailsActivity.class);
                        goDetails.putExtra("id", txt_id_personagem.getText().toString());
                        goDetails.putExtra("url", txt_url.getText().toString());
                        goDetails.putExtra("name", txt_nome_personagem.getText().toString());
                        activity.startActivity(goDetails);

                    }


                }
            });

        }

        public void vincula(Personagem personagem) {
            preencheCampos(personagem);

        }

        public void preencheCampos(Personagem personagem) {
            txt_nome_personagem.setText(personagem.getNome());
            txt_id_personagem.setText(personagem.getId());
            txt_url.setText(personagem.getUrl());

            Glide.with(activity)
                    .load(personagem.getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(img_personagem);
        }
    }

}
