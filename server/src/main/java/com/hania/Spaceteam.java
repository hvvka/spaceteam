package com.hania;

import com.hania.model.PanelType;
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
            server.register(new Player("Bar", PanelType.STEER));
            LOG.info("New player in.");

            Registry registry = LocateRegistry.getRegistry(2099);
            registry.rebind("SpaceteamServer", server);
            LOG.info("Server's ready.");
        } catch (RemoteException e) {
            LOG.error("", e);
            exit(1);
        }
    }
}
