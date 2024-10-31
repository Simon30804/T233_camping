package es.unizar.eina.notepad.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ReservaRepository {
    private final ReservaDao mReservaDao;
    private final LiveData<List<Reserva>> mAllReservas;

    private final long TIMEOUT = 15000;

    public ReservaRepository(Application application) {
        CampingDataDatabase db = CampingDataDatabase.getDatabase(application);
        mReservaDao = db.reservaDao();
        mAllReservas = mReservaDao.getAllReservas();
    }

    public LiveData<List<Reserva>> getAllReservas() {
        return mAllReservas;
    }

    //Habrá que pasarle la condición para ordenar (ahora ordena solo por nombre)
    public LiveData<List<Reserva>> getReservasOrdenadas(){
        return mAllReservas;
    }

    public LiveData<Reserva> getReservaById(int id) {
        return mReservaDao.getReservaById(id); // Asegúrate de que ReservaDao tenga este método
    }

    public long insert(Reserva reserva) {
        Future<Long> future = CampingDataDatabase.databaseWriteExecutor.submit(
                () -> mReservaDao.insert(reserva));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("ReservaRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }

    public int update(Reserva reserva) {
        Future<Integer> future = CampingDataDatabase.databaseWriteExecutor.submit(
                () -> mReservaDao.update(reserva));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("ReservaRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }

    public int delete(Reserva reserva) {
        Future<Integer> future = CampingDataDatabase.databaseWriteExecutor.submit(
                () -> mReservaDao.delete(reserva));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("ReservaRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }
}
