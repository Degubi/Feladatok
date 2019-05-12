import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Cimek_lambda{
	
	public static void main(String[] args) throws IOException{
		var lines = Files.readAllLines(Paths.get("ip.txt"));
		
		System.out.println("Adatsorok száma: " + lines.size());
		System.out.println("Legkisebb ip cím: " + lines.stream().min(Comparator.naturalOrder()).get());
		System.out.println("Dokumentációs címek: " + lines.stream().filter(k -> k.startsWith("2001:0db8")).count());
		System.out.println("Globális címek: " + lines.stream().filter(k -> k.startsWith("2001:0e")).count());
		System.out.println("Helyi egyedi címek: " + lines.stream().filter(k -> k.startsWith("fc") || k.startsWith("fd")).count());
		
		Files.write(Paths.get("sok.txt"), 
					lines.stream().filter(k -> k.chars().filter(l -> l == '0').count() > 17)
								  .map(k -> lines.indexOf(k) + 1 + " " + k)
								  .collect(Collectors.toList()));
		
		System.out.println("Írj be 1 sorszámot!");
		try(var input = new Scanner(System.in)){
			int index = input.nextInt() - 1;
			System.out.println(lines.get(index) + " (Eredeti)");
			String roviditett = rov1(lines.get(index));
			System.out.println(roviditett + " (1. Rövidítés)");
			System.out.println(rov2(roviditett));
		}
	}
	
	public static String rov1(String toRov) {
		return toRov.replace(":0", ":").replace(":0", ":").replace(":0", ":");
	}
	
	public static String rov2(String toRov) {
		var formatted = toRov.replace(":0:0:0:", "::").replace(":0:0:", "::");
		return toRov.equals(formatted) ? "Nem lehet egyszerûsíteni" : formatted + " (2. Rövidítés)";
	}
}