package org.launchcode.budget_planning_backend.data;

import org.launchcode.budget_planning_backend.models.Invitation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Integer> {
    Invitation findByToken(String token);

}
