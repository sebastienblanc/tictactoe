package com.redhat.developer.millionaire;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class QuestionsManager {

    @Inject
    ContestState state;

    public int getTimeBetweenQuestionsInSeconds() {
        return (state.getCurrentContest()
                .map(c -> c.timeBetweenRounds.toSeconds())
                .orElse(0L)).intValue();
    }


}