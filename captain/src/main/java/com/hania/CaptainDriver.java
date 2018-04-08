package com.hania;

import com.hania.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CaptainDriver {

    private static final Logger LOG = LoggerFactory.getLogger(CaptainDriver.class);

    private static final String SEVER_NAME = "SpaceteamServer";

    public static void main(String[] args) throws RemoteException {
        CaptainClient captainClient = new CaptainClientImpl(SEVER_NAME);
        captainClient.registerCaptain("Foo");

        List<String> list = new ArrayList<>();
        for (User user : captainClient.getPlayers()) {
            String name = user.getName();
            list.add(name);
        }
        LOG.info("Players' list: {}", list);
        LOG.info("Generated task: {}", captainClient.createTask());
    }
}
