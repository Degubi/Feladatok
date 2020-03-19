using System;
using System.Linq;
using System.IO;

namespace Erettsegi {
    public class Nasa_linq {

        public static void Main(string[] args) {
            var keresek = File.ReadLines("NASAlog.txt")
                              .Select(k => new Keres(k))
                              .ToArray();
        
            Console.WriteLine("5. Feladat: Kérések száma: " + keresek.Length);
        
            var osszmeret = keresek.Select(k => k.ByteMeret())
                                   .Sum();
        
            Console.WriteLine("6. Feladat: Összméret: " + osszmeret + " byte");
        
            var domainesek = keresek.Where(k => k.Domain())
                                    .Count();
        
            var domainSzazalek = ((float) domainesek) / keresek.Length * 100;
            Console.WriteLine("8. Feladat: Domaines kérések: " + domainSzazalek.ToString("0.00") + "%");
            Console.WriteLine("9. Feladat:");
            
            keresek.GroupBy(k => k.httpKod)
                   .ToList()
                   .ForEach(k => Console.WriteLine("    " + k.Key + ": " + k.Count() + " db"));
        }
    }
}