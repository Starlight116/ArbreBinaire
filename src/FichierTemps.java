import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

public class FichierTemps {
	private String nom;
	private int[] tableau;
	private static final int NBREMPLISSAGES = 10000;
	private static final String DELIMITER = ",";
    private static final String SEPARATOR = "\n";

    public FichierTemps(String n, int[] t) {
    	nom = n;
    	tableau = t;
    }
    
    public void creerFichier(){
    	try {
			FileWriter file = new FileWriter(nom);
			long startTime,elapsedTime,temps;
			file.append("type, taille, temps de remplissage min to max,temps de remplissage aléatoire");
			file.append(SEPARATOR);
			
			
				for (int j = 0; j<NBREMPLISSAGES;j++) {
					file.append("ABR");
					file.append(DELIMITER+tableau.length);
					startTime = System.currentTimeMillis();
					remplissageABR(tableau);
		            elapsedTime = System.currentTimeMillis();
		            
		            temps = elapsedTime - startTime;
		            file.append(DELIMITER+temps);
		            
		            int[] tab =mélanger(tableau);
		            startTime = System.currentTimeMillis();
		            remplissageABR(tab);
		            elapsedTime = System.currentTimeMillis();
		            
		            temps = elapsedTime - startTime;
		            file.append(DELIMITER+temps);
		            
		            file.append(SEPARATOR);
		            
		           
				}
				for (int j = 0; j<NBREMPLISSAGES;j++) {
					
					file.append("ANR");
					file.append(DELIMITER+tableau.length);
					
					startTime = System.currentTimeMillis();
					remplissageANR(tableau);
		            elapsedTime = System.currentTimeMillis();
		            
		            temps = elapsedTime - startTime;
		            file.append(DELIMITER+temps);
		            
		            int[] tab =mélanger(tableau);
		            startTime = System.currentTimeMillis();
		            remplissageANR(tab);
		            elapsedTime = System.currentTimeMillis();
		            
		            temps = elapsedTime - startTime;
		            file.append(DELIMITER+temps);
		            
		            file.append(SEPARATOR);
		            
		           
				}
				file.close();
				System.out.println("Fichier " +nom+" créé");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    public void remplissageABR(int[] list) {
    	ABR<Integer> a = new ABR();
    	for (int i=0; i<list.length; i++) {
    		a.add(list[i]);
    	}
    }
    
    public void remplissageANR(int[] list) {
    	ANR<Integer> a = new ANR();
    	for (int i=0; i<list.length; i++) {
    		a.add(list[i]);
    	}

    }
    
    public int[] mélanger(int[] list) {
    	Random rand = new Random();
    	int[] newlist = new int[list.length];
    	for (int i = 0; i<list.length; i++) {
    		newlist[i]=list[i];
    	}
    	
        for (int i = newlist.length - 1; i > 0; i--) {
            int indexAleatoire = rand.nextInt(i + 1);
            int temp = newlist[i];
            newlist[i] = newlist[indexAleatoire];
            newlist[indexAleatoire] = temp;
        }
        return newlist;
    }
}
