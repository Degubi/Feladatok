using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

var feladvanyok = File.ReadLines("feladvanyok.txt")
                      .Select(k => new Feladvany(k))
                      .ToArray();

Console.WriteLine($"3. Feladat: Feladványok száma: {feladvanyok.Length}");
Console.WriteLine("4. Feladat:");

var bekertMeret = Generate(MeretetBeker)
                 .Where(k => k >= 4 && k <= 9)
                 .First();

var bekertMeretuek = feladvanyok.Where(k => k.meret == bekertMeret)
                                .ToArray();

var kivalasztott = bekertMeretuek[new Random().Next(bekertMeretuek.Length)];

Console.WriteLine($"Ebből a méretből {bekertMeretuek.Length} db van");
Console.WriteLine($"5. Feladat: {kivalasztott.feladvanyTeljes}");

var kitoltottCellakSzama = kivalasztott.feladvanyTeljes.ToCharArray()
                                       .Where(k => k != '0')
                                       .Count();

Console.WriteLine("6. Feladat: A feladvány kitöltöttsége: " + (int) ((float) kitoltottCellakSzama / kivalasztott.feladvanyTeljes.Length * 100) + "%");
Console.WriteLine("7. Feladat:");
kivalasztott.Kirajzol();

File.WriteAllLines($"sudoku{bekertMeret}.txt", bekertMeretuek.Select(k => k.feladvanyTeljes));


int MeretetBeker() {
    Console.WriteLine("Kérem 1 feladvány méretét!");
    return int.Parse(Console.ReadLine());
}

IEnumerable<T> Generate<T>(Func<T> generator) {
    while(true) yield return generator.Invoke();
}