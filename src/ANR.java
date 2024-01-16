import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;




public class ANR<E> extends AbstractCollection<E>{
	private Noeud racine;
	private final Noeud sentinelle = new Noeud();
	private int taille;
	private Comparator<? super E> cmp;
	
	private class Noeud{
		private E cle;
		private Noeud pere;
		private Noeud gauche;
		private Noeud droit;
		private char couleur;

		Noeud (){
			cle = null;
			gauche = droit = pere = null;
			couleur = 'N';
		}
		
		Noeud(E cle) {
			this.cle = cle;
			gauche = sentinelle;
			droit = sentinelle;
			pere = null;
			couleur = 'R';
		}
		
		public Noeud minimum() {
			Noeud courant = this;
    		while (courant.gauche != sentinelle) {
        		courant = courant.gauche;
    		}
    		return courant;
		}
		
		public Noeud suivant() {
			if (droit != sentinelle) return droit.minimum();
			else {
				Noeud p = pere;
				Noeud courant = this;
				while (p != sentinelle && courant == p.droit) {
					courant = p;
					p = p.pere;
				}
				return p;
			}
		}
		
		public char getCouleur() {
			return couleur;
		}
		
		public E getCle() {return cle;}

		public Noeud getGauche() {
			return gauche;
		}

		public Noeud getDroite() {
			return droit;
		}

		public Noeud getPere() {
			return pere;
		}

		public void setPere(Noeud pere2) {
			this.pere = pere2;
			
		}

		public void setGauche(Noeud x) {
			this.gauche = x;
			
		}

		public void setDroite(Noeud x) {
			this.droit = x;
			
		}
		
		private void setCouleur(char c)  {
			if(c=='N' || c=='R') couleur = c;
			else System.out.println("Cette couleur n'est pas autorisée");
		}
		
	}
	
	public ANR() {
		racine = sentinelle;
		taille = 0;
		cmp = ((e1,e2) -> ((Comparable<E>) e1).compareTo(e2));
	}
	public ANR(Comparator<E> cmp) {
		racine = sentinelle;
		taille = 0;
		this.cmp = cmp;
	}
	
	public ANR(Collection<? extends E> c) {
		this();
		this.addAll(c);
	}
	
	
	@Override
	public Iterator<E> iterator() {
		return new ANRIterator();
	}
	
	@Override
	public int size() {
		return taille;
	}
	
	private Noeud rechercher(Object o) {
		if(o == null){
			return null;
		}
		
		Noeud courant = racine;
		while(courant != sentinelle){
			int comparateur = cmp.compare((E) o, courant.getCle());
			if(comparateur==0)return courant;
			else if (comparateur > 0) courant = courant.getDroite();
			else if (comparateur < 0) courant = courant.getGauche();
		}
		return courant;
	}
	
	private Noeud supprimer(Noeud z)  {
		Noeud y; // nœud à détacher

	    if (z.getGauche() == sentinelle || z.getDroite() == sentinelle) {
	        y = z;
	    } else {
	        y = z.suivant();
	    }

	    // x est le fils unique de y ou null si y n'a pas de fils
	    Noeud x = (y.getGauche() != sentinelle) ? y.getGauche() : y.getDroite();

	    
	    x.setPere(y.getPere());
	    

	    if (y.getPere() == null) { // suppression de la racine
	        racine = x;
	    } else { 
	        if (y == y.getPere().getGauche()) {
	            y.getPere().setGauche(x);
	        } else {
	            y.getPere().setDroite(x);
	        }
	    }

	    if (y != z) {
	        z.cle = y.cle;
	    }
	    if(y.getCouleur()=='N') corrigerSuppr(x);

	    return z.suivant();
	}
	
	private void corrigerSuppr(Noeud x)  {
		while(x!= racine && x.getCouleur()=='N') {
			if (x == x.getPere().getGauche()) {
			      Noeud w = x.getPere().getDroite(); // le frère de x
			      if (w.getCouleur() == 'R') {
			        // cas 1
			        w.setCouleur('N');
			        x.getPere().setCouleur('R');
			        rotationGauche(x.getPere());
			        w = x.getPere().getDroite();
			      }
			      if (w.getGauche().getCouleur() == 'N' && w.getDroite().getCouleur() == 'N') {
			        // cas 2
			        w.setCouleur('R');
			        x = x.getPere();
			      } else {
			        if (w.getDroite().getCouleur() == 'N') {
			          // cas 3
			          w.getGauche().setCouleur('N');
			          w.setCouleur('R');
			          rotationDroite(w);
			          w = x.getPere().getDroite();
			        }
			        // cas 4
			        w.setCouleur(x.getPere().getCouleur());
			        x.getPere().setCouleur('N');
			        w.getDroite().setCouleur('N');
			        rotationGauche(x.getPere());
			        x = racine;
			      }
			    } else {
			    	Noeud w = x.getPere().getGauche(); // le frère de x
				      if (w.getCouleur() == 'R') {
				        // cas 1
				        w.setCouleur('N');
				        x.getPere().setCouleur('R');
				        rotationDroite(x.getPere());
				        w = x.getPere().getGauche();
				      }
				      if (w.getGauche().getCouleur() == 'N' && w.getDroite().getCouleur() == 'N') {
				        // cas 2
				        w.setCouleur('R');
				        x = x.getPere();
				      } else {
				        if (w.getGauche().getCouleur() == 'N') {
				          // cas 3
				          w.getDroite().setCouleur('N');
				          w.setCouleur('R');
				          rotationGauche(w);
				          w = x.getPere().getGauche();
				        }
				        // cas 4
				        w.setCouleur(x.getPere().getCouleur());
				        x.getPere().setCouleur('N');
				        w.getGauche().setCouleur('N');
				        rotationDroite(x.getPere());
				        x = racine;
			     }
		    }
		}
		x.setCouleur('N');
		taille++;
	}
	
	@Override
	public boolean add(E e) {
		if (e == null) {
			System.out.println("Ajout impossible, clé nulle");
			return false;
		}
		Noeud z = new Noeud(e);
		Noeud y = sentinelle;
		Noeud x = racine;
		
		while(x!=sentinelle) {
			y=x;
			x=(cmp.compare(z.getCle(), x.getCle())<0) ? x.getGauche() : x.getDroite();
		}
		z.setPere(y);
		if(y==sentinelle) {
			racine = z;
		}else {
			if(cmp.compare(z.getCle(),y.getCle())<0) {
				y.setGauche(z);
			}else {
				y.setDroite(z);
			}
		}	
		z.setGauche(sentinelle);
		z.setDroite(sentinelle);
		corrigerAdd(z);
		return true;
	}
	
	private void corrigerAdd(Noeud z)  {
		//comme le père de la racine est fixée a null, ça ne sert a rien de regarder si son père est rouge, donc on ne rentre pas dans la boucle.
		//On en sortira également si jamais en remontant dans l'arbre on se retrouve sur la racine
		//Et la racine est toujours remise a noir après la boucle.
		
		while(z!=racine && z.getPere().getCouleur()=='R') {
			
			//Si le père de z est le fils gauche de son père.
			if(z.getPere()==z.getPere().getPere().getGauche()) {
				//y est l'oncle de z, on prend le fils droit du grand père de z
				Noeud y = z.getPere().getPere().getDroite(); 
				
				if(y.getCouleur()=='R') {
					//cas 1 l'oncle de z est ROUGE, 
					//on doit mettre z, son père et son oncle en noir
					//et mettre le grand père de z en rouge pour continuer a respecter le nombre de noeuds noirs.
					
					z.getPere().setCouleur('N'); //le père de Z est remis en noir
					y.setCouleur('N'); // l'oncle de z est remis en noir
					z.getPere().getPere().setCouleur('R'); // le grand père de z est mis en rouge
					//ici on a bien corrigé pour avoir 2 noeuds noirs sous un noeud rouge !
					
					z=z.getPere().getPere();
					//on remonte le noeud courant au grand père de z
					//qui est rouge et on revérifiera au prochain passage de boucle si son père existe et n'est pas rouge 
				} else {
					if(z==z.getPere().getDroite()) {
						//cas 2 si l'oncle de z est noir et z est le fils droit de son père
						//on remonte sur le père et on fait une rotation gauche
						z=z.getPere();
						rotationGauche(z);
						//z devient le fils gauche et on arrive au cas 3
					}
					//cas 3 : z est le fils gauche et son oncle est noir
					z.getPere().setCouleur('N');
					z.getPere().getPere().setCouleur('R');
					rotationDroite(z.getPere().getPere());
				}
			}else {
				//le père de Z est le fils droit de son père
				//on fait les même 3 cas mais en miroir.
				Noeud y = z.getPere().getPere().getGauche();
				//l'oncle de z est a gauche
				if(y.couleur=='R') {
					//cas 1 en mirroir
					z.getPere().setCouleur('N');
					y.setCouleur('N');
					z.getPere().getPere().setCouleur('R');
					z = z.getPere().getPere();
				}else {
					if(z==z.getPere().getGauche()) {
						//cas 2
						z=z.getPere();
						rotationDroite(z);
					}
					//cas 3
					z.getPere().setCouleur('N');
					z.getPere().getPere().setCouleur('R');
					rotationGauche(z.getPere().getPere());
				}				
			}
		}
		racine.setCouleur('N');
		
	}
	private void rotationGauche(Noeud x) {
		Noeud y = x.getDroite();
		x.setDroite(y.getGauche());
		if(y.getGauche()!=sentinelle) {
			y.getGauche().setPere(x);
		}
		y.setPere(x.getPere());
		if(x.getPere()==sentinelle) {
			racine = y;
		}else {
			if(x==x.getPere().getGauche()) {
				x.getPere().setGauche(y);
			}else {
				x.getPere().setDroite(y);
			}
		}
		y.setGauche(x);
		x.setPere(y);

	}
	
	public void rotationDroite(Noeud y) {
		Noeud x = y.getGauche();
		y.setGauche(x.getDroite());
		if(x.getDroite()!=sentinelle) {
			x.getDroite().setPere(y);
		}
		x.setPere(y.getPere());
		if(y.getPere()==sentinelle) {
			racine = x;
		}else {
			if(y==y.getPere().getDroite()) {
				y.getPere().setDroite(x);
			}else {
				y.getPere().setGauche(x);
			}
		}
		x.setDroite(y);
		y.setPere(x);
	}
	
	private class ANRIterator implements Iterator<E>{
		private Noeud courant;
		private Noeud precedent;
		
		public ANRIterator() {
			this.precedent=sentinelle;
        // Initialise le prochain nœud avec le nœud contenant la clé minimale
        	this.courant = (racine==sentinelle)?sentinelle:racine.minimum();
    	}
			
		@Override
		public boolean hasNext() {
			return courant != sentinelle;
		}
		
		@Override
		public E next() {
			
			if (courant==sentinelle) {
            	throw new NoSuchElementException("Aucun élément suivant");
			}
			precedent = courant;
			courant = courant.suivant(); // Utilise la méthode suivant de la classe Noeud
			
			return precedent.cle;
		}

		public void remove() {
    		if (precedent == null) {
				throw new IllegalStateException("Aucun élément à supprimer");
			}
			try {
				courant = supprimer(precedent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			precedent = null;
		}
		
	}
	
	@Override
	public String toString() {
	    StringBuilder buf = new StringBuilder();
	    toString(racine, buf, "", maxStrLen(racine), true);
	    return buf.toString();
	}

	private void toString(Noeud x, StringBuilder buf, String path, int len, boolean isRight) {
	    if (x == sentinelle) {
	        buf.append(getIndent(path, len, isRight) + "---NIL\n");
	        return;
	    }

	    toString(x.droit, buf, path + "D", len, true);
	    buf.append(getIndent(path, len, isRight) + "---" + x.cle.toString() + x.getCouleur() + "--+\n");
	    toString(x.gauche, buf, path + "G", len, false);
	}

	private String getIndent(String path, int len, boolean isRight) {
	    StringBuilder indent = new StringBuilder();
	    for (int i = 0; i < path.length(); i++) {
	        for (int j = 0; j < len + 6; j++)
	            indent.append(' ');

	        char c = ' ';
	        if (i == path.length() - 1)
	            c = isRight ? '+' : '+';  // Correction ici, les deux côtés devraient être "|"
	        else if (path.charAt(i) != path.charAt(i + 1))
	            c = '|';

	        indent.append(c);
	    }
	    return indent.toString();
	}
	private int maxStrLen(Noeud x) {
		return x.cle == null ? 0 : Math.max(x.cle.toString().length(),
				Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
	}
	
}
