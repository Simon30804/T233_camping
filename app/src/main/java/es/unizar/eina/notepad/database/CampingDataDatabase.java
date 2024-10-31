package es.unizar.eina.notepad.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Parcela.class, Reserva.class}, version = 1, exportSchema = false)
public abstract class CampingDataDatabase extends RoomDatabase {

    public abstract ParcelaDao parcelaDao();
    public abstract ReservaDao reservaDao();

    private static volatile CampingDataDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CampingDataDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CampingDataDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CampingDataDatabase.class, "camping_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more notes, just add them.

                ParcelaDao daoP = INSTANCE.parcelaDao();
                daoP.deleteAll();

                ReservaDao daoR = INSTANCE.reservaDao();
                daoR.deleteAll();

                //Para tener ya unos elementos en la base de datos
                Parcela parcela = new Parcela("Valle de Anso", "Tiene agua, y varias tomas de corriente", 7, 6.5);
                daoP.insert(parcela);

                Reserva reserva = new Reserva("2021-06-01", "2021-06-15", "Javier Susín", 623456324, 8);
                daoR.insert(reserva);
            });
        }
    };

}
