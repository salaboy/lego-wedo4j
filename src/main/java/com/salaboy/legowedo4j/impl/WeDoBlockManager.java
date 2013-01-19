/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salaboy.legowedo4j.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceInfo;
import com.codeminders.hidapi.HIDDeviceNotFoundException;
import com.codeminders.hidapi.HIDManager;
import com.salaboy.legowedo4j.api.BlockManager;
/**
 *
 * @author salaboy
 */
public class WeDoBlockManager implements BlockManager {
	

	// Singleton pattern
	private static class WeDoBlockManagerLoader {
        static final WeDoBlockManager INSTANCE = new WeDoBlockManager();
    }

	// Singleton pattern
   public static WeDoBlockManager getInstance() {
        return WeDoBlockManagerLoader.INSTANCE;
    }
    

    private ArrayList<HIDDevice> dev = new ArrayList<HIDDevice>();
    public static String arch = "pc";
    
    // support several WeDo Hubs
    // by default, first is "active"
    private int currentDevice = 0;

    /**
     * Singleton pattern
     * use WeDoBlockManager.getInstance() to get instance
     */
    private WeDoBlockManager() {
    	
    	System.out.println("init");
        
    	// Singleton pattern
    	if (WeDoBlockManagerLoader.INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }

        if(arch.equals("pc")){
            ClassPathLibraryLoader.loadNativeHIDLibrary();
       }
        if(arch.equals("arm7")){
            System.loadLibrary("hidapi-jni"); 
        }
        try {
            HIDDeviceInfo[] devices = HIDManager.getInstance().listDevices();
            
            if (devices.length == 0) {
            	throw new IllegalStateException(" No WeDo Device Found! ");
            }
            
            for (int i = 0 ; i < devices.length ; i++) {
            	if (devices[i].getProduct_id() == PRODUCT_ID && devices[i].getVendor_id() == VENDOR_ID) {
            		String path = devices[i].getPath();
            		
            		dev.add(HIDManager.getInstance().openByPath(path));
            		
            		System.out.println("found WeDo:" + path);
            	}
            }
            
            if (dev.size() == 0) {
            	throw new IllegalStateException(" No WeDo Device Found! ");
            }
           
            // simpler code to open just one device
            //dev = HIDManager.getInstance().openById(VENDOR_ID, PRODUCT_ID, null);
            
        } catch (HIDDeviceNotFoundException ex) {
        	ex.printStackTrace();
            Logger.getLogger(WeDoBlockManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(" Problem opening WeDo Device " + ex);
        } catch (IOException ex) {
        	ex.printStackTrace();
            Logger.getLogger(WeDoBlockManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(" Problem opening WeDo Device " + ex);
        }
    }

    public HIDDevice getDevice() {
        return this.dev.get(currentDevice);
    }
    
    /**
     * @return number of available hubs
     */
    public int getNumberOfHubs() {
    	return dev.size();
    }
    
    
    /**
     * 
     * try to select hub 'hub'
     * 
     * @param hub 0, 1, 2, ...
     * @return true if hub exists
     */
    public boolean setHub(int hub) {
    	currentDevice = hub;
    	
    	if (currentDevice >= dev.size()) {
    		currentDevice = 0;
    		return false;
    	}
    	
    	return true;
    }

    public synchronized void write(byte[] data) {
        try {
            dev.get(currentDevice).write(data);
        } catch (IOException ex) {
            Logger.getLogger(WeDoBlockManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized int read(byte[] buff) {
        try {
            return dev.get(currentDevice).read(buff);
        } catch (IOException ex) {
            Logger.getLogger(WeDoBlockManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
