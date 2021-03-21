package media.util;

import java.lang.management.ManagementFactory;

public class SystemInfoService {

	public static double getCpuUsage() {
        com.sun.management.OperatingSystemMXBean mxbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        
        

        return mxbean.getCpuLoad();
        
    }


    public static double getMemoryUsage() {
        com.sun.management.OperatingSystemMXBean mxbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();    

        long freePhysicalMemory = mxbean.getFreeMemorySize();
        long totalPhysicalMemory = mxbean.getTotalMemorySize();
        
        double usedPhysicalMemory =  (1 - ((double) freePhysicalMemory / (double) totalPhysicalMemory));
        
        return usedPhysicalMemory;

    }
	
}
