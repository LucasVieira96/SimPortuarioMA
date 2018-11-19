/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.agentes;

import controller.ambiente.Container;
import controller.ambiente.MapaPilha;
import controller.ambiente.PilhaContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Lucas
 */
public class AgentRTG extends Agent {

    private PilhaContainer pilha;
    private PilhaContainer pilhaNavio;
    private List<MapaPilha> cntrSelecionados;
    private Stack<Container> pilhaTemp;

    @Override
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
        pilhaTemp = new Stack<Container>();
        pilha = (PilhaContainer) getArguments()[1];
        pilhaNavio = (PilhaContainer) getArguments()[2];
        cntrSelecionados = (List<MapaPilha>) getArguments()[3];

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                MessageTemplate MT1 = MessageTemplate.MatchSender(new AID("Main", AID.ISLOCALNAME));
                ACLMessage msg = receive(MT1);
                if (msg != null) {
                    if (msg.getOntology().equalsIgnoreCase("Atracação Navio")) {
                        System.out.println(getAID().getLocalName() + ": Iniciando operação!");
                        for (MapaPilha cntrSelecionado : cntrSelecionados) {
                            for (int i = 0;
                                    i < pilha.getPilha()[cntrSelecionado.getPosWidth()]
                                            [cntrSelecionado.getPosHeight()].size(); i++) {
                                Container container = pilha.popContainer(
                                        cntrSelecionado.getPosWidth(),
                                        cntrSelecionado.getPosHeight());
                                if (cntrSelecionado.getContainer() == container) {
                                    pilhaNavio.pushContainer(container, cntrSelecionado.getPosWidth(), cntrSelecionado.getPosHeight());
                                    for (int j = 0; j < pilhaTemp.size(); j++) {
                                        pilha.pushContainer(pilhaTemp.pop(),
                                                cntrSelecionado.getPosWidth(),
                                                cntrSelecionado.getPosHeight());
                                    }
                                    break;
                                } else {
                                    pilhaTemp.push(container);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
