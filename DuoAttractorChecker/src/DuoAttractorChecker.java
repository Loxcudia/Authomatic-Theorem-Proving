public class DuoAttractorChecker {
    public static boolean hasDuoAttractor(String word, int position, int span) {
        int length = word.length();

        if (position < 0 || position >= length || position + span >= length) {
            System.out.println("Intervallo non valido.");
            return false;
        }

        int attractor1 = position;
        int attractor2 = position + span;

        for (int i = 0; i < length - 1; i++) {
                if (!crossesAttractors( i, i+1, attractor1, attractor2)) {
                    System.out.println(i);
                    return false;
                }
            }
        return true;
    }
    private static boolean crossesAttractors(int start, int end, int attractor1, int attractor2) {
        return (start <= attractor1 && attractor1 <= end) || (start <= attractor2 && attractor2 <= end);
    }
}
