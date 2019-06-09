import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Kektura{
	public static void main(String[] args) throws IOException{
		var lines = Files.readAllLines(Paths.get("kektura.csv"));
		
		int szint = Integer.parseInt(lines.get(0));
		var szakaszok = lines.stream()
							 .skip(1)
							 .map(Szakasz::new)
							 .collect(Collectors.toList());
		
		System.out.println("3.Feladat: Szakaszok száma: " + szakaszok.size() + " db");
		System.out.println("4.Feladat: Teljes hossz: " + szakaszok.stream().mapToDouble(k -> k.hossz).sum() + " km");
		
		szakaszok.stream().min(Comparator.comparingDouble(k -> k.hossz))
						  .ifPresent(k -> System.out.println("5.Feladat: Legrövidebb szakasz adatai: " + k));
		
		System.out.println("7.Feladat: Hiányos állomásnevek:");
		var hianyosok = szakaszok.stream().filter(Szakasz::hianyosNev).toArray(Szakasz[]::new);
		
		if(hianyosok.length == 0) {
			System.out.println("Nincs hiányos állomásnév");
		}else{
			System.out.println(Stream.of(hianyosok).map(k -> k.vegPont).collect(Collectors.joining("\n")));
		}
		
		szakaszok.stream()
				 .max(Comparator.comparingInt(k -> k.magasraa(szint)))
				 .ifPresent(k -> System.out.println("8.Feladat: A túra legmagasabban fekvõ pontja: " + "Végpont neve: " 
						 							+ k.vegPont + ", magasság: " + k.magasraa(szint) + "m"));
		
		try(var output = new PrintWriter("kektura2.csv")){
			output.println(szint);
			szakaszok.forEach(szakasz -> output.println(szakasz + (szakasz.hianyosNev() ? " pecsetelohely" : "")));
		}
	}
	
	static class Szakasz{
		String induloPont, vegPont;
		int emelkedesek, lejtesek;
		double hossz;
		boolean pecsetelo;
		
		public Szakasz(String data) {
			var split = data.split(";");
			
			induloPont = split[0];
			vegPont = split[1];
			hossz = Double.parseDouble(split[2].replace(',', '.'));
			emelkedesek = Integer.parseInt(split[3]);
			lejtesek = Integer.parseInt(split[4]);
			pecsetelo = split[5].charAt(0) == 'i';
		}
		
		public boolean hianyosNev() {
			return pecsetelo && !vegPont.contains("pecsetelohely");
		}
		
		public int magasraa(int alap) {
			return alap + (emelkedesek - lejtesek);
		}
		
		@Override
		public String toString() {
			return "Kezdet: " + induloPont + ", vég: " + vegPont + ", távolság: " + hossz + " km";
		}
	}
}