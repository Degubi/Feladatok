import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Jackie {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("jackie.txt"));
        var versenyek = new ArrayList<Verseny>();

        for(var i = 1; i < lines.size(); ++i) {
            versenyek.add(new Verseny(lines.get(i)));
        }

        System.out.println("3. Feladat: Adatsorok száma: " + versenyek.size());

        var legtobbIndulasosVerseny = versenyek.get(0);
        for(var verseny : versenyek) {
            if(verseny.indulasokSzama > legtobbIndulasosVerseny.indulasokSzama) {
                legtobbIndulasosVerseny = verseny;
            }
        }

        System.out.println("4. Feladat: " + legtobbIndulasosVerseny.ev);
        System.out.println("5. Feladat:");

        var evtizedesStat = new HashMap<Integer, Integer>();
        for(var verseny : versenyek) {
            var evtized = verseny.ev / 10 * 10 % 100;

            evtizedesStat.put(evtized, evtizedesStat.getOrDefault(evtized, 0) + verseny.nyeresekSzama);
        }

        for(var evtizedStatEntry : evtizedesStat.entrySet()) {
            System.out.println("    " + evtizedStatEntry.getKey() + "-es évek: " + evtizedStatEntry.getValue() + " db nyerés");
        }

        var htmlHeader = """
                         <!DOCTYPE html>
                         <html>
                             <head>
                                 <style>
                                     td {
                                         border: 1px solid black;
                                     }
                                 </style>
                             </head>
                             <body>
                                 <h1>Jackie Stewart</h1>
                                 <table>
                                 """;

        var htmlFooter = """
                                 </table>
                             </body>
                         </html>""";

        try(var output = new PrintWriter("jackie.html")) {
            output.print(htmlHeader);

            for(var verseny : versenyek) {
                output.println(" ".repeat(12) + "<tr><td>" + verseny.ev + "</td><td>" + verseny.indulasokSzama + "</td><td>" + verseny.nyeresekSzama + "</td></tr>");
            }

            output.print(htmlFooter);
        }
    }
}