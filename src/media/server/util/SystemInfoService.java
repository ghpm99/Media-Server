/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media.server.util;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

/**
 *
 * @author ghpm9
 */
public class SystemInfoService {

    public static double getCpuUsage() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});

            if (list.isEmpty()) {
                return Double.NaN;
            }

            Attribute att = (Attribute) list.get(0);
            Double value = (Double) att.getValue();

            if (value == -1.0) {
                return Double.NaN;
            }

            return ((int) (value * 1000) / 10.0);
        } catch (MalformedObjectNameException | NullPointerException | InstanceNotFoundException | ReflectionException ex) {
            Logger.getLogger(SystemInfoService.class.getName()).log(Level.SEVERE, null, ex);
            return Double.NaN;
        }

    }

    public static int getCpuUsagev2() {
        com.sun.management.OperatingSystemMXBean mxbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        
        int systemCpuLoad = (int) (mxbean.getSystemCpuLoad() * 100);

        return systemCpuLoad;
        
    }


    public static int getMemoryUsage() {
        com.sun.management.OperatingSystemMXBean mxbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();    

        long freePhysicalMemory = mxbean.getFreePhysicalMemorySize();
        long totalPhysicalMemory = mxbean.getTotalPhysicalMemorySize();
        
        int usedPhysicalMemory = (int) ((1 - ((double) freePhysicalMemory / (double) totalPhysicalMemory)) * 100);
        
        return usedPhysicalMemory;

    }

}
