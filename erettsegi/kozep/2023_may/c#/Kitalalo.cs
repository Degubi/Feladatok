var szavak = File.ReadAllText("szavak.txt").Trim().Replace("\"", "").Split(", ");
var random = new Random();
var kivalasztottSzo = szavak[random.Next(szavak.Length)];
var probakSzama = 1;

while(true) {
    Console.Write("Kérem a tippet: ");
    var tipp = Console.ReadLine()!;

    if(tipp == "stop") {
        break;
    }else{
        Console.Write("Az eredmény: ");

        var eltalaltKarakterek = 0;
        for(var i = 0; i < 6; ++i) {
            var eredetiKarakter = kivalasztottSzo[i];
            var eltalaltaE = eredetiKarakter == tipp[i];

            Console.Write(eltalaltaE ? eredetiKarakter : '.');

            if(eltalaltaE) {
                ++eltalaltKarakterek;
            }
        }

        Console.WriteLine();

        if(eltalaltKarakterek == 6) {
            Console.WriteLine("\n" + probakSzama + " tippeléssel sikerült kitalálni");
            break;
        }else{
            ++probakSzama;
        }
    }
}
