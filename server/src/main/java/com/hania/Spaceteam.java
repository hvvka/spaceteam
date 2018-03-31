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

    public static void main(String[] args) {
        try {
            Server server = new ServerImpl();

            server.register(new Captain("test"));
            System.out.println("captain in");
            server.register(new Player("dupa", Panel.STEER));
            System.out.println("new player in");

            Naming.rebind("//192.168.101.137:1099/Server", server);
        } catch (RemoteException | MalformedURLException e) {
            LOG.error("", e);
            exit(1);
        }
    }
}
