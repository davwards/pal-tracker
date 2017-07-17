package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntriesController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntriesController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/timeEntries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntry);
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("/timeEntries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") Long id) {
        TimeEntry record = timeEntryRepository.get(id);
        return record == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(record);
    }

    @GetMapping("/timeEntries")
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok(timeEntryRepository.list());
    }

    @PutMapping("/timeEntries/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody TimeEntry updatedTimeEntry) {
        TimeEntry updatedEntry = timeEntryRepository.update(id, updatedTimeEntry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("timeEntries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") Long id) {
        timeEntryRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
