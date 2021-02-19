import java.io.*;

public class Reversi {

    public static void main(String[] args) throws IOException {
        var tabla = new Tabla("allas.txt");

        System.out.println("5. Feladat:");
        tabla.megjelenit();

        System.out.println("6. Feladat:\n" +
                           "    Kék korongok: " + tabla.karaktertSzamol('K') + "\n" +
                           "    Fehér korongok: " + tabla.karaktertSzamol('F') + "\n" +
                           "    Üres mezők: " + tabla.karaktertSzamol('#'));

        System.out.println("8. Feladat: " + (tabla.vanForditas('F', 4, 1, 0, 1) ? "Van" : "Nincs") + " fordítás!");
        System.out.println("9. Feladat: " + (tabla.szabalyosLepes('K', 1, 3) ? "Szabályos" : "Nem szabályos") + " lépés!");
    }
}