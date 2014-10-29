package com.packtpub.resteasy.services;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.packtpub.resteasy.entities.Person;


@Path("/person")
public class PersonService {

	private Map<Integer, Person> dataInMemory;

	public PersonService() {
		dataInMemory = new HashMap<Integer, Person>();
	}

	@POST
	@Consumes("application/xml")
	public Response savePerson(Person person) {
		System.out.println("POST savePerson: " + person);
		int id = dataInMemory.size() + 1;
		person.setId(id);
		dataInMemory.put(id, person);
		return Response.created(URI.create("/person/" + id)).build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		Person person = dataInMemory.get(id);
		if (person == null) {
			// There is not person with this id
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		dataInMemory.remove(id);
		return Response.status(Status.GONE).build();
	}

	@GET
	@Path("{id}")
	@Produces("application/xml")
	public Person findById(@PathParam("id") int id) {
		Person person = dataInMemory.get(id);
		if (person == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return person;
	}
}
