/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.ambiente.Caminhao;
import controller.ambiente.Container;
import controller.ambiente.Navio;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.awt.Color;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 5922496
 */
public class MainAgent extends Agent {

    private List<Caminhao> caminhoes = new ArrayList<>();
    private List<Navio> navios = new ArrayList<>();

    @Override
    protected void setup() {
        System.out.println("Main Agent setup");
        navios.add(new Navio());
        navios.add(new Navio());
        this.createChegadaCaminhaoBehavior();
    }

    @Override
    protected void takeDown() {
        System.out.println("Main Agent down");
    }

    private void createChegadaCaminhaoBehavior() {

        addBehaviour(new TickerBehaviour(this, 10000) {
            @Override
            protected void onTick() {
                System.out.println("Novo caminhão chegando....");
                caminhoes.add(
                        new Caminhao(Color.red,
                                new Container(navios.get(
                                        new Random().nextInt(navios.size() - 1)), Color.red)));
            }

        });
    }
}
