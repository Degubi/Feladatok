using System;
using System.Collections.Generic;
using System.IO;

namespace Erettsegi {
    public class Nasa {

        public static void Main(string[] args) {
            var keresek = new List<Keres>();
            foreach(var line in File.ReadAllLines("NASAlog.txt")) {
                keresek.Add(new Keres(line));
            }
        
            Console.WriteLine("5. Feladat: Kérések száma: " + keresek.Count);
        
            var osszmeret = 0;
            foreach(var keres in keresek) {
                osszmeret += keres.ByteMeret();
            }
        
            Console.WriteLine("6. Feladat: Összméret: " + osszmeret + " byte");
        
            var domainesek = 0;
            foreach(var keres in keresek) {
                if(keres.Domain()){
                    ++domainesek;
                }
            }
            
            var domainSzazalek = ((float) domainesek) / keresek.Count * 100;
            Console.WriteLine("8. Feladat: Domaines kérések: " + domainSzazalek.ToString("0.00") + "%");
            Console.WriteLine("9. Feladat:");
        
            var stat = new Dictionary<string, int>();
            foreach(var keres in keresek) {
                if(stat.ContainsKey(keres.httpKod)) {
                    stat[keres.httpKod] = stat[keres.httpKod] + 1;
                } else {
                    stat.Add(keres.httpKod, 1);
                }
            }
        
            foreach(var entry in stat) {
                Console.WriteLine("    " + entry.Key + ": " + entry.Value + " db");
            }
        }
    }
}