/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.ui;

/**
 *
 * @author Usuario
 */
public interface BoardAction {
    void openClassName(String className);
    void refreshSession();

    public void removeCard(Card aThis);
}
