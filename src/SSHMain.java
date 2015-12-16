import com.jcraft.jsch.*;

import java.util.Properties;

/**
 * Created by user on 16.12.2015.
 */
public class SSHMain {

    private static String USER = "varenik";
    private static String HOST = "192.168.0.106";
    private static String PASS = "4554722";
    private static int PORT = 2222;

    private static Session session;
    private static Channel channelExe;


    public static void main(String[] args) {
       String command = "cmd.exe /u /c  \"start https://google.com\" ";

        session = createSession();
        channelExe = createChannelExe(session);
        setCommand(channelExe, command);
        close(session, channelExe);


    }

    private static void close(Session session, Channel channelExe) {
        channelExe.disconnect();
        session.disconnect();
    }

    private static void setCommand(Channel channelExe, String command) {
        ((ChannelExec)channelExe).setCommand(command);
        ((ChannelExec) channelExe).setErrStream(System.err);
        try {
            channelExe.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("Error cannot connetc to channel :"+ e);
        }


    }

    private static Session createSession() {
        JSch jSch = new JSch();
        try {
            System.out.println("creat session...");
            session = jSch.getSession(USER, HOST, PORT);
            session.setPassword(PASS);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("compression.s2c", "zlib,none");
            config.put("compression.c2s", "zlib,none");

            session.setConfig(config);
        } catch (JSchException e) {
            System.out.println("Error creat session: " + e);
            e.printStackTrace();
        }


        return session;
    }


    private static Channel createChannelExe(Session session) {
        try {
            System.out.println(" connect session ...");
            session.connect();
        } catch (JSchException e) {
            System.out.println("Error connect: " + e);
            e.printStackTrace();
        }


        try {
            channelExe = session.openChannel("exec");
        } catch (JSchException e) {
            System.out.println("Error connect: " + e);
            e.printStackTrace();
        }

        return channelExe;
    }


}
