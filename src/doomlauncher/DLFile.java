/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doomlauncher;

import com.sun.javafx.sg.prism.NGTriangleMesh;
import java.io.File;

/**
 *
 * @author lul
 */
public class DLFile {
    
    private String name;
    private String path;
    
    public DLFile(File f){
        name=f.getName();
        path=f.getAbsolutePath();
       ////Printer.print(name+" : "+path);
    }
    
      public DLFile(String s){
        this(new File(s));
    }
    
    public String getName(){
        return name;
    }
    
    public String getPath(){
        return path;
    }
    
}
