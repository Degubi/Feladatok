import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Futar{
    
    public static void main(String[] args) throws IOException{
        var lines = Files.readAllLines(Paths.get("tavok.txt"));
        
        List<Fuvar> fuvarLista = new ArrayList<>();
        for(String line : lines) {
            fuvarLista.add(new Fuvar(line.split(" ")));
        }
        
        System.out.println("2. Feladat: A hét legelső útja km-ben: " + fuvarLista.get(0).tavolsag + " km");
        System.out.println("3. Feladat: A hét utolsó útja km-ben: " + fuvarLista.get(fuvarLista.size() - 1).tavolsag + " km");
    
        HashSet<Integer> napok = new HashSet<>();
        for(Fuvar fuvar : fuvarLista) {
            napok.add(fuvar.nap);
        }
        
        System.out.println("4. Feladat");
        for(int k = 1; k <= 7; ++k) {
            if(!napok.contains(k)) {
                System.out.println("A hét " + k + ". napján volt 1 szabadnap");
            }
        }
        
        List<Fuvar> legtobb = new ArrayList<>();
        List<Fuvar> masodikLegtobb = new ArrayList<>();
        
        for(int k = 1; k <= 7; ++k) {
            for(Fuvar fuvar : fuvarLista) {
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
        
        for(int k = 1; k <= 7; ++k) {
            int napiKm = 0;
            for(Fuvar fuvar : fuvarLista) {
                if(fuvar.nap == k) {
                    napiKm += fuvar.tavolsag;
                }
            }
            System.out.println("A " + k + ". nap: " + napiKm + " km");
        }
        
        try(var input = new Scanner(System.in)){
            System.out.println("7.Feladat: Írj be 1 távolságot!");
            int readKm = input.nextInt();
            System.out.println(readKm + " km esetén fizetendő: " + calcPrice(readKm));
        }
        
        int allPrice = 0;
        try(var output = new PrintWriter("dijazas.txt")){
            for(Fuvar fuvar : fuvarLista) {
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
    
    static class Fuvar{
        int nap, sorszam, tavolsag;
        
        public Fuvar(String[] data) {
            nap = Integer.parseInt(data[0]);
            sorszam = Integer.parseInt(data[1]);
            tavolsag = Integer.parseInt(data[2]);
        }
    }
}