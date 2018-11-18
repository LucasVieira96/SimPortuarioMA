/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ambiente;

import java.io.Serializable;

/**
 *
 * @author Lucas
 */
public class MapaPilha implements Serializable{
    private int posWidth;
    private int posHeight;
    private Container container;

    public MapaPilha(int posWidth, int posHeight, Container container) {
        this.posWidth = posWidth;
        this.posHeight = posHeight;
        this.container = container;
    }

    public int getPosWidth() {
        return posWidth;
    }

    public int getPosHeight() {
        return posHeight;
    }

    public Container getContainer() {
        return container;
    }
    
    
}
