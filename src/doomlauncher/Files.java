/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doomlauncher;

import JConfig.JConfig;
import java.util.List;
import java.io.File;
import java.rmi.server.RemoteServer;
import java.util.ArrayList;

/**
 *
 * @author lul
 */
public class Files implements Constants{
    
    public JConfig dlConfig;
    
    
    
    private List<DLFile> engines = new ArrayList<DLFile>();
    private List<DLFile> iwads = new ArrayList<DLFile>();
    private List<DLFile> pwads = new ArrayList<DLFile>();
    
    public Files(){
           dlConfig=new JConfig(CONFIG_NAME);
        if(dlConfig.isConfigEmpty()){
           //Printer.print("Setting defauld config");
            dlConfig.setConfigValue(CFG_PR_ENGINE, "Set engine");
            dlConfig.setConfigValue(CFG_PR_IWAD_PATH, "/");
        }
        
        readConfig();
        
       
    }
    
    public void addIwad(File f){
        dlConfig.setConfigValue(CFG_PR_IWAD_PATH,dlConfig.getConfigValue(CFG_PR_IWAD_PATH)+","+f.getParent());
        readConfig();
    }
    
    public void addEngine(File f){
        dlConfig.setConfigValue(CFG_PR_ENGINE,dlConfig.getConfigValue(CFG_PR_ENGINE)+","+f.getAbsolutePath());
        readConfig();
    }
    
    public void addPwad(File f){
        pwads.add(new DLFile(f));
    }
    
    public void setEngine(File f, int index){
        int i=0; String combination = "";
        for (String engine : dlConfig.getConfigValue(CFG_PR_ENGINE).split(",")) {
            if(i==index){
                combination=dlConfig.getConfigValue(CFG_PR_IWAD_PATH).replace(engine, f.getAbsolutePath());
               //Printer.print("jej: "+combination);
            }
            i++;
//            if(i==index){
//                combination=combination+","+f.getAbsolutePath();
//               //Printer.print(engine+" change on "+f.getAbsolutePath());
//            }else{
//                combination=combination+","+engine;
//               Printer.print(combination);
//            }
        }
        dlConfig.setConfigValue(CFG_PR_ENGINE, combination);
        readConfig();
    }
    
    public void setIwad(File f, int index){
        int i=0; String combination = "";
        for (String iwad : dlConfig.getConfigValue(CFG_PR_IWAD_PATH).split(",")) {
            if(i==index){
                combination=combination+f.getAbsolutePath();
            }else{
                combination=combination+iwad;
            }
        }
        dlConfig.setConfigValue(CFG_PR_IWAD_PATH, combination);
        readConfig();
    }
    
    public List<DLFile> getEngines(){
        return engines;
        
    }
    
    public List<DLFile> getIwads(){
        return iwads;
    }
   
    public List<DLFile> getPwads(){
        return pwads;
    }
    
    public DLFile getEngine(int index){
        return engines.get(index);
    }
    
    public DLFile getIwad(int  index){
        return iwads.get(index);
    }
  
    public DLFile getPwad(int  index){
        return pwads.get(index);
    }
    
    public void removePwad(int index){
        pwads.remove(index);
    }
    
    public void removeAllPwad(){
        pwads.clear();
    }
    
        

    private void readConfig(){
        engines.clear();
        iwads.clear();
        for (String engine : dlConfig.getConfigValue(CFG_PR_ENGINE).split(",")) {
            engines.add(new DLFile(engine));
        }
        for (String iwadDirectory : dlConfig.getConfigValue(CFG_PR_IWAD_PATH).split(",")) {
            for (File file : new File(iwadDirectory).listFiles()) {
                for (String iwadName : IWAD_NAMES) {
                    if (iwadName.contentEquals(file.getName().toLowerCase())) {
                        iwads.add(new DLFile(file));
                    }
                }
            }
        }
    }
    
}
