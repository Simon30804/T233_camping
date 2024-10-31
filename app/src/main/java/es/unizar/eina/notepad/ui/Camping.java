package es.unizar.eina.notepad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.notepad.R;
import es.unizar.eina.notepad.database.Note;
import es.unizar.eina.notepad.database.Parcela;
import es.unizar.eina.notepad.database.Reserva;

public class Camping extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camping);

        // Inicializamos los botones
        Button buttonCreateParcela = findViewById(R.id.button_create_parcela);
        Button buttonListParcelas = findViewById(R.id.button_list_parcelas);
        Button buttonCreateReserva = findViewById(R.id.button_create_reserva);
        Button buttonListReservas = findViewById(R.id.button_list_reservas);

        // Configuramos el botón para crear una parcela
        buttonCreateParcela.setOnClickListener(view -> {
            Intent intent = new Intent(Camping.this, CrearParcela.class);
            startActivity(intent);
        });

        // Configuramos el botón para listar las parcelas
        buttonListParcelas.setOnClickListener(view -> {
            Intent intent = new Intent(Camping.this, ListarParcelas.class);
            startActivity(intent);
        });

        // Configuramos el botón para crear una reserva
        buttonCreateReserva.setOnClickListener(view -> {
            Intent intent = new Intent(Camping.this, CrearReserva.class);
            startActivity(intent);
        });

        // Configuramos el botón para listar las reservas
        buttonListReservas.setOnClickListener(view -> {
            Intent intent = new Intent(Camping.this, ListarReservas.class);
            startActivity(intent);
        });
    }
}

interface ExecuteActivityResult2 {
    void process(Bundle extras, Parcela parcela);
    void process(Bundle extras, Reserva reserva);
}
