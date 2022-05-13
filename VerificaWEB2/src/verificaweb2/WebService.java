package verificaweb2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import static verificaweb2.WebService.count;
import static verificaweb2.WebService.host_cattedra;

/**
 *
 * @author galliFrancesco
 *
 * @brief Metodi relativi al server SaveStrings
 * (https://savestrings.netsons.org/SaveStrings/)
 *
 */
public class WebService {

    static String host_cattedra = "https://savestrings.netsons.org/SaveStrings/";
    static int count = 0;

    /**
     * EX:
     * http://HOST_CATTEDRA/SaveStrings/register.php?username=aaa&password=bbb
     *
     * @brief registra l'utente com mail e password
     *
     * @param username
     * @param password
     *
     * @return String
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
     *
     * @return r
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
     * @throws java.net.MalformedURLException
     *
     * @return
     */
    static public Risposta setString(String token, Place p) throws MalformedURLException, IOException {

        // TODO :: //
        // PRENDERE IN INPUT UN PLACE
        // E PRENDERE DA QUELLO LE INFORMAZIONI      
        Risposta r = new Risposta();

        String daSalvare = /*Nome, lat, long*/ p.getTown() + "," + p.getLat() + " " + p.getLongi();
        daSalvare = URLEncoder.encode(daSalvare, StandardCharsets.UTF_8); // modifica la stringa di modo che possa entrare nell'URL

        String URLBase = host_cattedra + "setString.php?token=";
        String webRequest = URLBase + token + "&key=" + count + "&string='" + daSalvare + "'";  // imposta una stringa con identificativo
        //System.out.println(webRequest);

        URL request = new URL(webRequest);
        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta

        // {"status":"ok","result":{"key":"IDENTIFICATIVO","string":"HELLO_WORLD"}}   
        // OR 
        // {"status":"error","message":"parametro 'key' mancante"}
        //System.out.println(result);
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
    static public list<String> getString(String token) throws MalformedURLException, IOException {

        String URLBase = host_cattedra + "getString.php?token=";

        for (int i = 0; i < count; i++) {// Stampa ogni stringa scritta dall'utente          
            String webRequest = URLBase + token + "&key=" + i;  // imposta la stringa con identificativo
            //System.out.println(webRequest);

            URL request = new URL(webRequest);
            String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta
            // {"status":"ok","result":{"key":"9","string":"ciao"}} 
            // OR 
            // {"status":"error","message":"parametro 'key' mancante"}
            //System.out.println(result);
            String jsonString = result; //assign your JSON String here
            JSONObject obj = new JSONObject(jsonString);

            String oggetto = obj.getString("status");

            if (oggetto.equals("ok")) {
                // tutto a posto

                JSONObject jResult = obj.getJSONObject("result");
                System.out.println(jResult.getString("key") + i + ": " + jResult.getString("string"));

            }
        }

        if (count == 0) {
            System.out.println("\n Nessuna tappa");
        }
    }

    static public void getDistanza(String token) throws MalformedURLException, IOException {

        // alla fine è come getString l'inizio, perchè deve comunque prendere le tappe
        String URLBase = host_cattedra + "getString.php?token=";

        // Serviranno dopo
        float lat2 = 0;
        float lon2 = 0;

        double ris = 0; 
        
        for (int i = 0; i < count; i++) {// Stampa ogni stringa scritta dall'utente          
            String webRequest = URLBase + token + "&key=" + i;  // imposta la stringa con identificativo
            //System.out.println(webRequest);

            URL request = new URL(webRequest);
            String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta
            // {"status":"ok","result":{"key":"9","string":"ciao"}} 
            // OR 
            // {"status":"error","message":"parametro 'key' mancante"}
            //System.out.println(result);
            String jsonString = result; //assign your JSON String here
            JSONObject obj = new JSONObject(jsonString);

            String oggetto = obj.getString("status");

            if (oggetto.equals("ok")) {
                // tutto a posto

                JSONObject jResult = obj.getJSONObject("result");
                String sResult = jResult.getString("key") + i + ": " + jResult.getString("string");

                // EX:  "00: 'Erba, Como, Lombardia, Italia,45.80958 9.231242'"
                int posIniz = sResult.lastIndexOf(",");
                int posFin = sResult.lastIndexOf("'");

                // SUBSTRING
                String subbato = sResult.substring(posIniz + 1, posFin); // <- QUESTA STRINGA CONTIENE LAT E LONG              

                int posSpazio = subbato.indexOf(" ");

                String latS = subbato.substring(0, posSpazio);
                String lonS = subbato.substring(posSpazio + 1);

                float lat = Float.parseFloat(latS);
                float lon = Float.parseFloat(lonS);
                // TUTTO QUESTO SOLO PER PRENDERE I VALORI FLOAT DELLE POSIZIONI 

                // alla fine le coordinate attuali dovranno essere switchate con "lat2" e "lon2"
                // per fare il calcolo di altro 
                
                //  ------ --------
                // CALCOLO DISTANZA
                //  ------ --------
                if(lat2!=0){ // dàproblemi con lo 0 
                    int earthRadiusKm = 6371;

                    var dLat = degreesToRadians(lat2-lat);
                    var dLon = degreesToRadians(lon2-lon);

                    lat = degreesToRadians(lat);
                    lat2 = degreesToRadians(lat2);

                    var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat) * Math.cos(lat2); 

                    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
                    double ris1 = earthRadiusKm * c;

                    ris += ris1;
                    //System.out.println(ris);
                } else {
                }
                // Fine ciclo 
                lat2 = lat; 
                lon2 = lon; 
            }
        }     
        
        if (count == 0) {
            System.out.println("\n Nessuna tappa");
        } else {
            System.out.println("Distanza(KM):"+ris); 
        }

        /*
        SOURCE: https://stackoverflow.com/questions/365826/calculate-distance-between-2-gps-coordinates
        
        function degreesToRadians(degrees) {
            return degrees * Math.PI / 180;
          }

          function distanceInKmBetweenEarthCoordinates(lat1, lon1, lat2, lon2) {
            var earthRadiusKm = 6371;

            var dLat = degreesToRadians(lat2-lat1);
            var dLon = degreesToRadians(lon2-lon1);

            lat1 = degreesToRadians(lat1);
            lat2 = degreesToRadians(lat2);

            var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
            var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
            return earthRadiusKm * c;
          }
        
        
         */
    }
    
    static private float degreesToRadians(float degrees){
    
        return (float) (degrees * Math.PI / 180);
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
            String webRequest = URLBase + token + "&key=" + c; // imposta la stringa con identificativo
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
            System.out.println("Non ci sono così tante tappe :c");
        }

        return r;
    }

    /**
     * EX:
     * http://HOST_CATTEDRA/SaveStrings/getKeys.php?token=697ab188731ec4861e1eb72eca7a18d2
     *
     * @brief
     *
     * @param token
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    static public void getKeys(String token) throws MalformedURLException, IOException {
        String URLBase = host_cattedra + "getKeys.php?token=";
        String webRequest = URLBase + token;  // imposta la stringa con identificativo
        //System.out.println(webRequest); 

        URL request = new URL(webRequest);

        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta
        String jsonString = result; //assign your JSON String here
        //System.out.println(result);

        //System.out.println(webRequest);
        // {"status":"ok","result":{"key":"9","string":"ciao"}} 
        // OR 
        // {"status":"error","message":"parametro 'key' mancante"}
        //System.out.println(result);
        JSONObject obj = new JSONObject(jsonString);
        String oggetto = obj.getString("status");

        System.out.println("Chiavi: ");

        if (oggetto.equals("ok")) {
            // tutto a posto

            JSONArray jResult = obj.getJSONArray("result");
            //System.out.println(jResult.getString("key") + i + ": " + jResult.getString("string"));

            for (int i = 1; i < jResult.length(); i++) {
                System.out.print(jResult.get(i));
                System.out.print(", ");
            }

        }
    }

    /**
     * EX:
     * http://HOST_CATTEDRA/SaveStrings/deleteString.php?token=e0b46d738a3dc47990271beca88c3b6d&key=1
     *
     * @brief Simile all'altro metodo, ma elimina TUTTE le stringhe dell'utente
     *
     * @param token
     *
     * @return r(Risposta)
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    static void deleteAllStrings(String token) throws MalformedURLException, IOException {

        Risposta r = new Risposta();

        String URLBase = host_cattedra + "deleteString.php?token=";

        for (int i = 0; i < count; i++) {

            String webRequest = URLBase + token + "&key=" + i; // imposta la stringa con identificativo
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
        }

        count = 0;
    }
}
