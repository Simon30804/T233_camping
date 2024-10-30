package es.unizar.eina.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.unizar.eina.notepad.R;

/** Pantalla utilizada para la creación o edición de una reserva */

public class CrearReserva extends AppCompatActivity {

    public static final String RESERVA_FECHAINICIO = "fecha_inicio";
    public static final String RESERVA_FECHAFIN = "fecha_fin";
    public static final String RESERVA_NOMBRECLIENTE = "nombre_cliente";
    public static final String RESERVA_TELEFONO = "telefono";
    public static final String RESERVA_NUMOCUPANTES = "numOcupantes";
    public static final String RESERVA_ID = "id";

    private EditText mFechaInicioText;

    private EditText mFechaFinText;

    private EditText mNombreClienteText;

    private EditText mTelefonoText;

    private EditText mNumOcupantesText;

    private Integer mRowId;

    Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearreserva);

        mFechaInicioText = findViewById(R.id.fecha_inicio);
        mFechaFinText = findViewById(R.id.fecha_fin);
        mNombreClienteText = findViewById(R.id.nombre_cliente);
        mTelefonoText = findViewById(R.id.telefono);
        mNumOcupantesText = findViewById(R.id.numOcupantes);

        mSaveButton = findViewById(R.id.button_save);

        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mFechaInicioText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            } else {
                replyIntent.putExtra(CrearReserva.RESERVA_FECHAINICIO, mFechaInicioText.getText().toString());
                replyIntent.putExtra(CrearReserva.RESERVA_FECHAFIN, mFechaFinText.getText().toString());
                replyIntent.putExtra(CrearReserva.RESERVA_NOMBRECLIENTE, mNombreClienteText.getText().toString());
                replyIntent.putExtra(CrearReserva.RESERVA_TELEFONO, mTelefonoText.getText().toString());
                replyIntent.putExtra(CrearReserva.RESERVA_NUMOCUPANTES, mNumOcupantesText.getText().toString());
                if (mRowId!=null) {
                    replyIntent.putExtra(CrearReserva.RESERVA_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mFechaInicioText.setText(extras.getString(CrearReserva.RESERVA_FECHAINICIO));
            mFechaFinText.setText(extras.getString(CrearReserva.RESERVA_FECHAFIN));
            mNombreClienteText.setText(extras.getString(CrearReserva.RESERVA_NOMBRECLIENTE));
            mTelefonoText.setText(extras.getString(CrearReserva.RESERVA_TELEFONO));
            mNumOcupantesText.setText(extras.getString(CrearReserva.RESERVA_NUMOCUPANTES));
            mRowId = extras.getInt(CrearReserva.RESERVA_ID);
        }
    }

}