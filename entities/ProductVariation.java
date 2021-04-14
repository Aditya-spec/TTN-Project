package com.Bootcamp.Project.Application.entities;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class ProductVariation extends BaseDomain {


    private Integer quantityAvailable = 0;
    private Boolean active = true;
    private Double price = 0.0;
    private String primaryImageName;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private JSONObject metadata;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productVariation")
    private List<OrderProduct> orderProductList;

    @OneToMany(mappedBy = "productVariation", cascade = CascadeType.ALL)
    private List<Cart> cartList;

    //Constructors

    public ProductVariation() {
    }

    public ProductVariation(Long id) {
        this.setId(id);
    }

    //Getters

    public Double getPrice() {
        return price;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public Boolean getActive() {
        return active;
    }

    public JSONObject getMetadata() {
        return metadata;
    }



//Setters

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        if (orderProduct != null) {
            if (orderProductList == null) {
                orderProductList = new ArrayList<>();
            }
            orderProduct.setProductVariation(this);
            orderProductList.add(orderProduct);
        }
    }

    public void setCart(Cart cart) {
        if (cartList != null) {
            if (cartList == null) {
                cartList = new ArrayList<>();
            }
            cart.setProductVariation(this);
            this.getCartList().add(cart);
        }
    }
}