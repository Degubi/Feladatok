import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FootGolf {
	
    public static void main(String[] args) throws Exception{
    	var sorok = Files.readAllLines(Paths.get("fob2016.txt"));
    	var versenyzok = new ArrayList<Versenyzo>();
    	
    	for(String sor : sorok) {
    		versenyzok.add(new Versenyzo(sor));
    	}
    	
    	System.out.println("3.Feladat: Versenyzõk száma: " + versenyzok.size());
    	
    	int noiVersenyzok = 0;
    	for(Versenyzo versenyzo : versenyzok) {
    		if(versenyzo.kategoria.contains("Noi")) {
    			++noiVersenyzok;
    		}
    	}
    	System.out.println("4.Feladat: Nõi versenyzõk aránya: " + String.format("%.2f", noiVersenyzok / (float)versenyzok.size() * 100) + "%");
    	
    	Versenyzo legtobbNoi = versenyzok.get(0);
    	for(Versenyzo vers : versenyzok) {
    		if(vers.kategoria.contains("Noi") && vers.osszPont() > legtobbNoi.osszPont()) {
    			legtobbNoi = vers;
    		}
    	}
    	System.out.println("6.Feladat: Nõi versenyzõ: " + "Név: " + legtobbNoi.nev + ", Egyesület: " 
    						+ legtobbNoi.versenyEgyesulet + ", pontok: " + legtobbNoi.osszPont());
    	
    	try(PrintWriter output = new PrintWriter("osszpontFF.txt")){
    		for(Versenyzo vers : versenyzok) {
    			output.println(vers);
    		}
    	}
    	
    	var stat = new HashMap<String, Integer>();
    	for(var vers : versenyzok) {
    		if(!vers.versenyEgyesulet.equals("n.a.")) {
    			int jelenes = 0;
    			
    			for(Versenyzo ver : versenyzok) {
    				if(ver.versenyEgyesulet.equals(vers.versenyEgyesulet)) {
    					++jelenes;
    				}
    			}
    			
    			if(jelenes > 2) {
    				stat.put(vers.versenyEgyesulet, jelenes);
    			}
    		}
    	}
    	System.out.println("8.Feladat: " + stat);
    }
    
    static class Versenyzo{
    	String nev, kategoria, versenyEgyesulet;
    	int[] pontok = new int[8];
    	
    	public Versenyzo(String line) {
    		var split = line.split(";");
    		nev = split[0];
    		kategoria = split[1];
    		versenyEgyesulet = split[2];
    		
    		for(int k = 3; k < 11; ++k) {
    			pontok[k - 3] = Integer.parseInt(split[k]);
    		}
    		Arrays.sort(pontok);
		}
    	
    	public int osszPont() {
    		int pont = 0;
    		for(int k = 2; k < 8; ++k) {
    			pont += pontok[k];
    		}
    		return pont + (pontok[0] != 0 ? 10 : 0) + (pontok[1] != 0 ? 10 : 0);
    	}
    	
    	@Override
    	public String toString() {
    		return nev + " " + osszPont();
    	}
    }
}