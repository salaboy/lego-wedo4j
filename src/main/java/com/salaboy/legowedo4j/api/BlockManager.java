/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salaboy.legowedo4j.api;

import com.codeminders.hidapi.HIDDevice;

/**
 *
 * @author salaboy
 */
public interface BlockManager {

    public static final int VENDOR_ID = 1684;
    public static final int PRODUCT_ID = 3;
    
    public HIDDevice getDevice();
    
    public void write(byte[] data);
    
    public int read(byte[] buff);
}
