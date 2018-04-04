package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CaptainDriver {

    private static final Logger LOG = LoggerFactory.getLogger(CaptainDriver.class);

    private static final String SEVER_NAME = "SpaceteamServer";

    public static void main(String[] args) throws RemoteException {
        CaptainClient captainClient = new CaptainClientImpl(SEVER_NAME);
        captainClient.registerCaptain("Foo");
        LOG.info("Generated task: {}", captainClient.createTask());
    }
}
