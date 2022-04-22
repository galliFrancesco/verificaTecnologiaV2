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
import java.util.stream.Collectors;
import org.json.JSONObject;

/**
 *
 * @author galli_francesco
 *
 * @brief Fa i metodi relativi al server
 *
 */
public class WebService {

    static String host_cattedra = "http://172.16.102.100/";

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

        String URLBase = host_cattedra + "SaveStrings/register.php?username=";
        String webRequest = URLBase + username + "&password=" + password; // url completo  

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

        String URLBase = host_cattedra + "SaveStrings/getToken.php?username=";
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

    static public void setString() {

    }

    static public void getString() {

    }

    static public void deleteString() {

    }

    static public void getKeys() {

    }
}
