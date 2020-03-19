using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Prog{
    public class Tanciskola{

        public static void Main(string[] args){
            var file = File.ReadAllLines("tancrend.txt");
            var tancok = new List<Tanc>();
        
            for(var k = 0; k < file.Length; k += 3) {
                tancok.Add(new Tanc(file[k], file[k + 1], file[k + 2]));
            }
        
            Console.WriteLine("Első tánc neve: " + tancok[0].category + ", az utolsóé: " + tancok[tancok.Count - 1].category);
        
            var szambaCounter = 0;
            foreach(var dansz in tancok) {
                if(dansz.category.Equals("samba")) {
                    ++szambaCounter;
                }
            }
            Console.WriteLine("Összesen " + szambaCounter + "-an szambásztak");
        
            var viliCat = new List<String>();
            foreach(var dansz in tancok) {
                if(dansz.woman.Equals("Vilma") && !viliCat.Contains(dansz.category)) {
                    viliCat.Add(dansz.category);
                }
            }
            Console.WriteLine("Vilma által táncolt kategóriák: " + viliCat.Aggregate((k, l) => k + ", " + l));
            Console.WriteLine("Írj be 1 kategóriát!");
        
            var readCat = Console.ReadLine();
            var hasPrinted = false;
            foreach(var dansz in tancok) {
                if(dansz.woman.Equals("Vilma") && dansz.category.Equals(readCat)) {
                    Console.WriteLine("Vilma a " + readCat + " kategóriában " + dansz.man + "-val táncolt");
                    hasPrinted = true;
                }
             }
             if(!hasPrinted) {
                 Console.WriteLine("Vilma a " + readCat + " kategóriában nem táncolt");
             }
            
            var csajok = new HashSet<String>();
            var skacok = new HashSet<String>();
            var lanyok = new List<Szereplo>();
            var fiuk = new List<Szereplo>();

            var output = new StreamWriter("szereplok.txt");
            foreach(var dans in tancok) {
                csajok.Add(dans.woman);
                skacok.Add(dans.man);
            }

            foreach(var cs in csajok) {
                lanyok.Add(new Szereplo(cs));
            }

            foreach(var fik in skacok) {
                fiuk.Add(new Szereplo(fik));
            }
            output.Write("Lányok: ");
        
            for(var k = 0; k < lanyok.Count; ++k) {
                if(k != lanyok.Count - 1) {
                    output.Write(lanyok[k] + ", ");
                }else{
                    output.Write(lanyok[k]);
                }
            }

            output.WriteLine();
            output.Write("Fiúk: ");
            for(var k = 0; k < fiuk.Count; ++k) {
                if(k != fiuk.Count - 1) {
                    output.Write(fiuk[k] + ", ");
                }else{
                    output.Write(fiuk[k]);
                }
            }
            output.Close();
        
            foreach(var dan in tancok) {
                foreach(var csaj in lanyok) {
                    if(dan.woman.Equals(csaj.name)) {
                        ++csaj.alkalmak;
                    }
                }

                foreach(var fiu in fiuk) {
                    if(dan.man.Equals(fiu.name)) {
                        ++fiu.alkalmak;
                    }
                }
            }

            lanyok = lanyok.OrderByDescending(k => k.alkalmak).ToList();
            fiuk = fiuk.OrderByDescending(k => k.alkalmak).ToList();
        
            var lanyMax = lanyok[0].alkalmak;
            var fiuMax = fiuk[0].alkalmak;
        
            Console.Write("A legtöbbet táncolt lányok: ");
            foreach(var la in lanyok) {
                if(la.alkalmak == lanyMax) {
                    Console.Write(la.name + " ");
                }
            }

            Console.WriteLine();
            Console.Write("A legtöbbet táncolt fiúk: ");

            foreach(var fi in fiuk) {
                if(fi.alkalmak == fiuMax) {
                    Console.Write(fi.name + " ");
                }
            }
        }

        public class Szereplo{

            public readonly String name;
            public int alkalmak = 0;

            public Szereplo(String neim) {
                name = neim;
            }

            public override string ToString() {
                return name;
            }
        }
    }
}