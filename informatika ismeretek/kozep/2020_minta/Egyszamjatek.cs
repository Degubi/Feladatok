using System;
using System.Collections.Generic;
using System.IO;

namespace Mivaaa {
    public class Egyszamjatek {
        
        public static void Main() {
            var jatekosok = Beolvas("egyszamjatek1.txt");
            
            Console.WriteLine("3. Feladat: Játékosok száma: " + jatekosok.Count);
            Console.WriteLine("4. Feladat: Írj be egy forduló számot!");
            
            var beForduloSzam = int.Parse(Console.ReadLine()) - 1;
            var forduloTotal = 0F;
            
            foreach(var tippek in jatekosok.Values) {
                forduloTotal += tippek[beForduloSzam];
            }
            
            Console.WriteLine("5. Feladat: Tippek átlaga: " + (forduloTotal / jatekosok.Count).ToString("0.00"));
            Console.Read();
        }

        public static Dictionary<string, int[]> Beolvas(string file){
            var jatekosok = new Dictionary<string, int[]>();
        
            foreach(var line in File.ReadAllLines(file)) {
                var split = line.Split(' ');
                var tippek = new int[split.Length - 1];
                
                for(var i = 1; i < split.Length; ++i) {
                    tippek[i - 1] = int.Parse(split[i]);
                }
            
                jatekosok.Add(split[0], tippek);
            }

            return jatekosok;
        }
    }
}