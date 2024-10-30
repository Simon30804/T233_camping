package es.unizar.eina.notepad.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.unizar.eina.notepad.database.Parcela;

public class ParcelaListAdapter extends ListAdapter<Parcela, ParcelaViewHolder> {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ParcelaListAdapter(@NonNull DiffUtil.ItemCallback<Parcela> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ParcelaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ParcelaViewHolder.create(parent);
    }

    public Parcela getCurrent() {
        return getItem(getPosition());
    }

    @Override
    public void onBindViewHolder(ParcelaViewHolder holder, int position) {

        Parcela current = getItem(position);
        holder.bind(current.getNombre());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }
}
