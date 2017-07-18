package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntriesController {
    private final TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntriesController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gauge) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping("/timeEntries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("/timeEntries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") Long id) {
        TimeEntry record = timeEntryRepository.get(id);
        if (record != null) {
            counter.increment("TimeEntry.read");
            return ResponseEntity.ok(record);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/timeEntries")
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return ResponseEntity.ok(timeEntryRepository.list());
    }

    @PutMapping("/timeEntries/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody TimeEntry updatedTimeEntry) {
        TimeEntry updatedEntry = timeEntryRepository.update(id, updatedTimeEntry);
        if(updatedEntry != null) {
            counter.increment("TimeEntry.updated");
            return ResponseEntity.ok(updatedEntry);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("timeEntries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") Long id) {
        timeEntryRepository.delete(id);
        counter.increment("TimeEntry.deleted");
        return ResponseEntity.noContent().build();
    }
}
