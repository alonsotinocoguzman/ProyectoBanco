package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Model.Entity.Profile;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, ObjectId> {}
