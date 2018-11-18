/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import jade.util.leap.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Lucas
 */
public class PilhaContainer {
    
    private final List<MapaPilha> mapaPilha;
    private final Integer height;
    private final Integer width;
    private final Stack<Container>[][] pilha;
    private final int alturaMaxima;
    private int capacidade;

    /**
     * Inicia a pilha de containers do terminal
     * @param width
     * @param height
     * @param altMaxPilha 
     */
    public PilhaContainer(Integer width, Integer height, Integer altMaxPilha) {
        this.alturaMaxima = altMaxPilha;
        this.height = height;
        this.width = width;
        this.pilha = new Stack[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pilha[i][j] = new Stack();
            }
        }
        this.mapaPilha = new java.util.ArrayList();
        this.capacidade = (height * width) * altMaxPilha;
    }

    public boolean pushContainer(Container container, Integer height, Integer width) {
        if (pilha[width][height].size() <= alturaMaxima) {
            pilha[width][height].push(container);
            mapaPilha.add(new MapaPilha(width, height, container));
            capacidade ++;
            return true;
        }
        return false;
    }

    public Container popContainer (Integer width, Integer height){
        capacidade --;
        for (int i = 0; i < mapaPilha.size(); i++) {
            MapaPilha mapa = mapaPilha.get(i);
            if(mapa.getPosWidth() == width && mapa.getPosHeight() == height){
                mapaPilha.remove(i);
            }
        }
        return pilha[height][width].pop();
    }

    public Stack<Container>[][] getPilha() {
        return pilha;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public int getAlturaMaxima() {
        return alturaMaxima;
    }

    public List<MapaPilha> getMapaPilha() {
        return mapaPilha;
    }

    public int getCapacidade() {
        return capacidade;
    }
    
    
}
