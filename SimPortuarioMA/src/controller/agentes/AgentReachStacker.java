/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.agentes;

import controller.ambiente.Caminhao;
import controller.ambiente.Container;
import controller.ambiente.PilhaContainer;
import controller.behaviours.DelayBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.util.leap.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Lucas
 */
public class AgentReachStacker extends Agent {

    PilhaContainer pilha = null;
    Container container;
    Caminhao caminhao;

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
        pilha = (PilhaContainer) this.getArguments()[1];
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                try {
                    MessageTemplate MT1 = MessageTemplate.MatchSender(new AID("Gate", AID.ISLOCALNAME));
                    ACLMessage msg = receive(MT1);
                    if (msg != null) {
                        ACLMessage reply = msg.createReply();
                        if (msg.getOntology().equalsIgnoreCase("Permissão")) {
                            if (container == null && caminhao == null
                                    || caminhao.getStatus() == Caminhao.LIBERADO_STACKER) {
                                reply.setContent(getAID().getLocalName() + ": Pode mandar");
                                reply.setOntology("Permitido");
                            } else {
                                reply.setContent(": Estou ocupado no momento");
                            }
                            System.out.println(getAID().getLocalName() + reply.getContent());
                            myAgent.send(reply);
                        } else if (msg.getOntology().equalsIgnoreCase("Enviando")) {
                            caminhao = (Caminhao) msg.getContentObject();
                            container = caminhao.getContainer();
                            caminhao.setStatus(Caminhao.AGUARDANDO_STACKER);
                            System.out.println(getAID().getLocalName() + ": Caminhão recebido! - " + caminhao.getPlaca());
                        }
                        
                        if (container != null && container.getStatus() == Container.NO_CAMINHAO) {

                            container.setStatus(Container.AGUARDANDO_DESCARREGAR);

                            System.out.println(getAID().getLocalName()
                                    + ": Descarregando container! - " + container.getNumeracao());

                            addBehaviour(new DelayBehaviour(myAgent, TimeUnit.SECONDS.toMillis(5)) {
                                @Override
                                public void executeAfterTimeOut() {
                                    if (pilha.pushContainer(container,
                                            new Random().nextInt(pilha.getWidth()),
                                            new Random().nextInt(pilha.getHeight()))) {
                                        System.out.println(getAID().getLocalName()
                                                + ": Container descarregado! - " + container.getNumeracao());
                                        container = null;
                                        caminhao.setContainer(null);
                                        caminhao.setStatus(Caminhao.LIBERADO_STACKER);
                                        System.out.println(getAID().getLocalName() + ": Caminhão liberado! - " + caminhao.getPlaca());
                                        removeBehaviour(this);
                                    } else {
                                        if (pilha.getCapacidade() <= 0) {
                                            System.out.println(getAID().getLocalName()
                                                    + ": A pilha esta cheia!");
                                            System.out.println(getAID().getLocalName()
                                                    + ": Aguardando liberação de espaço na pilha!\n");
                                        }
                                    }

                                }
                            });
                        }
                    }
                } catch (UnreadableException ex) {
                    System.err.println("Erro no agente ReachStacker:\n" + ex);
                }
            }
        });
    }

}
