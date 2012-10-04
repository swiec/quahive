package eu.swiec.bearballin.tools;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.swiec.bearballin.common.io.Environment;
import eu.swiec.bearballin.common.sftp.SftpTestFilesDownloader;


public class SecureDownloader {
    SftpTestFilesDownloader sftpDler;
    private static final Logger LOGGER = LoggerFactory.getLogger("");

    @Test
    public void getFiles() throws Exception {
        String varCase = System.getProperty("var");

        //local run...
        if (varCase == null)
            varCase = "InsuranceLines_2";

        LOGGER.info("var='" + varCase + "'");
        if (authorize()) {
            String[] files = new String[]{"/home/test-automation/tests/testsTemp/params/" + varCase + "/params.txt",
                    "/home/test-automation/tests/testsCredentials/TED.txt"};
            LOGGER.info("Getting files:\n" + files[0] + "\n" + files[1]);
            sftpDler.sftpGetFiles(files, "..");
        } else {
            LOGGER.error("Authorization failure");
            fail();
        }
    }

    public boolean authorize() {
        sftpDler = new SftpTestFilesDownloader();
        sftpDler.addIdentity(Environment.getPkiFilePath(), "slavekey");
        sftpDler.setSession("test-automation", "svn.zagiel.com.pl", 22);
        return true;
    }
}
