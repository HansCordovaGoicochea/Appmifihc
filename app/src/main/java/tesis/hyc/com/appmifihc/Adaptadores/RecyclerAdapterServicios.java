package tesis.hyc.com.appmifihc.Adaptadores;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tesis.hyc.com.appmifihc.Clases.Servicios;
import tesis.hyc.com.appmifihc.Fragmentos.FragmentoAhorro;
import tesis.hyc.com.appmifihc.Fragmentos.FragmentoPrestamo;
import tesis.hyc.com.appmifihc.R;


public class RecyclerAdapterServicios extends RecyclerView.Adapter<RecyclerAdapterServicios.ViewHolder> {

    private final Context context;
    private final List<Servicios> items;
    public FragmentManager fragmentManager;

    public RecyclerAdapterServicios(Context mContext, List<Servicios> items) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterServicios.ViewHolder holder, int position) {
        final Servicios servicio = items.get(position);

//        Log.e("asdasd-?>>>>>>>", servicio.getNombre_servicio());
//        Toast.makeText(context, servicio.getNombre_servicio(), Toast.LENGTH_SHORT).show();

        if (servicio.getCodigo_servicio().equals(1)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.prestamo, context.getTheme()));
            } else {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.prestamo));
            }
        }
        else if (servicio.getCodigo_servicio().equals(2)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ahorro_movible, context.getTheme()));
            } else {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ahorro_movible));
            }
        }
        else if (servicio.getCodigo_servicio().equals(3)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.plazo_fijo, context.getTheme()));
            } else {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.plazo_fijo));
            }
        }
        else if (servicio.getCodigo_servicio().equals(4)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.prestamo, context.getTheme()));
            } else {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.prestamo));
            }
        }
        else if (servicio.getCodigo_servicio().equals(5)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.prestamo, context.getTheme()));
            } else {
                holder.servicioImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.prestamo));
            }
        }

        holder.txtServicio.setText(servicio.getNombre_servicio());
        holder.tvMonto.setText("S/ "+servicio.getMonto_servicio());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // declarar las vistas del XML
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtServicio, tvCodigo, tvMonto;
        ImageView servicioImageView, redirectImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txtServicio = itemView.findViewById(R.id.txtServicio);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            tvMonto = itemView.findViewById(R.id.tvMonto);
            servicioImageView = itemView.findViewById(R.id.servicioImageView);
            redirectImageView = itemView.findViewById(R.id.redirectImageView);


        }

        @Override
        public void onClick(View v) {
            Integer codigo_servicio = items.get(getAdapterPosition()).getCodigo_servicio();

            fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
            Fragment fragment;
            // below code you can make condition and check any value.
            if(codigo_servicio.equals(1)){
                fragment = new FragmentoPrestamo();// here give your fragment.
                fragmentManager.beginTransaction()
                        .replace(R.id.contenedor_principal, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }
            else if(codigo_servicio.equals(2)){
                fragment = new FragmentoAhorro();// here give your fragment.
                fragmentManager.beginTransaction()
                        .replace(R.id.contenedor_principal, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }

        }
    }


}