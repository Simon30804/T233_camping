package es.unizar.eina.notepad.ui;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.notepad.R;

class ParcelaViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    private final TextView mParcelaItemView;

    private ParcelaViewHolder(View itemView) {
        super(itemView);
        mParcelaItemView = itemView.findViewById(R.id.textView);

        itemView.setOnCreateContextMenuListener(this);
    }

    public void bind(String text) {
        mParcelaItemView.setText(text);
    }

    static ParcelaViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ParcelaViewHolder(view);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, Camping.MODIFICAR_PARCELA_ID, Menu.NONE, R.string.modificar_parcela);
        menu.add(Menu.NONE, Camping.ELIMINAR_PARCELA_ID, Menu.NONE, R.string.eliminar_parcela);
    }

}
