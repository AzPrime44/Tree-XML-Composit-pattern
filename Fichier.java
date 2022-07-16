public class Fichier extends FileClass {

  private String name;

  public Fichier(String nomFichier) {
    this.name = nomFichier;
  }

  public String getNom() {
    return this.name;
  }

  public void affichier(String tab) {
    System.out.printf("│\n│");
    System.out.printf("%s────%s\n", tab, this.name);
  }
}
