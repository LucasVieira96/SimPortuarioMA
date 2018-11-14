/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 5922496
 */
public final class Core {

    List<Color> cores = new ArrayList<>();
    
    public Core() {
        gerarCoresPadrao();
    }
    
    private void gerarCoresPadrao(){
        cores.add(Color.RED);
        cores.add(Color.BLUE);
        cores.add(Color.CYAN);
        cores.add(Color.GRAY);
        cores.add(Color.GREEN);
        cores.add(Color.MAGENTA);
        cores.add(Color.ORANGE);
        cores.add(Color.PINK);
        cores.add(Color.WHITE);
        cores.add(Color.YELLOW);
    }

    public List<Color> getCores() {
        return cores;
    }
    
    
}
