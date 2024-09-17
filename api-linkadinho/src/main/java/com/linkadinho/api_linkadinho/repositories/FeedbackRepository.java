package com.linkadinho.api_linkadinho.repositories;

import com.linkadinho.api_linkadinho.domain.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
