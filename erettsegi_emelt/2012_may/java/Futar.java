import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Futar{
    
    public static void main(String[] args) throws IOException{
        var fuvarLista = new ArrayList<Fuvar>();
        for(var line : Files.readAllLines(Paths.get("tavok.txt"))) {
            fuvarLista.add(new Fuvar(line));
        }
        
        System.out.println("2. Feladat: A hét legelső útja km-ben: " + fuvarLista.get(0).tavolsag + " km");
        System.out.println("3. Feladat: A hét utolsó útja km-ben: " + fuvarLista.get(fuvarLista.size() - 1).tavolsag + " km");
    
        var napok = new HashSet<Integer>();
        for(var fuvar : fuvarLista) {
            napok.add(fuvar.nap);
        }
        
        System.out.println("4. Feladat");
        for(var k = 1; k <= 7; ++k) {
            if(!napok.contains(k)) {
                System.out.println("A hét " + k + ". napján volt 1 szabadnap");
            }
        }
        
        var legtobb = new ArrayList<Fuvar>();
        var masodikLegtobb = new ArrayList<Fuvar>();
        
        for(var k = 1; k <= 7; ++k) {
            for(var fuvar : fuvarLista) {
                if(fuvar.nap == k) {
                    masodikLegtobb.add(fuvar);
                }
            }
            
            if(masodikLegtobb.size() > legtobb.size()) {
                legtobb = new ArrayList<>(masodikLegtobb);
            }
            masodikLegtobb.clear();
        }
        
        System.out.println("5. Feladat: Legtöbb fuvarú nap: " + legtobb.get(0).nap);
        System.out.println("6. Feladat");
        
        for(var k = 1; k <= 7; ++k) {
            var napiKm = 0;
            
            for(var fuvar : fuvarLista) {
                if(fuvar.nap == k) {
                    napiKm += fuvar.tavolsag;
                }
            }
            
            System.out.println("A " + k + ". nap: " + napiKm + " km");
        }
        
        try(var input = new Scanner(System.in)){
            System.out.println("7.Feladat: Írj be 1 távolságot!");
            var readKm = input.nextInt();
            System.out.println(readKm + " km esetén fizetendő: " + calcPrice(readKm));
        }
        
        var allPrice = 0;
        try(var output = new PrintWriter("dijazas.txt")){
            for(var fuvar : fuvarLista) {
                int fuvarAr = calcPrice(fuvar.tavolsag);
                allPrice += fuvarAr;
                output.println(fuvar.nap + ". nap " + fuvar.sorszam + ". fuvar: " + fuvarAr + "FT");
            }
        }
        System.out.println("9. Feladat: Az egész heti fizetés: " + allPrice);
    }
    
    private static int calcPrice(int distance) {
        if(distance <= 2) {
            return 500;
        }else if(distance <= 5) {
            return 700;
        }else if(distance <= 10) {
            return 900;
        }else if(distance <= 20) {
            return 1400;
        }
        return 2000;
    }
}