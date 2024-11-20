import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String word = "abcdabfecdfe";
        int position = 4;
        int span = 2;
        Set<Integer> positions = new HashSet<>();
        boolean result = DuoAttractorChecker.hasDuoAttractor(word, position, span);
        positions  = DuoAttractorChecker.getDuoAttractorsPositions(word, span);
        for (Integer p:
              positions) {
            System.out.print(p);
            System.out.print(", ");
        }
        System.out.println("Esiste un duo-attrattore: " + result);
    }

}