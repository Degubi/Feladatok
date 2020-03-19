using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace Erettsegi {
    public class Morze {
		
		//A mintában 49 morze abc karaktert ír, de a fájlban csak 43 van
        public static void Main(string[] args) {
            var abcLines = File.ReadAllLines("morzeabc.txt");
            var betuToMorze = new Dictionary<string, string>();
            var morzeToBetu = new Dictionary<string, string>();

            for(var i = 1; i < abcLines.Length; ++i) {
                var split = abcLines[i].Split('\t');
                betuToMorze.Add(split[0], split[1]);
                morzeToBetu.Add(split[1], split[0]);
            }
            
            Console.WriteLine("3. Feladat: Karakterek száma: " + betuToMorze.Count);
            Console.WriteLine("4. Feladat: Írjon be 1 karaktert!");
            
            var bekert = Console.ReadLine();
            
            if(betuToMorze.ContainsKey(bekert)){
                Console.WriteLine("A " + bekert + " karakter kódja: " + betuToMorze[bekert]);
            }else{
                Console.WriteLine("Nem található a kódtárban ilyen karakter!");
            }
        
            var morzeLines = File.ReadAllLines("morze.txt");
            var idezetek = new List<Idezet>();
            
            foreach(var line in morzeLines) {
                idezetek.Add(new Idezet(line, morzeToBetu));
            }
        
            Console.WriteLine("7. Feladat: Első idézet szerzője: " + idezetek[0].szerzo);
        
            var leghosszabbIdezet = idezetek[0];
            foreach(var idezet in idezetek) {
                if(idezet.uzenet.Length > leghosszabbIdezet.uzenet.Length) {
                    leghosszabbIdezet = idezet;
                }
            }
            
            Console.WriteLine("8. Feladat: Leghosszab idézet: " + leghosszabbIdezet.szerzo + ": " + leghosszabbIdezet.uzenet);
            Console.WriteLine("9. Feladat: Arisztotelés idézetei: ");
        
            foreach(var idezet in idezetek) {
                if(idezet.szerzo == "ARISZTOTELÉSZ") {
                   Console.WriteLine("\t- " + idezet.uzenet);
                }
            }
            
            using(var forditas = new StreamWriter("forditas.txt")){
                foreach(var idezet in idezetek) {
                   forditas.WriteLine(idezet.szerzo + ':' + idezet.uzenet);
                }
            }
        }

        public static string morze2Szoveg(string uzenet, Dictionary<string, string> abc) {
            var forditott = new StringBuilder();

            foreach(var szavak in uzenet.Split(new [] {"       "}, StringSplitOptions.None)) {
                foreach(var betuk in szavak.Split(new [] {"   "}, StringSplitOptions.None)) {
                    forditott.Append(abc[betuk]);
                }
                
                forditott.Append(' ');
            }
            
            return forditott.ToString();
        }

        public class Idezet{
            public readonly string szerzo;
            public readonly string uzenet;
            
            public Idezet(string sor, Dictionary<String, String> abc) {
                var split = sor.Split(';');
                     
                this.szerzo = morze2Szoveg(split[0], abc);
                this.uzenet = morze2Szoveg(split[1], abc);
            }
        }
    }
}