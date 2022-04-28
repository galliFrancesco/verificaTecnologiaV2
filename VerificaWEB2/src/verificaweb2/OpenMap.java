package verificaweb2;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author galliFrancesco
 */
public class OpenMap {

    String URLBase;

    public OpenMap() {
        URLBase = "https://nominatim.openstreetmap.org/search?q=";
        // https://nominatim.openstreetmap.org/search?q=mariano+comense,+monnet&format=xml&addressdetails=1 
    }

    /**
     * @param azione(String) -> Località da cercare nella query
     */
    public void run(String azione) throws MalformedURLException, IOException, SAXException, ParserConfigurationException {

        //Modifico la stringa per la query
        azione = modifica(azione);

        String URLCompleto = URLBase + azione;

        URL request = new URL(URLCompleto); //Fa la richiesta web

        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // prende il file XML
        System.out.println(result);

        // lo scrive in un file XML giusto per
        FileWriter fw = new FileWriter("temp.xml");
        fw.write(result);
        fw.close();

        // -------------
        // LETTURA DEL FILE XML DATO UN DOCUMENT
        // -------------
        Document document;

        DocumentBuilderFactory factory;
        Element root, element; // W3C.DOC
        NodeList nodelist;

        factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        document = db.parse("temp.xml");

        root = document.getDocumentElement();

        nodelist = root.getElementsByTagName("place");

        if (nodelist != null && nodelist.getLength() > 0) {
            int numNode = nodelist.getLength();
            //System.out.println("Ho degli elementi: " + numNode);

            Element e = (Element) nodelist.item(0); // <- prende il primo elemento 
 
            //System.out.println(e.getAttribute("lat"));
            //System.out.println(e.getAttribute("lon"));
            //String lat = e.getAttribute("lat");
            //String longi = e.getAttribute("lon");
            // Prende gli attributi di <place> e ne prende la latitudine e la longitudine

            //System.out.println(e.getAttribute("lat"));                   
            //System.out.println(e.getAttribute("lon"));    
            // NON SERVE, MA
            // Prende gli oggetti da <town> [...] </town>
            element = (Element) nodelist.item(0);
            NodeList nl = element.getElementsByTagName("town");
            String f = nl.item(0).getTextContent();
            //System.out.println(f);       
        }
        
        //// TODO: IMPOSTARE CHE RESTITUISCE UN VETTORE DI PLACES 
        //// MA IN QUESTO PRENDE SOLO IL PRIMO 
        //// TODO: CONTROLLARE SE ESISTE TOWN O ALTRO 
    }

    /**
     * @brief Imposta gli spazi e mette in coda le cose di cui ho bisogno(&format=xml&addressdetails=1)
     * @param s(String) -> Input dell'utente sulla tappa
     * @return Stringa pronta per la query
     */
    private String modifica(String s) {
        s = s.replaceAll(" ", "+");
        s += "&format=xml&addressdetails=1";

        return s;
    }
}