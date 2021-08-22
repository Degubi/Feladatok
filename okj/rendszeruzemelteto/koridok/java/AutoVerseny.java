import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class AutoVerseny {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("autoverseny.csv"));
        var versenyek = new ArrayList<Verseny>();

        for(var i = 1; i < lines.size(); ++i) {
            versenyek.add(new Verseny(lines.get(i)));
        }

        System.out.println("3. Feladat: Adatsorok száma: " + versenyek.size());

        for(var verseny : versenyek) {
            if(verseny.versenyzo.equals("Fürge Ferenc") && verseny.palya.equals("Gran Prix Circuit") && verseny.kor == 3) {
                System.out.println("4. Feladat: " + verseny.korido.toSecondOfDay() + " mp");
                break;
            }
        }

        try(var input = new Scanner(System.in)){
            System.out.println("5. Felatad: Írj be egy nevet!");
            var beNev = input.nextLine();
            var legrovidebb = LocalTime.of(23, 59);

            System.out.print("6. Feladat: ");

            for(var verseny : versenyek) {
                if(verseny.versenyzo.equals(beNev)) {
                    if(verseny.korido.compareTo(legrovidebb) == -1) {
                        legrovidebb = verseny.korido;
                    }
                }
            }

            System.out.println(legrovidebb.getHour() == 23 ? "Nincs ilyen versenyző" : legrovidebb);
        }
    }
}