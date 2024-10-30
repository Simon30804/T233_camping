package es.unizar.eina.notepad.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.unizar.eina.notepad.database.Reserva;

public class ReservaListAdapter extends ListAdapter<Reserva, ReservaViewHolder> {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ReservaListAdapter(@NonNull DiffUtil.ItemCallback<Reserva> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ReservaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ReservaViewHolder.create(parent);
    }

    public Reserva getCurrent() {
        return getItem(getPosition());
    }

    @Override
    public void onBindViewHolder(ReservaViewHolder holder, int position) {

        Reserva current = getItem(position);
        holder.bind(current.getNombreCliente());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }
}
