import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DuoAttractorChecker {
    public static boolean hasDuoAttractor(String word, int position, int span) {
        int length = word.length();
        String couple = new String();
        if (position < 0 || position >= length || position + span >= length) {
            System.out.println("Intervallo non valido.");
            return false;
        }
        for (int i = 0; i < length - 1; i++) {
            couple = word.substring(i, i+1);
                if (!crossesAttractors( word, couple, position, span)) {
                    return false;
            }
        }
        return true;
    }

    private static boolean crossesAttractors(String word, String couple, int position, int span) {
        for(int i = position - 1; i <= position + span; i++)
        {
            if(couple.equals(word.substring(i, i+1)))
                return true;
        }
        return false;
    }

    public static Set<Integer> getDuoAttractorsPositions(String word, int span)
    {
        int lenght = word.length();
        Set<Integer> positions = new HashSet<>();
        for(int p = 1; p < lenght - 1; p++)
        {
            if(p + span > lenght)
                return positions;
            if(hasDuoAttractor(word, p, span))
                positions.add(p);
        }
        return positions;
    }
}
