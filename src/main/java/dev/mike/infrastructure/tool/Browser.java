/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.infrastructure.tool;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Usuario
 */
public class Browser {

    public static void openUrl(String url) {
        // Check if the Desktop API is supported on the current platform
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                // Open the URL in the default browser
                desktop.browse(new URI(url));
                System.out.println("URL opened in the default browser successfully!");
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Desktop API is not supported on the current platform.");
        }
    }
}
