package com.pluralsight.bookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "Book resource representation")
public class Book {
	
	//Attributes
    @Id
    @GeneratedValue
    @ApiModelProperty("Identifier")
    private Long id;

    @Column(length = 200)
    @NotNull
    @Size(min = 1, max = 200)
    @ApiModelProperty("Title of the book")
    private String title;

    @Column(length = 10000)
    @Size(min = 1, max = 10000)
    @ApiModelProperty("Summary describing the book")
    private String description;

    @Column(name = "unit_cost")
    @Min(1)
    @ApiModelProperty("Unit cost")
    private Float unitCost;

    @Column(length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty("ISBN number")
    private String isbn;

    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    @Past
    @ApiModelProperty("Publication date")
    private Date publicationDate;

    @Column(name = "nb_of_pages")
    @ApiModelProperty("Number of pages")
    private Integer nbOfPages;

    @Column(name = "image_url")
    @ApiModelProperty("URL of the book cover image")
    private String imageURL;

    @Enumerated
    @ApiModelProperty( value = "Language of book")
    private Language language;
    
    //Constructors
    public Book() {
    }

    public Book(String isbn, String title, Float unitCost, Integer nbOfPages, Language language, Date publicationDate, String imageURL, String description) {
        this.isbn = isbn;
        this.title = title;
        this.unitCost = unitCost;
        this.nbOfPages = nbOfPages;
        this.language = language;
        this.publicationDate = publicationDate;
        this.imageURL = imageURL;
        this.description = description;
    }

    //Getters and setters
    //So you can call the set methods from another class
    //Then you can get them again specifying the same object
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getNbOfPages() {
        return nbOfPages;
    }

    public void setNbOfPages(Integer nbOfPages) {
        this.nbOfPages = nbOfPages;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imagURL) {
        this.imageURL = imagURL;
    }
 
    //ToString method
    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", unitCost=" + unitCost +
            ", isbn='" + isbn + '\'' +
            ", publicationDate=" + publicationDate +
            ", language=" + language +
            '}';
    }
}
