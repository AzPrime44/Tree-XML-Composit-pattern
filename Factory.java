public class Factory {

  public static FileClass fabrique(String name, String type) {
    FileClass instance;
    if (type.equals("dossier")) {
      instance = new Dossier(name);
    } else instance = new Fichier(name);
    return instance;
  }
}
