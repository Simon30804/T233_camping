package es.unizar.eina.notepad.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.notepad.R;

public class ListarReservas extends AppCompatActivity {

    private ReservaViewModel mReservaViewModel;
    private RecyclerView mRecyclerView;
    private ReservaListAdapter mAdapter;
    private Spinner mOrderSpinner;
    private Button mModificarButton;
    private Button mEliminarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarreservas);

        // Configurar RecyclerView
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ReservaListAdapter(new ReservaListAdapter.ReservaDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar ViewModel
        mReservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);
        mReservaViewModel.getAllReservas().observe(this, reservas -> {
            mAdapter.submitList(reservas);
        });



        // Configurar Spinner para ordenar
        mOrderSpinner = findViewById(R.id.spinner_order);
//        mOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedOrder = parent.getItemAtPosition(position).toString();
//                Toast.makeText(ListarReservas.this, "Ordenar por: " + selectedOrder, Toast.LENGTH_SHORT).show();
//                // Aquí podrías implementar la lógica de ordenación en el ViewModel si es necesario
//            }
//
//
//        });

        // Configurar botones de modificar y eliminar
        mModificarButton = findViewById(R.id.modificar_reserva);
        mEliminarButton = findViewById(R.id.eliminar_reserva);

        mModificarButton.setOnClickListener(v -> {
            // Lógica para modificar reserva
            Toast.makeText(this, "Modificar reserva seleccionado", Toast.LENGTH_SHORT).show();
            // Aquí podrías implementar la lógica de modificación
        });

        mEliminarButton.setOnClickListener(v -> {
            // Lógica para eliminar reserva
            Toast.makeText(this, "Eliminar reserva seleccionado", Toast.LENGTH_SHORT).show();
            // Aquí podrías implementar la lógica de eliminación
        });
    }
}