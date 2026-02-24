package com.kodnest.project.app.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
@Repository
public interface cartRepo extends JpaRepository<cart_items,Integer>{
	@Query("select c from cart_items c where c.user.userid=:userid and c.product.productid=:productid")
Optional<cart_items>findByuserandProduct(int userid, int productid);
@Query("select c from cart_items c join fetch c.product p left join fetch ProductImage pi on p.productid = pi.product.productid where c.user.userid=:userid")
List<cart_items>findCartItemsWithProductDetails(int userid);

@Query("update cart_items c set c.quantity=:quantity where c.id = :cartitemid")
void updateCartItemQuantity(int cartitemid, int quantity);

@Modifying
@Transactional
@Query("delete from cart_items c where c.user.userid=:userid and c.product.productid = :productid")
void deletcartItems(int userid, int productid);

@Query("select coalesce(sum(c.quantity),0) from cart_items c where c.user.userid=:userid")
int countTotalItems(int userid);

@Modifying
@Transactional
@Query("delete from cart_items c where c.user.userid=:userid")
void deletcartItemswithId(int userid);
}
