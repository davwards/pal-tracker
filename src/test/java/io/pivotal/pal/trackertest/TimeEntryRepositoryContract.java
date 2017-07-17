package io.pivotal.pal.trackertest;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public abstract class TimeEntryRepositoryContract {
    abstract TimeEntryRepository repo();

    @Test
    public void createAndGet() throws Exception {
        TimeEntry createdTimeEntry = repo().create(new TimeEntry(123, 456, "today", 8));
        assertThat(createdTimeEntry.getId()).isNotNull();

        TimeEntry expected = new TimeEntry(createdTimeEntry.getId(), 123, 456, "today", 8);
        assertThat(createdTimeEntry).isEqualTo(expected);

        TimeEntry readEntry = repo().get(createdTimeEntry.getId());
        assertThat(readEntry).isEqualTo(expected);
    }

    @Test
    public void list() throws Exception {
        TimeEntry first = repo().create(new TimeEntry(123, 456, "today", 8));
        TimeEntry second = repo().create(new TimeEntry(789, 654, "yesterday", 4));

        assertThat(repo().list()).containsExactlyInAnyOrder(
                new TimeEntry(first.getId(), 123, 456, "today", 8),
                new TimeEntry(second.getId(), 789, 654, "yesterday", 4)
        );
    }

    @Test
    public void update() throws Exception {
        TimeEntry created = repo().create(new TimeEntry(123, 456, "today", 8));

        TimeEntry updatedEntry = repo().update(
                created.getId(),
                new TimeEntry(321, 654, "tomorrow", 5));

        TimeEntry expected = new TimeEntry(created.getId(), 321, 654, "tomorrow", 5);
        assertThat(updatedEntry).isEqualTo(expected);
        assertThat(repo().get(created.getId())).isEqualTo(expected);
    }

    @Test
    public void delete() throws Exception {
        TimeEntry created = repo().create(new TimeEntry(123, 456, "today", 8));

        repo().delete(created.getId());
        assertThat(repo().list()).isEmpty();
    }
}
