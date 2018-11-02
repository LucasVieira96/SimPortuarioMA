/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import java.util.Stack;

/**
 *
 * @author Lucas
 */
public class Patio {

    private Integer height;
    private Integer weight;
    private Stack<Container>[][] pilha;
    private final int alturaMaxima;

    /**
     * Inicia a pilha de containers do terminal
     * @param largura
     * @param altura
     * @param altMaxPilha 
     */
    public Patio(Integer largura, Integer altura, Integer altMaxPilha) {
        this.alturaMaxima = altMaxPilha;
        this.height = largura;
        this.weight = altura;
        pilha = new Stack[largura][altura];
    }

    public boolean pushContainer(Container container, Integer weight, Integer height) {
        if (pilha[height][weight].size() <= alturaMaxima) {
            pilha[height][weight].push(container);
            return true;
        }
        return false;
    }

    public Container popContainer (Integer weight, Integer height){
        return pilha[height][weight].pop();
    }

    public Stack<Container>[][] getPilha() {
        return pilha;
    }
}
