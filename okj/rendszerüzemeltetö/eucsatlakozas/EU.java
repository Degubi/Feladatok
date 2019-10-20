import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class EU {

	public static void main(String[] args) throws IOException {
		var csatlakozasok = new ArrayList<Csatlakozas>();
		var lines = Files.readAllLines(Path.of("EUcsatlakozas.txt"), StandardCharsets.ISO_8859_1);
		
		for(var line : lines) {
			csatlakozasok.add(new Csatlakozas(line));
		}
		
		var ketezertizennyolc = LocalDate.of(2018, 1, 1);
		var tagallamok2018 = 0;
		
		for(var csati : csatlakozasok) {
			if(csati.csatlakozas.isBefore(ketezertizennyolc)) {
				++tagallamok2018;
			}
		}
		System.out.println("3. Feladat: EU tagállamainak száma: " + tagallamok2018);
		
		var csatlakozott2007 = 0;
		for(var csati : csatlakozasok) {
			if(csati.csatlakozas.getYear() == 2007) {
				++csatlakozott2007;
			}
		}
		System.out.println("4. Feladat: 2007-ben " + csatlakozott2007 + " ország csatlakozott");
		
		LocalDate magyarorszag = null;
		for(var csati : csatlakozasok) {
			if(csati.orszag.equals("Magyarország")) {
				magyarorszag = csati.csatlakozas;
				break;
			}
		}
		System.out.println("5. Feladat: M.o. csatlakozása: " + magyarorszag);
		
		var voltEMajusban = false;
		for(var csati : csatlakozasok) {
			if(csati.csatlakozas.getMonth() == Month.MAY) {
				voltEMajusban = true;
				break;
			}
		}
		if(voltEMajusban) {
			System.out.println("6. Feladat: Volt májusban csatlakozás");
		}else{
			System.out.println("6. Feladat: Nem volt májusban csatlakozás");
		}
		
		var utolso = csatlakozasok.get(0);
		for(var csati : csatlakozasok) {
			if(csati.csatlakozas.isAfter(utolso.csatlakozas)) {
				utolso = csati;
			}
		}
		System.out.println("7. Feladat: Utoljára csatlakozott: " + utolso.orszag);
		System.out.println("8. Feladat:");
		
		var stat = new HashMap<Integer, Integer>();
		for(var csati : csatlakozasok) {
			var ev = csati.csatlakozas.getYear();
			
			stat.put(ev, stat.getOrDefault(ev, 0) + 1);
		}
		
		for(var entry : stat.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue() + " ország");
		}
	}
	
	public static class Csatlakozas{
		public final String orszag;
		public final LocalDate csatlakozas;
		
		public Csatlakozas(String line) {
			var split = line.split(";");
			
			orszag = split[0];
			csatlakozas = LocalDate.parse(split[1].replace('.', '-'));
		}
	}
}