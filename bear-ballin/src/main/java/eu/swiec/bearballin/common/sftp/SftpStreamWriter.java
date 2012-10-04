package eu.swiec.bearballin.common.sftp;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;

public class SftpStreamWriter extends OutputStream {
    public CharArrayWriter caWritter = new CharArrayWriter(4096);

    @Override
    public void write(int b) throws IOException {
        caWritter.write(b);
    }

}
