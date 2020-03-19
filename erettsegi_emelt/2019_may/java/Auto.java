import java.time.*;

public class Auto{
    
    public final int nap;
    public final LocalTime idopont;
    public final String rendszam;
    public final int szemelyAzonosito;
    public final int km;
    public final boolean elvitel;
    
    public Auto(String line) {
        var split = line.split(" ");
        
        nap = Integer.parseInt(split[0]);
        idopont = LocalTime.parse(split[1]);
        rendszam = split[2];
        szemelyAzonosito = Integer.parseInt(split[3]);
        km = Integer.parseInt(split[4]);
        elvitel = split[5].charAt(0) == '0';
    }
    
    public String toFileInfo() {
        return elvitel ? szemelyAzonosito + "\t" + nap + ". " + idopont + "\t" + km + " km"
                       : "\t" + nap + ". " + idopont + "\t" + km + " km\n";
    }
}