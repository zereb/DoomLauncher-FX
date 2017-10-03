/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

import java.util.Observable;

/**
 *
 * @author loludaed
 */
public class Printer extends Observable{
    public static Printer printer;
    public static String log= new String();
    public static String logStr= new String();
    
    
    

    public Printer() {
        printer=this;
        print("Hello");
    }
    
    public void stateChanged(){
        setChanged();
        notifyObservers();
    }
    
    public static String getLogStr(){
        logStr=log;
        log ="";
        return logStr;
    }
    
    public static void print(String s){
        System.out.println(s);
        log=log.concat(s)+"\n";
        printer.stateChanged();
        
    }

   
}
