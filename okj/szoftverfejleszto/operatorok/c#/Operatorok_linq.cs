using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

public static class Operatorok_linq {

    public static void Main() {
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

        Operatorok_linq.Generate(() => kifejezestBeker())
                       .TakeWhile(k => k != "vége")
                       .ForEach(k => Console.WriteLine($"{k} = {new Kifejezes(k).kiertekel()}"));

        var kiertekeltSorok = kifejezesek.Select(k => $"{k.TeljesKifejezes} = {k.kiertekel()}")
                                         .ToArray();

        File.WriteAllLines("eredmenyek.txt", kiertekeltSorok);
    }

    public static IEnumerable<T> Generate<T>(Func<T> generator) {
        while(true) yield return generator.Invoke();
    }

    public static void ForEach<T>(this IEnumerable<T> source, Action<T> action) {
        foreach (T element in source) action(element);
    }

    public static String kifejezestBeker() {
        Console.WriteLine("Kérek egy kifejezést!");
        return Console.ReadLine();
    }
}