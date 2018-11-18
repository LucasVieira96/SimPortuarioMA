/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.behaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

/**
 *
 * @author jonat
 */
public abstract class DelayBehaviour extends SimpleBehaviour {

    private long timeout,
            wakeupTime;
    private boolean finished = false;

    public DelayBehaviour(Agent a, long timeout) {
        super(a);
        this.timeout = timeout;
    }

    @Override
    public void onStart() {
        wakeupTime = System.currentTimeMillis() + timeout;
    }

    @Override
    public void action() {
        long dt = wakeupTime - System.currentTimeMillis();
        if (dt <= 0) {
            finished = true;
            executeAfterTimeOut();
        } else {
            block(dt);
        }
    }

    public abstract void executeAfterTimeOut();

    @Override
    public boolean done() {
        return finished;
    }
}
