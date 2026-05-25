Console.Write("1. Feladat: Adja meg az aktivitását: ");

var aktivitas = Console.ReadLine()!;
var teljesTavolsag = aktivitas.Sum(k => k switch {
                                  'U' or 'G' => 1,
                                  'F' => 2,
                                  'K' => 10,
                                  _ => 0
                              });

var voltEMindenAktivitas = aktivitas.Contains("U") && aktivitas.Contains("G") && aktivitas.Contains("F") && aktivitas.Contains("K");
var teljesVegsoTavolsag = teljesTavolsag + (voltEMindenAktivitas ? 10 : 0);

Console.WriteLine($"2. Feladat: Az elért távolság: {teljesTavolsag}");
Console.WriteLine("3. Feladat: " + (voltEMindenAktivitas ? "Bravó! Jutalma még 10 km." : "Nem jár jutalom."));
Console.WriteLine($"4. Feladat: Eredménye: {teljesVegsoTavolsag}km. " + (teljesVegsoTavolsag >= 40 ? "Gratulálok, kihívás teljesítve!" : "Legközelebb sikerül!"));
