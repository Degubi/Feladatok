import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class Balkezesek {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("balkezesek.csv"), StandardCharsets.ISO_8859_1);
        var jatekosok = new ArrayList<Jatekos>();
        
        for(var i = 1; i < lines.size(); ++i) {
            jatekosok.add(new Jatekos(lines.get(i)));
        }
        
        System.out.println("3. Feladat: Adatsorok száma: " + jatekosok.size());
        System.out.println("4. Feladat:");
        
        for(var jate : jatekosok) {
            if(jate.befejezes.getYear() == 1999 && jate.befejezes.getMonth() == Month.OCTOBER) {
                System.out.printf("   %s, %.1f cm\n", jate.nev, (jate.magassag * 2.54D));
            }
        }
        
        System.out.println("5. Feladat: Írj be 1 évszámot! (1990 <= évszám <= 1999)");
        try(var console = new Scanner(System.in)){
            while(true) {
                var bekertEvszam = console.nextInt();
                
                if(bekertEvszam >= 1990 && bekertEvszam <= 1999) {
                    var sulyOsszes = 0;
                    var bekertEvszamosDb = 0;
                    
                    for(var jate : jatekosok) {
                        if(jate.kezdes.getYear() == bekertEvszam) {
                            ++bekertEvszamosDb;
                            sulyOsszes += jate.suly;
                        }
                    }
                    
                    System.out.printf("6. Feladat: %.2f", ((double) sulyOsszes / bekertEvszamosDb));
                    break;
                }
                
                System.out.println("Hibás adat, kérek egy 1990 és 1999 közötti évszámot");
                continue;
            }
        }
    }
}