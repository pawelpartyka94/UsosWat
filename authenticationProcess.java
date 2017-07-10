package com.example.pawel.usoswat;

/**
 * Created by pawel on 25.12.16.
 */

import android.support.v7.app.AppCompatActivity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;

public class authenticationProcess extends AppCompatActivity
{
    static ArrayList<String> stronaOcen = new ArrayList<String>();
    public String ciastka;

    public ArrayList verificationTry(String username, String password) throws IOException
    {
        //Tworzenie clienta
        HttpClient client = HttpClientBuilder.create().build();

        //Do poprawy polaczenia ssl
        System.setProperty("jsse.enableSNIExtension", "false");

        //Zapisywanie ciastek
        CookieHandler.setDefault(new CookieManager());

        //Wysylanie zapytania GET
         HttpGet request = new HttpGet("https://logowanie.wat.edu.pl/cas/login?service=https%3A%2F%2Fusos.wat.edu.pl%2Fkontroler.php%3F_action%3Dlogowaniecas%2Findex&locale=pl");

        //request.addHeader("User-Agent", "Mozilla/5.0");
        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        //Pierwsze odczytywanie strony i zapisywanie ukrytego kodu
        String line = "";
        String hiddencode = null;

        while ((line = rd.readLine()) != null)
        {
            if(line.contains("<input type=\"hidden\" name=\"lt\" value=\""))
            {
                String [] split = line.split("value=\"");
                String [] codesplit = split[1].split("\"");
                hiddencode = codesplit[0];
            }
        }

        rd.close();

        //Wysylania zapytan ia POST
        HttpPost post = new HttpPost("https://logowanie.wat.edu.pl/cas/login?service=https%3A%2F%2Fusos.wat.edu.pl%2Fkontroler.php%3F_action%3Dlogowaniecas%2Findex&locale=pl");
        post.setHeader("Cookie", ciastka);
        post.setHeader("Connection", "keep-alive");
        post.setHeader("User-Agent", "Mozilla/5.0");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        ArrayList <BasicNameValuePair> postParams = new ArrayList<BasicNameValuePair>();
        postParams.add(new BasicNameValuePair("username", username));
        postParams.add(new BasicNameValuePair("password", password));
        postParams.add(new BasicNameValuePair("lt", hiddencode));
        postParams.add(new BasicNameValuePair("execution", "e1s1"));
        postParams.add(new BasicNameValuePair("_eventId", "submit"));
        postParams.add(new BasicNameValuePair("submit", "ZALOGUJ"));

        post.setEntity(new UrlEncodedFormEntity(postParams));
        response = client.execute(post);

        //sprawdzenie próby zalogowania

        String responseCode = response.getStatusLine().toString();

        if (responseCode.compareTo("HTTP/1.1 302 Moved Temporarily")==0)
        {
            //Wysylanie zapytania GET z ocenami
            request = new HttpGet("https://usos.wat.edu.pl/kontroler.php?_action=dla_stud/studia/oceny/index");
            request.addHeader("User-Agent", "Mozilla/5.0");
            response = client.execute(request);

            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            //Pierwsze odczytywanie strony i zapisywanie ukrytego kodu
            line = "";

            while ((line = rd.readLine()) != null)
            {
                stronaOcen.add(line);
                //System.out.println(line);
            }
        }
        else {stronaOcen=null;}
    return (stronaOcen);
    }

}
