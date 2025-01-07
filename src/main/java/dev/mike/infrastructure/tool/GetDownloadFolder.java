/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.infrastructure.tool;

import java.io.File;

/**
 *
 * @author Usuario
 */
public class GetDownloadFolder {

    public static String getPath() {
        String userHome = System.getProperty("user.home");

        // Determine the OS and construct the path accordingly
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            // Windows
            return userHome + File.separator + "Downloads";
        } else if (osName.contains("mac")) {
            // macOS
            return userHome + File.separator + "Downloads";
        } else if (osName.contains("nix") || osName.contains("nux")) {
            // Linux/Unix
            return userHome + File.separator + "Downloads";
        } else {
            // Unsupported OS or unknown
            return null;
        }
    }
}
