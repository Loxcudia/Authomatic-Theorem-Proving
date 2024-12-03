import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String word = "abcabc";
        int position = 1;
        int span = 2;
        boolean result = DuoAttractorChecker.hasDuoAttractor(word, position, span);


        Set<Integer> positions = new HashSet<>();
        positions  = DuoAttractorChecker.getDuoAttractorsPositions(word, span);
        for (Integer p:
              positions) {
            System.out.print(p);
            System.out.print(", ");
        }

        System.out.println("Esiste un duo-attrattore: " + result);
    }

}