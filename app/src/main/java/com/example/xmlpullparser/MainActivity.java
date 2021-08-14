package com.example.xmlpullparser;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.net.URL;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String kindofmoney1;
    Spinner spinner1;
    Spinner spinner2;
    TextView textView;
    EditText edtinput;
    EditText edtoutput;
    Button btdoi;
    Button bthis;
    String input;
      String regex = "\\d+";

    ArrayList<History> list=new ArrayList<History>();
    CustomArrayAdapter arrayAdapter;
    String[] country1={"USD"};
    String[] country2={"BND","CNY","CRC","EUR","VND","AUD","ALL","DZD","ARS","CAD","GBP"};
    History h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtinput=(EditText)findViewById(R.id.edtinput);
        edtoutput=(EditText)findViewById(R.id.edtoutput);
        input=edtinput.getText().toString();


        btdoi=(Button)findViewById(R.id.btndoi);
        btdoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Fetchtask().execute();
                AddHistorytoList(h);

            }
        });
        bthis=(Button)findViewById(R.id.btnHistory);
        bthis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)list);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
        textView= (TextView)findViewById(R.id.text);
        spinner1=(Spinner)findViewById(R.id.spinner_currency);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter a1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country1);
        ArrayAdapter a2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country2);
        spinner1.setAdapter(a1);
        spinner2.setAdapter(a2);



    }
    public void AddHistorytoList(History h){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        String input=edtinput.getText().toString();
        String output=edtoutput.getText().toString();
        String currencyend=kindofmoney1;
        h=new History(input,output,formattedDate,currencyend);
        list.add(h);

    }



    private ArrayList<RssItem> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {

        ArrayList<RssItem> items=null;
        int eventType = parser.getEventType();

        RssItem rssitem=null;
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:

                    items=new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("item")){

                        rssitem =new RssItem();

                    } else if (rssitem != null){
                        if (name.equals("title")){
                            rssitem.title = parser.nextText();
                        } else if (name.equals("description")){
                            rssitem.description = parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("item") && rssitem != null){
                        items.add(rssitem);
                    }
            }
            eventType = parser.next();
        }

        return items;


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        kindofmoney1=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    String GetRate(String description){
        int tempspace=0;
        int startspace = 0;
        int endspace= 0;
        int i=0;
        for(i=0;i<description.length();i++){
            if(description.charAt(i)==' '){
                tempspace++;
            }
            if(tempspace==5 && description.charAt(i)==' '){
                startspace=i;
            }
            if(tempspace==6 && description.charAt(i)==' '){
                endspace=i;
            }
        }
        String result=description.substring(startspace+1,endspace+1);
        return result;
    }

    private class Fetchtask extends AsyncTask<String,Void,ArrayList>{
        String rssurl="https://usd.fxexchangerate.com/rss.xml";
        ArrayList<RssItem> items ;
        @Override
        protected void onPostExecute(ArrayList arrayList) {
            ChangeUI();
            super.onPostExecute(arrayList);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList doInBackground(String... params) {
            XmlPullParserFactory pullParserFactory;


            try {
                pullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = pullParserFactory.newPullParser();
                URL urll = new URL(rssurl);
                InputStream inputStream = urll.openConnection().getInputStream();

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);

                items = parseXML(parser);
                String description;
                for (int i = 0; i < items.size(); i++) {
                    description = items.get(i).getDescription();
                    items.get(i).setDescription(GetRate(description));
                }

            } catch (XmlPullParserException e) {

                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                           e.printStackTrace();  
            }
            return items;
        }
        void ChangeUI(){
            try{
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getTitle().contains(kindofmoney1)) {
                    Double rate = Double.parseDouble(items.get(i).getDescription());
                    Double input = Double.parseDouble(edtinput.getText().toString());
                    Double output = rate * input;
                    edtoutput.setText(output.toString());

                }
            }
        }catch(Exception e){
                 Toast.makeText(getApplicationContext(),"Không được chứa ký tự chữ !!", Toast.LENGTH_LONG).show();
            }
        }
        }
    }