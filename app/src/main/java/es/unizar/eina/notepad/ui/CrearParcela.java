package es.unizar.eina.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.unizar.eina.notepad.R;

/** Pantalla utilizada para la creación o edición de una parcela */


public class CrearParcela extends AppCompatActivity {

    public static final String PARCELA_NOMBRE = "nombre";
    public static final String PARCELA_DESCRIPCION = "descripcion";
    public static final String PARCELA_ID = "id";
    public static final String PARCELA_NUMOCUPANTES = "numOcupantes";
    public static final String PARCELA_PRECIOPERSONA = "precioPersona";

    private EditText mNombreText;

    private EditText mDescripcionText;

    private EditText mNumOcupantesText;

    private EditText mPrecioPersonaText;

    private Integer mRowId;

    Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearparcela);

        //Para hacer esto necesitamos exportar las pantallas del prototipo para obtener el codigo directamente
        //Estos elementos son las distintas filas que hay que rellenar para poder crear una parcela
        mNombreText = findViewById(R.id.nombre);
        mDescripcionText = findViewById(R.id.descripcion);

        //Aquí íria el boton de reservar
        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mNombreText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            } else {
                replyIntent.putExtra(CrearParcela.PARCELA_NOMBRE, mNombreText.getText().toString());
                replyIntent.putExtra(CrearParcela.PARCELA_DESCRIPCION, mDescripcionText.getText().toString());
                if (mRowId!=null) {
                    replyIntent.putExtra(CrearParcela.PARCELA_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        populateFields();

    }

    //Preguntar como hacer para poblar los campos que usan enteros, ya que con el setText no me vale
    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mNombreText.setText(extras.getString(CrearParcela.PARCELA_NOMBRE));
            mDescripcionText.setText(extras.getString(CrearParcela.PARCELA_DESCRIPCION));
            mNumOcupantesText.setText(extras.getString(CrearParcela.PARCELA_NUMOCUPANTES));
            mPrecioPersonaText.setText(extras.getString(CrearParcela.PARCELA_PRECIOPERSONA));
            mRowId = extras.getInt(CrearParcela.PARCELA_ID);
        }
    }

}

