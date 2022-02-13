package hello.itemservice.domain.item;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(){
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

//int가 아닌 Integer쓰는 이유는 가격과 양이 0일 때도 있으니깐. null 값도 받기 위해
