var meresek = File.ReadAllText("nyomas.txt").Trim().Split(", ")
                  .Select(int.Parse)
                  .ToArray();

var legkisebbIndex = Enumerable.Range(0, meresek.Length)
                               .MinBy(i => meresek[i]);

Console.WriteLine($"A legkisebb mért érték: {meresek[legkisebbIndex]}, sorszáma: {legkisebbIndex + 1}");
Console.Write("Minél kisebb értékeket keres? (egész szám) ");

var bekertErtek = int.Parse(Console.ReadLine()!);
var kisebbErtekekSzama = meresek.Count(k => k < bekertErtek);

Console.WriteLine($"{bekertErtek} alatti mérések száma: {kisebbErtekekSzama}");

var legnagyobbCsokkenes = Enumerable.Zip(meresek.Take(meresek.Length - 1), meresek.Skip(1))
                                    .Max((k) => k.First - k.Second);

Console.WriteLine($"A két mérés közötti legnagyobb csökkenés: {legnagyobbCsokkenes}");
