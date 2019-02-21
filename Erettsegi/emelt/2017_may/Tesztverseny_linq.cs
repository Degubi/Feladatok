using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Prog{
    static class Tesztverseny{
        public static void ForEach<T>(this IEnumerable<T> elements, Action<T> action) => Array.ForEach(elements.ToArray(), action);

        static void Main(string[] args){
		    var lines = File.ReadAllLines("valaszok.txt");
		
		    var megoldasok = lines[0];
		    var versenyzok = lines.Skip(1)
							      .Select(k => new Versenyzo(k, megoldasok))
                                  .OrderBy(k => k.pontok)
							      .ToArray();
		
		    Console.WriteLine("2. feladat: A vetélkedőn " + versenyzok.Length + " versenyző indult.\nÍrj be 1 ID-t!");
		
		    String readID = Console.ReadLine();
		    Versenyzo kivalasztott = versenyzok.Where(k => k.nev == readID).First();
		
		    Console.WriteLine("3. feladat: A versenyző azonosítója = " + readID + "\n" + kivalasztott.valaszok + " (a versenyző válaszai)");
		    Console.WriteLine("4. feladat:\n" + megoldasok + " (a helyes megoldás)");
		
		    Enumerable.Range(0, megoldasok.Length)
				      .Select(index => kivalasztott.valaszok[index] == megoldasok[index] ? "+" : " ")
				      .ForEach(Console.Write);
		
		    Console.WriteLine(" (a versenyző helyes válaszai)\nÍrd be 1 feladat sorszámát!");

		    int readIndex = int.Parse(Console.ReadLine()) - 1;
		    int good = versenyzok.Where(k => k.valaszok[readIndex] == megoldasok[readIndex]).Count();
		
		    Console.WriteLine("5. feladat: A feladat sorszáma = " + (readIndex + 1));
            
            string percent = ((float)good * 100 / versenyzok.Length).ToString().Substring(0, 5);
		    Console.WriteLine("A feladatra " + good + " fő, a versenyzők " + percent + "%-a adott helyes választ.");
		
		    using(StreamWriter output = new StreamWriter("pontok.txt")){
			    versenyzok.ForEach(output.WriteLine);
		    }
		
		    Console.WriteLine("7. feladat: A verseny legjobbjai:");
		
            int[] pontok = versenyzok.Select(k => k.pontok)
                                     .Distinct()
                                     .OrderByDescending(k => k)
                                     .ToArray();
		    Enumerable.Range(1, 3)
                      .ForEach(index => versenyzok.Where(k => k.pontok == pontok[index - 1])
					  	                          .ForEach(versenyzo => Console.WriteLine(index + ". díj: " + versenyzo)));
            Console.Read();
	    }
	
	    class Versenyzo{
		    public readonly string nev, valaszok;   //Minden readonly, igazi jófiúk vagyunk
		    public readonly int pontok;
		
		    public Versenyzo(string data, string megoldasok) {
			    string[] split = data.Split(' ');
			    nev = split[0];
			    valaszok = split[1];
			
			    pontok = Enumerable.Range(0, megoldasok.Length)
					               .Where(k => megoldasok[k] == valaszok[k])
					               .Select(SumPoint)
					               .Sum();
		    }
		
		    private static int SumPoint(int index) {
			    if(index <= 4) return 3;
			    else if(index >= 5 && index <= 9) return 4;
			    else if(index >= 10 && index <= 12) return 5;
			    return 6;
		    }
		
		    public override string ToString() => pontok + ": " + nev;
        }
	}
}