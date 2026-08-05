package org.example.simplemq;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface FailedMessageRepo extends JpaRepository<MQEntity,Integer> {
    List<MQEntity> findByProcessedFalse();
}
