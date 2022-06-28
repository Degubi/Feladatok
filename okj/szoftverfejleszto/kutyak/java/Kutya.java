import java.time.*;
import java.time.format.*;

public class Kutya {
    private static final DateTimeFormatter ellenorzesDateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public final int id;
    public final int fajtaId;
    public final int nevId;
    public final int eletkor;
    public final LocalDate ellenorzes;

    public Kutya(String[] data) {
        id = Integer.parseInt(data[0]);
        fajtaId = Integer.parseInt(data[1]);
        nevId = Integer.parseInt(data[2]);
        eletkor = Integer.parseInt(data[3]);
        ellenorzes = LocalDate.parse(data[4], ellenorzesDateFormat);
    }
}