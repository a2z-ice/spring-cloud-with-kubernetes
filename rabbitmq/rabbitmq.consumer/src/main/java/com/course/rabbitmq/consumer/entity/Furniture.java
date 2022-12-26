package com.course.rabbitmq.consumer.entity;

public class Furniture {

	private String color;

	private String material;

	private String sku;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Override
	public String toString() {
		return "Furniture [color=" + color + ", material=" + material + ", sku=" + sku + "]";
	}

}
