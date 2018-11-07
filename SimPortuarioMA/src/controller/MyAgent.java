/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jade.core.Agent;
import static jade.tools.sniffer.Agent.i;
import jade.util.leap.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas
 */
public class MyAgent extends Agent {

    protected void setup() {
        System.out.println("Olá. Eu sou um agente!");
        System.out.println("Todas as minhas informações: \n" + getAID());
        System.out.println("Meu nome local é: " + getAID().getLocalName());
        System.out.println("Meu nome global (GUID) é: " + getAID().getName());
        System.out.println("Meus endereços são:");
        Iterator it = getAID().getAllAddresses();
        while (it.hasNext()) {
            System.out.println("- " + it.next());
        }

        try {
            wait(10000);
            doDelete();
        } catch (InterruptedException ex) {
            Logger.getLogger(MyAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Morri!");
    }

}
