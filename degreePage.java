package com.example.pawel.usoswat;

import android.content.res.AssetManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.id.list;

public class degreePage extends AppCompatActivity
{

    String text = null;
    ArrayList<String> parsingData =null;
    ArrayList<String> list = new ArrayList<>();
    String [][] tab = null;
    int numberOfTD = 0;
    int row = 0;
    int column = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree_page);

        try
        {
            InputStream is = getAssets().open("subjects.txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            text = new String(buffer);
            parsingData = new ArrayList<String>(Arrays.asList(text.split("\n")));

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        String regex =  ">[^>]+</a>|>[^>]+</span>|tbody id=\"tab.\" > | <td>";

        //Kompilacja wzorca
        Pattern pattern = Pattern.compile(regex);


        Iterator it = parsingData.iterator();

        while (it.hasNext())
        {
            //Uzyskanie matchera
            Matcher matcher = pattern.matcher(it.next().toString());

            if (matcher.find() == true)
            {
                if (matcher.group().indexOf("<td>") != -1)
                {
                    numberOfTD++;
                    if (numberOfTD % 2 == 0)
                    {
                        continue;
                    }
                }
                //dodanie do listy dopasowania
                list.add(matcher.group());
            }
        }


        String[][] tab = new String[numberOfTD][numberOfTD];


        for (String id : list)
        {
            if (id.indexOf("<td>") != -1)
            {
                row++;
                column = 0;
                continue;
            }
            tab[row][column] = id;
            //System.out.println(tab[row][column]);
            column++;
        }


        ArrayList <String> subject = new ArrayList<>();
        ArrayList <String> numberOfSubject = new ArrayList<>();
        ArrayList <String> numberOfSemestr = new ArrayList<>();
        ArrayList <String> degree = new ArrayList<>();



        //dodaje do listy przedmioty
        int i=1;
        while (tab[i][0]!=null)
        {
            tab[i][0] = tab[i][0].replaceAll("</span>", "").replaceAll("</a>", "").replaceAll(">","");
            subject.add(tab[i][0]);
            i++;
        }


        i=1;
        //dodaje do listy numery przedmiotow
        while (tab[i][1]!=null)
        {
            tab[i][1] = tab[i][1].replaceAll("</span>", "").replaceAll("</a>", "").replaceAll(">","");
            numberOfSubject.add(tab[i][1]);
            i++;
        }

        i=1;
        //dodaje do listy numery przedmiotow na semestrze
        while (tab[i][2]!=null)
        {
            tab[i][2] = tab[i][2].replaceAll("</span>", "").replaceAll("</a>", "").replaceAll(">","");
            numberOfSemestr.add(tab[i][2]);
            i++;
        }


        // dodaje do listy rodzaje zajec
        i=1;
        int y = 3;

        String adhoc = "";

        while (tab[i][y]!=null)
        {
            adhoc += tab[i][y];
            y++;
            try
            {
                if (tab[i][y]==null)
                {
                    adhoc += ";";
                    y=3;
                    i++;
                }
            }
            catch(NullPointerException e)
            {
                break;
            }

        }

        StringTokenizer token = new StringTokenizer(adhoc, ";" );
        while (true)
        {
            try
            {
                degree.add(token.nextToken());
            }
            catch(NoSuchElementException e)
            {
                break;
            }
        }

        ArrayList<String> content = new ArrayList<>();

        String regex2 = ">[^>]+</a>";

        //Kompilacja wzorca
        Pattern pattern2 = Pattern.compile(regex2);

        Integer[] indexOfRegex;
        String doneStr = "";
        int c = 0;

        for (String id : degree)
        {
            Matcher matcher2 = pattern2.matcher(id);
            indexOfRegex = new Integer[500];

            while (matcher2.find())
            {
                indexOfRegex[c] = matcher2.start();
                c++;
            }
            indexOfRegex[c] = id.length();
            doneStr = id.substring(indexOfRegex[0], indexOfRegex[1]) + "\n";
            if (indexOfRegex[2] != null) {
                doneStr += id.substring(indexOfRegex[1], indexOfRegex[2]) + "\n";
            }

            if (indexOfRegex[3] != null) {
                doneStr += id.substring(indexOfRegex[2], indexOfRegex[3]) +"\n";
            }
            if (indexOfRegex[4] != null) {
                doneStr += id.substring(indexOfRegex[3], indexOfRegex[4]) + "\n";
            }
            doneStr = doneStr.replaceAll("</span>", "  ").replaceAll("</a>", " : ").replaceAll(">","");
            content.add(doneStr);
            c = 0;
        }

        degree.clear();

        ListView listView = (ListView) findViewById(R.id.listV);
        testadapter adapter = new testadapter(this, subject,numberOfSubject, numberOfSemestr, content);
        listView.setAdapter(adapter);

    }

}
