var meresek = File.ReadAllText("meres.txt").Trim().Split(", ")
                  .Select(int.Parse)
                  .ToArray();

var osszesKerekparos = meresek.Sum(k => Math.Max(0, k));

Console.WriteLine($"2. Feladat: Összesen {osszesKerekparos} kerékpárost számoltak");
Console.WriteLine("3. Feladat: Óránkénti mérések:");

var orankentiAthaladok = meresek.Chunk(4)
                                .Select(k => k.Sum(l => Math.Max(0, l)))
                                .ToArray();

Enumerable.Range(0, orankentiAthaladok.Length).ToList()
          .ForEach(i => Console.WriteLine($"{i + 6} órától {orankentiAthaladok[i]} kerékpáros"));

var maxAthaladokI = Enumerable.Range(0, meresek.Length)
                              .MaxBy(i => meresek[i]);

Console.WriteLine($"Az áthaladók maximális száma: {meresek[maxAthaladokI]}, időpontja: {idotFormaz(maxAthaladokI)}");


string idotFormaz(int meresI) {
    var percek = 6 * 60 + 15 + (meresI * 15);

    return (percek / 60) + ":" + (percek % 60);
}
