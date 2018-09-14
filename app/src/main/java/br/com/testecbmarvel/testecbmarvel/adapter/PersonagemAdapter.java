package br.com.testecbmarvel.testecbmarvel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.testecbmarvel.testecbmarvel.R;
import br.com.testecbmarvel.testecbmarvel.model.Personagem;
import br.com.testecbmarvel.testecbmarvel.view.DetailsActivity;

public class PersonagemAdapter extends RecyclerView.Adapter<PersonagemAdapter.PersonagemViewHolder> {

    private Activity activity;
    private List<Personagem> personagemList;
    private SharedPreferences sharedPreferences;
    private String id;

    public PersonagemAdapter(Activity activity, List<Personagem> personagemList) {

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

        public PersonagemViewHolder(View itemView) {
            super(itemView);

            txt_nome_personagem = itemView.findViewById(R.id.card_txt_nome_personagem);
            txt_id_personagem = itemView.findViewById(R.id.card_txt_id_personagem);
            txt_url = itemView.findViewById(R.id.card_txt_url);

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
        }
        }

    }
