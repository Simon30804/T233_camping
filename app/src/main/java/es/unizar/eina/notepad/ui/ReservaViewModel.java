package es.unizar.eina.notepad.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.notepad.database.Parcela;
import es.unizar.eina.notepad.database.Reserva;
import es.unizar.eina.notepad.database.ReservaRepository;

public class ReservaViewModel extends AndroidViewModel {

    private ReservaRepository mRepository;

    private final LiveData<List<Reserva>> mAllReservas;

    public ReservaViewModel(Application application) {
        super(application);
        mRepository = new ReservaRepository(application);
        mAllReservas = mRepository.getAllReservas();
    }

    LiveData<List<Reserva>> getAllReservas() {
        return mAllReservas;
    }

    LiveData<List<Reserva>> getReservasOrdenadas() {
        return mAllReservas;
    }

    public void insert(Reserva reserva) {
        mRepository.insert(reserva);
    }

    public void update(Reserva reserva) {
        mRepository.update(reserva);
    }
    public void delete(Reserva reserva) {
        mRepository.delete(reserva);
    }
}