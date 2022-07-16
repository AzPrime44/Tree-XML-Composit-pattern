import java.util.ArrayList;

public class Dossier extends FileClass {

  private String name;
  private ArrayList<FileClass> elements = new ArrayList<FileClass>();

  public Dossier(String nomDossier) {
    this.name = nomDossier;
  }

  public String getNom() {
    return this.name;
  }

  public void ajouter(FileClass element) {
    this.elements.add(element);
  }

  public void affichier(String tab) {
    System.out.printf("│\n│");
    System.out.printf("%s────├──  %s\n", tab, this.name);

    for (FileClass element : elements) {
      element.affichier(tab + "─────");
    }
  }
}
