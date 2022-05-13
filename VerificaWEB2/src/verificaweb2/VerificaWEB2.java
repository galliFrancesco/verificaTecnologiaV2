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
        Place posto = new Place();
        OpenMap mappa = new OpenMap();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        Utente ut = new Utente();

        boolean gandalf = true;

        int c = 0; // scelta
        System.out.println("\n0. Registrazione \n1. Login");
        c = myObj.nextInt();

        myObj.nextLine();
        System.out.println("Mail?");
        String mail = myObj.nextLine();
        System.out.println("Password?");
        String pass = myObj.nextLine();

        switch (c) {
            case 0: // Registrazione
                String res = WebService.register(mail, pass);
                System.out.println(res);

            case 1:// Login 

                ut.setMail(mail);
                ut.setPass(pass);

                r = WebService.getToken(mail, pass);

                if (r.getStato()) {
                    // Imposta il token    
                    System.out.println("Login Effettuato!");
                    ut.setToken(r.getDato());
                    System.out.println(r.getDato()); // <- Token 
                } else {
                    System.out.println(r.getDato()); // <- Errore 
                    gandalf = false;
                }

                break;
        }

        if (gandalf) {
            do {

                System.out.println("\n 1. Inserisci tappa \n 2. Visualizza lista tappe \n 3. Visualizza Chiavi \n 4. Rimuovi tappa\n 5. Inverti tappe\n 6. Calcola distanza itinerario\n 7. Cancella itinerario\n 0. Esci");
                c = myObj.nextInt();

                switch (c) {
                    case 1: // inserisci tappa
                        String tappa = setTappa();
                        posto = mappa.place(tappa);

                        r = WebService.setString(ut.getToken(), posto);
                        
                        if(!r.getStato())
                            System.out.println(r.getDato()); 
                        
                        break;
                    case 2: // visualizza tappe

                        WebService.getString(ut.getToken());

                        break;
                    case 3: // visualizza Chiavi 
                        
                        WebService.getKeys(ut.getToken());
                        
                        break;                    
                    case 4: // rimuovi tappa
                        
                        System.out.println("Numero della tappa da togliere?");

                        int num = 0;

                        num = myObj.nextInt();
                        r = WebService.deleteString(ut.getToken(), num);

                        if (r.getStato()) {
                            System.out.println("Tappa eliminata");
                        } else {
                            System.out.println("Errore");
                        }

                        break;
                    case 5: // inverti tappa
                        // swap tra le due 

                        int x = 0;
                        int y = 0;

                        break;
                    case 6: // calcola distanza itinerario 
                  
                        WebService.getDistanza(ut.getToken());
                        
                        break;
                    case 7: // cancella itinerario 

                        WebService.deleteAllStrings(ut.getToken());

                        break; 
                    case 0: // esci 
                        break;
                }
            } while (c != 0);
        }
    }

    private static String setTappa() {

        // chiede all'utente una tappa
        System.out.println("Tappa?");
        Scanner myObj = new Scanner(System.in);
        String tappa = myObj.nextLine();

        return tappa;
    }

}
