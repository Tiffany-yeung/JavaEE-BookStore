package com.pluralsight.bookstore.rest;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;
import com.pluralsight.bookstore.repository.BookRepository;
import com.pluralsight.bookstore.util.IsbnGenerator;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.*;

//client test to access remotely through JAX-RS client API
//needs an arquillian runner
@RunWith(Arquillian.class)
//telling arquillian that this test class will run as a remote client
@RunAsClient
public class BookEndpointTest {
	@Test
	public void createBook(@ArquillianResteasyResource("api/books") WebTarget webTarget) throws Exception {
		//Test counting books
		Response response = webTarget.path("count").request().get();
		assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
		
		//Test find all
		response = webTarget.request(APPLICATION_JSON).get();
		assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
		
		//creates a book
		Book book = new Book("isbn", "a  title", 12F, 123, Language.ENGLISH, new Date(), "imageURL", "description");
        response = webTarget.request(APPLICATION_JSON).post(Entity.entity(book, APPLICATION_JSON));
		assertEquals(CREATED.getStatusCode(), response.getStatus());
	}
	
	// deployment
	//tells ShrinkWrap to not package this test class in the archive
	//since we are testing the endpoint from outside the container
	@Deployment(testable = false)
	public static Archive<?> createDeploymentPackage() {
		return ShrinkWrap.create(WebArchive.class)
				.addClass(Book.class).addClass(Language.class)
				.addClass(BookRepository.class)
				.addClass(NumberGenerator.class)
				.addClass(IsbnGenerator.class)
				.addClass(TextUtil.class)
				.addClass(Language.class)
				//adding bookendpoint and JAX-RS config to archive for testing
				.addClass(BookEndpoint.class)
				.addClass(JAXRSConfiguration.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
	}
}
