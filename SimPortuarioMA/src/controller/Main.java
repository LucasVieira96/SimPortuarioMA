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

/**
 *
 * @author 5922496
 */
public class Main {

    public static void main(String[] args) {
        try {
            Runtime rt = Runtime.instance();
            Profile p = new ProfileImpl();
            ContainerController cc = rt.createMainContainer(p);
            AgentController ac = cc.createNewAgent("Lucas", "controller.MainAgent", args);
            AgentController rma = cc.createNewAgent("rma", "jade.tools.rma.rma", args);
            ac.start();
            rma.start();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
