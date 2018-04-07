package com.hania.model;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface Task extends Remote {

    PanelType getPanelType();

    Map<String, List<String>> getDescriptionAnswer();
}
