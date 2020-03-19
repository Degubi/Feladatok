public class Utas{
    
    public final int ules;
    public final int start;
    public final int end;
    public final int sorszam;
    
    public Utas(String line, int sorsz) {
        var split = line.split(" ");
        
        sorszam = sorsz;
        ules = Integer.parseInt(split[0]);
        start = Integer.parseInt(split[1]);
        end = Integer.parseInt(split[2]);
    }
    
    public int getTavolsag() {
        return end - start;
    }
    
    public int getAr(int kmAr) {
        var tav = getTavolsag();
        var utolso = tav % 10;
        var tizesek = tav / 10;
        
        if(utolso == 3 || utolso == 4 || utolso == 8 || utolso == 9) {
            ++tizesek;
        }
        
        return kmAr * tizesek;
    }
}