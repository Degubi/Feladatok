using System;
using System.IO;
using System.Linq;

var lines = File.ReadAllLines("valaszok.txt");

var megoldasok = lines[0];
var versenyzok = lines.Skip(1)
                      .Select(k => new Versenyzo(k, megoldasok))
                      .OrderBy(k => k.pontok)
                      .ToArray();

Console.WriteLine($"2. feladat: A vetélkedőn {versenyzok.Length} versenyző indult.\nÍrj be 1 ID-t!");

var readID = Console.ReadLine();
var kivalasztott = versenyzok.Where(k => k.nev == readID).First();

Console.WriteLine("3. feladat: A versenyző azonosítója = " + readID + "\n" + kivalasztott.valaszok + " (a versenyző válaszai)");
Console.WriteLine($"4. feladat:\n{megoldasok} (a helyes megoldás)");

Enumerable.Range(0, megoldasok.Length)
          .Select(index => kivalasztott.valaszok[index] == megoldasok[index] ? "+" : " ")
          .ToList()
          .ForEach(Console.Write);

Console.WriteLine(" (a versenyző helyes válaszai)\nÍrd be 1 feladat sorszámát!");

var readIndex = int.Parse(Console.ReadLine()) - 1;
var good = versenyzok.Where(k => k.valaszok[readIndex] == megoldasok[readIndex]).Count();

Console.WriteLine("5. feladat: A feladat sorszáma = " + (readIndex + 1));

var percent = ((float) good * 100 / versenyzok.Length).ToString().Substring(0, 5);
Console.WriteLine("A feladatra " + good + " fő, a versenyzők " + percent + "%-a adott helyes választ.");

File.WriteAllLines("pontok.txt", versenyzok.Select(k => k.pontok + ": " + k.nev));

Console.WriteLine("7. feladat: A verseny legjobbjai:");

var pontok = versenyzok.Select(k => k.pontok)
                        .Distinct()
                        .OrderByDescending(k => k)
                        .ToArray();

Enumerable.Range(1, 3).ToList()
            .ForEach(index => versenyzok.Where(k => k.pontok == pontok[index - 1]).ToList()
                                        .ForEach(versenyzo => Console.WriteLine(index + ". díj: " + versenyzo)));


class Versenyzo {
    public readonly string nev;
    public readonly string valaszok;
    public readonly int pontok;

    public Versenyzo(string data, string megoldasok) {
        var split = data.Split(' ');

        nev = split[0];
        valaszok = split[1];
        pontok = Enumerable.Range(0, megoldasok.Length)
                           .Where(k => megoldasok[k] == valaszok[k])
                           .Select(SumPoint)
                           .Sum();
    }

    private static int SumPoint(int index) => index <= 4 ? 3 :
                                              index >= 5 && index <= 9 ? 4 :
                                              index >= 10 && index <= 12 ? 5 : 6;
}