using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
 
namespace ConsoleApplication1{
    class Cimek{
        static void Main(string[] args){
            var jeloltek = File.ReadLines("szavazatok.txt")
                               .Select(k => new Jelolt(k))
                               .ToArray();

            Console.WriteLine("2. Feladat\nA választáson indult jelöltek száma: " + jeloltek.Length);
            Console.WriteLine("3.Feladat\nÍrd be 1 jelölt nevét!");
            string bekertNev = Console.ReadLine();

            bool printed = false;
            foreach(var jelolt in jeloltek) {
                if(jelolt.nev == bekertNev) {
                    Console.WriteLine("Szavazatok száma: " + jelolt.szavazatok);
                    printed = true;
                }
            }

            if(printed == false) {
                Console.WriteLine("Nem indult ilyen jelölt");
            }

            int osszSzavazat = jeloltek.Select(k => k.szavazatok).Sum();
            float arany = (float)osszSzavazat / 12345 * 100;

            Console.WriteLine("4. Feladat");
            Console.WriteLine("A választáson " + osszSzavazat + " polgár, a jogosultak {0:F2}%-a vett részt", arany);
            Console.WriteLine("5. Feladat");

            jeloltek.Select(k => k.part)
                    .Distinct()
                    .GroupBy(k => jeloltek.Where(l => l.part == k)
                                         .Select(l => l.szavazatok)
                                         .Sum() / (float)osszSzavazat * 100)
                    .ToList()
                    .ForEach(k => Console.WriteLine(k.Key.ToString("#.##") + "% " + k.Aggregate((m, a) => m + " " + a)));

            var max = jeloltek.Max(k => k.szavazatok);
            var legtobb = jeloltek.Where(k => k.szavazatok == max).First();

            Console.WriteLine("6. Feladat\nLegtobb szavazatot kapta: " + legtobb.nev + ", partja: " + legtobb.part);
            Console.WriteLine("7. Feladat");

            //c#-ba nem tudjuk :( + pontok pls
            File.WriteAllLines("kepviselok.txt", jeloltek.Select(k => k.nev).ToArray());

            Console.Read();
        }


        class Jelolt{
            public int kerSzam, szavazatok;
            public string nev, part;

            public Jelolt(string data) {
                string[] split = data.Split(' ');

                kerSzam = int.Parse(split[0]);
                szavazatok = int.Parse(split[1]);
                nev = split[2] + ' ' + split[3];
                part = split[4] == "-" ? "független" : split[4];
            }

            public override string ToString() {
                return "Nev: " + nev + ", part: " + part + ", szavazatok: " + szavazatok;
            }
        }
    }
}