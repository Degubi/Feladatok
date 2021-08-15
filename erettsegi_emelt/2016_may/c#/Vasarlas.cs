using System.Collections.Generic;
using System.Linq;

public class Vasarlas {
    public readonly Dictionary<string, int> dolgok = new Dictionary<string, int>();
    
    public Vasarlas(List<string> things) {
        foreach(string th in things) {
            if(!dolgok.ContainsKey(th)) {
                dolgok.Add(th, things.Where(k => k.Equals(th)).Count());
            }
        }
    }
}