/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salaboy.legowedo4j.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.salaboy.legowedo4j.api.BlockManager;
import com.salaboy.legowedo4j.api.Motor;
import com.salaboy.legowedo4j.impl.WeDo.DIRECTION;
import com.salaboy.legowedo4j.impl.WeDo.Motors;

/**
 *
 * @author salaboy
 */
public class WedoMotorImpl implements Motor {

    private String name;
    private BlockManager manager;
    
    private boolean running = false;

    public WedoMotorImpl(BlockManager bm) {
    	manager = bm;
	}

	public synchronized void forward(Motors m, int speed, long millisec) {
        try {
            
        	start(m, speed, DIRECTION.FORWARD);

        	Thread.sleep(millisec);
            
        	stop(m);
        	
            this.running = false;
        } catch (InterruptedException ex) {
            Logger.getLogger(WedoMotorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void backward(Motors m, int speed, long millisec) {
        try {
        	
           	start(m, speed, DIRECTION.BACKWARD);

        	Thread.sleep(millisec);
            
        	stop(m);
        	
        } catch (InterruptedException ex) {
            Logger.getLogger(WedoMotorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void stop(Motors motor) {
        
    	start(motor, 0, DIRECTION.FORWARD);
    	
        this.running = false;
    }
    
    // need to keep copies of these, otherwise Motor A stops when B started
    private byte speedA = 0;
    private byte speedB = 0;
    

    public synchronized void start(Motors motor, int speed, DIRECTION dir) {
        byte[] data = null;
        byte[] buff = new byte[8];
        int n = manager.read(buff);

        //System.out.println("## " + (buff[0] & 0xff) + "," + (buff[1] & 0xff) + "," + (buff[2] & 0xff) + "," + (buff[3] & 0xff) + "," + (buff[4] & 0xff) + "," + (buff[5] & 0xff) + "," + (buff[6] & 0xff) + "," + (buff[7] & 0xff));
      
        byte speedByte = dir == DIRECTION.BACKWARD ? (byte)speed : (byte)-speed;

        if (n == 8) {
        	data = new byte[]{0, 64, buff[2], buff[3], buff[4], buff[5], buff[6], buff[7]};
        } else {
            data = new byte[]{0, 64, 0, 0, 0, 0, 0, 0, 0};        	
        }

        // power A ???
        //data[4] = 100;
        // power B ???
        //data[5] = 100;

        switch (motor) {
        case A:
        	speedA = speedByte;
        	break;
        case B:
        	speedB = speedByte;
        	break;
        case Both:
        	speedA = speedByte;
        	speedB = speedByte;
        	break;
        }
        
        data[2] = speedA;
        data[3] = speedB;

        manager.write(data);

        this.running = true;

    }

    public void setName(String name) {
        this.name = name;
    }

     public String getName() {
        return this.name;
    }

     public boolean isRunning() {
        return running;
    }

     public void setRunning(boolean running) {
        this.running = running;
    }
}
