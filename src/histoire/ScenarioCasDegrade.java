package histoire;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Etal etal = new Etal();
        Gaulois acheteur = new Gaulois("Astérix", 5);
        
        try {
            etal.libererEtal();
        } catch (IllegalStateException e) {
            System.out.println("Exception capturée : " + e.getMessage());
        }
        System.out.println("Fin du test");
        
        try {
            etal.acheterProduit(5, null);
        } catch (NullPointerException e) {
            System.err.println("Erreur d'achat : " + e.getMessage());
            e.printStackTrace();
        }

        try {
            etal.acheterProduit(-1, acheteur);
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur d'achat : " + e.getMessage());
            e.printStackTrace();
        }

        try {
            etal.acheterProduit(5, acheteur);
        } catch (IllegalStateException e) {
            System.err.println("Erreur d'achat : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
