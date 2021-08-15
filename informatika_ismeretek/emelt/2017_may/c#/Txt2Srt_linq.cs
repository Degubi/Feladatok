﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;

var feliratok = new List<IdozitettFelirat>();
var adatok = File.ReadAllLines("feliratok.txt");

for(var i = 0; i < adatok.Length; i += 2) {
    feliratok.Add(new IdozitettFelirat(adatok[i], adatok[i + 1]));
}

var legtobbSzobolAllo = feliratok.First(f => f.SzavakSzama() == feliratok.Max(k => k.SzavakSzama())).felirat;

Console.WriteLine($"5. feladat: A feliratok száma: {feliratok.Count}");
Console.WriteLine($"7. feladat: A legtöbb szóból álló felirat: {legtobbSzobolAllo}");

using var output = new StreamWriter("felirat.srt");

for(var i = 0; i < feliratok.Count; ++i){
    output.WriteLine(i + 1);
    output.WriteLine(feliratok[i].StrIdozites());
    output.WriteLine(feliratok[i].felirat);
    output.WriteLine();
}