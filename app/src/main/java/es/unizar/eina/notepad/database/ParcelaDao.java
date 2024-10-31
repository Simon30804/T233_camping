package es.unizar.eina.notepad.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Definición de un Data Access Object para las parcelas */
@Dao
public interface ParcelaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Parcela parcela);

    @Update
    int update(Parcela parcela);

    @Delete
    int delete(Parcela parcela);

    @Query("DELETE FROM parcela")
    void deleteAll();

    //Ver como hacer para ordenarlas en base a como elige el cliente (ahora ordena solo por nombre)
    @Query("SELECT * FROM parcela ORDER BY nombre ASC")
    LiveData<List<Parcela>> getParcelasOrdenadas();
    @Query("SELECT * FROM parcela")
    LiveData<List<Parcela>> getAllParcelas();
}

