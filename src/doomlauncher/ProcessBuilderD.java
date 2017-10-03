/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

/**
 * @author loludaed
 *
 * @author loludaed
 */
public class ProcessBuilderD extends Observable implements Runnable{
    private String outEXELine;
    private String outEXE=new String();


    BufferedReader bufferedReader;
    InputStream inputStream;
    InputStream errorStream;

    private Process process;


    public ProcessBuilderD(String[] cmd){
        try {
            
            process = Runtime.getRuntime().exec(cmd);
            inputStream = process.getInputStream();
            errorStream = process.getErrorStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);



            Thread thread=new Thread(this);
            thread.start();


        } catch (IOException e) {
           Printer.print(e.toString());
        }

    }

    public int exitValue(){
        try {
            return process.exitValue();
        } catch (Exception e) {
            return 1;
        }

    }

    private void closeProcess(){
        try {
            deleteObservers();
        } catch (Exception e) {
           Printer.print(e.toString());
        }
    }



    public String getOutEXE(){
        return outEXE;
    }

    public String getOutEXELine(){
        return outEXELine;
    }

    private void stateChanged(){
        setChanged();
        notifyObservers();
    }



    public void run() {
        try {


            while ((outEXELine = bufferedReader.readLine()) != null && exitValue()==1) {
               if (exitValue()==0) {
                    closeProcess();
                    break;
                }else{
                Printer.print(outEXELine);
                outEXE=outEXE.concat(outEXELine);
               // stateChanged();

                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }




            }
        } catch (IOException ex) {
           Printer.print(ex.toString());
        }
    }


}
