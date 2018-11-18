/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.ambiente.Caminhao;
import controller.ambiente.Container;
import controller.ambiente.Navio;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.PatioView;

/**
 *
 * @author 5922496
 */
public class MainAgent extends Agent {

    private Core core;
    private PatioView patio;
    private List<Navio> navios = new ArrayList<>();

    @Override
    protected void setup() {
        patio = (PatioView) this.getArguments()[0];
        this.core = new Core();
        System.out.println("Main Agent setup");
        navios.add(new Navio());
        navios.add(new Navio());
        this.createChegadaCaminhaoBehavior();
//        patio.setVisible(true);
//        patio.getjPanelPosicaoGateIN().setBackground(Color.red);
    }

    @Override
    protected void takeDown() {
        System.out.println("Main Agent down");
    }

    private void createChegadaCaminhaoBehavior() {

        addBehaviour(new TickerBehaviour(this, 5000) {
            @Override
            protected void onTick() {
                System.out.println("Tick: " + this.getTickCount());
                System.out.println("Novo caminhão chegando....");
                Caminhao caminhao = new Caminhao(core.getCores().get(new Random().nextInt(core.cores.size())),
                        new Container(navios.get( new Random().nextInt(navios.size() - 1)), 
                                core.getCores().get(new Random().nextInt(core.cores.size()))),
                        Caminhao.FILA_GATE);

                adicionarCaminhaoNaFila(caminhao);
//            patio.getjPanelPosicaoGateIN().setBackground(core.getCores().get(new Random().nextInt(core.cores.size())));
//              patio.addCaminhao(caminhoes.size() * 50, 10, caminhao);
            }

            private void adicionarCaminhaoNaFila(Caminhao caminhao) {
                try {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Gate", AID.ISLOCALNAME));
                    msg.setContentObject(caminhao);
                    myAgent.send(msg);// envia mensagem
                } catch (IOException ex) {
                    System.err.println("Erro no envio da mensagem: " + ex);
                }
            }

        });
    }
}
