import module java.base;

public class Torpedo {

    public static void main(String[] args) {
        var hajoKezdet = new Random().nextInt(17);
        var folyo = new int[20];
        folyo[hajoKezdet]     = 1;
        folyo[hajoKezdet + 1] = 1;
        folyo[hajoKezdet + 2] = 1;
        folyo[hajoKezdet + 3] = 1;

        while(true) {
            var lovesHelye = Integer.parseInt(System.console().readLine("Kérem a lövés helyét (1-20): ")) - 1;

            if(folyo[lovesHelye] == 0) {
                System.out.println("Nem talált");
            }else{
                System.out.println("Talált");
                folyo[lovesHelye] = 2;
            }

            if(folyo[hajoKezdet] == 2 && folyo[hajoKezdet + 1] == 2 && folyo[hajoKezdet + 2] == 2 && folyo[hajoKezdet + 3] == 2) {
                System.out.println("Süllyedt");
                break;
            }
        }
    }
}