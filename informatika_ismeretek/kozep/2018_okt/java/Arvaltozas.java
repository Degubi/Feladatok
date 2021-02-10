import java.time.*;

public class Arvaltozas {
    public final LocalDate valtozasDatuma;
    public final int benzinAr;
    public final int gazolajAr;

    public Arvaltozas(String line) {
        var split = line.split(";");

        valtozasDatuma = LocalDate.parse(split[0].replace('.', '-'));
        benzinAr = Integer.parseInt(split[1]);
        gazolajAr = Integer.parseInt(split[2]);
    }
}