import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Kektura_stream{
	public static void main(String[] args) throws IOException{
		var lines = Files.readAllLines(Paths.get("kektura.csv"));
		var szint = Integer.parseInt(lines.get(0));
		var szakaszok = lines.stream()
							 .skip(1)
							 .map(Szakasz::new)
							 .toArray(Szakasz[]::new);
		
		System.out.println("3.Feladat: Szakaszok száma: " + szakaszok.length + " db");
		System.out.println("4.Feladat: Teljes hossz: " + Arrays.stream(szakaszok).mapToDouble(k -> k.hossz).sum() + " km");
		
		Arrays.stream(szakaszok)
			  .min(Comparator.comparingDouble(k -> k.hossz))
			  .ifPresent(k -> System.out.println("5.Feladat: Legrövidebb szakasz adatai: " + k));
		
		System.out.println("7.Feladat: Hiányos állomásnevek:");
		var hianyosok = Arrays.stream(szakaszok).filter(Szakasz::hianyosNev).toArray(Szakasz[]::new);
		
		if(hianyosok.length == 0) {
			System.out.println("Nincs hiányos állomásnév");
		}else{
			System.out.println(Stream.of(hianyosok).map(k -> k.vegPont).collect(Collectors.joining("\n")));
		}
		
		Arrays.stream(szakaszok)
			  .max(Comparator.comparingInt(k -> k.magasraa(szint)))
			  .ifPresent(k -> System.out.printf("8.Feladat: A túra legmagasabban pont neve: %s, magassága: %dm\n", k.vegPont, k.magasraa(szint)));
		
		var fileba = szint + "\n" + Arrays.stream(szakaszok)
										  .map(szakasz -> szakasz + (szakasz.hianyosNev() ? " pecsetelohely" : ""))
										  .collect(Collectors.joining("\n"));
		
		Files.writeString(Path.of("kektura2.csv"), fileba);
	}
	
	public static class Szakasz{
		public final String induloPont, vegPont;
		public final int emelkedesek, lejtesek;
		public final double hossz;
		public final boolean pecsetelo;
		
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