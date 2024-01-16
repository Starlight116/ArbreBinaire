import java.util.Random;
import java.util.stream.IntStream;

public class MainTest {

	public static void main(String[] args) {
		int[] tableau = {1,2,3,4,5};
		ABR<Integer> arbre= new ABR();
		ANR<Integer> arbreRN= new ANR();
		
		for(int i =0; i<5; i++) {
			arbre.add(tableau[i]);
			arbreRN.add(tableau[i]);
		}
		System.out.println("ARBRE BINAIRE DE RECHERCHE");
		System.out.print(arbre);
		System.out.println("ARBRE ROUGE NOIR");
		System.out.println(arbreRN);
		
		Random rand = new Random();
		int temp =0;
        for (int i = 5 - 1; i > 0; i--) {
            int indexAleatoire = rand.nextInt(i + 1);
            temp = tableau[i];
            tableau[i] = tableau[indexAleatoire];
            tableau[indexAleatoire] = temp;
        }
        arbre= new ABR();
        arbreRN = new ANR();
        
        for(int i =0; i<5; i++) {
			arbre.add(tableau[i]);
			arbreRN.add(tableau[i]);
		}
        
		System.out.println("ARBRE BINAIRE DE RECHERCHE");
		System.out.print(arbre);
		System.out.println("ARBRE ROUGE NOIR");
		System.out.println(arbreRN);
	}

}
