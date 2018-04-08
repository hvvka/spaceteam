package com.hania;

import com.hania.model.PanelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PlayerDriver {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerDriver.class);

    private static final String SEVER_NAME = "SpaceteamServer";

    public static void main(String[] args) throws RemoteException {
        PlayerClient captainClient = new PlayerClientImpl(SEVER_NAME);
        captainClient.registerPlayer("Quz", PanelType.STEER);
        LOG.info("Task assertion: {}", captainClient.verifyTask(
                new TaskGenerator.SingleTask(PanelType.STEER, "", "")));
    }
}
