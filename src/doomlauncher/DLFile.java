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
    private String parent;
    private File fparent;
    
    public DLFile(File f){
        name=f.getName();
        path=f.getAbsolutePath();
        parent=f.getParent();
        fparent=f.getParentFile();
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

    public String getParent() {
        return parent;
    }

    public File getFparent() {
        return fparent;
    }
    
    
    
    
}
