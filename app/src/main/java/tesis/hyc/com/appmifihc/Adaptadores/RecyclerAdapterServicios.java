package tesis.hyc.com.appmifihc.Adaptadores;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tesis.hyc.com.appmifihc.Clases.Servicios;
import tesis.hyc.com.appmifihc.R;


public class RecyclerAdapterServicios extends RecyclerView.Adapter<RecyclerAdapterServicios.ViewHolder> {

    private final Context context;
    private final List<Servicios> items;

    public RecyclerAdapterServicios(Context mContext,List<Servicios> items) {
        this.context = mContext;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerAdapterServicios.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_servicios, parent, false);




        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterServicios.ViewHolder holder, int position) {
        final Servicios item = items.get(position);
//        holder.placa.setText(item.getPlaca());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
}