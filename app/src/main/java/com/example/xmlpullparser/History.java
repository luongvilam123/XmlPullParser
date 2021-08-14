package com.example.xmlpullparser;

import androidx.annotation.NonNull;

public class History {
    String input;
    String output;
    String datetime;
    String currencyend;
    public History(){

    }
    public History(  String input,  String output,String datetime,String currencyend){
        this.currencyend=currencyend;
        this.output=output;
        this.input=input;
        this.datetime=datetime;
    }



    @NonNull
    @Override
    public String toString() {
        return this.input+" USD= "+this.output+ " "+this.currencyend;
    }
}
