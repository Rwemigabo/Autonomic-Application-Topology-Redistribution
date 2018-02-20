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
import com.spotify.docker.client.messages.Image;
import java.util.ArrayList;
import java.util.List;
/**
 * Connects to docker and gathers image info about the topologies(yml files)
 * the manager then prompts the feedback control loops to run on each of the containers of a service available in the running topology
 * @author eric
 */
public class DockerManager {
    private DockerClient cli;
    private List<Image> images;
    private List<Container> containers;
    
    public DockerManager() throws DockerCertificateException, DockerException, InterruptedException{
    cli = DefaultDockerClient.fromEnv().build();
    images = cli.listImages();
    containers = cli.listContainers();
    }
    
    public void repopulateImagesList() throws DockerException, InterruptedException{
        images = cli.listImages();
    }
    
    public void repopulateContainersList() throws DockerException, InterruptedException{
        containers = cli.listContainers();
    }
    
    public Container getContainer(String id){
        for (Container cont : containers) {
            if (cont.id().equals(id)){
                return cont;
            }
        }return null;
    }
    
    public Image getImage(String id){
        for (Image img : images) {
            if (img.id().equals(id)){
                return img;
            }
        }return null;
    }
    
    
}
