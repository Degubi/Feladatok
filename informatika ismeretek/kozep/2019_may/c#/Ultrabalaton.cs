using System;
using System.IO;
using System.Collections.Generic;

namespace Erettsegi {
    public class Ultrabalaton {

        public static void Main(string[] args) {
            var lines = File.ReadAllLines("ub2017egyeni.txt");
            var versenyzok = new List<Versenyzo>();
        
            for(var i = 1; i < lines.Length; ++i) {
                versenyzok.Add(new Versenyzo(lines[i]));
            }
        
            Console.WriteLine("3. Feladat: Egyéni indulók: " + versenyzok.Count);
        
            var celbaertNoiVersenyzok = 0;
            foreach(var versenyzo in versenyzok) {
                if(versenyzo.befejezesSzazalek == 100 && versenyzo.kategoria == "Noi") {
                    ++celbaertNoiVersenyzok;
                }
            }
        
            Console.WriteLine("4. Feladat: Célbaért női versenyzők: " + celbaertNoiVersenyzok);
            Console.WriteLine("5. Feladat: Írd be egy versenyző nevét!");
        
            var bekertNev = Console.ReadLine();
            var indultEBekert = false;
            var bekertSzazaleka = -1;
            
            foreach(var versenyzo in versenyzok) {
                if(versenyzo.nev == bekertNev) {
                    indultEBekert = true;
                    bekertSzazaleka = versenyzo.befejezesSzazalek;
                    break;
                }
            }
            
            if(indultEBekert) {
                Console.WriteLine("Indult egyéniben? Igen");
                Console.WriteLine("Teljesítette a távot? " + (bekertSzazaleka == 100 ? "Igen" : "Nem"));
            }else {
                Console.WriteLine("Indult egyéniben? Nem");
            }
        
            var ferfiAtlagIdo = 0D;
            var ferfiakSzama = 0;
        
            foreach(var versenyzo in versenyzok) {
                if(versenyzo.befejezesSzazalek == 100 && versenyzo.kategoria == "Ferfi") {
                    ++ferfiakSzama;
                    ferfiAtlagIdo += versenyzo.idoOraban();
                }
            }
        
            Console.WriteLine("7. Feladat: Átlagos idő: " + (ferfiAtlagIdo / ferfiakSzama));
        
            var ferfiGyoztes = versenyzok[0];
            var noiGyoztes = versenyzok[0];
        
            foreach(var versenyzo in versenyzok) {
                if(versenyzo.befejezesSzazalek == 100) {
                    if(versenyzo.kategoria == "Noi") {
                        if(versenyzo.idoOraban() < noiGyoztes.idoOraban()) {
                            noiGyoztes = versenyzo;
                        }
                    }else {
                        if(versenyzo.idoOraban() < ferfiGyoztes.idoOraban()) {
                            ferfiGyoztes = versenyzo;
                        }
                    }
                }
            }
        
            Console.WriteLine($"Nők: {noiGyoztes.nev} ({noiGyoztes.rajtszam}) - {noiGyoztes.ido}");
            Console.WriteLine($"Férfiak: {noiGyoztes.nev} ({noiGyoztes.rajtszam}) - {noiGyoztes.ido}");
        }
    }

    public class Versenyzo {
        public readonly string nev;
        public readonly int rajtszam;
        public readonly string kategoria;
        public readonly string ido;
        public readonly int befejezesSzazalek;
        
        public Versenyzo(string line) {
            var split = line.Split(';');
            
            nev = split[0];
            rajtszam = int.Parse(split[1]);
            kategoria = split[2];
            ido = split[3];
            befejezesSzazalek = int.Parse(split[4]);
        }
        
        public double idoOraban() {
            var daraboltIdo = ido.Split(':');
            var ora = double.Parse(daraboltIdo[0]);
            var perc = double.Parse(daraboltIdo[1]);
            var mp = double.Parse(daraboltIdo[2]);
            
            return ora + (perc / 60D) + (mp / 3600D);
        }
    }
}