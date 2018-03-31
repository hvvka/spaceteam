package com.hania;

import com.hania.model.Captain;
import com.hania.model.Panel;
import com.hania.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.lang.System.exit;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Spaceteam {

    private static final Logger LOG = LoggerFactory.getLogger(Spaceteam.class);

    public static void main(String[] args) {
        try {
            Server server = new ServerImpl();

            //todo delete
            server.register(new Captain("test"));
            LOG.info("captain in");
            server.register(new Player("dupa", Panel.STEER));
            LOG.info("new player in");

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("SpaceteamServer", server);
            LOG.info("Server ready");
        } catch (RemoteException e) {
            LOG.error("", e);
            exit(1);
        }
    }
}
