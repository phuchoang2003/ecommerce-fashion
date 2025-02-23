package org.example.ecommercefashion.module.order.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.example.ecommercefashion.common.core.entity.BaseEntity;
import org.example.ecommercefashion.module.order.dto.OrderRequest;
import org.example.ecommercefashion.module.order.enums.OrderStatus;
import org.example.ecommercefashion.module.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "order_details")
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class OrderDetail extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total")
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;


    @OneToMany(mappedBy = "orderDetail", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST
    })
    @JsonManagedReference
    private Set<OrderItem> orderItems;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    public static OrderDetail fromRequest(OrderRequest request, User user) {
        return OrderDetail.builder()
                .description(request.getDescription())
                .user(user)
                .userId(user.getId())
                .status(OrderStatus.PENDING)
                .build();
    }

    public void addOrderItems(Set<OrderItem> items) {
        if (this.orderItems == null) {
            this.orderItems = new HashSet<>();
        }
        for (OrderItem item : items) {
            item.setOrderDetail(this);
            this.orderItems.add(item);
        }
    }

    public void calculateTotal() {
        this.total = orderItems
                .stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
