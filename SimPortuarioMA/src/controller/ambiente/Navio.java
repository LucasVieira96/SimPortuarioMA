/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class Navio {

    private final String nomeNavio;
    private final Integer qtdMaxContainer;
    private List<Container> containers;

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

}
