import java.util.*;

public class DuoAttractorChecker {
    public static boolean hasDuoAttractor(String word, int position, int span) {
        //dovrebbe avere complessità O(n^2)
        int length = word.length();
        if (position < 0 || position >= length || position + span >= length) {
            System.out.println("Intervallo non valido.");
            return false;
        }
        String couple = new String();
        String attractorSubstring = word.substring(position - 1, position + span);
        for (int i = 0; i < length - 1; i++) {
            couple = word.substring(i, i+1);
                if (!attractorSubstring.contains(couple)) {
                    return false;
            }
        }
        return true;
    }

    public static boolean hasDuoAttractor2(String word, int position, int span)
    {
        int length = word.length();
        if (position < 0 || position >= length || position + span >= length) {
            System.out.println("Intervallo non valido.");
            return false;
        }
        Set<Character> alphabet = new HashSet<>();
        for(int i = 0; i < length - 1; i++)
            alphabet.add(word.charAt(i));
        //La seguente mappa associa i simboli dell'alfabeto ad un insieme che contiene tutte le posizioni in cui i simboli occorrono nella stringa data inizialmente in input
        Map<Character, List<Integer>> characterToCrossPositionsMap = new HashMap<>();
        //Riempio la mappa:
        for (Character symbol: alphabet)
        {
            List<Integer> crossPositions = new LinkedList<>();
            for(int i = 0; i < length - 1; i++)
            {
                if(word.charAt(i) == symbol)
                    crossPositions.add(i);
            }
            characterToCrossPositionsMap.put(symbol, crossPositions);
        }

        for (Character symbol: alphabet)
        {
            List<Integer> crossPositions = characterToCrossPositionsMap.get(symbol);
            List<Integer> tmpList = new LinkedList<>(); //lista che andiamo a riempire con le posizioni appartenenti a crossPositions che sono incluse tra p e position + span
            for(Integer n: crossPositions)
            {
                if(n >= position && n <= position + span - 1)
                        tmpList.add(n + 1);
            }

            for (Integer n: crossPositions)
            {
                if(n < position - 1 || n > position + span)
                {
                    if(checkIfIntersectIsEmpty(characterToCrossPositionsMap.get(word.charAt(n + 1)), tmpList))
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean checkIfIntersectIsEmpty(List<Integer> set1, List<Integer> set2)
    {
        /*
        for (Integer n: set1)
        {
            System.out.println(n);
        }
        */
        set1.retainAll(set2);
        return set1.isEmpty();
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
