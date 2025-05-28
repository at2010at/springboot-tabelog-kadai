package com.example.nagoyameshi.repository;

import java.util.List;

//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Page;
//import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.entity.Review;

//レビューに検索機能は不要　順番は新着順（？）で固定

//public class ReviewRepository {
public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByHouse(House house); // Houseオブジェクトで絞る
	Page<Review> findByHouse(House house, org.springframework.data.domain.Pageable pageable);
	List<Review> findByHouseIdOrderByReviewDateDesc(Integer houseId);
	
    // あるユーザーが特定の民宿にレビュー済みかどうかを確認
    boolean existsByHouseIdAndUserId(Integer houseId, Integer userId);
    
    Page<Review> findByHouseId(Integer houseId, Pageable pageable);


}


