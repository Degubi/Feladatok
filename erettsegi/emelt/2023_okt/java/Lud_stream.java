import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Lud_stream {
    record JatekAllapot(int[] jatekosPoziciok, int kor) {}


    public static void main(String[] args) throws Exception {
        var dobasok = Arrays.stream(Files.readString(Path.of("dobasok.txt")).trim().split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray();

        var osvenyek = Files.readAllLines(Path.of("osvenyek.txt"));

        System.out.println("2. Feladat: Dobások száma: " + dobasok.length + ", ösvények száma: " + osvenyek.size());

        osvenyek.stream()
                .max(Comparator.comparingInt(String::length))
                .ifPresent(k -> System.out.println("3. Feladat: " + (osvenyek.indexOf(k) + 1) + ". ösvény, hossza: " + k.length()));

        var input = System.console().readLine("4. Feladat: Írja be egy ösvény számát és egy játékos számát! ")
                                    .split(" ");

        var bekertJatekosSzam = Integer.parseInt(input[1]);
        var bekertOsveny = osvenyek.get(Integer.parseInt(input[0]) - 1);
        var bekertOsvenyHossz = bekertOsveny.length();

        System.out.println("5. Feladat:");

        var bekertOsvenyMezok = bekertOsveny.chars().boxed()
                                            .collect(Collectors.groupingBy(k -> (char) k.intValue(), Collectors.counting()));

        bekertOsvenyMezok.forEach((mezo, db) -> System.out.println(mezo + ": " + db + " db"));

        var kulonlegesFileba = IntStream.range(0, bekertOsvenyHossz)
                                        .filter(i -> bekertOsveny.charAt(i) != 'M')
                                        .mapToObj(i -> (i + 1) + ": " + bekertOsveny.charAt(i))
                                        .collect(Collectors.toList());

        Files.write(Path.of("kulonleges.txt"), kulonlegesFileba);

        var teljesMJatekVege = jatekJatszasa("M".repeat(bekertOsvenyHossz), bekertJatekosSzam, dobasok);
        var gyoztesSorszama = IntStream.range(0, bekertJatekosSzam).boxed()
                                       .max(Comparator.comparingInt(i -> teljesMJatekVege.jatekosPoziciok()[i]))
                                       .orElseThrow();

        System.out.println("7. Feladat: A játék a " + teljesMJatekVege.kor() + " körben fejeződött be, győztes sorszáma: " + (gyoztesSorszama + 1));

        // 8. Feladat: Gőzöm sincs, hogy mikor kéne hivatalosan a játéknak vége lenni... TODO
        var bekertJatekVege = jatekJatszasa(bekertOsveny, bekertJatekosSzam, dobasok);
    }

    static JatekAllapot jatekJatszasa(String osveny, int jatekosokSzama, int[] dobasok) {
        return Stream.iterate(new JatekAllapot(new int[jatekosokSzama], 0), k -> kovetkezoKor(k, dobasok, osveny.charAt(k.kor())))
                     .dropWhile(k -> Arrays.stream(k.jatekosPoziciok()).allMatch(l -> l < osveny.length()))
                     .findFirst()
                     .orElseThrow();
    }

    static JatekAllapot kovetkezoKor(JatekAllapot state, int[] dobasok, char mezo) {
        var jatekosPoziciok = state.jatekosPoziciok();
        var kor = state.kor();
        var jatekosokSzama = jatekosPoziciok.length;
        var ujPoziciok = IntStream.range(0, jatekosPoziciok.length)
                                  .map(i -> jatekosPoziciok[i] + lepesSzam(dobasok[jatekosokSzama * kor + i], mezo))
                                  .toArray();

        return new JatekAllapot(ujPoziciok, kor + 1);
    }

    static int lepesSzam(int dobas, char mezo) {
        return switch(mezo) {
            case 'M' -> dobas;
            case 'E' -> dobas * 2;
            default  -> 0;
        };
    }
}