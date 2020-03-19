import static java.nio.file.StandardOpenOption.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Kiralynok {

    public static void main(String[] args) throws IOException {
        var tabla = new Tabla('#');
        
        System.out.println("4. Feladat: Az üres tábla");
        tabla.megjelenit(System.out);
        
        System.out.println("6. Feladat: A feltöltött tábla");
        tabla.elhelyez(8);
        tabla.megjelenit(System.out);
        
        System.out.println("9. Feladat: Üres sorok és oszlopok száma");
        System.out.println("Oszlopok: " + tabla.uresOszlopokSzama());
        System.out.println("Sorok: " + tabla.uresSorokSzama());
        
        try(var file = Files.newOutputStream(Path.of("tablak64.txt"), WRITE, CREATE, TRUNCATE_EXISTING)){
            for(var i = 1; i < 65; ++i) {
                var fileTabla = new Tabla('*');
                fileTabla.elhelyez(i);
                
                fileTabla.megjelenit(file);
                file.write('\n');
            }
        }
    }
    
    public static class Tabla {
        private static final Random rand = new Random();
        
        private char[][] t;
        private char uresCella;
        
        public Tabla(char uresCella) {
            this.t = new char[8][8];
            this.uresCella = uresCella;
            
            for(var x = 0; x < 8; ++x) {
                Arrays.fill(t[x], uresCella);
            }
        }
        
        public void megjelenit(OutputStream output) throws IOException {
            for(var x = 0; x < 8; ++x) {
                for(var y = 0; y < 8; ++y) {
                    output.write(t[x][y]);
                }
                output.write('\n');
            }
        }
        
        public void elhelyez(int n) {
            for(var i = 0; i < n; ++i) {
                var randX = rand.nextInt(8);
                var randY = rand.nextInt(8);
                
                if(t[randX][randY] == uresCella) {
                    t[randX][randY] = 'K';
                }else{
                    --i;
                }
            }
        }
        
        public boolean uresSor(int sor) {
            var indexeltSor = t[sor];
            
            for(var i = 0; i < 8; ++i) {
                if(indexeltSor[i] == 'K') {
                    return false;
                }
            }
            return true;
        }
        
        public boolean uresOszlop(int oszlop) {
            for(var i = 0; i < 8; ++i) {
                if(t[i][oszlop] == 'K') {
                    return false;
                }
            }
            return true;
        }
        
        public int uresSorokSzama() {
            var ures = 0;
            
            for(var i = 0; i < 8; ++i) {
                if(uresSor(i)) {
                    ++ures;
                }
            }
            
            return ures;
        }
        
        public int uresOszlopokSzama() {
            var ures = 0;
            
            for(var i = 0; i < 8; ++i) {
                if(uresOszlop(i)) {
                    ++ures;
                }
            }
            
            return ures;
        }
    }
}