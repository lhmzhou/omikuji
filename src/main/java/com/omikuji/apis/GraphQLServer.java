package com.omikuji.apis;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;

import java.io.InputStream;

public class GraphQLServer extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", GraphQLServer.class.getName());
  }

  @Override
  public void init(Vertx vertx, Context context) {
    try {
      super.init(vertx, context);
      Router router = Router.router(vertx);
      // getting the configuration JSON
      JsonObject config = context.config();

      GraphQL graphQL = setupGraphQL();

      router.route("/graphql").handler(GraphQLHandler.create(graphQL));
      vertx.createHttpServer().requestHandler(router::accept).listen(8041);
    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {}

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {}

  private GraphQL setupGraphQL() throws Exception {
    InputStream in = this.getClass().getClassLoader().getResourceAsStream("schema.graphqls");
    String schema = new String(in.readAllBytes());
    GraphQLSchema graphQLSchema = buildSchema(schema);
    return GraphQL.newGraphQL(graphQLSchema).build();
  }

  private GraphQLSchema buildSchema(String sdl) {
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
    RuntimeWiring runtimeWiring = buildWiring();
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
        .type(
            TypeRuntimeWiring.newTypeWiring("Query")
                .dataFetcher("songById", MockGraphQLDataFetcher.getSongByIdDataFetcher()))
        .type(
            TypeRuntimeWiring.newTypeWiring("Song")
                .dataFetcher("songwriter", MockGraphQLDataFetcher.getSongwriterDataFetcher()))
        .build();
  }
}
