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
        Risposta r;
        Place posti = new Place();
        OpenMap mappa = new OpenMap();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        Utente ut = new Utente();

        int c = 0; // scelta
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

                String res = WebService.register(mail, pass);
                System.out.println(res);

                r = WebService.getToken(mail, pass);

                if (r.getStato()) { // se lo stato va bene
                    System.out.println(r.getDato()); // <- Token 
                    ut.setToken(r.getDato());
                }

                break;
            case 1:// Login 

                ut.setMail(mail);
                ut.setPass(pass);
                
                r = WebService.getToken(mail, pass);

                if (r.getStato()) {
                    // Imposta il token    
                    System.out.println("Login Effettuato!");
                    ut.setToken(r.getDato());
                } else {
                    System.out.println(r.getDato()); // <- Errore 
                }

                break;
        }

        do {

            System.out.println("\n 1. Inserisci tappa \n 2. Visualizza lista tappe \n 3. Rimuovi tappa\n 4. Inverti tappe\n 5. Calcola distanza itinerario\n 6. Cancella itinerario\n 0. Esci");
            c = myObj.nextInt();

            switch (c) {
                case 1: // inserisci tappa
                    //String tappa = setTappa();
                    //mappa.run(tappa);
                    r = WebService.setString(ut.getToken());                    
                    //System.out.println(r.getDato());

                    break;
                case 2: // visualizza tappe
                    
                    WebService.getString(ut.getToken());
                    
                    break;
                case 3: // rimuovi tappa
                    break;
                case 4: // inverti tappa
                    break;
                case 5: // calcola distanza itinerario 
                    break;
                case 6: // cancella itinerario 
                    break;
                case 0: // esci 
                    break;
            }
        } while (c != 0);

    }

    private static String setTappa() {

        // chiede all'utente una tappa
        System.out.println("Tappa?");
        Scanner myObj = new Scanner(System.in);
        String tappa = myObj.nextLine();

        return tappa;
    }

}
