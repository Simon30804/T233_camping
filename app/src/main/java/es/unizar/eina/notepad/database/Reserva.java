package es.unizar.eina.notepad.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa una parcela y que consta de fecha_inicio, fecha_fin, nombre del cliente, número de teléfono y número de ocupantes */
@Entity(tableName = "reserva")
public class Reserva {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "fecha_inicio")
    private String fechaInicio;

    @ColumnInfo(name = "fecha_fin")
    private String fechaFin;

    @ColumnInfo(name = "nombre_cliente")
    private String nombreCliente;

    @ColumnInfo(name = "telefono")
    private int telefono;

    @ColumnInfo(name = "numOcupantes")
    private int numOcupantes;

    public Reserva(@NonNull String fechaInicio, String fechaFin, String nombreCliente, int telefono, int numOcupantes) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreCliente = nombreCliente;
        this.telefono = telefono;
        this.numOcupantes = numOcupantes;
    }

    /** Devuelve el identificador de la reserva */
    public int getId(){
        return this.id;
    }

    /** Permite actualizar el identificador de una reserva */
    public void setId(int id) {
        this.id = id;
    }

    /** Devuelve la fecha de inicio de la reserva */
    public String getFechaInicio(){
        return this.fechaInicio;
    }

    /** Devuelve la fecha de fin de la reserva */
    public String getFechaFin(){
        return this.fechaFin;
    }

    /** Devuelve el nombre del cliente de la reserva */
    public String getNombreCliente(){
        return this.nombreCliente;
    }

    /** Devuelve el número de teléfono del cliente de la reserva */
    public int getTelefono(){
        return this.telefono;
    }

    /** Devuelve el número de ocupantes de la reserva */
    public int getNumOcupantes(){
        return this.numOcupantes;
    }
}
