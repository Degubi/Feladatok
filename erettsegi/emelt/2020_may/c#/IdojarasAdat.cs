using System;

public class IdojarasAdat {
        
    public readonly string telepules;
    public readonly DateTime ido;
    public readonly string szelIrany;
    public readonly int szelErosseg;
    public readonly int homerseklet;
    
    public IdojarasAdat(String line) {
        var split = line.Split(' ');
        var teljesIdo = int.Parse(split[1]);
        var now = DateTime.Now;

        telepules = split[0];
        ido = new DateTime(now.Year, now.Month, now.Day, teljesIdo / 100, teljesIdo % 100, 0);
        szelIrany = split[2].Substring(0, 3);
        szelErosseg = int.Parse(split[2].Substring(3));
        homerseklet = int.Parse(split[3]);
    }
}