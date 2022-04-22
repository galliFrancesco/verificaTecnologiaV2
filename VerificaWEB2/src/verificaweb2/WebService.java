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
    
    String host_cattedra = "http://172.16.102.100/"; 
    
    public WebService(){
    
    }
    
    
    /**
     * EX: http://HOST_CATTEDRA/SaveStrings/register.php?username=aaa&password=bbb
     * @brief registra l'utente "aaa" con password "bbb"
     * 
     * @param username
     * @param password
     * 
     * @throws java.net.MalformedURLException
     */
    public String register(String username, String password) throws MalformedURLException, IOException{
        
        String URLBase = host_cattedra + "SaveStrings/register.php?username="; 
        String webRequest = URLBase + username + "&password=" + password; // url completo  
        
        URL request = new URL(webRequest); 
        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta
        
        // JSON
        String jsonString = result; //assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);
        
        String oggetto = obj.getString("status");
        if(!oggetto.equals("ok")){
            // allora non si può registrare
            return obj.getString("message"); 
        } else {
            // si può registrare
            //return obj.get("Result").toString();
            return "Utente Registrato :D";
        }
        
        /** JSON FORMAT ? 
         * {"STATUS":OK, "RESULT":"quello che ti serve"}
         * 
         * {"STATUS":ERROR, "MESSAGE":"bibo"}
         */ 
    }
    
    /**
     * EX: http://HOST_CATTEDRA/SaveStrings/getToken.php?username=aaa&password=bbb
     * @brief prende il token per l'utente registrato in quel modo 
     * 
     * @param username
     * @param password 
     * 
     * @throws java.net.MalformedURLException 
     */
    /*
    public Risposta getToken(String username, String password) throws MalformedURLException, IOException{
        
        String URLBase = "http://HOST_CATTEDRA/SaveStrings/register.php?username="; 
        String webRequest = URLBase + username + "&password=" + password; // url completo  
        
        URL request = new URL(webRequest); 
        String result = new BufferedReader(new InputStreamReader(request.openStream())).lines().collect(Collectors.joining("\n")); // manda la richiesta
        
        //return result; // CHIEDERE SE ARRIVA COME JSON
    }*/
    
    public void setString(){
    
    }
    
    public void getString(){
    
    }
    
    public void deleteString(){
    
    }
    
    public void getKeys(){
    
    }
}
