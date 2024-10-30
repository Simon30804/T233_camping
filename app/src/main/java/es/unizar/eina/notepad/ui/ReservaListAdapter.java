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
        holder.bind(current.getNombreCliente()); //Esto lo he puesto para que no falle pero seguro que esta mal

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPosition(holder.getAdapterPosition());
            }
        });
    }

    static class ReservaDiff extends DiffUtil.ItemCallback<Reserva> {

        @Override
        public boolean areItemsTheSame(@NonNull Reserva oldItem, @NonNull Reserva newItem) {
            //android.util.Log.d ( "ReservaDiff" , "areItemsTheSame " + oldItem.getId() + " vs " + newItem.getId() + " " +  (oldItem.getId() == newItem.getId()));
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Reserva oldItem, @NonNull Reserva newItem) {
            //android.util.Log.d ( "ReservaDiff" , "areContentsTheSame " + oldItem.getNombre() + " vs " + newItem.getNombre() + " " + oldItem.getNombre().equals(newItem.getNombre()));
            // We are just worried about differences in visual representation, i.e. changes in the title
            return oldItem.getNombreCliente().equals(newItem.getNombreCliente());
        }
    }

}
