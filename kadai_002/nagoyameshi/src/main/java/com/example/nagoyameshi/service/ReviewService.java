package com.example.nagoyameshi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {
	
	

	@Autowired
	private final ReviewRepository reviewRepository;    
		   
	   public ReviewService(ReviewRepository reviewRepository) {
	       this.reviewRepository = reviewRepository;        
	   }    
	   
	   @Transactional
	   public void create(ReviewRegisterForm reviewRegisterForm,House houseId ,User userId) {
		   Review review = new Review();        
	       
		   review.setHouse(houseId);  
		   review.setUser(userId);              
		   review.setStar(reviewRegisterForm.getStar());
		   review.setReviewComment(reviewRegisterForm.getReviewComment());
	                   
		   reviewRepository.save(review);
	   }  
	   
	   @Transactional
	   public void update(ReviewEditForm reviewEditForm,House houseId ,User userId) {
		   Review review = reviewRepository.getReferenceById(reviewEditForm.getId());		   
	       
		   review.setHouse(houseId);  
		   review.setUser(userId);              
		   review.setStar(reviewEditForm.getStar());
		   review.setReviewComment(reviewEditForm.getReviewComment());
	                   
	       reviewRepository.save(review);
	   }  
	   
	
	public Review findById(Long id) {
	    return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
	}
	
	public void save(Review review) {
	    reviewRepository.save(review);
	}
	
	public void delete(Review review) {
	    reviewRepository.delete(review);  // レビューを削除
	}
	   

}
