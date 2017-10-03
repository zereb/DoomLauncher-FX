/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Plett Oleg
 */
public interface Constants{
   
    public static final int WINDOW_WIDTH=840;
    public static final int WINDOW_HEIGHT=580;
    public static final String TITLE_STRING="Doom Launcher 2.0";
    public static final String CONFIG_NAME="doomLauncher";
    
    
    
    public static final String CFG_PR_ENGINE="ENGINE";
    public static final String CFG_PR_IWAD_PATH="IWAD_PATH";
    public static final String CFG_PR_DEFAULT_FOLDER="DEFAULT_FOLDER";
    
    public static final ExtensionFilter EF_ALL= new ExtensionFilter("All Files", "**");
    public static final ExtensionFilter EF_WAD= new ExtensionFilter("Wad files", "*.wad");
    public static final ExtensionFilter EF_PWAD= new ExtensionFilter("Wad files", "*.wad", "*.pk3", "*.zip", "*.ini");
    
    
    public static final String[] IWAD_NAMES={
        "doom1.wad","freedoom1.wad","doom2.wad","tnt.wad","plutonia.wad",
         "freedm.wad","freedm.wad","freedoom2.wad","doom2f.wad","heretic1.wad","heretic.wad","hexen.wad",
         "hexdd.wad","strife0.wad","strife1.wad","chex.wad","chex3.wad","action2.wad","harm1.wad","hacx.wad","hacx2.wad",
         "strife.wad","hexendemo.wad","hexdemo.wad","blasphem.wad","blasphemer.wad","doom2bfg.wad","bfgdoom.wad","doomu.wad"
    };
    
    
 




}
