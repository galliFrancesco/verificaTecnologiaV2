package verificaweb2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author galliFrancesco
 */
public class VerificaWEB2 {
   
    public static void main(String[] args) throws IOException, MalformedURLException, SAXException, ParserConfigurationException {
        Place posti = new Place(); 
        OpenMap mappa = new OpenMap(); 
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        Utente ut = new Utente();
        WebService ws = new WebService(); 

        int c = 0; // scleta
        System.out.println("\n 0. Registrazione \n 1.login");
        c = myObj.nextInt();

        myObj.nextLine();
        System.out.println("Mail?");
        String mail = myObj.nextLine();
        System.out.println("Password?");
        String pass = myObj.nextLine();

        switch (c) {
            case 0: // Registrazione
                ut.setMail(mail);
                ut.setPass(pass);
                
                String res = ws.register(mail, pass);
                System.out.println(res);

                break;
            case 1:// Login 
                
                break;
        }
        
        //String tappa = setTappa(); 
        //mappa.run(tappa); 
        // una volta finito c'è la cosa delle sclete
    }
    
    private static String setTappa(){
        
        // chiede all'utente una tappa
        System.out.println("Tappa?");
        Scanner myObj = new Scanner(System.in); 
        String tappa = myObj.nextLine(); 
        
        return tappa;        
    }

}
