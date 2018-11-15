/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import java.awt.Color;
import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author Lucas
 */
public class Caminhao {

    private String placa;
    private Color cor;
    private Container container;
    private boolean carregado = false;
    private JLabel truckView;

    public JLabel getTruckView() {
        return truckView;
    }

    public void setTruckView(JLabel truckView) {
        this.truckView = truckView;
    }

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

    public Caminhao(Color cor, Container container) {
        this(gerarPlaca(), cor, container);
    }

    public Container descarregaContainer() {
        if (container != null) {
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

    public boolean isCarregado() {
        return carregado;
    }

    private static String gerarPlaca() {
        String str = "";

        int leftLimit = 65; // letter 'a'
        int rightLimit = 90; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(8);
        for (int i = 0; i < 3; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        buffer.append((char) '-');

        leftLimit = 48; // letter '0'
        rightLimit = 57; // letter '9'
        for (int i = 0; i < 4; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        String generatedString = buffer.toString();

        str += generatedString;

        return str;
    }
}
