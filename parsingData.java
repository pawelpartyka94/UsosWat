package com.example.pawel.usoswat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pawel on 14.01.17.
 */

public class parsingData
{
    public static void Load()
    {
        Scanner scan =null;
        ArrayList<String> list = new ArrayList<>();
        try
        {
            File f = new File("/home/pawel/AndroidStudioProjects/UsosWat/app/src/main/assets");
            scan = new Scanner(f);
        }
        catch (FileNotFoundException ex)
        {
        }

        //wzorzec
        String regex = ">[^>]+</a>|>[^>]+</span>|tbody id=\"tab.\" >";

        //Kompilacja wzorca
        Pattern pattern = Pattern.compile(regex);

        while (scan.hasNextLine())
        {
            //Uzyskanie matchera
            Matcher matcher = pattern.matcher(scan.nextLine());
            if (matcher.find()==true)
            {
                //dodanie do listy dopasowania
                list.add(matcher.group());
            }

        }
    }
}
