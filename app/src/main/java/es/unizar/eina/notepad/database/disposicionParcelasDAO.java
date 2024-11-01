package es.unizar.eina.notepad.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface disposicionParcelasDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(disposicionParcelas disposicionParcelas);

    //aqui yo lo que haría sería a parcela añadir un atributo (una cadena) que sea estado, y que por defecto sea "libre" y si ya la han seleccionado se pone a ocupada
    // y entonces en el where pondría "WHERE reserva_id = :reservaId AND estado = 'libre'"
    //Así que en el crear reserva, cuando se seleccionen las parcelas, se cambia el estado de las parcelas a ocupadas
    @Query("SELECT * FROM disposicion_parcelas WHERE reserva_id = :reservaId")
    List<disposicionParcelas> getParcelasForReserva(int reservaId);

    @Query("DELETE FROM disposicion_parcelas WHERE reserva_id = :reservaId")
    void deleteParcelasForReserva(int reservaId);
}
