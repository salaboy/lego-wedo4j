/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salaboy.legowedo4j.cmdtests;

import com.salaboy.legowedo4j.api.DistanceSensor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author salaboy
 * java -Djava.library.path=/home/pi/projects/javahidapi/linux/ -cp rolo_the_robot-1.0-SNAPSHOT.jar com.salaboy.rolo.tests.CMDSensorTest
 * 
 */
public class CMDSensorTest {

    static boolean readSensors = true;
    static long defaultLatency = 100;

    public static void main(String[] args) throws Exception {
        // create Options object
        Options options = new Options();

        // add t option
        options.addOption("t", true, "sensors latency");
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);

        String sensorLatency = cmd.getOptionValue("t");
        if (sensorLatency == null) {
            System.out.println(" The Default Latency will be used: " + defaultLatency);
        } else {
            System.out.println(" The Latency will be set to: " + sensorLatency);
            defaultLatency = new Long(sensorLatency);
        }

        System.out.println("Starting Sensor CMD Test ...");

        Weld weld = new Weld();

        WeldContainer container = weld.initialize();

        final DistanceSensor distanceSensor = container.instance().select(DistanceSensor.class).get();

        final Thread t = new Thread() {
            @Override
            public void run() {
                while (readSensors) {
                    int readDistance = distanceSensor.readDistance();
                    System.out.println(" Distance = " + readDistance);
                    try {
                        Thread.sleep(defaultLatency);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CMDSensorTest.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        };
        t.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutdown Hook is running !");
                readSensors = false;
            }
        });
    }
}
