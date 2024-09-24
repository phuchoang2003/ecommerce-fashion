package org.example.ecommercefashion.entities.postgres;

import lombok.*;
import org.example.ecommercefashion.enums.CartStatus;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "carts")
@Entity
public class Cart extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CartStatus status = CartStatus.ACTIVE;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    private Set<CartItem> cartItems;

    public static Cart fromRequest(User user) {
        return Cart.builder()
                .user(user)
                .userId(user.getId())
                .status(CartStatus.ACTIVE)
                .totalAmount(BigDecimal.ZERO)
                .build();
    }

    public void addItemToCart(CartItem cartItem) {
        if (this.cartItems == null) {
            this.cartItems = new HashSet<>();
        }
        cartItems.add(cartItem);
    }

    public void calculateTotalAmount() {
        if (this.getCartItems() == null || this.getCartItems().isEmpty()) {
            this.setTotalAmount(BigDecimal.ZERO);
            return;
        }
        this.setTotalAmount(this.getCartItems()
                .stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public void removeItemFromCart(CartItem cartItem) {
        if (this.cartItems != null) this.cartItems.remove(cartItem);
    }

    public void removeAllItemsFromCart() {
        if (this.cartItems != null) this.cartItems.clear();
    }

}
