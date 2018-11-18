/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Lucas
 */
public class Container implements Serializable {

    //STATUS CAMINHÃO
    public static final int NO_CAMINHAO = 1;
    public static final int AGUARDANDO_DESCARREGAR = 2;
    public static final int DESCARREGADO = 3;

    private String numeracao;
    private Navio navioDestino;
    private Color cor;
    private int status;

    public Container(Navio navioDestino, Color cor) {
        this(gerarNumeracao(), navioDestino, cor);
    }

    public Container(String numeracao, Navio navioDestino, Color cor) {
        this.numeracao = numeracao;
        this.navioDestino = navioDestino;
        this.cor = cor;
        this.status = NO_CAMINHAO;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
