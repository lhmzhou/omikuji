import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonObject;

public abstract class ApplicationTest {

	protected static Vertx vertx = null;

	protected static HttpClient client;

	@BeforeClass
	public static void setup() throws InterruptedException {
		vertx = Vertx.vertx();
		// CountDownLatch latch = new CountDownLatch(1);
		// vertx.deployVerticle(new GraphQLServer(), (e) -> {
		// 	latch.countDown();
		// });
		// latch.await();
		client = vertx.createHttpClient();
	}
}