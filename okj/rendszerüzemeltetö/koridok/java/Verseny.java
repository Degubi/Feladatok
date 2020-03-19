import java.time.*;

public class Verseny{
    
    public final String csapat;
    public final String versenyzo;
    public final int eletkor;
    public final String palya;
    public final LocalTime korido;
    public final int kor;
    
    public Verseny(String line) {
        var split = line.split(";");
        
        csapat = split[0];
        versenyzo = split[1];
        eletkor = Integer.parseInt(split[2]);
        palya = split[3];
        korido = LocalTime.parse(split[4]);
        kor = Integer.parseInt(split[5]);
    }
}