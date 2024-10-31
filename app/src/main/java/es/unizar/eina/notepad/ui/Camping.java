package es.unizar.eina.notepad.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.notepad.database.Note; //Esto lo tendremos que quitar en el futuro
import es.unizar.eina.notepad.database.Parcela;
import es.unizar.eina.notepad.database.Reserva;
import es.unizar.eina.notepad.R;

import static androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;



/** Pantalla principal de la aplicación Camping */
public class Camping extends AppCompatActivity {
    private ParcelaViewModel mParcelaViewModel;
    private ReservaViewModel mReservaViewModel;

    static final int CREAR_PARCELA_ID = Menu.FIRST;
    static final int LISTAR_PARCELAS_ID = Menu.FIRST + 1;
    static final int CREAR_RESERVA_ID = Menu.FIRST + 2;
    static final int LISTAR_RESERVAS_ID = Menu.FIRST + 3;
    static final int MODIFICAR_PARCELA_ID = Menu.FIRST + 4;
    static final int ELIMINAR_PARCELA_ID = Menu.FIRST + 5;
    static final int MODIFICAR_RESERVA_ID = Menu.FIRST + 6;
    static final int ELIMINAR_RESERVA_ID = Menu.FIRST + 7;

    RecyclerView mRecyclerView;
    ParcelaListAdapter mParAdapter;
    ReservaListAdapter mResAdapter;
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camping);

        Button buttonCreateParcela = findViewById(R.id.button_create_parcela);
        Button buttonListParcelas = findViewById(R.id.button_list_parcelas);
        Button buttonCreateReserva = findViewById(R.id.button_create_reserva);
        Button buttonListReservas = findViewById(R.id.button_list_reservas);

        buttonCreateParcela.setOnClickListener(view -> {
            // Inicia la actividad para crear una nueva parcela
            Intent intent = new Intent(Camping.this, CrearParcela.class);
            startActivity(intent);
        });

        buttonListParcelas.setOnClickListener(view -> {
            // Inicia la actividad para listar todas las parcelas
            Intent intent = new Intent(Camping.this, ListarParcelas.class);
            startActivity(intent);
        });

        buttonCreateReserva.setOnClickListener(view -> {
            // Inicia la actividad para crear una nueva reserva
            Intent intent = new Intent(Camping.this, CrearReserva.class);
            startActivity(intent);
        });

        buttonListReservas.setOnClickListener(view -> {
            // Inicia la actividad para listar todas las reservas
            Intent intent = new Intent(Camping.this, ListarReservas.class);
            startActivity(intent);
        });
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camping);
//        mRecyclerView = findViewById(R.id.recyclerview);
//        mParAdapter = new ParcelaListAdapter(new ParcelaListAdapter.ParcelaDiff());
//        mResAdapter = new ReservaListAdapter(new ReservaListAdapter.ReservaDiff());
//        mRecyclerView.setAdapter(mParAdapter);
//        mRecyclerView.setAdapter(mResAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mParcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);
//        mReservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);
//
//        mParcelaViewModel.getAllParcelas().observe(this, parcelas -> {
//            // Update the cached copy of the parcelas in the adapter.
//            mParAdapter.submitList(parcelas);
//        });
//
//        mReservaViewModel.getAllReservas().observe(this, reservas -> {
//            // Update the cached copy of the reservas in the adapter.
//            mResAdapter.submitList(reservas);
//        });
//
//        mFab = findViewById(R.id.fab);
//        mFab.setOnClickListener(view -> createParcela());
//
//        // It doesn't affect if we comment the following instruction
//        registerForContextMenu(mRecyclerView);
//
//    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, CREAR_PARCELA_ID, Menu.NONE, "Crear parcela");
        menu.add(Menu.NONE, LISTAR_PARCELAS_ID, Menu.NONE, "Listar parcelas");
        menu.add(Menu.NONE, CREAR_RESERVA_ID, Menu.NONE, "Crear reserva");
        menu.add(Menu.NONE, LISTAR_RESERVAS_ID, Menu.NONE, "Listar reservas");
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CREAR_PARCELA_ID:
                createParcela();
                return true;
            case LISTAR_PARCELAS_ID:
                listarParcelas();
                return true;
            case CREAR_RESERVA_ID:
                createReserva();
                return true;
            case LISTAR_RESERVAS_ID:
                listarReservas();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Ver como hacer las opciones, ya que nosotros tenemos tambien un eliminar y un modificar pero no estan en la pantalla principal, luego
    // yo creo que esto sobra, y que irá en el codigo de la pantalla correspondiente que será donde aparece la opcion de eliminar y modificar
//    public boolean onContextItemSelected(MenuItem item) {
//        Parcela current = mParAdapter.getCurrent();
//        Reserva currentRes = mResAdapter.getCurrent();
//        switch (item.getItemId()) {
//            case DELETE_ID:
//                Toast.makeText(
//                        getApplicationContext(),
//                        "Deleting " + current.getNombre(),
//                        Toast.LENGTH_LONG).show();
//                mParcelaViewModel.delete(current);
//                return true;
//            case EDIT_ID:
//                editParcela(current);
//                return true;
//            case DELETE_ID:
//                Toast.makeText(
//                        getApplicationContext(),
//                        "Deleting " + currentRes.getNombre(),
//                        Toast.LENGTH_LONG).show();
//                mReservaViewModel.delete(currentRes);
//                return true;
//            case EDIT_ID:
//                editReserva(currentRes);
//                return true;
//        }
//        return super.onContextItemSelected(item);
//    }

    private void createParcela() {
        mStartCreateParcela.launch(new Intent(this, CrearParcela.class));
    }

    ActivityResultLauncher<Intent> mStartCreateParcela = newActivityResultLauncher(new ExecuteActivityResult2() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            mParcelaViewModel.insert(parcela);
        }

        @Override
        public void process(Bundle extras, Reserva reserva) {
            mReservaViewModel.insert(reserva);
        }
    });

    private void listarParcelas() {
        mStartListarParcelas.launch(new Intent(this, ListarParcelas.class));
    }

    ActivityResultLauncher<Intent> mStartListarParcelas = newActivityResultLauncher(new ExecuteActivityResult2() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            mParcelaViewModel.insert(parcela);
        }

        @Override
        public void process(Bundle extras, Reserva reserva) {
            mReservaViewModel.insert(reserva);
        }
    });



    private void createReserva() {
        mStartCreateReserva.launch(new Intent(this, CrearReserva.class));
    }

    ActivityResultLauncher<Intent> mStartCreateReserva = newActivityResultLauncher(new ExecuteActivityResult2() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            mParcelaViewModel.insert(parcela);
        }

        @Override
        public void process(Bundle extras, Reserva reserva) {
            mReservaViewModel.insert(reserva);
        }
    });

    private void listarReservas() {
        mStartListarReservas.launch(new Intent(this, ListarReservas.class));
    }

    ActivityResultLauncher<Intent> mStartListarReservas = newActivityResultLauncher(new ExecuteActivityResult2() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            mParcelaViewModel.insert(parcela);
        }

        @Override
        public void process(Bundle extras, Reserva reserva) {
            mReservaViewModel.insert(reserva);
        }
    });

    ActivityResultLauncher<Intent> newActivityResultLauncher(ExecuteActivityResult2 executable) {
        return registerForActivityResult(
                new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Parcela parcela = new Parcela(extras.getString(CrearParcela.PARCELA_NOMBRE),
                                extras.getString(CrearParcela.PARCELA_DESCRIPCION),
                                extras.getInt(CrearParcela.PARCELA_NUMOCUPANTES),
                                extras.getFloat(CrearParcela.PARCELA_PRECIOPERSONA));
                        executable.process(extras, parcela);
                    }
                });
    }

    //La pantalla camping tiene las opciones de crear una parcela, listar las parcelas que hay, crear una reserva o listar las reservas que hay

    //En la pantalla de listar parcelas y reservas, se podrá modificar o eliminar una parcela o reserva, y se podrá volver a la pantalla principal

    //En la pantalla de crear parcela y reserva, se podrá crear una parcela o reserva, y se podrá volver a la pantalla principal



    private void editParcela(Parcela current) {
        Intent intent = new Intent(this, ParcelaEdit.class);
        intent.putExtra(ParcelaEdit.PARCELA_NOMBRE, current.getNombre());
        intent.putExtra(ParcelaEdit.PARCELA_DESCRIPCION, current.getDescripcion());
        intent.putExtra(ParcelaEdit.PARCELA_ID, current.getId());
        mStartUpdateParcela.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartUpdateParcela = newActivityResultLauncher(new ExecuteActivityResult2() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            int id = extras.getInt(ParcelaEdit.PARCELA_ID);
            parcela.setId(id);
            mParcelaViewModel.update(parcela);
        }
    });

    private void editReserva(Reserva currentRes) {
        Intent intent = new Intent(this, ReservaEdit.class);
        intent.putExtra(ReservaEdit.RESERVA_NOMBRE, currentRes.getNombre());
        intent.putExtra(ReservaEdit.RESERVA_FECHA, currentRes.getFecha());
        intent.putExtra(ReservaEdit.RESERVA_PARCELA, currentRes.getParcela());
        intent.putExtra(ReservaEdit.RESERVA_ID, currentRes.getId());
        mStartUpdateReserva.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartUpdateReserva = newActivityResultLauncher(new ExecuteActivityResult2() {
        @Override
        public void process(Bundle extras, Reserva reserva) {
            int id = extras.getInt(ReservaEdit.RESERVA_ID);
            reserva.setId(id);
            mReservaViewModel.update(reserva);
        }
    });

}

interface ExecuteActivityResult2 {
    void process(Bundle extras, Parcela parcela);
    void process(Bundle extras, Reserva reserva);
}