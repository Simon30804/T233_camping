package es.unizar.eina.notepad.ui;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.notepad.R;

class ReservaViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    private final TextView mReservaItemView;

    private ReservaViewHolder(View itemView) {
        super(itemView);
        mReservaItemView = itemView.findViewById(R.id.textView);

        itemView.setOnCreateContextMenuListener(this);
    }

    public void bind(String text) {
        mReservaItemView.setText(text);
    }

    static ReservaViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ReservaViewHolder(view);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, Camping.MODIFICAR_RESERVA_ID, Menu.NONE, R.string.modificar_reserva);
        menu.add(Menu.NONE, Camping.ELIMINAR_RESERVA_ID, Menu.NONE, R.string.eliminar_reserva);
    }
}
