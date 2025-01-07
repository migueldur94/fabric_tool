/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.core.configuration;

/**
 *
 * @author Usuario
 */
public class Configuration {

    private String pathJar;
    private String pathProject;
    private String serverDomain;

    public Configuration(String pathJar, String pathProject, String serverDomain) {
        this.pathJar = pathJar;
        this.pathProject = pathProject;
        this.serverDomain = serverDomain;
    }

    public String getPathJar() {
        return pathJar;
    }

    public String getServerDomain() {
        return serverDomain;
    }

    public void setPathJar(String pathJar) {
        this.pathJar = pathJar;
    }

    public void setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
    }

    public String getPathProject() {
        return pathProject;
    }

    public void setPathProject(String pathProject) {
        this.pathProject = pathProject;
    }
    
    

}
