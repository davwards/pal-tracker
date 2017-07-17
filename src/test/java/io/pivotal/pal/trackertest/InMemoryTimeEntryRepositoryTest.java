package io.pivotal.pal.trackertest;

import io.pivotal.pal.tracker.InMemoryTimeEntryRepository;
import io.pivotal.pal.tracker.TimeEntryRepository;

public class InMemoryTimeEntryRepositoryTest extends TimeEntryRepositoryContract {
    private InMemoryTimeEntryRepository repository = new InMemoryTimeEntryRepository();

    @Override
    TimeEntryRepository repo() {
        return repository;
    }
}
