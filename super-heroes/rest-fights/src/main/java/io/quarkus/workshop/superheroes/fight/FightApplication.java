package io.quarkus.workshop.superheroes.fight;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(title = "Fight API",
        description = "This API allows to get one random hero and one random villain and makes them fight",
        version = "1.0",
        contact = @Contact(name = "Quarkus", url = "https://github.com/quarkusio")),
    servers = {
        @Server(url = "http://localhost:8082")
    },
    externalDocs = @ExternalDocumentation(url = "https://github.com/quarkusio/quarkus-workshops", description = "All the Quarkus workshops")
)
public class FightApplication extends Application    {

    // Empty body
}
