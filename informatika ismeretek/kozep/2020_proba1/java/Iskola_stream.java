import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Iskola_stream {

    public static void main(String[] args) throws IOException {
        var tanulok = Files.lines(Path.of("nevek.txt"))
                           .map(Tanulo::new)
                           .toArray(Tanulo[]::new);
        
        System.out.println("3. Feladat: Tanulók száma: " + tanulok.length);
        
        var leghosszabbNevHossz = Arrays.stream(tanulok)
                                        .mapToLong(Iskola_stream::nevhosszSzokozNelkul)
                                        .max()
                                        .orElse(0);
        
        System.out.println("4. Feladat: Leghosszabb név hossz: " + leghosszabbNevHossz);
        
        Arrays.stream(tanulok)
              .filter(k -> nevhosszSzokozNelkul(k) == leghosszabbNevHossz)
              .forEach(k -> System.out.println("    " + k.nev));
        
        System.out.println("5. Feladat: Azonosítók");
        
        var elsoTanulo = tanulok[0];
        var utolsoTanulo = tanulok[tanulok.length - 1];
        System.out.println("    Első: " + elsoTanulo.nev + " - " + azonositoGeneralas(elsoTanulo));
        System.out.println("    Utolsó: " + utolsoTanulo.nev + " - " + azonositoGeneralas(utolsoTanulo));
        
        try(var console = new Scanner(System.in)){
            System.out.println("6. Feladat: Írj be 1 azonosítót!");
            
            var bekertAzonosito = console.nextLine();
            
            Arrays.stream(tanulok)
                  .filter(k -> azonositoGeneralas(k).equals(bekertAzonosito))
                  .findFirst()
                  .ifPresentOrElse(k -> System.out.println("    " + k.kezdesEve + " " + k.osztaly + " " + k.nev),
                                  () -> System.out.println("    Nincs megfelelő tanuló!"));
        }
        
        var rand = new Random();
        var pwGenerator = new JelszoGeneralo(rand);
        var randomTanulo = tanulok[rand.nextInt(tanulok.length)];
        
        System.out.println("7. Feladat: " + randomTanulo.nev + " - " + pwGenerator.jelszo(8));
    }
    
    public static long nevhosszSzokozNelkul(Tanulo tanulo) {
        return tanulo.nev.chars().filter(k -> k != ' ').count();
    }
    
    public static String azonositoGeneralas(Tanulo tanulo) {
        var utolsoSzamjegy = tanulo.kezdesEve % 10;
        var elsoNevSzokozIndex = tanulo.nev.indexOf(' ');
        var vezeteknev = tanulo.nev.substring(0, elsoNevSzokozIndex);
        var keresztnev = tanulo.nev.substring(elsoNevSzokozIndex + 1);
        
        return utolsoSzamjegy +
               tanulo.osztaly +
               vezeteknev.substring(0, 3).toLowerCase() +
               keresztnev.substring(0, 3).toLowerCase();
    }
}