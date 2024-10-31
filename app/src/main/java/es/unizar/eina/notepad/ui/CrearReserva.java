package es.unizar.eina.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import es.unizar.eina.notepad.R;

/** Pantalla utilizada para la creación de una nueva reserva */

public class CrearReserva extends AppCompatActivity {

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
    private Button mAceptarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearreserva);

        // Enlazamos los elementos de la interfaz

        mFechaInicioText = findViewById(R.id.fecha_inicio);
        mFechaFinText = findViewById(R.id.fecha_fin);
        mNombreClienteText = findViewById(R.id.nombre_cliente);
        mTelefonoText = findViewById(R.id.telefono_cliente);
        mNumOcupantesText = findViewById(R.id.num_ocupantes);
        mAceptarButton = findViewById(R.id.button_aceptar_reserva);

        // Configuramos la acción del botón "Guardar"
        mAceptarButton.setOnClickListener(view -> {
            if (areFieldsValid()) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(RESERVA_FECHA_INICIO, mFechaInicioText.getText().toString());
                replyIntent.putExtra(RESERVA_FECHA_FIN, mFechaFinText.getText().toString());
                replyIntent.putExtra(RESERVA_NOMBRE_CLIENTE, mNombreClienteText.getText().toString());
                replyIntent.putExtra(RESERVA_TELEFONO, mTelefonoText.getText().toString());
                replyIntent.putExtra(RESERVA_NUM_OCUPANTES, Integer.parseInt(mNumOcupantesText.getText().toString()));

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
