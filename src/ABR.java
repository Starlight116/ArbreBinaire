import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Implantation de l'interface Collection basée sur les arbres binaires de
 * recherche. Les éléments sont ordonnés soit en utilisant l'ordre naturel (cf
 * Comparable) soit avec un Comparator fourni à la création.
 * </p>
 * 
 * <p>
 * Certaines méthodes de AbstractCollection doivent être surchargées pour plus
 * d'efficacité.
 * </p>
 * 
 * @param <E>
 *            le type des clés stockées dans l'arbre
 */
public class ABR<E> extends AbstractCollection<E> {
	private Noeud racine;
	private int taille;
	private Comparator<? super E> cmp;

	private class Noeud {
		private E cle;
		private Noeud gauche;
		private Noeud droit;
		private Noeud pere;

		public Noeud(E cle) {
			this.cle = cle;
    	    this.gauche = null;
    		this.droit = null;
    		this.pere = null;
		}

		/**
		 * Renvoie le noeud contenant la clé minimale du sous-arbre enraciné
		 * dans ce noeud
		 * 
		 * @return le noeud contenant la clé minimale du sous-arbre enraciné
		 *         dans ce noeud
		 */
		public Noeud minimum() {
			Noeud courant = this;
    		while (courant.gauche != null) {
        		courant = courant.gauche;
    		}
    		return courant;
		}

		/**
		 * Renvoie le successeur de ce noeud
		 * 
		 * @return le noeud contenant la clé qui suit la clé de ce noeud dans
		 *         l'ordre des clés, null si c'es le noeud contenant la plus
		 *         grande clé
		 */
		public Noeud suivant() {
			if (droit != null) return droit.minimum();
			else {
				Noeud p = pere;
				Noeud courant = this;
				while (p != null && courant == p.droit) {
					courant = p;
					p = p.pere;
				}
				return p;
			}
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
	}

	// Constructeurs

	/**
	 * Crée un arbre vide. Les éléments sont ordonnés selon l'ordre naturel
	 */
	public ABR() {
		racine = null;
		taille = 0;
		cmp = ((e1,e2) -> ((Comparable<E>) e1).compareTo(e2));
	}

	/**
	 * Crée un arbre vide. Les éléments sont comparés selon l'ordre imposé par
	 * le comparateur
	 * 
	 * @param cmp
	 *            le comparateur utilisé pour définir l'ordre des éléments
	 */
	public ABR(Comparator<? super E> cmp) {
		racine = null;
		taille = 0;
		this.cmp = cmp;
	}

	/**
	 * Constructeur par recopie. Crée un arbre qui contient les mêmes éléments
	 * que c. L'ordre des éléments est l'ordre naturel.
	 * 
	 * @param c
	 *            la collection à copier
	 */
	public ABR(Collection<? extends E> c) {
		this();
		this.addAll(c);
	}

	@Override
	public Iterator<E> iterator() {
		return new ABRIterator();
	}

	@Override
	public int size() {
		return taille;
	}


	// Quelques méthodes utiles

	/**
	 * Recherche une clé. Cette méthode peut être utilisée par
	 * {@link #contains(Object)} et {@link #remove(Object)}
	 * 
	 * @param o
	 *            la clé à chercher
	 * @return le noeud qui contient la clé ou null si la clé n'est pas trouvée.
	 */
	private Noeud rechercher(Object o) {
		if(o == null){
			return null;
		}
		Noeud courant = racine;
		while(courant != null){
			int comparateur = cmp.compare((E) o, courant.getCle());
		if(comparateur==0)return courant;
		else if (comparateur > 0) courant = courant.getDroite();
		else if (comparateur < 0) courant = courant.getGauche();
		}
		return courant;
	}

	/**
	 * Supprime le noeud z. Cette méthode peut être utilisée dans
	 * {@link #remove(Object)} et {@link Iterator#remove()}
	 * 
	 * @param z
	 *            le noeud à supprimer
	 * @return le noeud contenant la clé qui suit celle de z dans l'ordre des
	 *         clés. Cette valeur de retour peut être utile dans
	 *         {@link Iterator#remove()}
	 */
	private Noeud supprimer(Noeud z) {
		Noeud y; // nœud à détacher

	    if (z.getGauche() == null || z.getDroite() == null) {
	        y = z;
	    } else {
	        y = z.suivant();
	    }

	    // x est le fils unique de y ou null si y n'a pas de fils
	    Noeud x = (y.getGauche() != null) ? y.getGauche() : y.getDroite();

	    if (x != null) {
	        x.setPere(y.getPere());
	    }

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

	    return z.suivant();
	}
	
	@Override
	public boolean add(E e){
		if(e==null) {
			System.out.println("le noeud à ajouter est null !!");
			return false;
		}
		
		Noeud n = new Noeud(e);
		Noeud y = null;
		Noeud courant = racine;
		while (courant!=null) {
			y= courant;
			courant = (cmp.compare(n.getCle(), courant.getCle())<0) ? courant.getGauche() : courant.getDroite();
		}
		
		n.setPere(y);
		
		if(y==null) {
			racine = n;
		} 
		else {
			if(cmp.compare(n.getCle(), y.getCle())<0) y.setGauche(n);
			else  y.setDroite(n);
		}
		
		n.gauche = n.droit = null;
		taille++;
		return true;
	}

	/**
	 * Les itérateurs doivent parcourir les éléments dans l'ordre ! Ceci peut se
	 * faire facilement en utilisant {@link Noeud#minimum()} et
	 * {@link Noeud#suivant()}
	 */
	private class ABRIterator implements Iterator<E> {
		private Noeud courant;
		private Noeud precedent;
		
		public ABRIterator() {
			this.precedent=null;
        // Initialise le prochain nœud avec le nœud contenant la clé minimale
        	this.courant = (racine==null)?null:racine.minimum();
			
    	}
		
		public boolean hasNext() {
			return courant != null;
		}
		
		public E next() {
			
			if (courant==null) {
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
			courant = supprimer(precedent);
			precedent = null;
		}
}

	// Pour un "joli" affichage

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		toString(racine, buf, "", maxStrLen(racine));
		return buf.toString();
	}

	private void toString(Noeud x, StringBuffer buf, String path, int len) {
		if (x == null)
			return;
		toString(x.droit, buf, path + "D", len);
		for (int i = 0; i < path.length(); i++) {
			for (int j = 0; j < len + 6; j++)
				buf.append(' ');
			char c = ' ';
			if (i == path.length() - 1)
				c = '+';
			else if (path.charAt(i) != path.charAt(i + 1))
				c = '|';
			buf.append(c);
		}
		buf.append("-- " + x.cle.toString());
		if (x.gauche != null || x.droit != null) {
			buf.append(" --");
			for (int j = x.cle.toString().length(); j < len; j++)
				buf.append('-');
			buf.append('|');
		}
		buf.append("\n");
		toString(x.gauche, buf, path + "G", len);
	}

	private int maxStrLen(Noeud x) {
		return x == null ? 0 : Math.max(x.cle.toString().length(),
				Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
	}


	
}
