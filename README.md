# omikuji

`omikuji` a sample Vert.x application implementing a GraphQL Server.

## Build

### Execute

Build the gradle project by executing `./gradlew run` to run the `GraphQLServer`.

### Test

The server exposes a POST endpoint to ` http://localhost:8041/graphql`. Test the mock implementation by using a GraphQL Client or by using a curl command:

```
curl -X POST -H "Content-Type: application/json" -d '{"query": "{songById(id: \"song-1\"){name}}"}'
http://localhost:8041/graphql
```
