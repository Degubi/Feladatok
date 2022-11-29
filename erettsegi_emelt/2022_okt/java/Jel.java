import java.time.*;

public class Jel {

    public final LocalTime idopont;
    public final int x;
    public final int y;

    public Jel(String line) {
        var split = line.split(" ");

        this.idopont = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        this.x = Integer.parseInt(split[3]);
        this.y = Integer.parseInt(split[4]);
    }
}