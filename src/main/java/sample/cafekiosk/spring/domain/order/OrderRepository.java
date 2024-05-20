package sample.cafekiosk.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(value = "SELECT * FROM orders o WHERE o.registered_date_time >= :startDateTime " +
            "AND o.registered_date_time < :endDateTime " +
            "AND o.order_status = :orderStatus", nativeQuery = true)
    List<Order> findOrdersBy(@Param("startDateTime")LocalDateTime startDateTime,
                             @Param("endDateTime")LocalDateTime endDateTime,
                             @Param("orderStatus")String orderStatus);
}
