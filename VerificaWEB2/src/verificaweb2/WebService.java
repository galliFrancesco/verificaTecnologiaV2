/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verificaweb2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.json.JSONObject;

/**
 *
 * @author galli_francesco
 *
 * @brief Fa i metodi relativi al server SaveStrings
 *
 */
public class WebService {

    static String host_cattedra = "https://savestrings.netsons.org/SaveStrings/";
    static int count = 0;

    
    /**
     * EX:
     * http://HOST_CATTEDRA/SaveStrings/register.php?username=aaa&password=bbb
     *
     * @brief registra l'utente "aaa" con password "bbb"
     *
     * @param username
     * @param password
     *
     * @throws java.net.MalformedURLException
     */
    static public String register(String username, String password) throws MalformedURLException, IOException {

        String URLBase = host_cattedra + "register.php?username=";
        String webRequest = URLBase + username + "&password=" + password; // url completo  

        System.out.println(webRequest);

        URL request = new URL(webRequest);
        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta

        // JSON <- Lo faccio qui, perchè ognuno ha una propria risposta
        String jsonString = result; //assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);

        String oggetto = obj.getString("status");
        if (!oggetto.equals("ok")) {
            // allora non si può registrare
            return obj.getString("message");
        } else {
            // si può registrare
            //return obj.get("Result").toString();
            return "Utente Registrato :D";
        }

        /**
         * JSON FORMAT ? {"STATUS":OK, "RESULT":"quello che ti serve"}
         *
         * {"STATUS":ERROR, "MESSAGE":"bibo"}
         */
    }

    
    /**
     * EX:
     * http://HOST_CATTEDRA/SaveStrings/getToken.php?username=aaa&password=bbb
     *
     * @brief prende il token per l'utente registrato in quel modo
     *
     * @param username
     * @param password
     * @param r
     *
     * @throws java.net.MalformedURLException
     */
    static public Risposta getToken(String username, String password) throws MalformedURLException, IOException {

        Risposta r = new Risposta();

        String URLBase = host_cattedra + "getToken.php?username=";
        String webRequest = URLBase + username + "&password=" + password; // url completo  

        URL request = new URL(webRequest);
        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta

        // {"status":"ok","result":{"token":"85456c36ea2b1f513cf5d708fa6d336d"}}
        // {"status":"error","message":"username o password errati"}
        // JSON <- Lo faccio qui, perchè ognuno ha una propria risposta
        String jsonString = result; //assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);

        String oggetto = obj.getString("status");

        if (!oggetto.equals("ok")) {
            // allora non ritorna il token     
            r.setStato(false);
            r.setDato(obj.getString("message"));

        } else {
            // Ritorna il token
            //return obj.get("Result").toString();
            //return "Utente Registrato :D";
            JSONObject jResult = obj.getJSONObject("result");

            r.setStato(true);
            r.setDato(jResult.getString("token"));
        }

        return r;
    }

    
    /**
     * EX:
     * https://savestrings.netsons.org/SaveStrings/getString.php?token=e5493a6f14a9ab8656163d431f9130c9&key=0&string=ciao
     *
     * @brief Imposta una stringa con un identificativo (un count in questo
     * caso)
     *
     * @param token
     *
     * @return
     */
    static public Risposta setString(String token) throws MalformedURLException, IOException {

        Risposta r = new Risposta();

        String URLBase = host_cattedra + "setString.php?token=";
        String webRequest = URLBase + token + "&key=" + count + "&string=ciao";  // imposta una stringa con identificativo
        System.out.println(webRequest);

        URL request = new URL(webRequest);
        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta

        // {"status":"ok","result":{"key":"IDENTIFICATIVO","string":"HELLO_WORLD"}}   
        // OR 
        // {"status":"error","message":"parametro 'key' mancante"}
        System.out.println(result);
        String jsonString = result; //assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);

        String oggetto = obj.getString("status");

        if (!oggetto.equals("ok")) {
            // allora non è andato a buon fine
            r.setStato(false);
            r.setDato(obj.getString("message"));

        } else {

            r.setStato(true);
        }

        count++;

        return r;
    }

    
    /**
     * EX:
     * https://savestrings.netsons.org/SaveStrings/getString.php?token=e0b46d738a3dc47990271beca88c3b6d&key=1
     *
     * @brief Stampa a video tutte le stringhe salvate dall'utente
     *
     * @param token
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    static public void getString(String token) throws MalformedURLException, IOException {

        String URLBase = host_cattedra + "getString.php?token=";

        for (int i = 0; i < count; i++) {// Stampa ogni stringa scritta dall'utente          
            String webRequest = URLBase + token + "&key=" + i;  // imposta la stringa con identificativo
            System.out.println(webRequest);

            URL request = new URL(webRequest);
            String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta
            // {"status":"ok","result":{"key":"9","string":"ciao"}} 
            // OR 
            // {"status":"error","message":"parametro 'key' mancante"}
            System.out.println(result);
            String jsonString = result; //assign your JSON String here
            JSONObject obj = new JSONObject(jsonString);

            String oggetto = obj.getString("status");

            if (oggetto.equals("ok")) {
                // tutto a posto

                JSONObject jResult = obj.getJSONObject("result");
                System.out.println(jResult.getString("key") + i + ": " + jResult.getString("string"));

            }

        }
    }

    
    /**
     * EX:
     * //http://HOST_CATTEDRA/SaveStrings/deleteString.php?token=e0b46d738a3dc47990271beca88c3b6d&key=1
     * 
     * @brief
     * 
     * @param token
     * @param c
     * 
     * @return r(Risposta)
     * 
     * @throws MalformedURLException
     * @throws IOException 
     */
    static public Risposta deleteString(String token, int c) throws MalformedURLException, IOException {
        
        Risposta r = new Risposta();

        // c-> scelta dell'utente del messaggio da cancellare
        if (c < count) {
            // solo se c< count, altrimenti inserirebbe un numero non valido, e quindi darebbe errore

            String URLBase = host_cattedra + "deleteString.php?token=";
            String webRequest = URLBase + token + "&key=" + c ; // imposta la stringa con identificativo
            System.out.println(webRequest);

            URL request = new URL(webRequest);
            String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta
            // {"status":"ok","result":{"key":"9","string":"ciao"}} 
            // OR 
            // {"status":"error","message":""}
            System.out.println(result);
            String jsonString = result; //assign your JSON String here
            JSONObject obj = new JSONObject(jsonString);

            String oggetto = obj.getString("status");

            if (oggetto.equals("ok")) {
                // tutto a posto
                r.setStato(true);    
            }

        } else {
            r.setStato(false);
        }

        return r;
    }

    static public void getKeys() {

    }
}
