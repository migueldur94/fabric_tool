/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.infrastructure.jar;

/**
 *
 * @author Usuario
 */
public class TreeNode {

    private String treeName;
    private String packageName;

    public TreeNode(String treeName, String packageName) {
        this.treeName = treeName;
        this.packageName = packageName;
    }

    public String getTreeName() {
        return treeName;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public String toString() {
        return treeName;
    }

}
