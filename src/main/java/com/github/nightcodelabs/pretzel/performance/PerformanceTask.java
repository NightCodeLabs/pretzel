package com.github.nightcodelabs.pretzel.performance;

import com.github.myzhan.locust4j.Locust;
import com.github.myzhan.locust4j.AbstractTask;

/**
 * Wrapper class made in order to contain the locust4j dependency inside pretzel
 * */

public class PerformanceTask extends AbstractTask {

    public Locust performance = Locust.getInstance();

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute() throws Exception {

    }
}
