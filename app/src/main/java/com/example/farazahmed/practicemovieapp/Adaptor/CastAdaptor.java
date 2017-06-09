package com.example.farazahmed.practicemovieapp.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farazahmed.practicemovieapp.Model.Cast;
import com.example.farazahmed.practicemovieapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by FarazAhmed on 5/17/2017.
 */

public class CastAdaptor extends RecyclerView.Adapter<CastAdaptor.MyViewHolder> {

    private Context context;
    private List<Cast> castsList;

    public CastAdaptor(Context context, List<Cast> castsList) {
        this.context = context;
        this.castsList = castsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView actorimage;
        private TextView actorname;
        private TextView charactorname;

        public MyViewHolder(View itemView) {
            super(itemView);
            actorimage = (ImageView)itemView.findViewById(R.id.iv_castimage_singleitem);
            actorname = (TextView) itemView.findViewById(R.id.tv_actorname_singleitem);
            charactorname = (TextView) itemView.findViewById(R.id.tv_charactorname_singleitem);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_single_item,parent ,false);
        return new MyViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Cast cast = castsList.get(position);


            Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+cast.getProfile_path()).placeholder(R.drawable.account_circle).fit().centerCrop().into(holder.actorimage);
            holder.actorname.setText(cast.getName());
            holder.charactorname.setText(cast.getCharacter());



    }

    @Override
    public int getItemCount() {
        return castsList.size();
    }
}
