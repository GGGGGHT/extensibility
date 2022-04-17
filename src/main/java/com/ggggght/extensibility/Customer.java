package com.ggggght.extensibility;

public class Customer {
    Integer id;
    String name;

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override public String toString() {
        return "Customer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
