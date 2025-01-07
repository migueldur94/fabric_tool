/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package dev.mike.ui;

import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.PlainTextOutput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.authentication.repository.ConfigurationRepository;
import dev.mike.infrastructure.jar.TreeNode;
import dev.mike.infrastructure.tool.CommandLine;
import dev.mike.infrastructure.tool.GetDownloadFolder;
import dev.mike.infrastructure.widget.JFileChooserJar;
import dev.mike.infrastructure.widget.tree.INodeSelected;

/**
 *
 * @author Usuario
 */
public class JarDialog extends javax.swing.JDialog {

    private File selectedFile;
    private File selectedTargetJar;
    private static final String PATH_JAVA = "\\src\\main\\java";
    private Configuration configuration;
//    private String entrySelected;
    private List<String> entriesSelected = new ArrayList<>();

    /**
     * Creates new form JarDialog
     */
    public JarDialog(java.awt.Frame parent, ConfigurationRepository configurationRepository) {
        super(parent, true);
        initComponents();

        configuration = configurationRepository.getConfiguration();
    }

    private void populateTreeWithClasses(DefaultMutableTreeNode root, String jarFilePath) {
        // Read the JAR file and organize the classes into a TreeMap
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.endsWith(".class")) {
                    // Convert the file path to a class name with package structure
                    String className = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                    String[] packages = className.split("\\.");

                    // Build the tree structure
                    DefaultMutableTreeNode current = root;
                    for (int i = 0; i < packages.length; i++) {
                        String packageName = packages[i];
                        DefaultMutableTreeNode child = findOrCreateNode(current, packageName, entryName);
                        current = child;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DefaultMutableTreeNode findOrCreateNode(DefaultMutableTreeNode parent, String name, String entryName) {
        // Traverse existing children to find the node with the given name
        for (int i = 0; i < parent.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);
            if (((TreeNode) child.getUserObject()).getTreeName().equals(name)) {
                return child;
            }
        }
        // If not found, create a new node
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(new TreeNode(name, entryName));
        parent.add(node);
        return node;
    }

    private void openClass(String nodeName) throws Exception {
        String pathJavaFolder = configuration.getPathProject() + PATH_JAVA;

        JarFile jarFile = new JarFile(selectedFile);
        JarEntry entry = jarFile.getJarEntry(nodeName);
        if (entry != null) {
            String packagePath = pathJavaFolder + File.separator + entry.getName().substring(0, entry.getName().lastIndexOf("/"));
            String pathClassFile = pathJavaFolder + File.separator + entry.getName();
            try (InputStream inputStream = jarFile.getInputStream(entry)) {
                // Write the content to a new file
                Files.createDirectories(Paths.get(packagePath));

                Files.copy(inputStream, Paths.get(pathClassFile), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Class file copied successfully!");
            }

            try (Writer writer = new OutputStreamWriter(new FileOutputStream(Paths.get(pathClassFile.replace(".class", ".java")).toFile()))) {
                Decompiler.decompile(pathClassFile, new PlainTextOutput(writer));
                System.out.println("Decompilation completed successfully!");
            }

            Files.delete(Paths.get(pathClassFile));

//            CommandLine.run(new String[]{"cmd.exe", "/c", "code ", pathClassFile.replace(".class", ".java")});
        }
    }

    private void loadJarToTree(INodeSelected nodeSelected) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Classes");
        populateTreeWithClasses(root, selectedFile.getAbsolutePath());

        treeJar.setModel(new DefaultTreeModel(root));
        treeJar.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath selectedPath = treeJar.getSelectionPath();
                if (selectedPath != null) {
                    DefaultMutableTreeNode selectedNode
                            = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    if (selectedNode.isLeaf()) {
                        try {
                            nodeSelected.onSelection(((TreeNode) selectedNode.getUserObject()).getPackageName());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    private void addEntryToJar(JarFile jarFile, JarEntry jarEntry, JarOutputStream jos) throws IOException {
        try (InputStream is = jarFile.getInputStream(jarEntry)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                jos.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnJar = new javax.swing.JButton();
        btnMoveSelectedClasses = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeJar = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        btnBuildCustomProject = new javax.swing.JButton();
        btnChooseTargetJar = new javax.swing.JButton();
        btnLoadCustomJar = new javax.swing.JButton();
        btnChooseJarToMoveClasses = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnJar.setText("Choose Jar ");
        btnJar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJarActionPerformed(evt);
            }
        });

        btnMoveSelectedClasses.setText("Move Selected Classes");
        btnMoveSelectedClasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveSelectedClassesActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Pending");
        treeJar.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(treeJar);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("All the classes will be moved to a new Jar in Downloads folder");

        btnBuildCustomProject.setText("Build Custom Project");
        btnBuildCustomProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuildCustomProjectActionPerformed(evt);
            }
        });

        btnChooseTargetJar.setText("Choose target jar");
        btnChooseTargetJar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseTargetJarActionPerformed(evt);
            }
        });

        btnLoadCustomJar.setText("Load Custom Jar");
        btnLoadCustomJar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadCustomJarActionPerformed(evt);
            }
        });

        btnChooseJarToMoveClasses.setText("Choose jar to Move Classes");
        btnChooseJarToMoveClasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseJarToMoveClassesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuildCustomProject)
                            .addComponent(btnJar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnChooseJarToMoveClasses, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLoadCustomJar, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnChooseTargetJar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMoveSelectedClasses))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJar)
                    .addComponent(btnChooseJarToMoveClasses))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoveSelectedClasses)
                    .addComponent(btnChooseTargetJar)
                    .addComponent(btnBuildCustomProject)
                    .addComponent(btnLoadCustomJar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnJarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJarActionPerformed
        JFileChooser fileChooser = new JFileChooserJar();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            loadJarToTree(new INodeSelected() {
                @Override
                public void onSelection(String nodeName) {
                    try {
                        openClass(nodeName);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }//GEN-LAST:event_btnJarActionPerformed

    private void btnMoveSelectedClassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveSelectedClassesActionPerformed
        if (selectedTargetJar == null || !selectedTargetJar.exists()) {
            JOptionPane.showMessageDialog(null, "Please choose target jar", "Message", JOptionPane.WARNING_MESSAGE);
        }
        try {
            try (JarFile jarTarget = new JarFile(selectedTargetJar); FileOutputStream fos = new FileOutputStream(GetDownloadFolder.getPath() + File.separator + System.currentTimeMillis() + ".jar"); JarOutputStream jos = new JarOutputStream(fos)) {
                Enumeration<JarEntry> entries = jarTarget.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entryIteration = entries.nextElement();
                    String entryName = entryIteration.getName();

                    // If the entry is the class file to be replaced, skip adding it
                    if (entriesSelected.contains(entryName)) {
                        continue;
                    }

                    // Add the existing entry to the new JAR file
                    jos.putNextEntry(new JarEntry(entryName));
                    addEntryToJar(jarTarget, entryIteration, jos);
                    jos.closeEntry();
                }

                JarFile jarFile = new JarFile(selectedFile);

                entriesSelected.forEach((entryName) -> {
                    JarEntry entry = jarFile.getJarEntry(entryName);
                    if (entry != null) {
                        try {
                            jos.putNextEntry(new JarEntry(entryName));
                            addEntryToJar(jarFile, entry, jos);
                            jos.closeEntry();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                // Now add the new class file to the JAR

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnMoveSelectedClassesActionPerformed

    private void btnBuildCustomProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuildCustomProjectActionPerformed
        try {
            CommandLine.run(new String[]{"cmd.exe", "/c", "mvn package -f ", configuration.getPathProject() + File.separator + "pom.xml"});
            JOptionPane.showMessageDialog(null, "Build was success!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnBuildCustomProjectActionPerformed

    private void btnLoadCustomJarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadCustomJarActionPerformed
        entriesSelected.clear();
        String pathProject = configuration.getPathProject();
        selectedFile = Paths.get(pathProject + File.separator + "target" + File.separator + "CAMBPayments-0.0.1.jar").toFile();
        loadJarToTree(new INodeSelected() {
            @Override
            public void onSelection(String nodeName) {
                entriesSelected.add(nodeName);
            }
        });
    }//GEN-LAST:event_btnLoadCustomJarActionPerformed

    private void btnChooseTargetJarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseTargetJarActionPerformed
        JFileChooser fileChooser = new JFileChooserJar();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedTargetJar = fileChooser.getSelectedFile();
        }
    }//GEN-LAST:event_btnChooseTargetJarActionPerformed

    private void btnChooseJarToMoveClassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseJarToMoveClassesActionPerformed
        JFileChooser fileChooser = new JFileChooserJar();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            loadJarToTree(new INodeSelected() {
                @Override
                public void onSelection(String nodeName) {
                    entriesSelected.add(nodeName);
                }
            });
        }
    }//GEN-LAST:event_btnChooseJarToMoveClassesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuildCustomProject;
    private javax.swing.JButton btnChooseJarToMoveClasses;
    private javax.swing.JButton btnChooseTargetJar;
    private javax.swing.JButton btnJar;
    private javax.swing.JButton btnLoadCustomJar;
    private javax.swing.JButton btnMoveSelectedClasses;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree treeJar;
    // End of variables declaration//GEN-END:variables
}
