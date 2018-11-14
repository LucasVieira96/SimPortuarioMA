/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.List;
import view.Patio;

/**
 *
 * @author Lucas
 */
public class Main {

    public static void main(String[] args) {
        Patio patio = new Patio();
        List<Object> argumentos = new ArrayList<>();
        argumentos.add(patio);
        try {
            Runtime rt = Runtime.instance();
            Profile p = new ProfileImpl();
            ContainerController cc = rt.createMainContainer(p);
            AgentController ac = cc.createNewAgent("Main", "controller.MainAgent", argumentos.toArray());
            AgentController rma = cc.createNewAgent("rma", "jade.tools.rma.rma", args);
            ac.start();
            rma.start();
        } catch (StaleProxyException e) {
            System.out.println("Erro: " + e.getMessage());
        }
//        patio.setVisible(true);
    }
}
