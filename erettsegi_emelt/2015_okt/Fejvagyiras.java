import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Fejvagyiras {

    public static void main(String[] args) throws IOException {
        var random = new Random();
        
        System.out.println("1. Feladat: Pénzfeldobás eredménye: " + (random.nextBoolean() ? 'I' : 'F'));
        System.out.println("2. Feladat: Tippelj: F/I");

        try(var input = new Scanner(System.in)){
            var userIn = input.next();
            var generalt = random.nextBoolean() ? 'I' : 'F';
            
            System.out.println(userIn.charAt(0) == generalt ? "Eltaláltad!" : "Nem talált!");
        }
        
        var osszes = 0;
        var fejek = 0;
        var irasok = 0;
        var csupaFejes = 0;
        var maxCsupaFejes = 0;
        var csupaFejSorszam = 0;
        
        try(var file = Files.newBufferedReader(Path.of("kiserlet.txt"))){
            for(String line; (line = file.readLine()) != null;) {
                var jelenlegi = line.charAt(0);
                
                ++osszes;
                
                if(jelenlegi == 'I') {
                    ++irasok;
                    
                    if(csupaFejes > maxCsupaFejes) {
                        maxCsupaFejes = csupaFejes;
                        csupaFejSorszam = osszes - csupaFejes;
                    }
                    csupaFejes = 0;
                }else {
                    ++fejek;
                    ++csupaFejes;
                }
            }
        }
        
        System.out.println("3. Feladat: Dobások száma: " + osszes);
        System.out.printf("4. Feladat: Fej relativ gyakorisága: %.2f%%\n", ((float) fejek / osszes * 100));
        
        //TODO: Ez itt nem tûnik helyesnek (5. Feladat)
        var ketFejesek = 0;
        try(var file = Files.newBufferedReader(Path.of("kiserlet.txt"))){
            
            String negyes;
            do {
                negyes = file.readLine() + file.readLine() + file.readLine() + file.readLine();
                
                if(negyes.equals("IFFI") || negyes.equals("FFII") || negyes.equals("FFIF") || negyes.equals("IFFF")) {
                    ++ketFejesek;
                }
            }while(!negyes.contains("null"));
        }
        
        System.out.println("5. Feladat: Dupla fejes dobások: " + ketFejesek);
        System.out.println("6. Feladat: Leghoszabb tisztafejes: " + maxCsupaFejes + ", kezdete: " + csupaFejSorszam);
        
        
        var generaltTeljes = new StringBuilder(5000); //4000 db IIII + 1000 db szóköz
        var FFFF = 0;
        var FFFI = 0;
        
        for(var i = 0; i < 1000; ++i) {
            var karakterek = new char[5];
            
            for(var c = 0; c < 4; ++c) {
                karakterek[c] = (random.nextBoolean() ? 'I' : 'F');
            }
            karakterek[4] = ' ';
            
            var generalt = new String(karakterek);
            
            if(generalt.equals("FFFF ")) {
                ++FFFF;
            }else if(generalt.equals("FFFI ")) {
                ++FFFI;
            }
            
            generaltTeljes.append(generalt);
        }
        
        Files.writeString(Path.of("dobasok.txt"), "FFFF: " + FFFF + ", FFFI: " + FFFI + "\n" + generaltTeljes);
    }
}