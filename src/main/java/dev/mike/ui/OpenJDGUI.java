/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.authentication.repository.ConfigurationRepository;
import org.jd.gui.App;

/**
 *
 * @author Usuario
 */
public class OpenJDGUI {

    public static void loadClassName(ConfigurationRepository configurationRepository, String className) throws Exception {
        Configuration configuration = configurationRepository.getConfiguration();

        final String classNameJava = className.replaceAll("\\.", "\\" + File.separator) + ".java";
        Optional<Path> javaFile = Files.walk(Paths.get(configuration.getPathProject())).filter(Files::isRegularFile).filter(file -> file.endsWith(classNameJava)).findFirst();
        if (javaFile.isPresent()) {
            String[] command = {"cmd.exe", "/c", "code", javaFile.get().toFile().getPath()};
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
            return;
        }
        ////////////////////////////////////////////////////////////////////////
        String absolutePathJars = configuration.getPathJar();
        String classNameEntry = className.replaceAll("\\.", "/") + ".class";

        Path jars = Paths.get(absolutePathJars);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(jars, "*.jar")) {
            for (Path subPath : stream) {
                if (!Files.isDirectory(subPath)) {
                    JarFile jarFile = new JarFile(subPath.toFile());
                    JarEntry entry = jarFile.getJarEntry(classNameEntry);
                    if (entry != null) {
                        App.main(new String[]{subPath.toString().replaceAll("\\\\", "\\/") + "!/" + classNameEntry});
                    }
                }
            }
        }
    }
}
