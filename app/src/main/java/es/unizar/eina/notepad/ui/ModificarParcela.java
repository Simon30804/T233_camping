package es.unizar.eina.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import es.unizar.eina.notepad.R;
import es.unizar.eina.notepad.database.Parcela;
import es.unizar.eina.notepad.database.ParcelaRepository;

public class ModificarParcela extends AppCompatActivity {

    public static final String PARCELA_NOMBRE = "nombre";
    public static final String PARCELA_DESCRIPCION = "descripcion";
    public static final String PARCELA_NUM_OCUPANTES = "numOcupantes";
    public static final String PARCELA_PRECIO_PERSONA = "precioPersona";

    private EditText mNombreText;
    private EditText mDescripcionText;
    private EditText mNumOcupantesText;
    private EditText mPrecioPersonaText;
    private Button mGuardarButton;

    private ParcelaRepository parcelaRepository;
    private String parcelaNombre; // Nombre de la parcela que se está editando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarparcela);

        // Inicializamos el repositorio de la base de datos
        parcelaRepository = new ParcelaRepository(getApplication());

        // Enlazamos los elementos de la interfaz
        mNombreText = findViewById(R.id.nombre_parcela);
        mDescripcionText = findViewById(R.id.descripcion);
        mNumOcupantesText = findViewById(R.id.num_ocupantes);
        mPrecioPersonaText = findViewById(R.id.precio_persona);
        mGuardarButton = findViewById(R.id.button_modif_parcela);

        // Obtenemos los datos de la parcela desde el Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PARCELA_NOMBRE)) {
            parcelaNombre = intent.getStringExtra(PARCELA_NOMBRE);
            mNombreText.setText(parcelaNombre);
            mDescripcionText.setText(intent.getStringExtra(PARCELA_DESCRIPCION));
            mNumOcupantesText.setText(String.valueOf(intent.getIntExtra(PARCELA_NUM_OCUPANTES, 1)));
            mPrecioPersonaText.setText(String.valueOf(intent.getDoubleExtra(PARCELA_PRECIO_PERSONA, 0.0)));
        }

        // Configuramos la acción del botón "Guardar"
        mGuardarButton.setOnClickListener(view -> {
            if (areFieldsValid()) {
                // Creamos un objeto Parcela con los datos actualizados
                Parcela parcela = new Parcela(
                        parcelaNombre, // No cambiaremos el nombre, que es la clave primaria
                        mDescripcionText.getText().toString(),
                        Integer.parseInt(mNumOcupantesText.getText().toString()),
                        Double.parseDouble(mPrecioPersonaText.getText().toString())
                );

                // Actualizamos la parcela en la base de datos
                parcelaRepository.update(parcela);

                // Mostramos un mensaje de confirmación
                Toast.makeText(this, "Parcela actualizada correctamente", Toast.LENGTH_SHORT).show();

                // Enviamos el resultado y finalizamos la actividad
                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                finish();
            } else {
                Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para validar que los campos requeridos no estén vacíos
    private boolean areFieldsValid() {
        return !TextUtils.isEmpty(mDescripcionText.getText()) &&
                !TextUtils.isEmpty(mNumOcupantesText.getText()) &&
                !TextUtils.isEmpty(mPrecioPersonaText.getText());
    }
}

