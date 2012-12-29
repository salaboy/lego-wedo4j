/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salaboy.legowedo4j;

import com.salaboy.legowedo4j.api.DistanceSensor;
import com.salaboy.legowedo4j.api.Motor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class WeDo4jMainTest {

    @Deployment()
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "we-do.jar")
                .addPackage("com.salaboy.legowedo4j.api")
                .addPackage("com.salaboy.legowedo4j.impl")
                .addPackage("com.codeminders.hidapi") // hidapi
                .addAsManifestResource("META-INF/beans.xml", ArchivePaths.create("beans.xml"));

    }
    @Inject
    private DistanceSensor distanceSensor;

    @Inject
    private Motor motor;
    
    @Test
    public void initialTest() throws InterruptedException {
        
         
        motor.setName("main");
        distanceSensor.setName("front");

        
        
        
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    int readDistance = distanceSensor.readDistance();
                    System.out.println("Distance: "+ distanceSensor);
                    motor.start(126, Motor.DIRECTION.FORWARD);
                    try {
                        this.sleep(1000);
                        motor.stop();
                        this.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(WeDo4jMainTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
        
        
        
        Thread.sleep(10000);
        
        motor.stop();
    }
}
