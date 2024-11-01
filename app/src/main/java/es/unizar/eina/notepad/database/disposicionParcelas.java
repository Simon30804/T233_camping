package es.unizar.eina.notepad.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "disposicion_parcelas",
        primaryKeys = {"reserva_id", "parcela_nombre"},
        foreignKeys = {
                @ForeignKey(entity = Reserva.class, parentColumns = "id", childColumns = "reserva_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Parcela.class, parentColumns = "nombre", childColumns = "parcela_nombre", onDelete = ForeignKey.CASCADE)
        }
)
public class disposicionParcelas {

    @ColumnInfo(name = "reserva_id")
    private int reservaId;

    @ColumnInfo(name = "parcela_nombre")
    @NonNull
    private String parcelaNombre;

    public disposicionParcelas(int reservaId, @NonNull String parcelaNombre) {
        this.reservaId = reservaId;
        this.parcelaNombre = parcelaNombre;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    @NonNull
    public String getParcelaNombre() {
        return parcelaNombre;
    }

    public void setParcelaNombre(@NonNull String parcelaNombre) {
        this.parcelaNombre = parcelaNombre;
    }
}

