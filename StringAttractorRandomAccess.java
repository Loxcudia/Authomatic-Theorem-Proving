import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringAttractorRandomAccess {
    private String T;
    private Set<Integer> Γ; //attrattore
    private int τ; //numero di blocchi a ciascun livello
    private int w; //dimensione in bit di una parola in memoria
    private int n; //lunghezza della stringa
    private int γ; //dimensione dell'attrattore
    private int i_star; //livello massimo della struttura dati
    private int σ; //dimensione alfabeto della stringa T
    private List<List<Block>> structure;

    public StringAttractorRandomAccess(String T, Set<Integer> Γ, int τ, int w) {
        this.T = T;
        this.Γ = Γ;
        this.τ = τ;
        this.w = w;
        this.n = T.length();
        this.γ = Γ.size();
        this.i_star = calculateIStar();
        this.structure = buildStructure();
        //il seguente pezzetto di codice calcola la dimensione dell'alfabeto della stringa T
        Set<Character> distinctCharacters = new HashSet<>();
        for(int index = 0; index < this.n; index++)
        {
            distinctCharacters.add(T.charAt(index));
        }
        this.σ = distinctCharacters.size();
    }
    public static int log2(int n)
    {
        int result = (int)(Math.log(n) / Math.log(2));
        return result;
    }

    private int calculateIStar() {
        int i = 0;
        while (Math.pow(τ, i + 1) <= n / γ && n / (γ * Math.pow(τ, i + 1)) >= 2 * w / log2(σ)) {
            i++;
        }
        return i;
    }

    private List<List<Block>> buildStructure() {
        List<List<Block>> structure = new ArrayList<>();

        // Livello 0
        List<Block> level0 = new ArrayList<>();
        for (int i = 0; i < γ; i++) {
            level0.add(new Block(i * n / γ, (i + 1) * n / γ - 1, -1, -1));
        }
        structure.add(level0);

        // Livelli da 1 a i∗
        for (int i = 1; i <= i_star; i++) {
            int si = (int) (n / (γ * Math.pow(τ, i - 1)));
            List<Block> levelI = new ArrayList<>();
            for (int j : Γ) {
                // Crea 2τ blocchi non sovrapposti che fiancheggiano j
                for (int k = 1; k <= τ; k++) {
                    //Gestione dei limiti
                    int start = Math.max(0, j - si + 1 - si * k);
                    int end = Math.min(n - 1, j - si * (k - 1) - 1);
                    levelI.add(new Block(start, end, -1, -1));

                    start = Math.max(0, j + si * (k - 1));
                    end = Math.min(n - 1, j + si * k - 1);
                    levelI.add(new Block(start, end, -1, -1));
                }

                // Crea 2τ-1 blocchi aggiuntivi consecutivi e non sovrapposti
                for (int k = 1; k <= τ; k++) {
                    //Gestione dei limiti
                    int start = Math.max(0, j - si + 1 - si * k + si / 2);
                    int end = Math.min(n - 1, j - si * (k - 1) - 1 + si / 2);
                    levelI.add(new Block(start, end, -1, -1));
                }
                for (int k = 1; k <= τ - 1; k++) {
                    //Gestione dei limiti
                    int start = Math.max(0, j + si * (k - 1) + si / 2);
                    int end = Math.min(n - 1, j + si * k - 1 + si / 2);
                    levelI.add(new Block(start, end, -1, -1));
                }
            }

            // Associa ogni blocco a un'occorrenza al livello i+1 che attraversa un elemento in Γ
            for (Block block : levelI) {
                //Ricerca dell'associazione
                boolean found = false;
                for (int k = block.start; k <= block.end && !found; k++) {
                    for (int j : Γ) {
                        if (k == j) {
                            block.off = j - block.start;
                            block.j = j;
                            found = true;
                            break;
                        }
                    }
                }
                // Gestione del caso in cui non si trova un'associazione
                if (!found) {
                    //Ho scelto di gestire questo caso semplicemente sollevando un eccezione e dunque di terminare l'esecuzione
                    throw new RuntimeException();
                }
            }
            structure.add(levelI);
        }
        return structure;
    }

    public String extractSubstring(int i, int ℓ) {
        int α = (int) (w * Math.log(n / γ) / Math.log(T.length()));

        // Caso in cui la sottostringa attraversa più blocchi al livello 0
        if (i + ℓ - 1 > ((i / (n / γ)) + 1) * (n / γ)) {
            int i1 = i;
            int ℓ1 = ((i / (n / γ)) + 1) * (n / γ) - i1 + 1;
            String S1 = extractSubstring(i1, ℓ1);
            int i2 = ((i / (n / γ)) + 1) * (n / γ) + 1;
            int ℓ2 = ℓ - ℓ1;
            String S2 = extractSubstring(i2, ℓ2);
            return S1 + S2;
        }

        // Trovo il blocco al livello 0 che contiene la sottostringa
        Block currentBlock = structure.get(0).get(i / (n / γ));
        int currentOffset = i % (n / γ);

        // Mappo la sottostringa ai livelli inferiori
        for (int level = 1; level <= i_star; level++) {
            // Trovo il blocco al livello corrente che contiene la sottostringa
            int blockIndex = currentOffset / (int) (n / (γ * Math.pow(τ, level)));
            currentBlock = structure.get(level).get(blockIndex);

            // Aggiorno l'offset
            currentOffset = currentOffset % (int) (n / (γ * Math.pow(τ, level)));
        }

        // Estraggo la sottostringa dal blocco al livello i∗
        int beginIndex = currentBlock.start + currentOffset;
        int endIndex = Math.min(T.length(), beginIndex + ℓ);
        return T.substring(beginIndex, endIndex);
    }

    private static class Block {
        int start;
        int end;
        int off;
        int j;

        public Block(int start, int end, int off, int j) {
            this.start = start;
            this.end = end;
            this.off = off;
            this.j = j;
        }
    }
}
