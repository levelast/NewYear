package ru.broject.newyear.gameobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vyacheslav.svininyh on 29.12.2015.
 */
public class DialogHandler {

    private boolean isActive = false;
    private boolean isComplete = false;

    public static List<String> snowmanDialogs = createSnowmanDialogs();
    public static List<String> dedMorozDialogs = createDedMorozDialogs();

    private static List<String> createSnowmanDialogs() {
        List<String> list = new ArrayList<String>();
        list.add("Hello, Ded Moroz!");
        list.add("Can you make\n winter colder?");
        list.add("MAKE IT!!!");
        list.add("Thank you!");
        return list;
    }

    private static List<String> createDedMorozDialogs() {
        List<String> list = new ArrayList<String>();
        list.add("Hi, Snowman");
        list.add("Yes, of course");
        list.add("   OK");
        list.add("Boom!");
        return list;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
