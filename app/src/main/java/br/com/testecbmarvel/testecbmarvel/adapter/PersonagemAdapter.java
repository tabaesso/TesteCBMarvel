package br.com.testecbmarvel.testecbmarvel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.testecbmarvel.testecbmarvel.R;
import br.com.testecbmarvel.testecbmarvel.model.Personagem;
import br.com.testecbmarvel.testecbmarvel.model.Result;
import br.com.testecbmarvel.testecbmarvel.view.DetailsActivity;

public class PersonagemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Result> resultList;
    private SharedPreferences sharedPreferences;
    private String id;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public PersonagemAdapter(Activity activity) {

        this.activity = activity;
        resultList = new ArrayList<>();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList){
        this.resultList = resultList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View view2 = inflater.inflate(R.layout.load_progress, parent, false);
                viewHolder = new LoadingVH(view2);
                break;
        }

        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater){
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.card_personagem, parent, false);
        viewHolder = new PublicationVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Result result = resultList.get(position);

        String resultId = resultList.get(position).getId();
        String resultName = resultList.get(position).getName();
        String resultThumbnail = resultList.get(position).getThumbnail().getPath()
                + "." + resultList.get(position).getThumbnail().getExtension();

        Personagem personagem = new Personagem(resultId, resultName, resultThumbnail);

        switch (getItemViewType(position)){
            case ITEM:

                if (result.getId() != null && result.getName() != null && result.getThumbnail() != null ){
                    final PublicationVH publicationVH = (PublicationVH) holder;

                     publicationVH.txt_id_personagem.setText(personagem.getId());
                     publicationVH.txt_nome_personagem.setText(personagem.getNome());
                     publicationVH.txt_url.setText(personagem.getUrl());

                    Glide.with(activity)
                    .load(personagem.getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(publicationVH.img_personagem);

                }

                break;
            case LOADING:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == resultList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Result result) {
        resultList.add(result);
        notifyItemInserted(resultList.size() - 1);
    }

    public void addAll(ArrayList<Result> mcList) {
        for (Result mc : mcList) {
            add(mc);
        }
    }

    public void remove(Result result) {
        int position = resultList.indexOf(result);
        if (position > -1) {
            resultList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;

            int position = resultList.size() - 1;
            Result item = getItem(position);

            if (item.getId() == null) {  //o item que não tem atributo é um loading footer
                resultList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Result getItem(int position) {
        return resultList.get(position);
    }

    protected class PublicationVH extends RecyclerView.ViewHolder {
        TextView txt_nome_personagem;
        TextView txt_id_personagem;
        TextView txt_url;
        ImageView img_personagem;

        public PublicationVH(View view) {
            super(view);

            txt_nome_personagem = itemView.findViewById(R.id.card_txt_nome_personagem);
            txt_id_personagem = itemView.findViewById(R.id.card_txt_id_personagem);
            txt_url = itemView.findViewById(R.id.card_txt_url);
            img_personagem = itemView.findViewById(R.id.card_img_personagem);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (resultList.get(position).getId() == txt_id_personagem.getText().toString()){
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
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    }
