package com.hania;

import com.hania.model.Captain;
import com.hania.model.Panel;
import com.hania.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import static java.lang.System.exit;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Spaceteam {

    private static final Logger LOG = LoggerFactory.getLogger(Spaceteam.class);

    private static final String HOST = "192.168.101.137";
    private static final int PORT = 1099;

    public static void main(String[] args) {
        String serverName = "//" + HOST + ":" + PORT + "/SpaceteamServer";

        try {
            Server server = new ServerImpl();

            //todo delete
            server.register(new Captain("test"));
            LOG.info("captain in");
            server.register(new Player("dupa", Panel.STEER));
            LOG.info("new player in");

            Naming.rebind(serverName, server);
        } catch (RemoteException | MalformedURLException e) {
            LOG.error("", e);
            exit(1);
        }
    }
}
