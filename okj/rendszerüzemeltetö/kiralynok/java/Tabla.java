import java.io.*;
import java.util.*;

public class Tabla {
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