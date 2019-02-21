import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class Txt2Srt{
	
	public static void main(String[] args) throws IOException{
		var feliratok = new ArrayList<IdozitettFelirat>();
		var adatok = Files.readAllLines(Paths.get("feliratok.txt"));
		
		for(int i = 0; i < adatok.size(); i += 2){
			feliratok.add(new IdozitettFelirat(adatok.get(i), adatok.get(i + 1)));
        }

		System.out.println("5. feladat: A feliratok száma: " + feliratok.size());
		
		System.out.println("7. feladat: A legtöbb szóból álló felirat:\n" 
								+ feliratok.stream().max(Comparator.comparingInt(k -> k.szavakSzama())).get().felirat);
		
		try(var output = new PrintWriter("felirat.srt")) {
			for(int i = 0; i < feliratok.size(); ++i){
				output.println(i + 1);
				output.println(feliratok.get(i).strIdozites());
				output.println(feliratok.get(i).felirat);
				output.println();
			}
        }
	}
	
    static class IdozitettFelirat{
        String idozites;
        String felirat;

        public IdozitettFelirat(String idoz, String felir){
            idozites = idoz;
            felirat = felir;
        }

        public int szavakSzama(){
        	return felirat.chars().filter(k -> k == ' ').map(k -> 1).sum() + 1;
        }
        
        private static String str_idoforma(String ido){
            return LocalTime.parse(ido, DateTimeFormatter.ISO_LOCAL_TIME.withResolverStyle(ResolverStyle.LENIENT))
            			    .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        public String strIdozites(){
        	String[] split = idozites.split(" - ");
            return str_idoforma("00:" + split[0]) + "--> " + str_idoforma("00:" + split[1]);
        }
    }
}