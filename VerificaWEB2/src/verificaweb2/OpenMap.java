package verificaweb2;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
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
    }
    
    /**
     * EX: 
     * https://nominatim.openstreetmap.org/search?q=erba&format=xml&addressdetails=1
     * 
     * @brief Invia una query e prende l einfo di un posto da un XML
     * 
     * @param azione
     * 
     * @throws MalformedURLException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException 
     */
    public Place place(String azione) throws MalformedURLException, IOException, SAXException, ParserConfigurationException {
        
        Place p = new Place(); 
        //Modifico la stringa per la query
        azione = modifica(azione);

        String URLCompleto = URLBase + azione;
        URL request = new URL(URLCompleto); //Fa la richiesta web

        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // prende il file XML

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
 
            String latS = e.getAttribute("lat");
            String longiS = e.getAttribute("lon");
            
            // String to Float
            float lat = Float.parseFloat(latS); 
            float longi = Float.parseFloat(longiS);
            p.setPos(lat, longi);
            
            String nome = e.getAttribute("display_name");
            //System.out.println("Stampa: "+nome);
            p.setName(nome); 

            
            // Prende gli oggetti da <town> [...] </town>
            /* POTREBBE SERVIRE
            element = (Element) nodelist.item(0);
            NodeList nl = element.getElementsByTagName("town");
           
            if (nl.getLength() > 0 ){ // controlla se c'è il tag town
        
                String f = nl.item(0).getTextContent();
                System.out.println("Stampa: "+f); 
                p.setName(f);
                
            } else { // nulla mette le coordinate
                // che ha già messo 
            }
            
            nl = element.getElementsByTagName("county");
            String f = nl.item(0).getTextContent();
            System.out.println("County: "+f); 
            p.setCounty(f);
            
            nl = element.getElementsByTagName("postcode");
            f = nl.item(0).getTextContent();
            System.out.println("postcode: "+f); 
            p.setPostCode(f);*/
        }
        
        return p;  
    }

    
    public Place places(){     
        
        //// TODO: IMPOSTARE CHE RESTITUISCE UN VETTORE DI PLACES 
        //// NELL'ALTRO PRENDE SOLO IL PRIMO 
        Place p = new Place(); 
        
        
        return p; 
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