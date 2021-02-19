import java.io.*;
import java.nio.file.*;

public class Tabla {

    public final char[][] t = new char[8][8];

    public Tabla(String fileName) throws IOException {
        var lines = Files.readAllLines(Path.of(fileName));
        for(var x = 0; x < 8; ++x) {
            var jelenlegiSor = lines.get(x);

            for(var y = 0; y < 8; ++y) {
                t[x][y] = jelenlegiSor.charAt(y);
            }
        }
    }

    public void megjelenit() {
        for(var oszlop : t) {
            for(var sor : oszlop) {
                System.out.print(sor);
            }

            System.out.println();
        }
    }

    public int karaktertSzamol(char karakter) {
        var db = 0;

        for(var oszlop : t) {
            for(var sor : oszlop) {
                if(sor == karakter) {
                    ++db;
                }
            }
        }

        return db;
    }

    public boolean vanForditas(char jatekos, int sor, int oszlop, int iranySor, int iranyOszlop) {
        var aktSor = sor + iranySor;
        var aktOszlop = oszlop + iranyOszlop;
        var ellenfel = jatekos == 'K' ? 'F' : 'K';
        var nincsEllenfel = true;

        while(aktSor > 0 && aktSor < 8 && aktOszlop > 0 && aktOszlop < 8 && t[aktSor][aktOszlop] == ellenfel) {
            aktSor += iranySor;
            aktOszlop += iranyOszlop;
            nincsEllenfel = false;
            break;
        }

        return !(nincsEllenfel || aktSor < 0 || aktSor > 7 || aktOszlop < 0 || aktOszlop > 7 || t[aktSor][aktOszlop] != jatekos);
    }

    public boolean szabalyosLepes(char jatekos, int sor, int oszlop) {
        return t[sor][oszlop] == '#' &&
              (vanForditas(jatekos, sor, oszlop, -1, -1) || vanForditas(jatekos, sor, oszlop, -1, 0) || vanForditas(jatekos, sor, oszlop, -1, 1) ||
               vanForditas(jatekos, sor, oszlop,  0, -1) || vanForditas(jatekos, sor, oszlop,  0, 1) ||
               vanForditas(jatekos, sor, oszlop,  1, -1) || vanForditas(jatekos, sor, oszlop,  1, 0) || vanForditas(jatekos, sor, oszlop,  1, 1));
    }
}