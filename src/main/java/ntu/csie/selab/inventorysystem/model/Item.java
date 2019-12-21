package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "condition")
    private ItemCondition condition;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aid")
    private Acquisition acquisition;

    public Integer getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Double getPrice() {
        return price;
    }
    public ItemCondition getCondition() {
        return condition;
    }
    public Acquisition getAcquisition() {
        return acquisition;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setCondition(ItemCondition condition) {
        this.condition = condition;
    }
    public void setAcquisition(Acquisition acquisition) {
        this.acquisition = acquisition;
    }
    @Override
    public String toString() {
        return String.format("Item[id=%d, description='%s', quantity=%d, price=%2f, condition='%s', aid=%d]",
                id, description, quantity, price, condition.getCondition(), acquisition.getId());
    }
}
