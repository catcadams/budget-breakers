package org.launchcode.budget_planning_backend.data;

import org.launchcode.budget_planning_backend.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
}
