import java.io.File;
import java.io.StringReader;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class GestionnaireFichier {

  /*la fonction parcourir permettre de lire tous les fichiers et sous dossier d'un dossier 
  d' une maniere recursive
  elle permet de passer d'une liste Directory et File a une list Document
*/
  public static String parcourir(File dossier, String tabulation) {
    String tmpXml = "";
    tabulation += "\t";
    File[] fileListe = dossier.listFiles();
    for (File item : fileListe) {
      if (item.isDirectory()) {
        tmpXml +=
          "\n " + tabulation + "<dossier name='" + item.getName() + "'>";
        tmpXml += parcourir(item, tabulation);
        tmpXml += "\n " + tabulation + "</dossier>";
      } else tmpXml +=
        "\n " + tabulation + "<fichier name='" + item.getName() + "'/>";
    }
    return tmpXml;
  }

  public static String pathToXml(String chemin) {
    String tabulation = "\t";
    String xml = "";

    File file = new File(chemin);

    xml += "<dossier name='" + file.getName() + "'>";
    xml += parcourir(file, tabulation);
    xml += "\n" + "</dossier>";

    return xml;
  }

  public static Document xmlToDoc(String xml) {
    try {
      DocumentBuilder parser = DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder();
      Document doc = parser.parse(new InputSource(new StringReader(xml)));
      return doc;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void parcourirXml(NodeList nodeList, FileClass doc) {
    String nodeName, attribu;

    for (int i = 0; i < nodeList.getLength(); ++i) {
      Node node = nodeList.item(i);
      if (node.getNodeType() != Node.ELEMENT_NODE) continue; //on ignore les node de type #text
      nodeName = nodeList.item(i).getNodeName();
      attribu = node.getAttributes().getNamedItem("name").getNodeValue();
      FileClass tmpDoc = Factory.fabrique(attribu, nodeName); //on cree une instance

      if (nodeName.equals("dossier")) {
        NodeList tmp = node.getChildNodes();
        parcourirXml(tmp, tmpDoc);
      }
      doc.ajouter(tmpDoc); // on ajoute au dossiers d'une maniere resoective
    }
  }

  public static void main(String[] args) {
    String chemin = "C:/Users/LENOVO/Desktop/Disigne Patern/tree";
    String test = pathToXml(chemin);
    String tabulation = "───";
    System.out.println("***********************  XML *****************");

    System.out.println(test);

    Document document = xmlToDoc(test);
    document.getDocumentElement().normalize();

    Element el = document.getDocumentElement();
    Dossier dossierPrincipale = new Dossier(el.getAttribute("name"));

    NodeList nodeList = el.getChildNodes();
    parcourirXml(nodeList, dossierPrincipale);

    System.out.println("***********************  Tree *****************");
    dossierPrincipale.affichier(tabulation);
  }
}
