/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Lucas
 */
public class Container {

    private String numeracao;
    private Navio navioDestino;
    private Color cor;

    public Container(Navio navioDestino, Color cor) {
        this(gerarNumeracao(), navioDestino, cor);
    }

    public Container(String numeracao, Navio navioDestino, Color cor) {
        this.numeracao = numeracao;
        this.navioDestino = navioDestino;
        this.cor = cor;
    }

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

    private static String gerarNumeracao() {
        String str = "";

        int leftLimit = 65; // letter 'a'
        int rightLimit = 90; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(11);
        for (int i = 0; i < 4; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        leftLimit = 48; // letter '0'
        rightLimit = 57; // letter '9'
        for (int i = 0; i < 7; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        String generatedString = buffer.toString();

        str += generatedString;

        return str;
    }

}
