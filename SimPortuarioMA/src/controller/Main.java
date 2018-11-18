/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.ambiente.PilhaContainer;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.List;
import view.PatioView;

/**
 *
 * @author Lucas
 */
public class Main {

    public static void main(String[] args) {
        PatioView patio = new PatioView();
        PilhaContainer pilhaArmazenagem = new PilhaContainer(5, 5, 6);
        PilhaContainer pilhaNavio = new PilhaContainer(
                pilhaArmazenagem.getWidth(), 
                pilhaArmazenagem.getHeight(), 
                pilhaArmazenagem.getAlturaMaxima());
        List<Object> argumentos = new ArrayList<>();
        argumentos.add(patio);
        argumentos.add(pilhaArmazenagem);
        argumentos.add(pilhaNavio);
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl();
            ContainerController containerController = runtime.createMainContainer(profile);
            AgentController agentMain = containerController.createNewAgent("Main", "controller.MainAgent", argumentos.toArray());
            AgentController agentGate = containerController.createNewAgent("Gate", "controller.agentes.AgentGate", argumentos.toArray());
            AgentController agentRTG = containerController.createNewAgent("RTG", "controller.agentes.AgentRTG", argumentos.toArray());
            AgentController agentReachStacker = containerController.createNewAgent("ReachStacker", "controller.agentes.AgentReachStacker", argumentos.toArray());
            AgentController rma = containerController.createNewAgent("rma", "jade.tools.rma.rma", args);
            agentMain.start();
            agentGate.start();
            agentReachStacker.start();
            rma.start();
        } catch (StaleProxyException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        patio.setVisible(true);
    }
}
