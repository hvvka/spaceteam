package com.hania.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PanelTask implements Task, Serializable {

    private final PanelType panelType;

    private Map<String, List<String>> descriptionAnswer;

    public PanelTask(PanelType panelType, Map<String, List<String>> descriptionAnswer) {
        this.panelType = panelType;
        this.descriptionAnswer = descriptionAnswer;
    }

    @Override
    public PanelType getPanelType() {
        return panelType;
    }

    @Override
    public Map<String, List<String>> getDescriptionAnswer() {
        return descriptionAnswer;
    }
}
