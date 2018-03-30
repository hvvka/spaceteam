package com.hania;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import static java.lang.System.exit;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Spaceteam {

    public static void main(String[] args) {
        try {
            Server server = new ServerImpl();
            Naming.rebind("//192.168.101.137:1099/Server", server);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
            exit(1);
        }
    }
}
