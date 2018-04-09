package com.hania.view;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ErrorMessageUtil {

    private ErrorMessageUtil() {
        throw new IllegalStateException("Utility class!");
    }

    public static void show(RemoteException e) {
        String messageWrapped = "<html><body><p style='width: 400px;'>" + e.toString() + "</p></body></html>";
        String title = "Remote error!";
        JOptionPane.showMessageDialog(new Frame(), messageWrapped, title, JOptionPane.ERROR_MESSAGE);
    }
}
