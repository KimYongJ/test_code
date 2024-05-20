package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverages.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Getter
public class CafeKiosk {
    private static final LocalTime SHOP_OPEN_TIME  = LocalTime.of(10,0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22,0);

    private final ArrayList<Beverage> beverages = new ArrayList<>();
    public void add(Beverage beverage, int cnt) {
        if(cnt <= 0){
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
        }
        while(cnt-- > 0){
            beverages.add(beverage);
        }
    }

    public void remove(Beverage beverage){
        beverages.remove(beverage);
    }

    public void clear(){
        beverages.clear();
    }

//    public int calculateTotalPrice() {
//        int totalPrice = 0;
//        for(Beverage beverage : beverages){
//            totalPrice += beverage.getPrice();
//        }
//        return totalPrice;
//    }

    public Order cerateOrder(LocalDateTime currentDateTime){
        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        }
        return new Order(currentDateTime, beverages);
    }

    public int calculateTotalPrice() {
        return beverages.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }
}
