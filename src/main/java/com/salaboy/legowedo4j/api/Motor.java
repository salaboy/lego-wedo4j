/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salaboy.legowedo4j.api;

/**
 *
 * @author salaboy
 */
public interface Motor {

    public enum DIRECTION {
        FORWARD, BACKWARD
    };

    void setName(String string);
    
    String getName();
    
    void forward(int speed, long millisec);

    void backward(int speed, long millisec);

    void start(int speed, DIRECTION dir);
    
    void stop();
    
    boolean isRunning();
    
    void setRunning(boolean running);
}
