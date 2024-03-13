package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nombreEtals) {
	    this.nom = nom;
	    this.villageois = new Gaulois[nbVillageoisMaximum];
	    this.marche = new Marche(nombreEtals); // Création du marché avec les étals
	}


	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}
	
	public class Marche {
        private Etal[] etals;

        public Marche(int nombreEtals) {
            etals = new Etal[nombreEtals];
            for (int i = 0; i < etals.length; i++) {
                etals[i] = new Etal();
            }
        }
        
        public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
            if (indiceEtal >= 0 && indiceEtal < etals.length) {
                etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
            } else {
                System.out.println("Indice d'étal invalide.");
            }
        }
        
        public int trouverEtalLibre() {
            for (int i = 0; i < etals.length; i++) {
                if (!etals[i].isEtalOccupe()) {
                    return i;
                }
            }
            return -1;
        }
        
        public Etal[] trouverEtals(String produit) {
            int compteur = 0;

            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
                    compteur++;
                }
            }

            Etal[] etalsVendantProduit = new Etal[compteur];
            int index = 0;

            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
                    etalsVendantProduit[index++] = etal;
                }
            }

            return etalsVendantProduit;
        }
        
        public Etal trouverVendeur(Gaulois gaulois) {
            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.getVendeur().equals(gaulois)) {
                    return etal;
                }
            }
            return null;
        }
        
        public String afficherMarche() {
            StringBuilder resultat = new StringBuilder();
            int nbEtalVide = 0;

            for (Etal etal : etals) {
                if (etal.isEtalOccupe()) {
                    resultat.append(etal.afficherEtal());
                } else {
                    nbEtalVide++;
                }
            }

            if (nbEtalVide > 0) {
                resultat.append("Il reste ").append(nbEtalVide).append(" étals non utilisés dans le marché.\n");
            }

            return resultat.toString();
        }
    }

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
	    if (chef == null) {
	        throw new VillageSansChefException("Le village doit avoir un chef pour afficher ses villageois.");
	    }

	    StringBuilder chaine = new StringBuilder();
	    if (nbVillageois < 1) {
	        chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
	    } else {
	        chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
	        for (int i = 0; i < nbVillageois; i++) {
	            chaine.append("- " + villageois[i].getNom() + "\n");
	        }
	    }
	    return chaine.toString();
	}

	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
	    int indiceEtalLibre = marche.trouverEtalLibre();
	    if (indiceEtalLibre != -1) {
	        marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
	        return vendeur.getNom() + " vend " + nbProduit + " " + produit + " à l'étal n°" + (indiceEtalLibre + 1) + ".\n";
	    } else {
	        return "Il n'y a plus d'étals libres pour " + vendeur.getNom() + ".\n";
	    }
	}
	
	public String rechercherVendeursProduit(String produit) {
	    Etal[] etalsVendantProduit = marche.trouverEtals(produit);
	    StringBuilder resultat = new StringBuilder();
	    if (etalsVendantProduit.length > 0) {
	        resultat.append("Les vendeurs qui proposent des ").append(produit).append(" sont :\n");
	        for (Etal etal : etalsVendantProduit) {
	            resultat.append("- ").append(etal.getVendeur().getNom()).append("\n");
	        }
	    } else {
	        resultat.append("Il n'y a pas de vendeur qui propose des ").append(produit).append(" au marché.\n");
	    }
	    return resultat.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
	    return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
	    Etal etal = marche.trouverVendeur(vendeur);
	    if (etal != null) {
	        return etal.libererEtal() + vendeur.getNom() + " quitte son étal.\n";
	    } else {
	        return vendeur.getNom() + " n'a pas d'étal à quitter.\n";
	    }
	}
	
	public String afficherMarche() {
	    return marche.afficherMarche();
	}

}