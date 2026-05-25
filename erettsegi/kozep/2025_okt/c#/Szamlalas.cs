var meresek = File.ReadAllText("meres.txt").Trim().Split(", ")
                  .Select(int.Parse)
                  .ToArray();

var osszesKerekparos = meresek.Sum(KerekparosErtekOsszeadashoz);

Console.WriteLine($"2. Feladat: Összesen {osszesKerekparos} kerékpárost számoltak");
Console.WriteLine("3. Feladat: Óránkénti mérések:");

var orankentiAthaladok = meresek.Chunk(4)
                                .Select(k => k.Sum(KerekparosErtekOsszeadashoz))
                                .ToArray();

Enumerable.Range(0, orankentiAthaladok.Length).ToList()
          .ForEach(i => Console.WriteLine($"{i + 6} órától {orankentiAthaladok[i]} kerékpáros"));

var maxAthaladokI = Enumerable.Range(0, meresek.Length)
                              .MaxBy(i => meresek[i]);

Console.WriteLine($"Az áthaladók maximális száma: {meresek[maxAthaladokI]}, időpontja: {idotFormaz(maxAthaladokI)}");


int KerekparosErtekOsszeadashoz(int ertek) => ertek == -1 ? 0 : ertek;

String idotFormaz(int meresI) {
    var percek = 6 * 60 + 15 + (meresI * 15);

    return (percek / 60) + ":" + (percek % 60);
}
