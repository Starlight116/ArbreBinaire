import java.util.stream.IntStream;

public class Main {

	public static void main(String[] args) {
		int[][] tableau = new int[10][];
		
		tableau[0] =  IntStream.range(0, 10).toArray();
		tableau[1] = IntStream.range(0, 50).toArray();
		tableau[2] = IntStream.range(0, 100).toArray();
		tableau[3] = IntStream.range(0, 500).toArray();
		tableau[4] = IntStream.range(0, 1000).toArray();
		tableau[5] = IntStream.range(0, 5000).toArray();
		tableau[6] = IntStream.range(0, 10000).toArray();
		tableau[7] = IntStream.range(0, 50000).toArray();
		tableau[8] = IntStream.range(0, 100000).toArray();
		tableau[9] = IntStream.range(0, 500000).toArray();
		
		FichierTemps t = new FichierTemps("fichier10.csv",tableau[0]);
		t.creerFichier();
		t = new FichierTemps("fichier50.csv",tableau[1]);
		t.creerFichier();
		t = new FichierTemps("fichier100.csv",tableau[2]);
		t.creerFichier();
		t = new FichierTemps("fichier500.csv",tableau[3]);
		t.creerFichier();
		t = new FichierTemps("fichier1000.csv",tableau[4]);
		t.creerFichier();
		t = new FichierTemps("fichier5000.csv",tableau[5]);
		t.creerFichier();
		t = new FichierTemps("fichier10000.csv",tableau[6]);
		t.creerFichier();
		

	}

}
