/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.ui;

import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.PlainTextOutput;
import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.authentication.repository.ConfigurationRepository;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Usuario
 */
public class EditorCode extends JTextPane {

    private static final Map<String, String> methodClassMap = new HashMap<>();
    private final ConfigurationRepository configurationRepository;

    public EditorCode(String className, ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
        setEditable(false);
        setContentType("text/plain");

        this.loadClass(className);
    }

    private void loadClass(String className) {
        Configuration configuration = configurationRepository.getConfiguration();
        String absolutePathJars = configuration.getPathJar();
        className = className.replaceAll("\\.", "/") + ".class";

        Path jars = Paths.get(absolutePathJars);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(jars, "*.jar")) {
            for (Path subPath : stream) {
                if (!Files.isDirectory(subPath)) {
                    JarFile jarFile = new JarFile(subPath.toFile());
                    JarEntry entry = jarFile.getJarEntry(className);
                    if (entry != null) {
                        try (InputStream inputStream = jarFile.getInputStream(entry)) {

                            Path temporalDirectory = Paths.get(System.getProperty("java.io.tmpdir"), "classes_temenos");
                            if (!Files.exists(temporalDirectory)) {
                                Files.createDirectory(temporalDirectory);
                            }

                            Path tempClass = Files.createTempFile(temporalDirectory, "", ".class");
                            Path tempJava = Files.createTempFile(temporalDirectory, "", ".java");

                            Files.copy(inputStream, tempClass, StandardCopyOption.REPLACE_EXISTING);
                            String line;

                            try (Writer writer = new OutputStreamWriter(new FileOutputStream(tempJava.toFile()))) {
                                Decompiler.decompile(tempClass.toString(), new PlainTextOutput(writer));
                                System.out.println("Decompilation completed successfully!");
                            }
                            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(tempJava.toFile())))) {
                                StringBuilder stringBuilder = new StringBuilder();

                                while ((line = bufferedReader.readLine()) != null) {
                                    stringBuilder.append(line).append("\n");
                                }
                                setText(stringBuilder.toString());

                                parseCode(stringBuilder.toString());
                                addStyles();

                                // Add a mouse listener for clicks
                                addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        handleMouseClick(e);
                                    }
                                });

                            }
                            tempClass.toFile().delete();
                            tempJava.toFile().delete();
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseCode(String code) {
        // Regex to find method definitions and calls
        Pattern methodDefPattern = Pattern.compile("\\b(public|private|protected|static|\\s) +\\w+ +\\w+ *\\(.*\\) *\\{?");
        Pattern methodCallPattern = Pattern.compile("\\b\\w+\\s*\\(");
        Pattern packagePattern = Pattern.compile("package\\s+([\\w\\.]+);");
        Pattern classPattern = Pattern.compile("class\\s+(\\w+)");

        String currentPackage = null;
        String currentClass = null;

        Matcher packageMatcher = packagePattern.matcher(code);
        if (packageMatcher.find()) {
            currentPackage = packageMatcher.group(1);
        }

        Matcher matcher = classPattern.matcher(code);
        while (matcher.find()) {
            currentClass = matcher.group(1);
        }

        matcher = methodDefPattern.matcher(code);
        while (matcher.find()) {
            String methodSignature = matcher.group();
            String methodName = methodSignature.split("\\(")[0].split(" ")[methodSignature.split("\\(")[0].split(" ").length - 1];
            if (currentClass != null && currentPackage != null) {
                methodClassMap.put(methodName, currentPackage + "." + currentClass);
            }
        }

        matcher = methodCallPattern.matcher(code);
        while (matcher.find()) {
            String methodCall = matcher.group();
            String methodName = methodCall.split("\\(")[0].trim();
            if (methodClassMap.containsKey(methodName)) {
                System.out.println("Method call found: " + methodName);
            }
        }
    }

    private void addStyles() {
        // Add styles for Java code (simplified for example)
        StyledDocument doc = getStyledDocument();
        Style style = doc.addStyle("Keyword", null);
        StyleConstants.setForeground(style, Color.BLUE);

        // Regex to find method calls
        String regex = "\\b\\w+\\s*\\(";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getText());
        while (matcher.find()) {
            doc.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), style, false);
        }
    }

    private void handleMouseClick(MouseEvent e) {
        try {
            int offset = viewToModel(e.getPoint());
            StyledDocument doc = getStyledDocument();
            Element element = doc.getCharacterElement(offset);
            String clickedWord = doc.getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset()).trim();

            if (clickedWord.endsWith("(")) {
                // Remove the trailing '(' for method name
                clickedWord = clickedWord.substring(0, clickedWord.length() - 1);
                System.out.println("Clicked on method: " + clickedWord);

                if (methodClassMap.containsKey(clickedWord)) {
                    System.out.println("Package and Class: " + methodClassMap.get(clickedWord));
                } else {
                    System.out.println("Method not found in the map: " + clickedWord);
                }
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}
