import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DuoAttractorChecker {
    public static boolean hasDuoAttractor(String word, int position, int span) {
        int length = word.length();
        if (position < 0 || position >= length || position + span >= length) {
            System.out.println("Intervallo non valido.");
            return false;
        }
        for (int i = 0; i < length - 1; i++) {

                if (!crossesAttractors( word, i, i+1, position, span)) {
                    return false;
            }
        }
        return true;
    }

    public static Set<Integer> getDuoAttractorsPositions(String word, int span)
    {
        int lenght = word.length();
        Set<Integer> positions = new HashSet<>();
        for(int p = 0; p < lenght - 1; p++)
        {
            if(p + span > lenght)
                return positions;
            if(hasDuoAttractor(word, p, span))
                positions.add(p);
        }
        return positions;
    }

    private static boolean crossesAttractors(String word, int start, int end, int position, int span) {
        int count = 0;
        for(int i = position; i <= position + span; i++)
        {
            if(word.charAt(i) != word.charAt(start) && word.charAt(i) != word.charAt(end))
            {
                if(count < span + 1)
                    count++;
                if(count == span + 1)
                    return false;
            }
        }
        return true;
    }
}
