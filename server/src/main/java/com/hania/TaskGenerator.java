package com.hania;

import com.hania.model.PanelTask;
import com.hania.model.PanelType;
import com.hania.model.Task;

import java.io.Serializable;
import java.util.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class TaskGenerator implements Serializable {

    private List<Task> tasks;

    private Random random;

    TaskGenerator() {
        tasks = new ArrayList<>();
        random = new Random();
    }

    private static Task getSteerTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Turn fleet", Arrays.asList("Right", "Left"));
        descriptionAnswer.put("Fly towards", Arrays.asList("Moon", "Earth", "Carrot"));
        descriptionAnswer.put("Gear", Arrays.asList("1", "2", "3", "4", "5"));
        return new PanelTask(PanelType.STEER, descriptionAnswer);
    }

    private static Task getEngineTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Speed", Arrays.asList("5", "10", "15"));
        descriptionAnswer.put("Sell", Arrays.asList("Engines", "Fuel", "Colleague"));
        descriptionAnswer.put("Turbo", Arrays.asList("ON", "OFF"));
        return new PanelTask(PanelType.ENGINE, descriptionAnswer);
    }

    private static Task getMathematicalTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Prove", Arrays.asList("P=NP", "Life's sense"));
        descriptionAnswer.put("Calculate", Arrays.asList("2+1", "3*7", "1*0", "4/2", "5-1"));
        descriptionAnswer.put("Determine position of", Arrays.asList("Stars", "Particles", "Captain"));
        return new PanelTask(PanelType.MATHEMATICAL, descriptionAnswer);
    }

    private static Task getCleanerTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Collect", Arrays.asList("Dirty mugs", "Sympathy"));
        descriptionAnswer.put("Enjoy", Arrays.asList("Tea", "Life", "Your task"));
        descriptionAnswer.put("Space thing", Arrays.asList("ON", "OFF"));
        return new PanelTask(PanelType.CLEANER, descriptionAnswer);
    }

    private static Task getRandomTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Edit", Arrays.asList("Wikipedia Page", "Ship's logbook"));
        descriptionAnswer.put("Comfort", Arrays.asList("Your mom", "Passengers"));
        descriptionAnswer.put("Rate your team", Arrays.asList("1", "2", "3", "4", "5"));
        return new PanelTask(PanelType.RANDOM, descriptionAnswer);
    }

    public static List<Task> getAllTask() {
        return Arrays.asList(getCleanerTasks(), getEngineTasks(), getSteerTasks(), getMathematicalTasks(), getRandomTasks());
    }

    SingleTask getTask() {
        int randomPanelNumber = random.nextInt(tasks.size());
        PanelType panelType = tasks.get(randomPanelNumber).getPanelType();
        Map<String, List<String>> descriptionAnswer = tasks.get(randomPanelNumber).getDescriptionAnswer();
        String randomDescriptionKey = getRandomDescription(descriptionAnswer);
        String randomAnswer = getRandomAnswer(descriptionAnswer, randomDescriptionKey);

        return new SingleTask(panelType, randomDescriptionKey, randomAnswer);
    }

    private String getRandomDescription(Map<String, List<String>> descriptionAnswer) {
        List<String> descriptionKeys = new ArrayList<>(descriptionAnswer.keySet());
        return descriptionKeys.get(random.nextInt(descriptionKeys.size()));
    }

    private String getRandomAnswer(Map<String, List<String>> descriptionAnswer, String randomDescriptionKey) {
        List<String> answers = descriptionAnswer.get(randomDescriptionKey);
        return answers.get(random.nextInt(answers.size()));
    }

    //TODO put in properties or at least util class
    void initTasks(PanelType panelType) {
        switch (panelType) {
            case CAPTAIN:
                break;
            case STEER:
                tasks.add(getSteerTasks());
                break;
            case ENGINE:
                tasks.add(getEngineTasks());
                break;
            case MATHEMATICAL:
                tasks.add(getMathematicalTasks());
                break;
            case CLEANER:
                tasks.add(getCleanerTasks());
                break;
            case RANDOM:
                tasks.add(getRandomTasks());
                break;
        }
    }

    void removeTasks(PanelType panelType) {
        tasks.forEach(t -> {
            if (t.getPanelType() == panelType) tasks.remove(t);
        });
    }

    public static class SingleTask implements Serializable {

        private PanelType panelType;
        private String description;
        private String answer;

        public SingleTask(PanelType panelType, String description, String answer) {
            this.panelType = panelType;
            this.description = description;
            this.answer = answer;
        }

        public PanelType getPanelType() {
            return panelType;
        }

        public String getDescription() {
            return description;
        }

        public String getAnswer() {
            return answer;
        }

        public void setPanelType(PanelType panelType) {
            this.panelType = panelType;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SingleTask that = (SingleTask) o;

            if (panelType != that.panelType) return false;
            if (description != null ? !description.equals(that.description) : that.description != null) return false;
            return answer != null ? answer.equals(that.answer) : that.answer == null;
        }

        @Override
        public int hashCode() {
            int result = panelType != null ? panelType.hashCode() : 0;
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + (answer != null ? answer.hashCode() : 0);
            return result;
        }
    }
}
