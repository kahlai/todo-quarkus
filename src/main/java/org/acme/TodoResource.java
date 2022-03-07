package org.acme;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {
    
    @GET
    public List<Todo> getAll(){
        return Todo.listAll();
    }

    @POST
    @Transactional
    public Response create(Todo item){
        item.persist();
        return Response.ok(item).status(Status.CREATED).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id){
        Todo.deleteById(id);
        return Response.ok().status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Transactional
    @Path("/{id}")
    public Response update(Todo item, @PathParam("id") Long id){
        Todo entity = Todo.findById(id);
        entity.setTitle(item.getTitle());
        entity.setOrder(item.getOrder());
        entity.setCompleted(item.isCompleted());
        entity.persist();
        return Response.ok(entity).status(Status.OK).build();
    }
}
