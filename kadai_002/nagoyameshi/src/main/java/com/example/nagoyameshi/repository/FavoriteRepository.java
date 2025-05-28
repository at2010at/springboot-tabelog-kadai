package com.example.nagoyameshi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.entity.User;


public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Optional<Favorite> findByHouseAndUser(House house, User user);
    List<Favorite> findByUser(User user);
    void deleteByHouseAndUser(House house, User user);
    
    boolean existsByHouseIdAndUserId(Integer houseId, Integer userId);   
    Favorite findByHouseIdAndUserId(Integer houseId, Integer userId);
        
    Page<Favorite> findByUserId(Integer userId, Pageable pageable);
}