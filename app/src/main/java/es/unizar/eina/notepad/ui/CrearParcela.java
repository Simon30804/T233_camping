//package es.unizar.eina.notepad.ui;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import es.unizar.eina.notepad.R;
//
///** Pantalla utilizada para la creación o edición de una parcela */
//
//
//public class CrearParcela extends AppCompatActivity {
//
//    public static final String PARCELA_NOMBRE = "nombre";
//    public static final String PARCELA_DESCRIPCION = "descripcion";
//    public static final String PARCELA_ID = "id";
//    public static final String PARCELA_NUMOCUPANTES = "numOcupantes";
//    public static final String PARCELA_PRECIOPERSONA = "precioPersona";
//
//    private EditText mNombreText;
//
//    private EditText mDescripcionText;
//
//    private EditText mNumOcupantesText;
//
//    private EditText mPrecioPersonaText;
//
//    private Integer mRowId;
//
//    Button mSaveButton;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_crearparcela);
//
//        //Para hacer esto necesitamos exportar las pantallas del prototipo para obtener el codigo directamente
//        //Estos elementos son las distintas filas que hay que rellenar para poder crear una parcela
//        mNombreText = findViewById(R.id.nombre);
//        mDescripcionText = findViewById(R.id.descripcion);
//
//        //Aquí íria el boton de reservar
//        mSaveButton = findViewById(R.id.button_save);
//        mSaveButton.setOnClickListener(view -> {
//            Intent replyIntent = new Intent();
//            if (TextUtils.isEmpty(mNombreText.getText())) {
//                setResult(RESULT_CANCELED, replyIntent);
//                Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
//            } else {
//                replyIntent.putExtra(CrearParcela.PARCELA_NOMBRE, mNombreText.getText().toString());
//                replyIntent.putExtra(CrearParcela.PARCELA_DESCRIPCION, mDescripcionText.getText().toString());
//                if (mRowId!=null) {
//                    replyIntent.putExtra(CrearParcela.PARCELA_ID, mRowId.intValue());
//                }
//                setResult(RESULT_OK, replyIntent);
//            }
//            finish();
//        });
//
//        populateFields();
//
//    }
//
//    //Preguntar como hacer para poblar los campos que usan enteros, ya que con el setText no me vale
//    private void populateFields () {
//        mRowId = null;
//        Bundle extras = getIntent().getExtras();
//        if (extras!=null) {
//            mNombreText.setText(extras.getString(CrearParcela.PARCELA_NOMBRE));
//            mDescripcionText.setText(extras.getString(CrearParcela.PARCELA_DESCRIPCION));
//            mNumOcupantesText.setText(extras.getString(CrearParcela.PARCELA_NUMOCUPANTES));
//            mPrecioPersonaText.setText(extras.getString(CrearParcela.PARCELA_PRECIOPERSONA));
//            mRowId = extras.getInt(CrearParcela.PARCELA_ID);
//        }
//    }
//
//}


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

/** Pantalla utilizada para la creación o edición de una parcela */

public class CrearParcela extends AppCompatActivity {

    public static final String PARCELA_NOMBRE = "nombre";
    public static final String PARCELA_DESCRIPCION = "descripcion";
    public static final String PARCELA_NUMOCUPANTES = "numOcupantes";
    public static final String PARCELA_PRECIOPERSONA = "precioPersona";

    private EditText mNombreText;
    private EditText mDescripcionText;
    private EditText mNumOcupantesText;
    private EditText mPrecioPersonaText;
    private ParcelaRepository parcelaRepository;
    private Button mAceptarButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearparcela);

        // Inicializamos el repositorio de Parcela
        parcelaRepository = new ParcelaRepository(getApplication());

        // Inicializamos los campos de entrada
        mNombreText = findViewById(R.id.nombre_parcela);
        mDescripcionText = findViewById(R.id.descripcion);
        mNumOcupantesText = findViewById(R.id.num_ocupantes); // ID basado en la imagen proporcionada
        mPrecioPersonaText = findViewById(R.id.precio_persona); // ID basado en la imagen proporcionada

        // Inicializamos el botón de guardar
        mAceptarButton = findViewById(R.id.button_aceptar_parcela);
        mAceptarButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            // Verificamos que el nombre y los otros campos obligatorios no estén vacíos
            if (TextUtils.isEmpty(mNombreText.getText()) || TextUtils.isEmpty(mNumOcupantesText.getText()) || TextUtils.isEmpty(mPrecioPersonaText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            } else {
                // Obtenemos los valores de los campos de entrada
                String nombre = mNombreText.getText().toString();
                String descripcion = mDescripcionText.getText().toString();
                int numOcupantes = Integer.parseInt(mNumOcupantesText.getText().toString());
                int precioPersona = Integer.parseInt(mPrecioPersonaText.getText().toString());

                // Creamos una nueva instancia de Parcela
                Parcela parcela = new Parcela(nombre, descripcion, numOcupantes, precioPersona);

                // Insertamos la parcela en la base de datos

                parcelaRepository.insert(parcela);
                String mensaje = "Parcela guardada correctamente:\n" +
                        "Nombre: " + nombre + "\n" +
                        "Descripción: " + descripcion + "\n" +
                        "Número de Ocupantes: " + numOcupantes + "\n" +
                        "Precio por Persona: " + precioPersona;

                // Mostramos el mensaje en un Toast
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Parcela guardada correctamente", Toast.LENGTH_SHORT).show();

                // Finalizamos la actividad
                finish();
            }
        });
        //populateFields();
    }

//    private void populateFields() {
//        mRowId = null;
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            // Cargamos los datos de la parcela si están disponibles
//            mNombreText.setText(extras.getString(CrearParcela.PARCELA_NOMBRE));
//            mDescripcionText.setText(extras.getString(CrearParcela.PARCELA_DESCRIPCION));
//
//            // Verificamos y cargamos el número de ocupantes y el precio como enteros
//            if (extras.containsKey(CrearParcela.PARCELA_NUMOCUPANTES)) {
//                mNumOcupantesText.setText(String.valueOf(extras.getInt(CrearParcela.PARCELA_NUMOCUPANTES)));
//            }
//            if (extras.containsKey(CrearParcela.PARCELA_PRECIOPERSONA)) {
//                mPrecioPersonaText.setText(String.valueOf(extras.getInt(CrearParcela.PARCELA_PRECIOPERSONA)));
//            }
//
//            // Cargamos el ID de la parcela
//            mRowId = extras.getInt(CrearParcela.PARCELA_ID);
//        }
//    }
}

