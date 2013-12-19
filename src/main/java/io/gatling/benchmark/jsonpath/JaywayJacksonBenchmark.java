package io.gatling.benchmark.jsonpath;

import static io.gatling.benchmark.jsonpath.Bytes.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.logic.BlackHole;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@OutputTimeUnit(TimeUnit.SECONDS)
public class JaywayJacksonBenchmark {

	private static final class BytesAndPath {
		public final byte[] bytes;
		public final JsonPath path;

		public BytesAndPath(byte[] bytes, JsonPath path) {
			this.bytes = bytes;
			this.path = path;
		}
	}

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private static final BytesAndPath[] BYTES_AND_PATHS = { /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH1)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH2)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH3)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH4)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH5)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH6)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH7)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH8)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH9)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH10)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH11)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH12)), /**/
	new BytesAndPath(GOESSNER_BYTES, JsonPath.compile(GOESSNER_PATH13)), /**/
	new BytesAndPath(WEBXML_BYTES, JsonPath.compile(WEBXML_PATH1)), /**/
	new BytesAndPath(TWITTER_BYTES, JsonPath.compile(TWITTER_PATH1)), /**/
	new BytesAndPath(TWITTER_BYTES, JsonPath.compile(TWITTER_PATH2)), /**/
	new BytesAndPath(TWITTER_BYTES, JsonPath.compile(TWITTER_PATH3)), /**/
	new BytesAndPath(TWITTER_BYTES, JsonPath.compile(TWITTER_PATH4)), /**/
	new BytesAndPath(TWITTER_BYTES, JsonPath.compile(TWITTER_PATH5)), /**/
	new BytesAndPath(TWITTER_BYTES, JsonPath.compile(TWITTER_PATH6)), /**/
	new BytesAndPath(TWITTER_BYTES, JsonPath.compile(TWITTER_PATH7)), /**/
	new BytesAndPath(TWENTY_K_BYTES, JsonPath.compile(TWENTY_K_PATH1)), /**/
	new BytesAndPath(TWENTY_K_BYTES, JsonPath.compile(TWENTY_K_PATH2)), /**/
	new BytesAndPath(TWENTY_K_BYTES, JsonPath.compile(TWENTY_K_PATH3)) /**/
	};

	@State
	public static class ThreadState {
		private int i = -1;

		public int next() {
			i++;
			if (i == BYTES_AND_PATHS.length)
				i = 0;
			return i;
		}
	}

	private Object parseStringPrecompiled(BytesAndPath bytesAndPath) throws Exception {
		String text = new String(bytesAndPath.bytes, StandardCharsets.UTF_8);
		return bytesAndPath.path.read(OBJECT_MAPPER.readValue(text, Object.class));
	}

	private Object parseBytesPrecompiled(BytesAndPath bytesAndPath) throws Exception {
		return bytesAndPath.path.read(OBJECT_MAPPER.readValue(bytesAndPath.bytes, Object.class));
	}

	@GenerateMicroBenchmark
	public void parseStringPrecompiledRoundRobin(ThreadState state, BlackHole bh) throws Exception {
		int i = state.next();
		bh.consume(parseStringPrecompiled(BYTES_AND_PATHS[i]));
	}

	@GenerateMicroBenchmark
	public void parseBytesPrecompiledRoundRobin(ThreadState state, BlackHole bh) throws Exception {
		int i = state.next();
		bh.consume(parseBytesPrecompiled(BYTES_AND_PATHS[i]));
	}
}
