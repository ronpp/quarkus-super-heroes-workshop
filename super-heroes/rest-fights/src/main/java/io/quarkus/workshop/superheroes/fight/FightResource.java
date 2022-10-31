package io.quarkus.workshop.superheroes.fight;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/fights")
@Tag(name = "Fight")
@Produces(APPLICATION_JSON)
public class FightResource {

    @Inject Logger logger;

    @Inject
    FightService service;

    @Operation(summary = "Returns a random fighters")
    @GET
    @Path("/randomfighters")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Fighters.class, required = true)))
    public Response getRandomFighters() {
        Fighters fighters = service.findRandomFighters();
        logger.debug("Get random fighters " + fighters);
        return Response.ok(fighters).build();
    }

    @Operation(summary = "Returns all the fights from the database")
    @GET
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Fight.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No fights")
    public Response getAllFights() {
        List<Fight> fights = service.findAllFights();
        logger.debug("Total number of fights " + fights);
        return Response.ok(fights).build();
    }

    @Operation(summary = "Returns a fight for a given identifier")
    @GET
    @Path("/{id}")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Fight.class)))
    @APIResponse(responseCode = "204", description = "The fight is not found for a given identifier")
    public Response getFight(Long id) {
        Fight fight = service.findFightById(id);
        if (fight != null) {
            logger.debug("Found fight " + fight);
            return Response.ok(fight).build();
        } else {
            logger.debug("No fight found with id " + id);
            return Response.noContent().build();
        }
    }

    @Operation(summary = "Creates a valid fighters")
    @POST
    @APIResponse(responseCode = "200", description = "The created fighters", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Fight.class)))
    public Fight fight(@Valid Fighters fighters, UriInfo uriInfo) {
        return service.persistFight(fighters);
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    @Tag(name="hello")
    public String hello() {
        return "Hello Fight Resource";
    }
}
