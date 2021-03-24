package com.Bootcamp.Project.Application.entities;
/*
import com.Bootcamp.Project.Application.entities.orderProduct.OrderProduct;*/
import com.Bootcamp.Project.Application.entities.cart.Cart;
import com.Bootcamp.Project.Application.entities.orderProduct.OrderProduct;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class ProductVariation extends BaseDomain {
    private int quantityAvailable;
    private boolean active;
    private int price;
    private String primaryImageName;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private JSONObject metadata;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productVariation")
    private Set<OrderProduct> orderProductSet;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<Cart> cartSet;

    //Constructors

    public ProductVariation() {
    }

    public ProductVariation(long id) {
        this.setId(id);
    }

    //Getters

    public int getPrice() {
        return price;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public Set<OrderProduct> getOrderProductSet() {
        return orderProductSet;
    }

    public boolean isActive() {
        return active;
    }

    public JSONObject getMetadata() {
        return metadata;
    }

    //Setters

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrderProductSet(Set<OrderProduct> orderProductSet) {
        this.orderProductSet = orderProductSet;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public Set<Cart> getCartSet() {
        return cartSet;
    }

    public void setCartSet(Set<Cart> cartSet) {
        this.cartSet = cartSet;
    }

    public void setOrderProduct(OrderProduct orderProduct){
        if(orderProduct!=null){
            if(orderProductSet==null){
                orderProductSet=new HashSet<>();
            }
            orderProduct.setProductVariation(this);
            orderProductSet.add(orderProduct);
        }
    }

    public void setCart(Cart cart){
        if(cartSet !=null){
            if(cartSet==null){
                cartSet=new HashSet<>();
            }
            cart.setProductVariation(this);
            this.getCartSet().add(cart);
        }
    }
}