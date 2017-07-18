package io.pivotal.pal.trackertest;

import io.pivotal.pal.tracker.JdbcTimeEntryRepository;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.junit.Before;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcTimeEntryRepositoryTest extends TimeEntryRepositoryContract {

    private JdbcTemplate jdbcTemplate;
    private JdbcTimeEntryRepository subject;

    @Before
    public void setUp() throws Exception {
        DataSource dataSource = new MariaDbDataSource(System.getenv("SPRING_DATASOURCE_URL"));
        subject = new JdbcTimeEntryRepository(dataSource);

        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DELETE FROM time_entries");
    }

    @Override
    TimeEntryRepository repo() {
        return subject;
    }
}