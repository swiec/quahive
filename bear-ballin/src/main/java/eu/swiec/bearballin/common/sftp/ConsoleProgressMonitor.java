package eu.swiec.bearballin.common.sftp;

import com.jcraft.jsch.SftpProgressMonitor;

//http://epaul.github.com/jsch-documentation/javadoc/com/jcraft/jsch/SftpProgressMonitor.html
public class ConsoleProgressMonitor implements SftpProgressMonitor {

    int operationType;
    String src;
    String dst;
    long maxSize;
    long currentSize;
    long stepSize;
    long stepCount;
    int starsCount;


    public boolean count(long arg0) {
        currentSize += arg0;

        if ((currentSize / stepSize) > stepCount) {
            stepCount = currentSize / stepSize;
            while (starsCount < stepCount) {
                System.out.print("**");
                starsCount++;
            }
        }
        return currentSize < maxSize;
    }

    public void end() {
        System.out.println("| 100%] DONE " + dst);
    }

    public void init(int arg0, String arg1, String arg2, long arg3) {
        currentSize = 0;
        operationType = arg0; // get =1 , put =0
        src = arg1;
        dst = arg2;
        maxSize = arg3;
        stepSize = maxSize / 10;
        stepCount = 0;
        starsCount = 0;

        System.out.print("[0% |--------------------| 100%]");

        if (operationType == 1) {
            System.out.print(" GET ");
        } else
            System.out.print(" PUT ");

        System.out.print(arg1 + "\n[0% |");

    }

}
