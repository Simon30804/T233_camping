package es.unizar.eina.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import es.unizar.eina.notepad.R;
import es.unizar.eina.notepad.database.Reserva;
import es.unizar.eina.notepad.database.ReservaRepository;

/** Pantalla utilizada para modificar una reserva existente */

public class ModificarReserva extends AppCompatActivity {

    public static final String RESERVA_ID = "id";
    public static final String RESERVA_FECHA_INICIO = "fecha_inicio";
    public static final String RESERVA_FECHA_FIN = "fecha_fin";
    public static final String RESERVA_NOMBRE_CLIENTE = "nombre_cliente";
    public static final String RESERVA_TELEFONO = "telefono";
    public static final String RESERVA_NUM_OCUPANTES = "num_ocupantes";

    private EditText mFechaInicioText;
    private EditText mFechaFinText;
    private EditText mNombreClienteText;
    private EditText mTelefonoText;
    private EditText mNumOcupantesText;
    private Button mGuardarButton;

    private ReservaRepository reservaRepository;
    private int reservaId; // ID de la reserva que se está editando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarreserva);

        // Inicializamos el repositorio de la base de datos
        reservaRepository = new ReservaRepository(getApplication());

        // Enlazamos los elementos de la interfaz
        mFechaInicioText = findViewById(R.id.fecha_inicio);
        mFechaFinText = findViewById(R.id.fecha_fin);
        mNombreClienteText = findViewById(R.id.nombre_cliente);
        mTelefonoText = findViewById(R.id.telefono_cliente);
        mNumOcupantesText = findViewById(R.id.num_ocupantes);
        mGuardarButton = findViewById(R.id.button_modificar_reserva);

        // Obtenemos los datos de la reserva desde el Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RESERVA_ID)) {
            reservaId = intent.getIntExtra(RESERVA_ID, -1);
            mFechaInicioText.setText(intent.getStringExtra(RESERVA_FECHA_INICIO));
            mFechaFinText.setText(intent.getStringExtra(RESERVA_FECHA_FIN));
            mNombreClienteText.setText(intent.getStringExtra(RESERVA_NOMBRE_CLIENTE));
            mTelefonoText.setText(intent.getStringExtra(RESERVA_TELEFONO));
            mNumOcupantesText.setText(String.valueOf(intent.getIntExtra(RESERVA_NUM_OCUPANTES, 1)));
        }

        // Configuramos la acción del botón "Guardar"
        mGuardarButton.setOnClickListener(view -> {
            if (areFieldsValid()) {
                // Creamos un objeto Reserva con los datos actualizados
                Reserva reserva = new Reserva(reservaId,
                        mFechaInicioText.getText().toString(),
                        mFechaFinText.getText().toString(),
                        mNombreClienteText.getText().toString(),
                        mTelefonoText.getText().toString(),
                        Integer.parseInt(mNumOcupantesText.getText().toString()));

                // Actualizamos la reserva en la base de datos
                reservaRepository.update(reserva);

                // Mostramos un mensaje de confirmación
                Toast.makeText(this, "Reserva actualizada correctamente", Toast.LENGTH_SHORT).show();

                // Enviamos el resultado y finalizamos la actividad
                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                finish();
            } else {
                Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Método para validar que los campos requeridos no estén vacíos.
     *
     * @return true si todos los campos son válidos, false en caso contrario.
     */
    private boolean areFieldsValid() {
        return !TextUtils.isEmpty(mFechaInicioText.getText()) &&
                !TextUtils.isEmpty(mFechaFinText.getText()) &&
                !TextUtils.isEmpty(mNombreClienteText.getText()) &&
                !TextUtils.isEmpty(mTelefonoText.getText()) &&
                !TextUtils.isEmpty(mNumOcupantesText.getText());
    }
}

