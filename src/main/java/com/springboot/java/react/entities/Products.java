package com.springboot.java.react.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="descriptions")
    private String descriptions;

    @Column(name="qty")
    private int qty;

    @Column(name="unit")
    private String unit;

    @Column(name="cost_price")
    private float cost_price;

    @Column(name="sell_price")
    private float sell_price;

    @Column(name="prod_pic")
    private String prod_pic;

    @Column(name="category")
    private String category;

    @Column(name="sale_price")
    private float sale_price;

    @Column(name="alert_level")
    private int alert_level;

    @Column(name="critical_level")
    private int critical_level;

    @Column(name="datecreated")
    private Timestamp datecreated;

    @Column(name="dateupdated")
    private Timestamp dateupdated ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getCost_price() {
		return cost_price;
	}

	public void setCost_price(float cost_price) {
		this.cost_price = cost_price;
	}

	public float getSell_price() {
		return sell_price;
	}

	public void setSell_price(float sell_price) {
		this.sell_price = sell_price;
	}

	public String getProd_pic() {
		return prod_pic;
	}

	public void setProd_pic(String prod_pic) {
		this.prod_pic = prod_pic;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getSale_price() {
		return sale_price;
	}

	public void setSale_price(float sale_price) {
		this.sale_price = sale_price;
	}

	public int getAlert_level() {
		return alert_level;
	}

	public void setAlert_level(int alert_level) {
		this.alert_level = alert_level;
	}

	public int getCritical_level() {
		return critical_level;
	}

	public void setCritical_level(int critical_level) {
		this.critical_level = critical_level;
	}

	public Timestamp getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Timestamp datecreated) {
		this.datecreated = datecreated;
	}

	public Timestamp getDateupdated() {
		return dateupdated;
	}

	public void setDateupdated(Timestamp dateupdated) {
		this.dateupdated = dateupdated;
	}
    
    
}
