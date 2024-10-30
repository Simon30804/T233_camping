package es.unizar.eina.notepad.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Definición de un Data Access Object para las reservas */
@Dao
public interface ReservaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Reserva reserva);

    @Update
    int update(Reserva reserva);

    @Delete
    int delete(Reserva reserva);

    @Query("DELETE FROM reserva")
    void deleteAll();

    //Ver como hacer para ordenarlas en base a como elige el cliente
    @Query("SELECT * FROM reserva ORDER BY nombre_cliente ASC")
    LiveData<List<Reserva>> getReservasOrdenadas();
    @Query("SELECT * FROM reserva")
    LiveData<List<Reserva>> getAllReservas();
}

