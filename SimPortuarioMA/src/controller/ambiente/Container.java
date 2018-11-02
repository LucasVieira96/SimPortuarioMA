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
public class Container {
    
    private String numeracao;
    private Navio navioDestino;
    private Color cor;

    public String getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(String numeracao) {
        this.numeracao = numeracao;
    }

    public Navio getNavioDestino() {
        return navioDestino;
    }

    public void setNavioDestino(Navio navioDestino) {
        this.navioDestino = navioDestino;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }
    
    
    
}
