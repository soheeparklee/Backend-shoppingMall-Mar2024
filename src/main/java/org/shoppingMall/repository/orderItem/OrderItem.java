package org.shoppingMall.repository.orderItem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.shoppingMall.repository.orderTable.OrderTable;
import org.shoppingMall.repository.productOption.ProductOption;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderTable orderTable;

    @ManyToOne
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Column(name = "item_amount", nullable = false)
    private Integer itemAmount;

    @Column(name = "order_item_price", nullable = false)
    private Integer orderItemPrice;
}
