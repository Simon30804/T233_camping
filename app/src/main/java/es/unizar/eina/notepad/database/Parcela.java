package es.unizar.eina.notepad.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/** Clase anotada como entidad que representa una parcela y que consta de nombre, descripción, numero de ocupantes y precio por persona */
@Entity(tableName = "parcela")
public class Parcela {
    //@PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    private int id;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "numOcupantes")
    private int numOcupantes;

    @ColumnInfo(name = "precioPersona")
    private double precioPersona;

    public Parcela(@NonNull String nombre, String descripcion, int numOcupantes, double precioPersona) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.numOcupantes = numOcupantes;
        this.precioPersona = precioPersona;
    }

    /** Devuelve el identificador de la parcela */
    public String getNombre(){
        return this.nombre;
    }

    /** Permite actualizar el nombre de una parcela */
    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    /** Devuelve la descripción de la parcela */
    public String getDescripcion(){
        return this.descripcion;
    }

    /** Devuelve el número de ocupantes de la parcela */
    public int getNumOcupantes(){
        return this.numOcupantes;
    }

    /** Devuelve el precio por persona de la parcela */
    public double getPrecioPersona(){
        return this.precioPersona;
    }

}
