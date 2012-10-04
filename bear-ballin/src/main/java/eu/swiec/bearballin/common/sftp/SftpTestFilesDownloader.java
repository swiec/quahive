package eu.swiec.bearballin.common.sftp;

import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

public class SftpTestFilesDownloader {

    JSch jSch;
    Session session;

    public void addIdentity(String prvKey, String passPhrase) {
        jSch = new JSch();
        try {
            System.out.println("Using private key:\n" + prvKey + "\npassphrase:" + passPhrase);
            jSch.addIdentity(prvKey, passPhrase);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }


    public void setSession(String username, String host, int port) {
        try {
            session = jSch.getSession(username, host, port);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void sftpGetFile(String src, String dst) throws Exception {
        sftpGetFiles(new String[]{src}, dst);
    }

    public void sftpGetFileToStream(String sourceFiles, OutputStream outputStream) throws SftpException, JSchException {
        sftpGetFilesToStream(new String[]{sourceFiles}, outputStream);
    }

    public void sftpGetFilesToStream(String[] sourceFiles, OutputStream outputStream) throws SftpException, JSchException {
        if (session != null || jSch != null) {

            ChannelSftp sftp = sftpConnection();

            for (String s : sourceFiles) {
                sftp.get(s, outputStream, new ConsoleProgressMonitor());
            }

            sftp.disconnect();
            session.disconnect();
        } else {
            throw new RuntimeException("Initialisation Error");
        }
    }

    private ChannelSftp sftpConnection() throws JSchException {
        UserInfo ui = new NoGUIUserInfo(); // MyUserInfo implements
        session.setUserInfo(ui);
        session.connect();
        Channel channel = session.openChannel("sftp");
        ChannelSftp sftp = (ChannelSftp) channel;
        sftp.connect();
        return sftp;
    }

    public void sftpGetFiles(String[] src, String dst) throws Exception {
        if (session != null || jSch != null) {

            ChannelSftp sftp = sftpConnection();

            for (String s : src) {
                sftp.get(s, dst, new ConsoleProgressMonitor());
            }

            sftp.disconnect();
            session.disconnect();
        } else {
            throw new Exception();
        }
    }

}
