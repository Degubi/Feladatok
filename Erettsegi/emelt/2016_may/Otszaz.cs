using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Prog{
    class Otszaz{
        static void Main(string[] args){
            var lines = File.ReadAllLines("penztar.txt");
            var vasarlasok = new List<Vasarlas>();
       
            var toAdd = new List<string>();
            foreach(String sor in lines) {
                if(sor.Equals("F")) {
                    vasarlasok.Add(new Vasarlas(toAdd));
                    toAdd.Clear();
                }else{
                    toAdd.Add(sor);
                }
            }
		
            Console.WriteLine("Vásárlások száma: " + vasarlasok.Count);
            Console.WriteLine("Elsö vásárlásnál vett dolgok száma: " + vasarlasok[0].dolgok.Count);
       
            Console.WriteLine("Írj be 1 sorszámot");
            int sorszam = int.Parse(Console.ReadLine());
            Console.WriteLine("Írj be 1 árut");
            String aru = Console.ReadLine();
            Console.WriteLine("Írj be 1 mennyiséget");
            int dbszam = int.Parse(Console.ReadLine());
        
            int amount = 0, utolso = 0;
            for(int k = 0; k < vasarlasok.Count; ++k) {
                foreach(var entries in vasarlasok[k].dolgok.Keys){
                    if(entries.Equals(aru)) {
                        ++amount;
                        utolso = k;
                        if(amount == 1) {
                            Console.WriteLine("Először a " + ++k + ". vásárlásnál vettek " + aru + "-t");
                        }
                    }
                }
            }
			
            Console.WriteLine("Utoljára a " + ++utolso + ". vásárlásnál vettek " + aru + "-t");
            Console.WriteLine("Összesen " + amount + "-szor vettek " + aru + "-t");
            Console.WriteLine(dbszam + " db esetén a fizetendő: " + ertek(dbszam));
            Console.WriteLine("A " + sorszam + ". vásárláskor vásárolt dolgok: " + vasarlasok[sorszam - 1].dolgok.ToString());
			
            using(var output = new StreamWriter("osszeg.txt")){
	            for(int k = 0; k < vasarlasok.Count; ++k) {
	                int printMount = 0;
	                foreach(var entries in vasarlasok[k].dolgok.Values) {
	                    printMount += ertek(entries);
	                }
	                output.WriteLine((k + 1).ToString() + ":" + printMount);
	            }
            }
            Console.Read();
        }

        public static int ertek(int dbSzam) {
            if(dbSzam == 1) {
                return 500;
            }else if(dbSzam == 2) {
                return 950;
            }else if(dbSzam == 3) {
                return 1350;
            }
            return 1350 + (500 * (dbSzam - 1));
        }

        class Vasarlas{
            public Dictionary<string, int> dolgok = new Dictionary<string, int>();
            public Vasarlas(List<string> things) {
                foreach(string th in things) {
                    if(!dolgok.ContainsKey(th)) {
                        dolgok.Add(th, things.Where(k => k.Equals(th)).Count());
                    }
                }
            }
        }
    }
}