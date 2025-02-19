package org.beginningandroid.futbolappdos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

    public class CanalAdapter extends RecyclerView.Adapter<CanalAdapter.CanalViewHolder> {
        private List<Canal> listaCanales;
        ///
        private OnItemClickListener listener;

        // Define la interfaz para el click
        public interface OnItemClickListener {
            void onItemClick(Canal canal);
        }



        public CanalAdapter(List<Canal> listaCanales, OnItemClickListener listener) {
            this.listaCanales = listaCanales;
            this.listener = listener;
        }

        @NonNull
        @Override
        public CanalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_canal, parent, false);
            return new CanalViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CanalViewHolder holder, int position) {
            Canal canal = listaCanales.get(position);
            holder.nombre.setText(canal.getNombre());
            // Ocultamos el TextView del site
            holder.site.setVisibility(View.GONE);
            //holder.site.setText(canal.getSite());

            // Al hacer clic en un item, se activa el listener
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(canal);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listaCanales.size();
        }
        // Método para actualizar la lista filtrada
        public void actualizarLista(List<Canal> nuevaLista) {
            this.listaCanales = nuevaLista;
            notifyDataSetChanged();
        }

        public static class CanalViewHolder extends RecyclerView.ViewHolder {
            TextView nombre, site;

            public CanalViewHolder(View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.tvNombre);
                site = itemView.findViewById(R.id.tvSite);
            }
        }
    }
