package es.ua.biblioteca.model;

import jakarta.persistence.*;

//import java.util.Date;
import java.time.LocalDate; // Importamos LocalDate
import java.util.Objects;


@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private String author;
    
    private LocalDate publication; //cambiamos el tipo

    public Book() {
    }
    
    public Book(String title, String author, LocalDate publication) {
        this.title = title;
        this.author = author;
        this.publication = publication; //añadido
    }

    public Book(Long id, String title, String author, LocalDate publication) {
    	this.id = id;
        this.title = title;
        this.author = author;
        this.publication = publication; //añadido
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.title != other.title) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {

        var builder = new StringBuilder();
        builder.append("Book{id=").append(id).append(", title=")
                .append(title).append(", author=")
                .append(author).append(", publication=") //añadido
                .append(author).append("}");

        return builder.toString();
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDate getPublication() {
		return publication;
	}

	public void setPublication(LocalDate publication) {
		this.publication = publication;
	}
    
}