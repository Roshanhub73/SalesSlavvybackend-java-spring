package com.kodnest.project.app.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface orderitemsRepo extends JpaRepository<order_items, Integer>{
@Query("select oi from order_items oi where oi.order.order_id= :order_id")
List<order_items>findByOrderid(String order_id);


@Query("select oi from order_items oi where oi.order.user_id = :userid and oi.order.status = 'Success'")
List<order_items>findSucessfulOrderitemsByuserId(int userid);
}
