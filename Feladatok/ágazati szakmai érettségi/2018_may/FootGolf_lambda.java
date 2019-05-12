import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FootGolf_lambda {
    public static void main(String[] args) throws Exception{
    	var versenyzok = Files.lines(Paths.get("fob2016.txt")).map(Versenyzo::new).collect(Collectors.toList());
    	
    	System.out.println("3.Feladat\nVersenyzõk száma: " + versenyzok.size());
    	System.out.println("4.Feladat\nNõi versenyzõk aránya: " + String.format("%.2f", 
    					   versenyzok.stream().filter(k -> k.kategoria.contains("Noi"))
    					   					  .count() / (float)versenyzok.size() * 100) + "%");
    	
    	versenyzok.stream().filter(k -> k.kategoria.contains("Noi"))
    					   .max(Comparator.comparingInt(Versenyzo::osszPont))
    					   .ifPresent(versenyzo -> System.out.println("6.Feladat\nNõi versenyzõ: " 
    							   + "Név: " + versenyzo.nev + ", Egyesület: " + versenyzo.versenyEgyesulet + ", pontok: " + versenyzo.osszPont()));
    	
    	Files.write(Paths.get("osszpontFF.txt"), versenyzok.stream().map(Versenyzo::toString).collect(Collectors.toList()));
    	System.out.println("8.Feladat:");
    	System.out.println(versenyzok.stream().collect(Collectors.groupingBy(k -> k.versenyEgyesulet, Collectors.counting()))
    								 		  .entrySet().stream()
    								 		  .filter(k -> !k.getKey().equals("n.a."))
    								 		  .filter(k -> k.getValue() > 2)
    								 		  .collect(Collectors.toList()));
    }
    
    static class Versenyzo{
    	String nev, kategoria, versenyEgyesulet;
    	int[] pontok;
    	
    	public Versenyzo(String line) {
    		var split = line.split(";");
    		nev = split[0];
    		kategoria = split[1];
    		versenyEgyesulet = split[2];
    		pontok = IntStream.range(3, 11).map(k -> Integer.parseInt(split[k])).sorted().toArray();
		}
    	
    	public int osszPont() {  //5. Feladat
    		return Arrays.stream(pontok, 2, 8).sum() + (pontok[0] != 0 ? 10 : 0) + (pontok[1] != 0 ? 10 : 0);
    	}
    	
    	@Override
    	public String toString() {
    		return nev + " " + osszPont();
    	}
    }
}