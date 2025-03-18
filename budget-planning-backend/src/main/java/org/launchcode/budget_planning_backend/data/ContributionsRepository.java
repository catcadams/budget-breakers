package org.launchcode.budget_planning_backend.data;

import org.launchcode.budget_planning_backend.models.Contributions;
import org.springframework.data.repository.CrudRepository;

public interface ContributionsRepository extends CrudRepository<Contributions, Integer> {
}
