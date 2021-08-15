using System;
using System.IO;

var tabla = new Tabla('#');

Console.WriteLine("4. Feladat: Az üres tábla");
tabla.megjelenit(Console.Out);

Console.WriteLine("6. Feladat: A feltöltött tábla");
tabla.elhelyez(8);
tabla.megjelenit(Console.Out);

Console.WriteLine("9. Feladat: Üres sorok és oszlopok száma");
Console.WriteLine("Oszlopok: " + tabla.uresOszlopokSzama());
Console.WriteLine("Sorok: " + tabla.uresSorokSzama());

using var file = new StreamWriter("tablak64.txt");

for(var i = 1; i < 65; ++i) {
    var fileTabla = new Tabla('*');
    fileTabla.elhelyez(i);

    fileTabla.megjelenit(file);
    file.Write('\n');
}