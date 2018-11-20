/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Lucas
 */
public class Navio implements Serializable{

    public static final int DESATRACADO = 0;
    public static final int ATRACADO = 1;
    private final String nomeNavio;
    private final Integer qtdMaxContainer;
    private List<Container> containers;
    private int status = DESATRACADO;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Navio() {
        this(gerarNomeNavio(), 100 + new Random().nextInt(1900));
    }

    public Navio(Integer qtdMaxContainer) {
        this(gerarNomeNavio(), qtdMaxContainer);
    }

    public Navio(Integer qtdMaxContainer, List<Container> containers) {
        this(gerarNomeNavio(), qtdMaxContainer, containers);
    }

    public Navio(String nomeNavio, Integer qtdMaxContainer, List<Container> containers) {
        this.nomeNavio = nomeNavio;
        this.qtdMaxContainer = qtdMaxContainer;
        this.containers = containers;
    }

    public Navio(String nomeNavio, Integer qtdMaxContainer) {
        this.nomeNavio = nomeNavio;
        this.qtdMaxContainer = qtdMaxContainer;
        this.containers = new ArrayList<>();
    }

    public List<Container> getContainers() {
        return containers;
    }

    public boolean setContainers(List<Container> containers) {
        if (this.containers.size() <= qtdMaxContainer) {
            this.containers = containers;
            return true;
        }
        return false;
    }

    public String getNomeNavio() {
        return nomeNavio;
    }

    public Integer getQtdMaxContainer() {
        return qtdMaxContainer;
    }
    
    

    private static String gerarNomeNavio() {
        int leftLimit = 65; // letter 'a'
        int rightLimit = 90; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}
