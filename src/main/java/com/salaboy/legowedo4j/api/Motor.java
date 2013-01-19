/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salaboy.legowedo4j.api;

import com.salaboy.legowedo4j.impl.WeDo.DIRECTION;
import com.salaboy.legowedo4j.impl.WeDo.Motors;

/**
 *
 * @author salaboy
 */
public interface Motor {


    void setName(String string);
    
    String getName();
    
    void forward(Motors m, int speed, long millisec);

    void backward(Motors m, int speed, long millisec);

    void start(Motors m, int speed, DIRECTION dir);
    
    void stop(Motors m);
    
    boolean isRunning();
    
    void setRunning(boolean running);
}
