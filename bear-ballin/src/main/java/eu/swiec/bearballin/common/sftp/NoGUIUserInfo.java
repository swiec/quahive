package eu.swiec.bearballin.common.sftp;

import com.jcraft.jsch.UserInfo;


public class NoGUIUserInfo implements UserInfo {

    public String getPassphrase() {
        System.out.println("getPassPhrase method called");
        return "TopSecretPass";
    }

    public String getPassword() {
        System.out.println("getPassword method called");
        return "TopSecretPass";
    }

    public boolean promptPassphrase(String arg0) {
        System.out.println("Passphrase promtion");
        return false;
    }

    public boolean promptPassword(String arg0) {
        System.out.println("Password promtion");
        return false;
    }

    public boolean promptYesNo(String arg0) {
        //System.out.println("Yes|No promption");
        return true;
    }

    public void showMessage(String arg0) {
        System.out.println("Show message called. Message is: " + arg0);
    }

}
