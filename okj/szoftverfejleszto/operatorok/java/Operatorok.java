import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Operatorok {

    public static void main(String[] args) throws IOException {
        var kifejezesek = new ArrayList<Kifejezes>();

        for(var line : Files.readAllLines(Path.of("kifejezesek.txt"), StandardCharsets.ISO_8859_1)) {  // Kell encoding, mert különben elhal az egész... ??
            kifejezesek.add(new Kifejezes(line));
        }

        System.out.println("2. Feladat: Kifejezések száma: " + kifejezesek.size());

        var operatoronkentiDbSzam = new HashMap<String, Integer>();
        for(var kifejezes : kifejezesek) {
            operatoronkentiDbSzam.put(kifejezes.operator, operatoronkentiDbSzam.getOrDefault(kifejezes.operator, 0) + 1);
        }

        System.out.println("3. Feladat: Maradékos osztások száma: " + operatoronkentiDbSzam.get("mod"));

        var vanETizzelOszthatoOperandusu = false;
        for(var kifejezes : kifejezesek) {
            if(kifejezes.elsoOperandus % 10 == 0 && kifejezes.masodikOperandus % 10 == 0) {
                vanETizzelOszthatoOperandusu = true;
                break;
            }
        }

        System.out.println("4. Feladat: " + (vanETizzelOszthatoOperandusu ? "Van" : "Nincs") + " ilyen kifejezés");
        System.out.println("5. Feladat: \n" +
                           "    'mod' -> " + operatoronkentiDbSzam.get("mod") + " db\n" +
                           "      '/' -> " + operatoronkentiDbSzam.get("/") + " db\n" +
                           "    'div' -> " + operatoronkentiDbSzam.get("div") + " db\n" +
                           "      '-' -> " + operatoronkentiDbSzam.get("-") + " db\n" +
                           "      '*' -> " + operatoronkentiDbSzam.get("*") + " db\n" +
                           "      '+' -> " + operatoronkentiDbSzam.get("+") + " db");

        while(true) {
            var bekertKifejezes = System.console().readLine("Kérek egy kifejezést: ");

            if(bekertKifejezes.equals("vége")) {
                break;
            }

            System.out.println(bekertKifejezes + " = " + new Kifejezes(bekertKifejezes).kiertekel());
        }

        var kiertekeltSorok = new ArrayList<String>();
        for(var kifejezes : kifejezesek) {
            kiertekeltSorok.add(kifejezes.kiertekel());
        }

        Files.write(Path.of("eredmenyek.txt"), kiertekeltSorok);
    }
}