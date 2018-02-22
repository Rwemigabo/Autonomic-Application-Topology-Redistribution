/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerStats;
import com.spotify.docker.client.messages.Image;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton class
 * Connects to docker and gathers image info about the topologies(yml files) the
 * manager then prompts the feedback control loops to run on each of the
 * containers of a service available in the running topology
 *
 * @author eric
 */
public class DockerManager {

    private final DockerClient cli;
    private List<Image> images;
    private List<Container> containers;
    private List<String> monitored;
    
    private static DockerManager instance;    //private static final DockerManager instance; //private static final DockerManager instance;

//    static {
//        try {
//            instance = new DockerManager();
//        } catch (DockerCertificateException | DockerException | InterruptedException ex) {
//            Logger.getLogger(DockerManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public DockerManager() throws DockerCertificateException, DockerException, InterruptedException {
        cli = DefaultDockerClient.fromEnv().build();
        images = cli.listImages();
        containers = cli.listContainers();
        createMonitors();
    }

    public static DockerManager getInstance() {
        return instance;
    }

    public void repopulateImagesList() throws DockerException, InterruptedException {
        images = cli.listImages();
    }

    public void repopulateContainersList() throws DockerException, InterruptedException {
        containers = cli.listContainers();
    }

    public Container getContainer(String id) {
        for (Container cont : containers) {
            if (cont.id().equals(id)) {
                return cont;
            }
        }
        return null;
    }
    
    private void createMonitors() throws DockerException, InterruptedException {
        MonitorManager mm = MonitorManager.getInstance();
        for (Container cont : containers) {
            if(monitored.contains(cont.id())){System.out.println("Already being monitored");}
            else{
                mm.newMonitor(cont.id());
                monitored.add(cont.id());
                System.out.println(cont.id() + "Monitored");
            }
        }
    }

    public Image getImage(String id) {
        for (Image img : images) {
            if (img.id().equals(id)) {
                return img;
            }
        }
        return null;
    }

    public ContainerStats getContainerStats(String id) throws DockerException, InterruptedException {
        ContainerStats stats = cli.stats(id);
        return stats;
    }
    
    public static void main(String[] args) {
         try {
            instance = new DockerManager();
        } catch (DockerCertificateException | DockerException | InterruptedException ex) {
            Logger.getLogger(DockerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
