package eu.swiec.bearballin.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import eu.swiec.bearballin.common.io.Environment;
import eu.swiec.bearballin.common.io.FileIO;
import eu.swiec.bearballin.common.sftp.SftpStreamWriter;
import eu.swiec.bearballin.common.sftp.SftpTestFilesDownloader;

public class SecureTestRunParamsReader {
    public final static String TARGET_ENV_VAR_NAME = "TARGETENV";
    public final static String DEFAULT_TARGET_PATH = "/home/test-automation/tests/testsCredentials/TED.txt";

    public static List<String> paramsList;
    public static String[] credentials;
    private final static SftpTestFilesDownloader SFTPDWLR = new SftpTestFilesDownloader();

    private final static Logger LOGGER = LoggerFactory.getLogger("");

    public enum Environments {
        UAT1("/home/test-automation/tests/testsCredentials/UAT1/credentials.txt"),
        TST1("/home/test-automation/tests/testsCredentials/TST1/credentials.txt"),
        DEFAULT("/home/test-automation/tests/testsCredentials/default/credentials.txt");

        public String tedFile;

        private Environments(final String tedCredentialsLoc) {
            tedFile = tedCredentialsLoc;
        }

        public String getTedCredentialFilePath() {
            return tedFile;
        }
    }

    private final static Environments TARGET_ENVI = initTarEnv();

    private static void setTestCaseParams() {

        final File paramsFile = new File("../params.txt");
        LOGGER.info("Assuming parameters are in: " + paramsFile.getAbsolutePath());

        try {
            final SftpStreamWriter paramsString = new SftpStreamWriter();
            final SftpStreamWriter credentialsString = new SftpStreamWriter();

            if (authorize()) {
                SFTPDWLR.sftpGetFileToStream(TARGET_ENVI.getTedCredentialFilePath(), credentialsString);
            }

            paramsList = FileIO.getTestParamsFromCSVFile(paramsString.caWritter.toString());
            credentials = FileIO.getLoginPassFromCSVString(credentialsString.caWritter.toString());

        } catch (IOException e) {
            LOGGER.error("Test parameters reading error");
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        }

        LOGGER.info("Readed: " + credentials.length + " credentials parameters");
    }

    private static Environments initTarEnv() {
        final String tarVarStr = Environment.getPropOrVar(TARGET_ENV_VAR_NAME);

        return tarVarStr.isEmpty() ? Environments.DEFAULT : Environments.valueOf(tarVarStr);
    }

    private static boolean authorize() {
        SFTPDWLR.addIdentity(Environment.getPkiFilePath(), "slavekey");
        SFTPDWLR.setSession("userName", "sft.adress.pl", 22);
        return true;
    }

    /**
     * @return the paramsList
     */
    public static List<String> getParamsList() {
        if (paramsList == null) {
            setTestCaseParams();
        }
        return paramsList;
    }

    /**
     * @return the credentials
     */
    public static String[] getCredentials() {
        if (credentials == null) {
            setTestCaseParams();
        }
        return credentials;
    }

    public static String getCredential(int index) {
        if (credentials == null) {
            setTestCaseParams();
        }
        return credentials[index];
    }
}
