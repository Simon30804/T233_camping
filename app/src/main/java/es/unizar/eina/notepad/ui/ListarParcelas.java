package es.unizar.eina.notepad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import es.unizar.eina.notepad.R;
import es.unizar.eina.notepad.database.Parcela;

public class ListarParcelas extends AppCompatActivity {

    private LinearLayout listaParcelas;
    private LinearLayout layoutBotones;
    private Button buttonVolver;
    private Button buttonModificar;
    private Button buttonEliminar;
    private Spinner spinnerOrder;
    private ArrayList<CheckBox> checkBoxes;
    private ParcelaViewModel parcelaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarparcelas);

        // Inicialización de vistas
        listaParcelas = findViewById(R.id.lista_parcelas);
        layoutBotones = findViewById(R.id.layout_botones);
        buttonVolver = findViewById(R.id.button_volver);
        buttonModificar = findViewById(R.id.button_modificar);
        buttonEliminar = findViewById(R.id.button_eliminar);
        spinnerOrder = findViewById(R.id.spinner_order);

        checkBoxes = new ArrayList<>();

        // Inicializar el ViewModel
        parcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);

        // Observar el LiveData de parcelas
        parcelaViewModel.getAllParcelas().observe(this, new Observer<List<Parcela>>() {
            @Override
            public void onChanged(List<Parcela> parcelas) {
                // Limpiar la lista actual de CheckBoxes
                listaParcelas.removeAllViews();
                checkBoxes.clear();

                // Crear un CheckBox para cada parcela en la lista
                for (Parcela parcela : parcelas) {
                    CheckBox checkBox = new CheckBox(ListarParcelas.this);
                    checkBox.setText(parcela.getNombre());
                    checkBox.setTag(parcela.getNombre());
                    checkBox.setOnClickListener(ListarParcelas.this::onCheckBoxClicked);
                    listaParcelas.addView(checkBox);
                    checkBoxes.add(checkBox);
                }
                layoutBotones.setVisibility(View.GONE);
            }
        });


        // Configurar el listener del botón
        buttonVolver.setOnClickListener(view -> {
            // Crear el Intent para abrir la actividad Camping
            Intent intent = new Intent(ListarParcelas.this, Camping.class);
            startActivity(intent);

            // Finalizar la actividad actual
            finish();
        });



        buttonModificar.setOnClickListener(v -> {
            CheckBox selectedCheckBox = getSelectedCheckBox();
            if (selectedCheckBox != null) {
                String parcelaNombre = (String) selectedCheckBox.getTag();

                // Llamada al método para obtener la parcela real desde la base de datos
                Parcela parcelaSeleccionada = obtenerParcelaPorNombre(parcelaNombre);
                if (parcelaSeleccionada != null) {
                    Intent intent = new Intent(ListarParcelas.this, ModificarParcela.class);
                    intent.putExtra(ModificarParcela.PARCELA_NOMBRE, parcelaSeleccionada.getNombre());
                    intent.putExtra(ModificarParcela.PARCELA_DESCRIPCION, parcelaSeleccionada.getDescripcion());
                    intent.putExtra(ModificarParcela.PARCELA_NUM_OCUPANTES, parcelaSeleccionada.getNumOcupantes());
                    intent.putExtra(ModificarParcela.PARCELA_PRECIO_PERSONA, parcelaSeleccionada.getPrecioPersona());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Parcela no encontrada", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Seleccione una parcela para modificar", Toast.LENGTH_SHORT).show();
            }
        });

        buttonEliminar.setOnClickListener(v -> {
            CheckBox selectedCheckBox = getSelectedCheckBox();
            if (selectedCheckBox != null) {
                String parcelaNombre = (String) selectedCheckBox.getTag();
                Parcela parcelaSeleccionada = obtenerParcelaPorNombre(parcelaNombre);
                if (parcelaSeleccionada != null) {
                    parcelaViewModel.delete(parcelaSeleccionada);
                    Toast.makeText(this, "Parcela eliminada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Parcela no encontrada", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Seleccione una parcela para eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private CheckBox getSelectedCheckBox() {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                return checkBox;
            }
        }
        return null;
    }

    private void onCheckBoxClicked(View view) {
        boolean anyChecked = checkBoxes.stream().anyMatch(CheckBox::isChecked);
        layoutBotones.setVisibility(anyChecked ? View.VISIBLE : View.GONE);
    }

    // Modificado para obtener la parcela real desde el ViewModel
    private Parcela obtenerParcelaPorNombre(String parcelaNombre) {
        List<Parcela> parcelas = parcelaViewModel.getAllParcelas().getValue();
        if (parcelas != null) {
            for (Parcela parcela : parcelas) {
                if (parcela.getNombre().equals(parcelaNombre)) {
                    return parcela;
                }
            }
        }
        return null;
    }
}



//package es.unizar.eina.notepad.ui;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import es.unizar.eina.notepad.R;
//import es.unizar.eina.notepad.database.Parcela;
//
//
//public class ListarParcelas extends AppCompatActivity {
//
//    private LinearLayout listaParcelas;
//    private LinearLayout layoutBotones;
//    private Button buttonModificar;
//    private Button buttonEliminar;
//    private Spinner spinnerOrder;
//    private ArrayList<CheckBox> checkBoxes;
//    private ParcelaViewModel parcelaViewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_listarparcelas);
//
//        // Inicialización de vistas
//        listaParcelas = findViewById(R.id.lista_parcelas);
//        layoutBotones = findViewById(R.id.layout_botones);
//        buttonModificar = findViewById(R.id.button_modificar);
//        buttonEliminar = findViewById(R.id.button_eliminar);
//        spinnerOrder = findViewById(R.id.spinner_order);
//
//        checkBoxes = new ArrayList<>();
//
//        // Inicializar el ViewModel
//        parcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);
//
//        // Observar el LiveData de parcelas
//        parcelaViewModel.getAllParcelas().observe(this, new Observer<List<Parcela>>() {
//            @Override
//            public void onChanged(List<Parcela> parcelas) {
//                // Limpiar la lista actual de CheckBoxes
//                listaParcelas.removeAllViews();
//                checkBoxes.clear();
//
//                // Crear un CheckBox para cada parcela en la lista
//                for (Parcela parcela : parcelas) {
//                    CheckBox checkBox = new CheckBox(ListarParcelas.this);
//                    checkBox.setText(parcela.getNombre());
//                    checkBox.setTag(parcela.getNombre());
//                    checkBox.setOnClickListener(ListarParcelas.this::onCheckBoxClicked);
//                    listaParcelas.addView(checkBox);
//                    checkBoxes.add(checkBox);
//                }
//            }
//        });
//
//        buttonModificar.setOnClickListener(v -> {
//            CheckBox selectedCheckBox = getSelectedCheckBox();
//            if (selectedCheckBox != null) {
//                String parcelaNombre = (String) selectedCheckBox.getTag();
//
//                Intent intent = new Intent(ListarParcelas.this, ModificarParcela.class);
//                Parcela parcelaSeleccionada = obtenerParcelaPorNombre(parcelaNombre);
//                if (parcelaSeleccionada != null) {
//                    intent.putExtra(ModificarParcela.PARCELA_NOMBRE, parcelaSeleccionada.getNombre());
//                    intent.putExtra(ModificarParcela.PARCELA_DESCRIPCION, parcelaSeleccionada.getDescripcion());
//                    intent.putExtra(ModificarParcela.PARCELA_NUM_OCUPANTES, parcelaSeleccionada.getNumOcupantes());
//                    intent.putExtra(ModificarParcela.PARCELA_PRECIO_PERSONA, parcelaSeleccionada.getPrecioPersona());
//
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, "Parcela no encontrada", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(this, "Seleccione una parcela para modificar", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private CheckBox getSelectedCheckBox() {
//        for (CheckBox checkBox : checkBoxes) {
//            if (checkBox.isChecked()) {
//                return checkBox;
//            }
//        }
//        return null;
//    }
//
//    private void onCheckBoxClicked(View view) {
//        boolean anyChecked = checkBoxes.stream().anyMatch(CheckBox::isChecked);
//        layoutBotones.setVisibility(anyChecked ? View.VISIBLE : View.GONE);
//    }
//
//    private Parcela obtenerParcelaPorNombre(String parcelaNombre) {
//        for (CheckBox checkBox : checkBoxes) {
//            if (checkBox.isChecked()) {
//                return new Parcela(parcelaNombre, "Descripción de ejemplo", 4, 10.0);
//            }
//        }
//        return null;
//    }
//}

