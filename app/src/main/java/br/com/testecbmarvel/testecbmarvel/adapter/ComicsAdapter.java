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

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder> {

    private Activity activity;
    private List<String> comicsList;
    private SharedPreferences sharedPreferences;
    private String id;

    public ComicsAdapter(Activity activity, List<String> comicsList) {

        this.activity = activity;
        this.comicsList = comicsList;
    }


    @Override
    public ComicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comics, parent, false);

//        sharedPreferences = activity.getSharedPreferences("personagem_information", Context.MODE_PRIVATE);
//        id = sharedPreferences.getString("personagem_id",null);

        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicsViewHolder holder, int position) {

        String comics = comicsList.get(position);
        holder.vincula(comics);

    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }


    public class ComicsViewHolder extends RecyclerView.ViewHolder{

        ImageView image_comics;
//        TextView txt_id_personagem;
//        TextView txt_url;

        public ComicsViewHolder(View itemView) {
            super(itemView);

            image_comics = itemView.findViewById(R.id.img_comics);
//            txt_id_personagem = itemView.findViewById(R.id.card_txt_id_personagem);
//            txt_url = itemView.findViewById(R.id.card_txt_url);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (comicsList.get(position).getId() == txt_id_personagem.getText().toString()){
                        Toast.makeText(activity, txt_nome_personagem.getText().toString(), Toast.LENGTH_SHORT).show();

                        Intent goDetails = new Intent(activity,DetailsActivity.class);
                        goDetails.putExtra("id", txt_id_personagem.getText().toString());
                        goDetails.putExtra("url", txt_url.getText().toString());
                        goDetails.putExtra("name", txt_nome_personagem.getText().toString());
                        activity.startActivity(goDetails);

                    }


                }
            }); */

        }

        public void vincula(String comics) {
            preencheCampos(comics);

        }

        public void preencheCampos(String comics) {

                Glide.with(activity)
                        .load(comics)
                        .apply(RequestOptions.circleCropTransform())
                        .into(image_comics);

        }
    }

}
