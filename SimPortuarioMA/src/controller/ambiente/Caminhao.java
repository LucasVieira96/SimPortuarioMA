/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import java.awt.Color;

/**
 *
 * @author Lucas
 */
public class Caminhao {

    private String placa;
    private Color cor;
    private Container container;
    private boolean carregado = false;

    public Caminhao(String placa, Color cor, Container container) {
        this.placa = placa;
        this.cor = cor;
        this.container = container;
        if (container != null) {
            this.carregado = true;
        } else {
            this.carregado = false;
        }
    }

    public Container descarregaContainer(){
        if(container != null){
            carregado = false;
        }
        return container;
    }
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        if (!carregado) {
            this.container = container;
            this.carregado = true;
        }
    }
    
    public boolean isCarregado(){
        return carregado;
    }

}
