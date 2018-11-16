/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.agentes;

import controller.ambiente.Caminhao;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.util.leap.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class AgentGate extends Agent{
    
    List <Caminhao> caminhoes = new ArrayList();
    
    protected void setup(){
        System.out.println("Olá. Eu sou um agente!");
        System.out.println("Todas as minhas informações: \n" + getAID());
        System.out.println("Meu nome local é: " + getAID().getLocalName());
        System.out.println("Meu nome global (GUID) é: " + getAID().getName());
        System.out.println("Meus endereços são:");
        Iterator it = getAID().getAllAddresses();
        while (it.hasNext()) {
            System.out.println("- " + it.next());
        }
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                if(caminhoes.size() > 0){
                    System.out.println("Atendendo um caminhão!");
                }else{
                    System.out.println("Fila do Gate livre!");
                    block(5000);
                    System.out.println("Verificando se existem caminhões na fila");
                }
            }
        });
    }

    public List<Caminhao> getCaminhoes() {
        return caminhoes;
    }

    public void addCaminhaoFila(Caminhao caminhao) {
        this.caminhoes.add(caminhao);
    }
    
}
