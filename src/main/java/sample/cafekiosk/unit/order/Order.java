package sample.cafekiosk.unit.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sample.cafekiosk.unit.beverages.Beverage;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Getter
@RequiredArgsConstructor
public class Order {
    private final LocalDateTime orderDateTime;
    private final ArrayList<Beverage> beverages;
}
