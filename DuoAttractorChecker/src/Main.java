import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String word = "abcabc";
        int position = 0;
        int span = 1;
        boolean result = DuoAttractorChecker.hasDuoAttractor2(word, position, span);

/*
        Set<Integer> positions = new HashSet<>();
        positions  = DuoAttractorChecker.getDuoAttractorsPositions(word, span);
        for (Integer p:
              positions) {
            System.out.print(p);
            System.out.print(", ");
        }
*/
        System.out.println("Esiste un duo-attrattore: " + result);
    }

}