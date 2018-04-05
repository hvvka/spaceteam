package com.hania;

import com.hania.model.PanelTask;
import com.hania.model.PanelType;
import com.hania.model.Task;

import java.io.Serializable;
import java.util.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class TaskGenerator implements Serializable {

    private List<Task> tasks;

    private Random random;

    TaskGenerator() {
        random = new Random();
        initTasks();
    }

    //TODO put in properties
    private void initTasks() {
        tasks = new ArrayList<>();
        addSteerTasks();
        addEngineTasks();
        addMathematicalTasks();
        addCleanerTasks();
        addRandomTasks();
    }

    private void addSteerTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Turn fleet", Arrays.asList("Right", "Left"));
        descriptionAnswer.put("Fly towards", Arrays.asList("Moon", "Earth", "Carrot"));
        descriptionAnswer.put("Gear", Arrays.asList("1", "2", "3", "4", "5"));

        tasks.add(new PanelTask(PanelType.STEER, descriptionAnswer));
    }

    private void addEngineTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Speed", Arrays.asList("5", "10", "15"));
        descriptionAnswer.put("Sell", Arrays.asList("Engines", "Fuel", "Colleague"));
        descriptionAnswer.put("Turbo", Arrays.asList("ON", "OFF"));

        tasks.add(new PanelTask(PanelType.ENGINE, descriptionAnswer));
    }

    private void addMathematicalTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Prove", Arrays.asList("P=NP", "Life's sense"));
        descriptionAnswer.put("Calculate", Arrays.asList("2+1", "3*7", "1*0", "4/2", "5-1"));
        descriptionAnswer.put("Determine position of", Arrays.asList("Stars", "Particles", "Captain"));

        tasks.add(new PanelTask(PanelType.MATHEMATICAL, descriptionAnswer));
    }

    private void addCleanerTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Collect", Arrays.asList("Dirty mugs", "Sympathy"));
        descriptionAnswer.put("Enjoy", Arrays.asList("Tea", "Life", "Your task"));
        descriptionAnswer.put("Space thing", Arrays.asList("ON", "OFF"));

        tasks.add(new PanelTask(PanelType.CLEANER, descriptionAnswer));
    }

    private void addRandomTasks() {
        Map<String, List<String>> descriptionAnswer = new HashMap<>();
        descriptionAnswer.put("Edit", Arrays.asList("Wikipedia Page", "Ship's logbook"));
        descriptionAnswer.put("Comfort", Arrays.asList("Your mom", "Passengers"));
        descriptionAnswer.put("Rate your team", Arrays.asList("1", "2", "3", "4", "5"));

        tasks.add(new PanelTask(PanelType.RANDOM, descriptionAnswer));
    }

    Task getTask() {
        int randomTaskNumber = random.nextInt(tasks.size());
        return tasks.get(randomTaskNumber);
    }
}
