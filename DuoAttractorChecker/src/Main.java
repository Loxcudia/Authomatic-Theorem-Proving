
public class Main {
    public static void main(String[] args) {
        String word = "aabba";
        int position = 1;
        int span = 1;

        boolean result = DuoAttractorChecker.hasDuoAttractor(word, position, span);
        System.out.println("Esiste un duo-attrattore: " + result);
    }

}