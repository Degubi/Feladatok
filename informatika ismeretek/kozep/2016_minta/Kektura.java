import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Kektura{
    
    public static void main(String[] args) throws IOException{
        var lines = Files.readAllLines(Paths.get("kektura.csv"));
        var szint = Integer.parseInt(lines.get(0));
        var szakaszok = new ArrayList<Szakasz>();
        
        for(var i = 1; i < lines.size(); i++) {
            szakaszok.add(new Szakasz(lines.get(i)));
        }
        
        System.out.println("3.Feladat: Szakaszok száma: " + szakaszok.size() + " db");
        
        var teljesHossz = 0D;
        for(var szakasz : szakaszok) {
            teljesHossz += szakasz.hossz;
        }
        
        System.out.println("4.Feladat: Teljes hossz: " + teljesHossz + " km");
        
        var legrovidebbSzakasz = szakaszok.get(0);
        for(var szakasz : szakaszok) {
            if(szakasz.hossz < legrovidebbSzakasz.hossz) {
                legrovidebbSzakasz = szakasz;
            }
        }
        
        System.out.println("5.Feladat: Legrövidebb szakasz adatai: " + legrovidebbSzakasz);
        System.out.println("7.Feladat: Hiányos állomásnevek:");
        
        var hianyosok = new ArrayList<Szakasz>();
        for(var szakasz : szakaszok) {
            if(szakasz.hianyosNev()) {
                hianyosok.add(szakasz);
            }
        }
        
        if(hianyosok.size() == 0) {
            System.out.println("Nincs hiányos állomásnév");
        }else{
            for(var hianyos : hianyosok) {
                System.out.println(hianyos.vegPont);
            }
        }
        
        var legmagasabb = szakaszok.get(0);
        for(var szakasz : szakaszok) {
            if(szakasz.magasraa(szint) > legmagasabb.magasraa(szint)) {
                legmagasabb = szakasz;
            }
        }
        
        System.out.printf("8.Feladat: A túra legmagasabban pont neve: %s, magassága: %dm\n", legmagasabb.vegPont, legmagasabb.magasraa(szint));
        
        try(var file = new PrintWriter("kektura2.csv")){
            file.println(szint);
            
            for(var szakasz : szakaszok) {
                file.println(szakasz + (szakasz.hianyosNev() ? " pecsetelohely" : ""));
            }
        }
    }
    
    public static class Szakasz{
        public final String induloPont, vegPont;
        public final int emelkedesek, lejtesek;
        public final double hossz;
        public final boolean pecsetelo;
        
        public Szakasz(String data) {
            var split = data.split(";");
            
            induloPont = split[0];
            vegPont = split[1];
            hossz = Double.parseDouble(split[2].replace(',', '.'));
            emelkedesek = Integer.parseInt(split[3]);
            lejtesek = Integer.parseInt(split[4]);
            pecsetelo = split[5].charAt(0) == 'i';
        }
        
        public boolean hianyosNev() {
            return pecsetelo && !vegPont.contains("pecsetelohely");
        }
        
        public int magasraa(int alap) {
            return alap + (emelkedesek - lejtesek);
        }
        
        @Override
        public String toString() {
            return "Kezdet: " + induloPont + ", vég: " + vegPont + ", távolság: " + hossz + " km";
        }
    }
}