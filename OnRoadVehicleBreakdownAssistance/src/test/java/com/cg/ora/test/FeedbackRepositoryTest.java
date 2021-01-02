package com.cg.ora.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import com.cg.ora.model.Feedback;
import com.cg.ora.repository.FeedbackRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class FeedbackRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private FeedbackRepository feedbackRepo;

	public Feedback getFeedback() {
		Feedback feedback=new Feedback();
		feedback.setUserId(1);
		feedback.setFeedback("Good mechanic");
		feedback.setRatings(4.5f);
		return feedback;
		
	}

	
	// Testing the add Feedback functionality

	@Test
	@Rollback(false)
	@Order(1)
	public void addFeedback()
	{
		Feedback feedback=getFeedback();
		Feedback addFeedback=entityManager.persist(feedback);
		Feedback getFeedback=feedbackRepo.getOne(addFeedback.getFeedbackId());
		assertThat(getFeedback).isEqualTo(addFeedback);	
	}
	
	//testing view feedback by admin
	@Test
	@Order(2)
	public void viewFeedback(){
		List<Feedback> feedbackList=feedbackRepo.findAll();
		assertThat(feedbackList).size().isGreaterThan(0);
	}
	
	//testing view feedback by mechanic
//	@Test
//	@Order(3)
//	public void viewFeedbackByMechanic(){
//		Feedback getFeedback=feedbackRepo.getOne(1);
//		assertThat(getFeedback).isEqualTo(addFeedback.getMechanicId());
//	}
//	
	
	
	
	
}
