/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.infrastructure.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Usuario
 */
public class CommandLine {

    public static void run(String[] command) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // Start the process
        Process process = processBuilder.start();

        // Reading output of the command
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // Print the output of the command
        }

        // Wait for the process to complete
        int exitCode = process.waitFor();
        System.out.println("Command executed, exit code: " + exitCode);
    }
}
