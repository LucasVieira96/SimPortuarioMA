/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.ambiente.Caminhao;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 5922496
 */
public class MainAgent extends Agent {

    private List<Caminhao> caminhoesNoPorto = new ArrayList<>();
    
    @Override
    protected void setup() {
        System.out.println("Main Agent setup");
        
        this.createChegadaCaminhaoBehavior();
    }

    @Override
    protected void takeDown() {
        System.out.println("Main Agent down");
    }

    private void createChegadaCaminhaoBehavior(){
        
        addBehaviour(new TickerBehaviour(this, 10000) {
            @Override
            protected void onTick() {
                System.out.println("Novo caminhão chegando....");
            }
            
        });
    }
}
