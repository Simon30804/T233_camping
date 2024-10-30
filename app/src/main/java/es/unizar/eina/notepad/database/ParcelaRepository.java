package es.unizar.eina.notepad.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ParcelaRepository {
    private final ParcelaDao mParcelaDao;
    private final LiveData<List<Parcela>> mAllParcelas;

    private final long TIMEOUT = 15000;

    public ParcelaRepository(Application application) {
        CampingDataDatabase db = CampingDataDatabase.getDatabase(application);
        mParcelaDao = db.parcelaDao();
        mAllParcelas = mParcelaDao.getAllParcelas();
    }

    public LiveData<List<Parcela>> getAllParcelas() {
        return mAllParcelas;
    }

    //Habrá que pasarle la condición para ordenar (ahora ordena solo por nombre)
    public LiveData<List<Parcela>> getParcelasOrdenadas(){
        return mAllParcelas;
    }

    public long insert(Parcela parcela) {
        Future<Long> future = CampingDataDatabase.databaseWriteExecutor.submit(
                () -> mParcelaDao.insert(parcela));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("ParcelaRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }

    public int update(Parcela parcela) {
        Future<Integer> future = CampingDataDatabase.databaseWriteExecutor.submit(
                () -> mParcelaDao.update(parcela));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("ParcelaRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }

    public int delete(Parcela parcela) {
        Future<Integer> future = CampingDataDatabase.databaseWriteExecutor.submit(
                () -> mParcelaDao.delete(parcela));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("ParcelaRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }
}
