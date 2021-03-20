/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media.server.util;

import java.lang.management.ManagementFactory;

/**
 *
 * @author ghpm9
 */
public class SystemInfoService {
    
    public static int getCpuUsage() {
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
