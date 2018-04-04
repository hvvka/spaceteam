package com.hania;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class TaskGenerator implements Serializable {

    private List<String> tasks;

    private Random random;

    TaskGenerator() {
        random = new Random();
        initTasks();
    }

    //TODO put in properties
    private void initTasks() {
        tasks = Arrays.asList(
                "Engage Eigenthrottle",
                "Turn on Dangling Shunter",
                "Soak Ferrous Holospectrum",
                "Set Granular Putty to 0",
                "Set Granular Putty to 1",
                "Set Granular Putty to 2",
                "Set Granular Putty to 3",
                "Engage Primesucker",
                "Extinguish Stunt Men",
                "Light Stunt Men",
                "Edit Wikipedia Page",
                "Re-enact Rocky Horror Picture Show",
                "Watch Rocky Horror Picture Show",
                "Set Shiftsanitizer to 0",
                "Set Shiftsanitizer to 1",
                "Set Shiftsanitizer to 2",
                "Set Shiftsanitizer to 3",
                "Carry Old Computer",
                "Use Old Computer",
                "Unlock Grooved Cable",
                "Comfort Passengers",
                "Infuse Tea",
                "Turn on Brainplugger",
                "Turn off Brainplugger",
                "Activate Red Guys",
                "Deactivate Red Guys",
                "Engage Beautiful Man",
                "Set on Space-thing",
                "Set off Space-thing",
                "Produce Huge Baby",
                "Exchange Huge Baby"
        );
    }

    String getTask() {
        int randomTaskNumber = random.nextInt(tasks.size());
        return tasks.get(randomTaskNumber);
    }
}
