import java.io.*;
import java.nio.file.*;
import java.util.*;

public class KarakterDekodolo {

    public static void main(String[] args) throws IOException {
        var bankKarakterek = readKarakterFile("bank.txt");

        System.out.println("5. Feladat: Karakterek száma: " + bankKarakterek.size());

        var bekertBetu = betutBeker();
        var bekerthezKarakter = (Karakter) null;

        for(var bankKarakter : bankKarakterek) {
            if(bankKarakter.karakter.equals(bekertBetu)) {
                bekerthezKarakter = bankKarakter;
                break;
            }
        }

        if(bekerthezKarakter == null) {
            System.out.println("7. Feladat: Nincs ilyen karakter a bankban!");
        }else{
            System.out.println("7. Feladat: \n" + bekerthezKarakter.formatMatrix());
        }

        System.out.print("9. Feladat: ");

        for(var dekodolKarakter : readKarakterFile("dekodol.txt")) {
            var kiirando = "?";

            for(var bankKarakter : bankKarakterek) {
                if(dekodolKarakter.felismer(bankKarakter)) {
                    kiirando = bankKarakter.karakter;
                    break;
                }
            }

            System.out.print(kiirando);
        }
    }


    private static String betutBeker() {
        try(var console = new Scanner(System.in)) {
            while(true) {
                System.out.print("6. Feladat: Kérek egy angol nagybetűt!");

                var bekertBetu = console.nextLine();
                if(bekertBetu.length() == 1 && Character.isUpperCase(bekertBetu.charAt(0))) {
                    return bekertBetu;
                }
            }
        }
    }

    private static ArrayList<Karakter> readKarakterFile(String filePath) throws IOException {
        var lines = Files.readAllLines(Path.of(filePath));
        var karakterLineIndices = new ArrayList<Integer>();

        for(var i = 0; i < lines.size(); ++i) {
            if(lines.get(i).length() == 1) {
                karakterLineIndices.add(i);
            }
        }

        var result = new ArrayList<Karakter>();
        for(var i = 0; i < karakterLineIndices.size(); ++i) {
            var karakterLineIndex = karakterLineIndices.get(i).intValue();
            var matrixEndIndex = i == karakterLineIndices.size() - 1 ? lines.size() : karakterLineIndices.get(i + 1);

            result.add(new Karakter(lines.get(karakterLineIndex), lines.subList(karakterLineIndex + 1, matrixEndIndex)));
        }

        return result;
    }
}