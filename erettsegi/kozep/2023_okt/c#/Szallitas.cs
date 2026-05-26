var tomegek = File.ReadAllText("tomeg.txt").Trim().Split(", ")
                  .Select(int.Parse)
                  .ToArray();

Console.WriteLine($"2. Feladat: A tárgyak tömegének összege: {tomegek.Sum()}kg");

var dobozok = tomegek.Aggregate(new List<int> { 0 }, (dobozokResult, tomeg) => {
    var utolsoDoboz = dobozokResult.Last();
    var ujTomeg = utolsoDoboz + tomeg;

    if(ujTomeg <= 20) {
        dobozokResult[dobozokResult.Count - 1] = ujTomeg;
    }else{
        dobozokResult.Add(tomeg);
    }

    return dobozokResult;
});

var kiirniLista = string.Join(' ', dobozok);

Console.WriteLine($"3. Feladat: A dobozok tartalmának tömege (kg): {kiirniLista}\nSzükséges dobozok száma: {dobozok.Count}");
