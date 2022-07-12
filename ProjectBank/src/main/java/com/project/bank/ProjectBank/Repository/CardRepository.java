package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Model.Entity.Card;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CardRepository extends ReactiveCrudRepository<Card,String> {
}
