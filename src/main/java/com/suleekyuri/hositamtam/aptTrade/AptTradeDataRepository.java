package com.suleekyuri.hositamtam.aptTrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AptTradeDataRepository extends JpaRepository<AptTradeData, Long> {
}
