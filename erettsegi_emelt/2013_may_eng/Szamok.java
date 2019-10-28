import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Szamok {
    
    public static void main(String[] args) throws IOException {
        //Beolvasás + tárolás
        Random rand = new Random();
        ArrayList<Feladat> feladatok = new ArrayList<>();
        List<String> minden = Files.readAllLines(Paths.get("felszam.txt"), StandardCharsets.ISO_8859_1);
        for(int k = 0; k < minden.size() - 1; k += 2) {
            String[] split = minden.get(k + 1).split(" ");
            feladatok.add(new Feladat(minden.get(k), Integer.parseInt(split[0]), Integer.parseInt(split[1]), split[2]));
        }
        
        //2. Feladat
        System.out.println(feladatok.size() + " Feladat van a fájlban!");
        
        ArrayList<String> temakorok = new ArrayList<>();
        int[] matekCounters = new int[4];
        for(Feladat all : feladatok) {
            if(!temakorok.contains(all.temakor))
                temakorok.add(all.temakor);
            if(all.temakor.equals("matematika")) {
                ++matekCounters[0];
                if(all.pont == 1) {
                    ++matekCounters[1];
                }else if(all.pont == 2) {
                    ++matekCounters[2];
                }else {
                    ++matekCounters[3];
                }
            }
        }
        
        //3. Feladat kiíratás, ez felett a számlálás
        System.out.println("Az adatfajlban " + matekCounters[0] + " matematika feladat van,\n1 pontot er: " + matekCounters[1] + ""
                + " feladat, 2 pontot er " + matekCounters[2] + " feladat, 3 pontot er " + matekCounters[3] + " feladat. ");
        
        feladatok.sort(new Comparator<Feladat>() {
            @Override
            public int compare(Feladat o1, Feladat o2) {
                return Integer.compare(o1.valasz, o2.valasz);
            }
        });
        
        //4. Feladat kiíratás, felette sorbarendezés
        System.out.println("A legkisebb válaszú feladat: " + feladatok.get(0).valasz + ", a legnagyobb: " + feladatok.get(feladatok.size() - 1).valasz);
        
        //5. Feladat kiíratás, a meghatározás a 2. feladat feletti listába tárolással van
        System.out.println("Elõforduló témakörök: " + temakorok);
        
        Scanner input = new Scanner(System.in);
        System.out.println("Írj be 1 témakört!");
        String readCat = input.nextLine();
        ArrayList<Feladat> categorizált = new ArrayList<>();
        
        for(Feladat all : feladatok)
            if(all.temakor.equals(readCat))
                categorizált.add(all);
        
        Feladat chosen = categorizált.get(rand.nextInt(categorizált.size()));
        
        //6. feladat kiíratás, fent a random kiválasztás logika, eltároljuk hátha kell még késõbb a random feladat
        System.out.println(chosen.kerdes);
        
        //6. feladat 2. része, kérdés ellenõrzés, pontozás
        if(input.nextInt() == chosen.valasz) {
            System.out.println("Kapott pontszám: " + chosen.pont);
        }else{
            System.out.println("Rossz válasz, 0 pont...\nA helyes válasz: " + chosen.valasz);
        }
        input.close();
        
        //7. feladat, 10 különbözõ random feladat generálás
        ArrayList<Feladat> generalt = new ArrayList<>();
        for(int k = 0; k < 10; ++k) {
            Feladat randomFeladat = feladatok.get(rand.nextInt(feladatok.size()));
            if(generalt.contains(randomFeladat)) {
                --k;
            }else{
                generalt.add(randomFeladat);
            }
        }
        
        //7. Feladat, összpont kiszámolás és a kérdések file-ba írása
        int osszpont = 0;
        try(PrintWriter output = new PrintWriter("tesztfel.txt")){
            for(Feladat toPrint : generalt) {
                osszpont += toPrint.pont;
                output.println(toPrint.pont + " " + toPrint.kerdes);
            }
            output.print(osszpont);
        }
    }
    
    static class Feladat{
        String kerdes, temakor;
        int pont, valasz;
        
        public Feladat(String quest, int answ, int points, String cat) {
            kerdes = quest;
            valasz = answ;
            pont = points;
            temakor = cat;
        }
    }
}