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
import es.unizar.eina.notepad.database.Reserva;
import es.unizar.eina.notepad.database.ReservaDao;


public class ListarReservas extends AppCompatActivity {

    private LinearLayout listaReservas;
    private LinearLayout layoutBotones;
    private Button buttonVolver;
    private Button buttonModificar;
    private Button buttonEliminar;
    private Spinner spinnerOrder;
    private ArrayList<CheckBox> checkBoxes;
    //private ArrayList<Reserva> reservas;
    private ReservaViewModel reservaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarreservas);

        // Inicialización de vistas
        listaReservas = findViewById(R.id.lista_reservas);
        layoutBotones = findViewById(R.id.layout_botones);
        buttonVolver = findViewById(R.id.button_volver);
        buttonModificar = findViewById(R.id.button_modificar);
        buttonEliminar = findViewById(R.id.button_eliminar);
        spinnerOrder = findViewById(R.id.spinner_order);

        checkBoxes = new ArrayList<>();

        // Inicializar el ViewModel
        reservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);

        // Observar el LiveData de reservas
        reservaViewModel.getAllReservas().observe(this, new Observer<List<Reserva>>() {
            @Override
            public void onChanged(List<Reserva> reservas) {
                // Limpia la lista actual de CheckBoxes
                listaReservas.removeAllViews();
                checkBoxes.clear();

                // Crear un CheckBox para cada reserva en la lista
                for (Reserva reserva : reservas) {
                    CheckBox checkBox = new CheckBox(ListarReservas.this);
                    checkBox.setText(reserva.getNombreCliente()); // Mostrar el nombre del cliente
                    checkBox.setTag(reserva.getId()); // Guarda el ID en el tag
                    checkBox.setOnClickListener(ListarReservas.this::onCheckBoxClicked);
                    listaReservas.addView(checkBox);
                    checkBoxes.add(checkBox);
                }
                layoutBotones.setVisibility(View.GONE);
            }
        });

        // Configurar el listener del botón
        buttonVolver.setOnClickListener(view -> {
            // Crear el Intent para abrir la actividad Camping
            Intent intent = new Intent(ListarReservas.this, Camping.class);
            startActivity(intent);

            // Finalizar la actividad actual
            finish();
        });

        buttonModificar.setOnClickListener(v -> {
            CheckBox selectedCheckBox = getSelectedCheckBox();
            if (selectedCheckBox != null) {
                int reservaId = getReservaId(selectedCheckBox); // Obtén el ID real de la reserva

                Intent intent = new Intent(ListarReservas.this, ModificarReserva.class);
                Reserva reservaSeleccionada = obtenerReservaPorId(reservaId);
                if (reservaSeleccionada != null) {
                    intent.putExtra(ModificarReserva.RESERVA_ID, reservaSeleccionada.getId());
                    intent.putExtra(ModificarReserva.RESERVA_FECHA_INICIO, reservaSeleccionada.getFechaInicio());
                    intent.putExtra(ModificarReserva.RESERVA_FECHA_FIN, reservaSeleccionada.getFechaFin());
                    intent.putExtra(ModificarReserva.RESERVA_NOMBRE_CLIENTE, reservaSeleccionada.getNombreCliente());
                    intent.putExtra(ModificarReserva.RESERVA_TELEFONO, reservaSeleccionada.getTelefono());
                    intent.putExtra(ModificarReserva.RESERVA_NUM_OCUPANTES, reservaSeleccionada.getNumOcupantes());

                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Reserva no encontrada", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Seleccione una reserva para modificar", Toast.LENGTH_SHORT).show();
            }
        });

        //Ahora quiero escribir el codigo necesario para que al pulsar el boton eliminar, borre la reserva seleccionada de la base de datos
        buttonEliminar.setOnClickListener(v -> {
            CheckBox selectedCheckBox = getSelectedCheckBox();
            if (selectedCheckBox != null) {
                int reservaId = getReservaId(selectedCheckBox); // Obtenemos el ID real de la reserva
                Reserva reservaSeleccionada = obtenerReservaPorId(reservaId);
                if (reservaSeleccionada != null) {
                    reservaViewModel.delete(reservaSeleccionada);
                    Toast.makeText(this, "Reserva eliminada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Reserva no encontrada", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Seleccione una reserva para eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método que se ejecuta cuando un CheckBox es clickeado
    private void onCheckBoxClicked(View view) {
        boolean anyChecked = false;
        // Verificar si al menos un CheckBox está seleccionado
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                anyChecked = true;
                break;
            }
        }
        // Mostrar u ocultar los botones en función de la selección
        if (anyChecked) {
            layoutBotones.setVisibility(View.VISIBLE);
        } else {
            layoutBotones.setVisibility(View.GONE);
        }
    }


    private CheckBox getSelectedCheckBox() {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                return checkBox;
            }
        }
        return null;
    }

    private int getReservaId(CheckBox checkBox) {
        return (int) checkBox.getTag();
    }

    private Reserva obtenerReservaPorId(int reservaId) {
        List<Reserva> reservas = reservaViewModel.getAllReservas().getValue();
        if (reservas != null) {
            for (Reserva reserva : reservas) {
                if (reserva.getId() == reservaId) {
                    return reserva;
                }
            }
        }
        return null;
    }


//        // Generar los CheckBoxes dinámicamente y asociarlos con sus reservas
//        for (Reserva reserva : reservas) {
//            CheckBox checkBox = new CheckBox(this);
//            checkBox.setText(reserva.getNombreCliente()); // Muestra el nombre del cliente
//            checkBox.setTag(reserva.getId()); // Guarda el ID de la reserva como etiqueta
//            checkBox.setOnClickListener(this::onCheckBoxClicked);
//            listaReservas.addView(checkBox);
//            checkBoxes.add(checkBox);
//        }
//
//        buttonModificar.setOnClickListener(v -> {
//            CheckBox selectedCheckBox = getSelectedCheckBox();
//            if (selectedCheckBox != null) {
//                int reservaId = getReservaId(selectedCheckBox); // Obtén el ID real de la reserva
//
//                Intent intent = new Intent(ListarReservas.this, ModificarReserva.class);
//                Reserva reservaSeleccionada = obtenerReservaPorId(reservaId);
//                if (reservaSeleccionada != null) {
//                    intent.putExtra(ModificarReserva.RESERVA_ID, reservaSeleccionada.getId());
//                    intent.putExtra(ModificarReserva.RESERVA_FECHA_INICIO, reservaSeleccionada.getFechaInicio());
//                    intent.putExtra(ModificarReserva.RESERVA_FECHA_FIN, reservaSeleccionada.getFechaFin());
//                    intent.putExtra(ModificarReserva.RESERVA_NOMBRE_CLIENTE, reservaSeleccionada.getNombreCliente());
//                    intent.putExtra(ModificarReserva.RESERVA_TELEFONO, reservaSeleccionada.getTelefono());
//                    intent.putExtra(ModificarReserva.RESERVA_NUM_OCUPANTES, reservaSeleccionada.getNumOcupantes());
//
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, "Reserva no encontrada", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(this, "Seleccione una reserva para modificar", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Configuración del botón Eliminar
//        buttonEliminar.setOnClickListener(v -> {
//            Toast.makeText(this, "Eliminar reserva seleccionada", Toast.LENGTH_SHORT).show();
//            // Implementa la lógica para eliminar la reserva seleccionada
//        });
//    }
//
//    // Método que se ejecuta cuando un CheckBox es clickeado
//    private void onCheckBoxClicked(View view) {
//        boolean anyChecked = false;
//
//        // Verificar si al menos un CheckBox está seleccionado
//        for (CheckBox checkBox : checkBoxes) {
//            if (checkBox.isChecked()) {
//                anyChecked = true;
//                break;
//            }
//        }
//
//        // Mostrar u ocultar los botones en función de la selección
//        if (anyChecked) {
//            layoutBotones.setVisibility(View.VISIBLE);
//        } else {
//            layoutBotones.setVisibility(View.GONE);
//        }
//    }
//
//    // Método para obtener el CheckBox seleccionado (solo uno permitido)
//    private CheckBox getSelectedCheckBox() {
//        for (CheckBox checkBox : checkBoxes) {
//            if (checkBox.isChecked()) {
//                return checkBox;
//            }
//        }
//        return null;
//    }
//
//    // Método para obtener el ID de la reserva desde el CheckBox seleccionado
//    private int getReservaId(CheckBox checkBox) {
//        // Obtenemos el ID almacenado en el tag del CheckBox
//        return (int) checkBox.getTag();
//    }
//
//    private Reserva obtenerReservaPorId(int reservaId) {
//        for (Reserva reserva : reservas) {
//            if (reserva.getId() == reservaId) {
//                return reserva;
//            }
//        }
//        return null;
//    }



//        // Configuración del botón Modificar
//        buttonModificar.setOnClickListener(v -> {
//            Toast.makeText(this, "Modificar reserva seleccionada", Toast.LENGTH_SHORT).show();
//            // Implementa la lógica para modificar la reserva seleccionada
//
//        });
//
//        // Configuración del botón Eliminar
//        buttonEliminar.setOnClickListener(v -> {
//            Toast.makeText(this, "Eliminar reserva seleccionada", Toast.LENGTH_SHORT).show();
//            // Implementa la lógica para eliminar la reserva seleccionada
//        });
//    }
//
//    // Método que se ejecuta cuando un CheckBox es clickeado
//    private void onCheckBoxClicked(View view) {
//        boolean anyChecked = false;
//
//        // Verificar si al menos un CheckBox está seleccionado
//        for (CheckBox checkBox : checkBoxes) {
//            if (checkBox.isChecked()) {
//                anyChecked = true;
//                break;
//            }
//        }
//
//        // Mostrar u ocultar los botones en función de la selección
//        if (anyChecked) {
//            layoutBotones.setVisibility(View.VISIBLE);
//        } else {
//            layoutBotones.setVisibility(View.GONE);
//        }
}
