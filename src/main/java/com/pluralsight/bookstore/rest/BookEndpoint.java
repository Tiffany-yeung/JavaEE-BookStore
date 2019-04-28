package com.pluralsight.bookstore.rest;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.repository.BookRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@SwaggerDefinition(
	info = @Info(
		description = "Bookstore APIs exposed from a Java EE back-end to an Angular front-end",
		title = "BookStore APIs",
		version = "V1.0.0"),
	host = "localhost:8080",
	basePath = "/bookstore-back/api",
	schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
	tags = {
        @Tag(name = "Book", description = "Tag used to denote operations as private")
	}
)

@Path("/books")
@Api("Book")
public class BookEndpoint {
	
	//injection points
    @Inject
    private BookRepository bookRepository;

    //business methods
    
    @POST
    //consumes book object in JSON format from client
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Creates a book given a JSon Book representation")
    @ApiResponses({
        @ApiResponse(code = 201, message = "The book is created"),
        @ApiResponse(code = 415, message = "Format is not JSon")
    })
    //JAX-RS endpoints returns a response with the book
    public Response createBook(Book book, @Context UriInfo uriInfo) {
        book = bookRepository.create(book);
        //builds the new uri and returns it to the client
        URI createdURI = uriInfo.getBaseUriBuilder().path(book.getId().toString()).build();
        //build the response and return it
        return Response.created(createdURI).build();
    }

    @GET
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Returns a book given an id", response = Book.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Book found"),
        @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
        @ApiResponse(code = 404, message = "Book not found")
    })
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookRepository.find(id);
        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(book).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Deletes a book given an id")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Book has been deleted"),
        @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
        @ApiResponse(code = 500, message = "Book not found")
    })
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookRepository.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Returns all the books", response = Book.class, responseContainer = "List")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Books found"),
        @ApiResponse(code = 204, message = "No books found"),
    })
    public Response getBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.size() == 0)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok(books).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Returns the number of books", response = Long.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Number of books found"),
        @ApiResponse(code = 204, message = "No books found"),
    })
    public Response countBooks() {
        Long nbOfBooks = bookRepository.countAll();
        if (nbOfBooks == 0)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok(nbOfBooks).build();
    }
}
