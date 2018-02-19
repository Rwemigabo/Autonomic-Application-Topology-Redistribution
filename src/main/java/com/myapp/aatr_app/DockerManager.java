/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
/**
 * Connects to docker and gathers image info about the topologies(yml files)
 * the manager then prompts the feedback control loops to run on each of the containers of a service available in the running topology
 * @author eric
 */
public class DockerManager {
    public DockerClient cli;
    
    public DockerManager() throws DockerCertificateException{
    cli = DefaultDockerClient.fromEnv().build();
    }
    
}
