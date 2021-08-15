using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

var kifejezesek = File.ReadLines("kifejezesek.txt", Encoding.GetEncoding("ISO-8859-1"))
                      .Select(k => new Kifejezes(k))
                      .ToArray();

Console.WriteLine($"2. Feladat: Kifejezések száma: {kifejezesek.Length}");

var operatoronkentiDbSzam = kifejezesek.GroupBy(k => k.Operator)
                                       .ToDictionary(k => k.Key, k => k.Count());

Console.WriteLine($"2. Feladat: Maradékos osztások száma: {operatoronkentiDbSzam["mod"]}");

var vanETizzelOszthatoOperandusu = kifejezesek.Any(k => k.ElsoOperandus % 10 == 0 && k.MasodikOperandus % 10 == 0);

Console.WriteLine($"4. Feladat: {(vanETizzelOszthatoOperandusu ? "Van" : "Nincs")} ilyen kifejezés");
Console.WriteLine("5. Feladat: \n" +
                  "    'mod' -> " + operatoronkentiDbSzam["mod"] + " db\n" +
                  "      '/' -> " + operatoronkentiDbSzam["/"] + " db\n" +
                  "    'div' -> " + operatoronkentiDbSzam["div"] + " db\n" +
                  "      '-' -> " + operatoronkentiDbSzam["-"] + " db\n" +
                  "      '*' -> " + operatoronkentiDbSzam["*"] + " db\n" +
                  "      '+' -> " + operatoronkentiDbSzam["+"] + " db");

Generate(() => kifejezestBeker())
.TakeWhile(k => k != "vége")
.ToList()
.ForEach(k => Console.WriteLine($"{k} = {new Kifejezes(k).kiertekel()}"));

File.WriteAllLines("eredmenyek.txt", kifejezesek.Select(k => $"{k.TeljesKifejezes} = {k.kiertekel()}"));


IEnumerable<T> Generate<T>(Func<T> generator) {
    while(true) yield return generator.Invoke();
}

String kifejezestBeker() {
    Console.WriteLine("Kérek egy kifejezést!");
    return Console.ReadLine();
}