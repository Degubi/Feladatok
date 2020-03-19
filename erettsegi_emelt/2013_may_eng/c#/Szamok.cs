using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Prog{
    class Szamok{
        static void Main(string[] args){
            //Beolvasás + tárolás
            Random rand = new Random();
            List<Feladat> feladatok = new List<Feladat>();
            string[] minden = File.ReadAllLines("felszam.txt");
            for(int k = 0; k < minden.Length - 1; k += 2) {
                String[] split = minden[k + 1].Split(' ');
                feladatok.Add(new Feladat(minden[k], int.Parse(split[0]), int.Parse(split[1]), split[2]));
            }
        
            //2. Feladat
            Console.WriteLine(feladatok.Count + " Feladat van a fájlban!");
        
            List<String> temakorok = new List<String>();
            int[] matekCounters = new int[4];
            foreach(Feladat all in feladatok) {
                if(!temakorok.Contains(all.temakor)) {
                    temakorok.Add(all.temakor);
                }
                if(all.temakor.Equals("matematika")) {
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
            Console.WriteLine("Az adatfajlban " + matekCounters[0] + " matematika feladat van,\n1 pontot er: " + matekCounters[1] + ""
                    + " feladat, 2 pontot er " + matekCounters[2] + " feladat, 3 pontot er " + matekCounters[3] + " feladat. ");
        
            feladatok = feladatok.OrderBy(k => k.valasz).ToList();
            
            //4. Feladat kiíratás, felette sorbarendezés
            Console.WriteLine("A legkisebb válaszú feladat: " + feladatok[0].valasz + ", a legnagyobb: " + feladatok[feladatok.Count - 1].valasz);
            
            //5. Feladat kiíratás, a meghatározás a 2. feladat feletti listába tárolással van
            Console.WriteLine("Előforduló témakörök: " + temakorok);
        
            Console.WriteLine("Írj be 1 témakört!");
            String readCat = Console.ReadLine();
            List<Feladat> categorizált = new List<Feladat>();
        
            foreach(Feladat all in feladatok) {
                if(all.temakor.Equals(readCat)) {
                    categorizált.Add(all);
                }
            }
            Feladat chosen = categorizált[rand.Next(categorizált.Count)];
            
            //6. feladat kiíratás, fent a random kiválasztás logika, eltároljuk hátha kell még később a random feladat
            Console.WriteLine(chosen.kerdes);
        
            //6. feladat 2. része, kérdés ellenőrzés, pontozás
            if(int.Parse(Console.ReadLine()) == chosen.valasz) {
                Console.WriteLine("Kapott pontszám: " + chosen.pont);
            }else{
                Console.WriteLine("Rossz válasz, 0 pont...\nA helyes válasz: " + chosen.valasz);
            }
            
            //7. feladat, 10 különböző random feladat generálás
            List<Feladat> generalt = new List<Feladat>();
            for(int k = 0; k < 10; ++k) {
                Feladat randomFeladat = feladatok[rand.Next(feladatok.Count)];
                if(generalt.Contains(randomFeladat)) {
                    --k;
                }else{
                    generalt.Add(randomFeladat);
                }
            }
            
            //7. Feladat, összpont kiszámolás és a kérdések file-ba írása
            int osszpont = 0;
            using(StreamWriter output = new StreamWriter("tesztfel.txt")){
                foreach(Feladat toPrint in generalt) {
                    osszpont += toPrint.pont;
                    output.WriteLine(toPrint.pont + " " + toPrint.kerdes);
                }
                output.Write(osszpont);
            }
        }
        class Feladat{
            public String kerdes, temakor;
            public int pont, valasz;
            public Feladat(String quest, int answ, int points, String cat) {
                kerdes = quest;
                valasz = answ;
                pont = points;
                temakor = cat;
            }
        }
    }
}