import java.io.*;
import java.nio.file.*;
import java.util.*;
 
public class Otszaz {
	
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("penztar.txt"));
        var vasarlasok = new ArrayList<Vasarlas>();
       
        ArrayList<String> toAdd = new ArrayList<>();
        for(String sor : lines) {
            if(sor.equals("F")) {
                vasarlasok.add(new Vasarlas(toAdd));
                toAdd.clear();
            }else{
                toAdd.add(sor);
            }
        }
		
        System.out.println("Vásárlások száma: " + vasarlasok.size());
        System.out.println("Elsö vásárlásnál vett dolgok száma: " + vasarlasok.get(0).dolgok.size());
		
        Scanner input = new Scanner(System.in);
        System.out.println("Írj be 1 sorszámot");
        int sorszam = input.nextInt();
        System.out.println("Írj be 1 árut");
        String aru = input.next();
        System.out.println("Írj be 1 mennyiséget");
        int dbszam = input.nextInt();
        input.close();
        
        int amount = 0, utolso = 0;
        for(int k = 0; k < vasarlasok.size(); ++k) {
            for(var entries : vasarlasok.get(k).dolgok.entrySet()) {
                if(entries.getKey().equals(aru)) {
                    ++amount;
                    utolso = k;
                    if(amount == 1) {
                        System.out.println("Elõször a " + ++k + ". vásárlásnál vettek " + aru + "-t");
                    }
                }
            }
        }
       
        System.out.println("Utoljára a " + ++utolso + ". vásárlásnál vettek " + aru + "-t");
        System.out.println("Összesen " + amount + "-szor vettek " + aru + "-t");
        System.out.println(dbszam + " db esetén a fizetendõ: " + ertek(dbszam));
        System.out.println("A " + sorszam + ". vásárláskor vásárolt dolgok: " + vasarlasok.get(sorszam - 1).dolgok.toString());
       
        try(PrintWriter output = new PrintWriter("osszeg.txt")){
	        for(int k = 0; k < vasarlasok.size(); ++k) {
	            int printMount = 0;
	            for(var entries : vasarlasok.get(k).dolgok.entrySet()) {
	                printMount += ertek(entries.getValue());
	            }
	            output.println(Integer.toString(k + 1) + ":" + printMount);
	        }
        }
    }
    
    public static int ertek(int dbSzam) {
        if(dbSzam == 1) {
            return 500;
        }else if(dbSzam == 2) {
            return 950;
        }else if(dbSzam == 3) {
            return 1350;
        }
        return 1350 + (500 * (dbSzam - 1));
    }
    
    static class Vasarlas{
        HashMap<String, Integer> dolgok = new HashMap<>();
        
        public Vasarlas(ArrayList<String> things) {
            for(String th : things) {
                if(!dolgok.containsKey(th)) {
                    dolgok.put(th, Collections.frequency(things, th));
                }
            }
        }
    }
}