using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;

public class Program {

    public static void Main(string[] args) {
        var sorok = File.ReadAllLines("autok.txt");
        var autok = new List<Auto>();
    
        foreach(var sor in sorok) {
            autok.Add(new Auto(sor));
        }
    
        for(var i = autok.Count - 1; i >= 0; --i) {
            var jelenlegi = autok[i];
        
            if(jelenlegi.elvitel) {
                Console.WriteLine("2. Feladat: Utoljára elvitt autó: " + jelenlegi.nap + ". nap, rendszam: " + jelenlegi.rendszam);
                break;
            }
        }
        
        autok.OrderBy(k => k.rendszam);
        Console.WriteLine("3. Feladat: Írj be egy napot!");
    
        var beNap = int.Parse(Console.ReadLine());
        var visszahozottAutokSzama = 0;
        
        foreach(var auto in autok) {
            if(auto.nap == beNap) {
                Console.WriteLine(auto.idopont + " " + auto.rendszam + " " + auto.szemelyAzonosito + (auto.elvitel ? " ki" : " be"));
            }
            
            if(!auto.elvitel) {
                ++visszahozottAutokSzama;
            }
        }
        
        Console.WriteLine("4. Feladat: Nem visszahozott autók száma: " + (autok.Count - visszahozottAutokSzama * 2));
        Console.WriteLine("5. Feladat");
        
        var rendszamok = new SortedSet<String>();
        
        foreach(var auto in autok) {
            rendszamok.Add(auto.rendszam);
        }
        
        foreach(var rendszam in rendszamok) {
            var maxKm = int.MinValue;
            var minKm = int.MaxValue;
            
            foreach(var auto in autok) {
                if(auto.rendszam == rendszam) {
                    var km = auto.km;
                
                    if(km > maxKm) {
                        maxKm = km;
                    }
                    if(km < minKm) {
                        minKm = km;
                    }
                }
            }
            Console.WriteLine(rendszam + ": " + (maxKm - minKm) + " km");
        }
        
        var maxAuto = autok[0];
        var maxTav = 0;
        
        for(var i = 1; i < autok.Count; ++i) {
            var jelen = autok[i];
            var elozo = autok[i - 1];
            
            if(jelen.rendszam == elozo.rendszam && !jelen.elvitel) {
                var dist = jelen.km - elozo.km;
                
                if(dist > maxTav) {
                    maxTav = dist;
                    maxAuto = jelen;
                }
            }
        }
        
        Console.WriteLine("6. Feladat: Leghosszabb út: " + maxTav + " km, személy: " + maxAuto.szemelyAzonosito);
        Console.WriteLine("7. Feladat: Írj be egy rendszámot!");
        var beRendszam = Console.ReadLine();
        
        using(var file = new StreamWriter(beRendszam + "_menetlevel.txt")){
            foreach(var auto in autok) {
                if(auto.rendszam == beRendszam) {
                    if(auto.elvitel) {
                        file.Write(auto.szemelyAzonosito + "\t" + auto.nap + ". " + auto.idopont + "\t" + auto.km + " km");
                    }else {
                        file.Write("\t" + auto.nap + ". " + auto.idopont + "\t" + auto.km + " km\n");
                    }
                }
            }
        }
    }
}