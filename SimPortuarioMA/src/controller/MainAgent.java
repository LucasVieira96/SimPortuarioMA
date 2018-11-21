/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.ambiente.Caminhao;
import controller.ambiente.Container;
import controller.ambiente.MapaPilha;
import controller.ambiente.Navio;
import controller.ambiente.PilhaContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import view.PatioView;

/**
 *
 * @author Lucas
 */
public class MainAgent extends Agent {

    private final List<Navio> navios = new ArrayList<>();
    private List<MapaPilha> cntrSelecionados;
    private Core core;
    private PilhaContainer pilha;
    private PilhaContainer pilhaNavio;
    private Navio navioEmAtendimento;

    //Parametros Main
//    private int chegadaNavioTick = 10;
//    private int partidaNavioTick = 30;
//    private final int diferencaEntreAtracacaoNavios = 2;
    private final int tempoTick = 5000;

    private PatioView patio;

    @Override
    protected void setup() {
        patio = (PatioView) this.getArguments()[0];
        pilha = (PilhaContainer) getArguments()[1];
        pilhaNavio = (PilhaContainer) getArguments()[2];
        cntrSelecionados = (List<MapaPilha>) this.getArguments()[3];
        this.core = new Core();
        System.out.println("Main Agent setup");
        navios.add(new Navio());
//        navios.add(new Navio());
        navios.add(new Navio());
        this.createChegadaCaminhaoBehavior();
        this.createNavioBehavior();
//        patio.setVisible(true);
//        patio.getjPanelPosicaoGateIN().setBackground(Color.red);
    }

    @Override
    protected void takeDown() {
        System.out.println("Agente Main finalizado!");
    }

    private void createNavioBehavior() {

        addBehaviour(new TickerBehaviour(this, tempoTick) {
            @Override
            protected void onTick() {
                MessageTemplate MT1 = MessageTemplate.MatchSender(new AID("RTG", AID.ISLOCALNAME));
                ACLMessage msg = receive(MT1);

                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equals("Main, qual o proximo navio?")) {
                        try {
                            msg = new ACLMessage(ACLMessage.INFORM);
                            msg.setOntology("Navio");
                            msg.addReceiver(new AID("RTG", AID.ISLOCALNAME));

                            if (navioEmAtendimento == null) {
                                verificaContainersNavio();
                            }

                            msg.setContentObject(navioEmAtendimento);
                            myAgent.send(msg);
                        } catch (IOException ex) {
                            System.err.println("Erro no envio da mensagem: " + ex);
                        }

                    }
                }
            }
        });
    }

    private void verificaContainersNavio() {
        cntrSelecionados.clear();
        navioEmAtendimento = selecionaNavio();
//        navios.remove(navioEmAtendimento);
        for (MapaPilha mapa : pilha.getMapaPilha()) {
            if (mapa.getContainer().getNavioDestino().getNomeNavio().equals(navioEmAtendimento.getNomeNavio())) {
                cntrSelecionados.add(mapa);
            }
        }
    }

    private Navio selecionaNavio() {
        return navios.get(0);
    }

    private void createChegadaCaminhaoBehavior() {

        addBehaviour(new TickerBehaviour(this, tempoTick) {
            @Override
            protected void onTick() {
                System.out.println("Tick: " + this.getTickCount());
                System.out.println("Novo caminhão chegando....");
                Caminhao caminhao = new Caminhao(core.getCores().get(new Random().nextInt(core.cores.size())),
                        new Container(navios.get(new Random().nextInt(navios.size())),
                                core.getCores().get(new Random().nextInt(core.cores.size()))),
                        Caminhao.FILA_GATE);
                //adicionar animacao chegada caminhao
                informaGateNovoCaminhao(caminhao);
                if (this.getTickCount() % 10 == 0) {
                    partidaNavio();
                    navios.add(new Navio());
                }

                patio.setTick(this.getTickCount());
                patio.setNavio(navioEmAtendimento);
//            patio.getjPanelPosicaoGateIN().setBackground(core.getCores().get(new Random().nextInt(core.cores.size())));
//              patio.addCaminhao(caminhoes.size() * 50, 10, caminhao);
            }

            private void informaGateNovoCaminhao(Caminhao caminhao) {
                try {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Gate", AID.ISLOCALNAME));
                    msg.setContentObject(caminhao);
                    myAgent.send(msg);// envia mensagem
                } catch (IOException ex) {
                    System.err.println("Erro no envio da mensagem: " + ex);
                }
            }

            private void partidaNavio() {
                if (navioEmAtendimento != null) {

                    System.out.println(getAID().getLocalName() + ": Navio " + navioEmAtendimento.getNomeNavio() + " partindo");
                    navioEmAtendimento = null;
                    pilhaNavio.clear();
                    patio.drawPilha02(pilhaNavio);
                }
            }

        });
    }
}
