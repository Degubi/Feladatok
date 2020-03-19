import java.time.*;
import java.time.format.*;

public class Utazas{
    private static final DateTimeFormatter felszallasFormatum = DateTimeFormatter.ofPattern("uuuuMMdd-HHmm");
    private static final DateTimeFormatter ervenyessegFormatum = DateTimeFormatter.ofPattern("uuuuMMdd");
    
    public final int megalloSorszama;
    public final LocalDateTime felszallas;
    public final String kartyaAzonosito;
    public final String tipus;
    public final int jegyszam;
    public final LocalDate ervenyesseg;
    
    public Utazas(String line) {
        var split = line.split(" ");
        
        megalloSorszama = Integer.parseInt(split[0]);
        felszallas = LocalDateTime.parse(split[1], felszallasFormatum);
        kartyaAzonosito = split[2];
        tipus = split[3];
        
        if(split[4].length() <= 2) {
            jegyszam = Integer.parseInt(split[4]);
            ervenyesseg = null;
        }else{
            jegyszam = -1;
            ervenyesseg = LocalDate.parse(split[4], ervenyessegFormatum);
        }
    }
    
    public boolean ervenytelenE() { return jegyszam == 0 || (ervenyesseg != null && felszallas.toLocalDate().isAfter(ervenyesseg)); }
    public boolean ervenyesE() { return !ervenytelenE(); }
}