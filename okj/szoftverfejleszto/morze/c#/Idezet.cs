using System.Collections.Generic;

public class Idezet {

    public readonly string szerzo;
    public readonly string uzenet;
    
    public Idezet(string sor, Dictionary<string, string> abc) {
        var split = sor.Split(';');
                
        this.szerzo = Morze.morze2Szoveg(split[0], abc);
        this.uzenet = Morze.morze2Szoveg(split[1], abc);
    }
}