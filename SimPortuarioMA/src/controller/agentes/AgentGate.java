/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.agentes;

import controller.ambiente.Caminhao;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class AgentGate extends Agent {

    List<Caminhao> filaGate = new ArrayList();
    Caminhao atendendo = null;
    Caminhao caminhao = null;

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
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                MessageTemplate MT1 = MessageTemplate.MatchSender(new AID("Main", AID.ISLOCALNAME));
                MessageTemplate MT2 = MessageTemplate.MatchSender(new AID("ReachStacker", AID.ISLOCALNAME));
                ACLMessage msgMain = receive(MT1);//captura novas mensagens
                ACLMessage msgReachStacker = receive(MT2);//captura novas mensagens
                try {
                    if (msgMain != null) {
                        if (msgMain.getContentObject() != null) {
                            caminhao = (Caminhao) msgMain.getContentObject();
                            System.out.println(getAID().getLocalName() + ": Caminhão recebido!"
                                    + "\nINFO CAMINHÃO"
                                    + "\nPlaca: " + caminhao.getPlaca()
                                    + "\nContainer: " + caminhao.getContainer().getNumeracao());
                            filaGate.add(caminhao);
                            System.out.println("Caminhões na Fila: " + filaGate.size());
                        }
                        if (filaGate.size() > 0) {
                            if (atendendo == null) {
                                if (filaGate.size() > 0) {
                                    System.out.println(getAID().getLocalName() + ": Atendendo um caminhão!");
                                    System.out.println("Falta(m) " + filaGate.size() + " para ser(em) atendido(s)!");
                                    atendendo = filaGate.get(0);
                                    atendendo.setStatus(Caminhao.NO_GATE);
                                    filaGate.remove(0);
                                    myAgent.doWait(4000);
                                } else {
                                    System.out.println("Fila do Gate livre!");
                                }
                            }
                        }
                    }
                    if (msgReachStacker != null) {
                        ACLMessage reply = msgReachStacker.createReply();
                        if (caminhao != null) {
                            if (msgReachStacker.getOntology().equalsIgnoreCase("Permitido")) {
                                reply.setOntology("Enviando");
                                reply.setContentObject(caminhao);
                                System.out.println(getAID().getLocalName() + ": Liberando Caminhão!");
                                myAgent.send(reply);
                                atendendo = null;
                            }
                        }
                    } else {
                        if (atendendo != null) {
                            ACLMessage qReachStacker = new ACLMessage(ACLMessage.INFORM);
                            qReachStacker.setOntology("Permissão");
                            qReachStacker.addReceiver(new AID("ReachStacker", AID.ISLOCALNAME));
                            qReachStacker.setContent("Reach "
                                    + "Stacker posso mandar este caminhão que "
                                    + "estou atendendo?");
                            System.out.println(getAID().getLocalName() + ": " + qReachStacker.getContent());
                            myAgent.send(qReachStacker);
                        } else {
                            block();
                        }
                    }

                } catch (Exception ex) {
                    System.err.println("Erro no agente GATE!\n" + ex);
                }
                block();
            }
        }
        );
    }

    public List<Caminhao> getCaminhoes() {
        return filaGate;
    }

    public void addCaminhaoFila(Caminhao caminhao) {
        this.filaGate.add(caminhao);
    }

}
