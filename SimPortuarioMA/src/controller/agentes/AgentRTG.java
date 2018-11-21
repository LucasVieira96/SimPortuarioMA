/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.agentes;

import controller.ambiente.Container;
import controller.ambiente.MapaPilha;
import controller.ambiente.Navio;
import controller.ambiente.PilhaContainer;
import controller.behaviours.DelayBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.util.leap.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.PatioView;

/**
 *
 * @author Lucas
 */
public class AgentRTG extends Agent {

    private Navio navio;
    private PilhaContainer pilha;
    private PilhaContainer pilhaNavio;
    private List<MapaPilha> cntrSelecionados = new ArrayList<>();
    private Stack<Container> pilhaTemp;
    private PatioView patio;

    private void perguntaProximoNavio(Agent agent) {
        ACLMessage msgToMain = new ACLMessage(ACLMessage.INFORM);
        msgToMain.addReceiver(new AID("Main", AID.ISLOCALNAME));
        msgToMain.setContent("Main, qual o proximo navio?");
        agent.send(msgToMain);
        System.out.println(getAID().getLocalName() + ": Perguntando para o Main qual o proximo navio.");
    }

    @Override
    protected void setup() {
        patio = (PatioView) this.getArguments()[0];
        System.out.println("Olá. Eu sou um agente!");
        System.out.println("Todas as minhas informações: \n" + getAID());
        System.out.println("Meu nome local é: " + getAID().getLocalName());
        System.out.println("Meu nome global (GUID) é: " + getAID().getName());
        System.out.println("Meus endereços são:");
        Iterator it = getAID().getAllAddresses();
        while (it.hasNext()) {
            System.out.println("- " + it.next());
        }
        pilhaTemp = new Stack<>();
        pilha = (PilhaContainer) getArguments()[1];
        pilhaNavio = (PilhaContainer) getArguments()[2];

        patio.drawPilha02(pilhaNavio);

        addBehaviour(new TickerBehaviour(this, 1000 * 5) {
            @Override
            protected void onTick() {
                MessageTemplate MT1 = MessageTemplate.MatchSender(new AID("Main", AID.ISLOCALNAME));
                ACLMessage msg = receive(MT1);

                if (msg != null) {
                    if (msg.getOntology().equalsIgnoreCase("Navio")) {
                        System.out.println(getAID().getLocalName() + ": Main respondeu qual o proximo navio.");
                        try {
                            navio = (Navio) msg.getContentObject();
                        } catch (UnreadableException ex) {
                            System.out.println(getAID().getLocalName() + ": Erro ao extrair navio da mensagem do Main.");
                        }

                        if (navio == null) {
                            System.out.println(getAID().getLocalName() + ": Nenhum navio disponivel.");
                        } else {
                            System.out.println(getAID().getLocalName() + ": Iniciando operação para o navio " + navio.getNomeNavio());
                            System.out.println(getAID().getLocalName() + ": Selecionando containers para o navio " + navio.getNomeNavio());

                            cntrSelecionados.clear();

                            for (MapaPilha mapa : pilha.getMapaPilha()) {
                                if (mapa.getContainer().getNavioDestino().getNomeNavio().equals(navio.getNomeNavio())) {
                                    cntrSelecionados.add(mapa);
                                }
                            }

                            System.out.println(getAID().getLocalName() + ": " + cntrSelecionados.size() + " containers selecionados para o navio " + navio.getNomeNavio());

                            int i = 0;
                            for (MapaPilha cntrSelecionado : cntrSelecionados) {
                                for (Container item : pilha.getPilha()[cntrSelecionado.getPosWidth()][cntrSelecionado.getPosHeight()]) {
                                    i++;
                                    addBehaviour(new DelayBehaviour(myAgent, 1000 * i) {
                                        @Override
                                        public void executeAfterTimeOut() {
                                            Container container = pilha.popContainer(
                                                    cntrSelecionado.getPosWidth(),
                                                    cntrSelecionado.getPosHeight());
                                            patio.drawPilha01(pilha);
                                            System.out.println(getAID().getLocalName() + ": Operando container - " + container.getNumeracao());
                                            if (cntrSelecionado.getContainer().getNavioDestino().getNomeNavio().equals(container.getNavioDestino().getNomeNavio())
                                                    && cntrSelecionado.getContainer().getNumeracao().equals(container.getNumeracao())) {
                                                pilhaNavio.pushContainer(container, cntrSelecionado.getPosWidth(), cntrSelecionado.getPosHeight());
                                                patio.drawPilha02(pilhaNavio);
                                                for (Container pilhaTemp1 : pilhaTemp) {
                                                    pilha.pushContainer(pilhaTemp.pop(),
                                                            cntrSelecionado.getPosWidth(),
                                                            cntrSelecionado.getPosHeight());
                                                }
                                            } else {
                                                pilhaTemp.push(container);
                                            }
                                        }
                                    });
                                }
                            }
                            addBehaviour(new DelayBehaviour(myAgent, 1000 * i) {
                                @Override
                                public void executeAfterTimeOut() {
                                    System.out.println(getAID().getLocalName() + ": Finalizando a operação!");
                                }
                            });

                        }

                    } else {
                        perguntaProximoNavio(myAgent);
                    }

//                    if (msg.getOntology().equalsIgnoreCase("Atracação Navio")) {
//                        System.out.println(getAID().getLocalName() + ": Iniciando operação!");
//                        int i = 0;
//                        for (MapaPilha cntrSelecionado : cntrSelecionados) {
//                            for (Container item : pilha.getPilha()[cntrSelecionado.getPosWidth()][cntrSelecionado.getPosHeight()]) {
//                                i++;
//                                addBehaviour(new DelayBehaviour(myAgent, 1000 * i) {
//                                    @Override
//                                    public void executeAfterTimeOut() {
//                                        Container container = pilha.popContainer(
//                                                cntrSelecionado.getPosWidth(),
//                                                cntrSelecionado.getPosHeight());
//                                        System.out.println(getAID().getLocalName() + ": Operando container - " + container.getNumeracao());
//                                        if (cntrSelecionado.getContainer().getNavioDestino().getNomeNavio().equals(container.getNavioDestino().getNomeNavio())
//                                                && cntrSelecionado.getContainer().getNumeracao().equals(container.getNumeracao())) {
//                                            pilhaNavio.pushContainer(container, cntrSelecionado.getPosWidth(), cntrSelecionado.getPosHeight());
//                                            patio.drawPilha02(pilhaNavio);
//                                            for (Container pilhaTemp1 : pilhaTemp) {
//                                                pilha.pushContainer(pilhaTemp.pop(),
//                                                        cntrSelecionado.getPosWidth(),
//                                                        cntrSelecionado.getPosHeight());
//                                            }
//                                        } else {
//                                            pilhaTemp.push(container);
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                        System.out.println(getAID().getLocalName() + ": Finalizando a operação!");
//                    }
                } else {
                    perguntaProximoNavio(myAgent);
                }
            }
        });
    }
}
