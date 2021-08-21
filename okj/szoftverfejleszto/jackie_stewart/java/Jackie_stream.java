import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Jackie_stream {

    public static void main(String[] args) throws IOException {
        var versenyek = Files.lines(Path.of("jackie.txt"))
                             .skip(1)
                             .map(Verseny::new)
                             .toArray(Verseny[]::new);

        System.out.println("3. Feladat: Adatsorok száma: " + versenyek.length);

        Arrays.stream(versenyek)
              .max(Comparator.comparingInt(k -> k.indulasokSzama))
              .ifPresent(k -> System.out.println("4. Feladat: " + k.ev));

        System.out.println("5. Feladat:");

        Arrays.stream(versenyek)
              .collect(Collectors.groupingBy(k -> k.ev / 10 * 10 % 100, Collectors.summingInt(k -> k.nyeresekSzama)))
              .forEach((ev, nyeresek) -> System.out.println("    " + ev + "-es évek: " + nyeresek + " db nyerés"));

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

        var tableContent = Arrays.stream(versenyek)
                                 .map(k -> " ".repeat(12) + "<tr><td>" + k.ev + "</td><td>" + k.indulasokSzama + "</td><td>" + k.nyeresekSzama + "</td></tr>")
                                 .collect(Collectors.joining("\n")) + '\n';

        var htmlFooter = """
                                 </table>
                             </body>
                         </html>""";

        Files.writeString(Path.of("jackie.html"), htmlHeader + tableContent + htmlFooter);

    }
}