package com.hania;

import com.hania.controller.LoginFrameController;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PlayerDriver {

    public static void main(String[] args) {
        new LoginFrameController();
//        new PlayerFrame(new PlayerClientImpl(""), new User() {
//            @Override
//            public String getName() throws RemoteException {
//                return "Henry";
//            }
//
//            @Override
//            public PanelType getPanelType() throws RemoteException {
//                return PanelType.STEER;
//            }
//        });
    }
}
