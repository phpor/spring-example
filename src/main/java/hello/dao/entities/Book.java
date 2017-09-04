package hello.dao.entities;

import javax.persistence.Entity;

@Entity
public class Book extends Base {
    private String id;
    private String name;
    private String isbn;
    private int price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Book() {}

    public Book(String id, String name, String isbn, int price) {
        super();
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.price = price;
    }
}