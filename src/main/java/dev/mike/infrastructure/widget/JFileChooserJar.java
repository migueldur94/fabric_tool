/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.infrastructure.widget;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Usuario
 */
public class JFileChooserJar extends JFileChooser {
    
    private static FileFilter jarFilter;
    
    static {
       jarFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                // Allow directories so the user can navigate through them
                if (file.isDirectory()) {
                    return true;
                }
                // Allow files with .jar extension
                return file.getName().toLowerCase().endsWith(".jar");
            }
            
             @Override
            public String getDescription() {
                return "JAR Files (*.jar)";
            }
        }; 
    }

    public JFileChooserJar() {
        super();
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setFileFilter(jarFilter);
    }

}
