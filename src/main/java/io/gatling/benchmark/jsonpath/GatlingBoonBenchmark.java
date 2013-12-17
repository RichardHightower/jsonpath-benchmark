package io.gatling.benchmark.jsonpath;

import static io.gatling.benchmark.jsonpath.GatlingJacksonBenchmark.*;
import static io.gatling.benchmark.util.UnsafeUtil.*;
import io.gatling.benchmark.jsonpath.GatlingJacksonBenchmark.BytesAndPath;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.boon.json.JsonParserFactory;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.logic.BlackHole;

@OutputTimeUnit(TimeUnit.SECONDS)
public class GatlingBoonBenchmark {

	@State(Scope.Thread)
	public static class ThreadState {
		private int i = -1;

		public int next() {
			i++;
			if (i == BYTES_AND_PATHS.length)
				i = 0;
			return i;
		}
	}

	private Object parseCharsPrecompiled(BytesAndPath bytesAndPath) throws Exception {
		char[] chars = getChars(new String(bytesAndPath.bytes, StandardCharsets.UTF_8));
		@SuppressWarnings("unchecked")
		Map<String, Object> json = new JsonParserFactory().setCharset(StandardCharsets.UTF_8).create().parse(Map.class, chars);
		return bytesAndPath.path.queryJsonObject(json);
	}

	@GenerateMicroBenchmark
	public void parseCharsPrecompiledRoundRobin(ThreadState state, BlackHole bh) throws Exception {
		int i = state.next();
		bh.consume(parseCharsPrecompiled(BYTES_AND_PATHS[i]));
	}
	
	private Object parseBytesPrecompiled(BytesAndPath bytesAndPath) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> json = new JsonParserFactory().setCharset(StandardCharsets.UTF_8).create().parse(Map.class, bytesAndPath.bytes);
		return bytesAndPath.path.queryJsonObject(json);
	}

	@GenerateMicroBenchmark
	public void parseBytesPrecompiledRoundRobin(ThreadState state, BlackHole bh) throws Exception {
		int i = state.next();
		bh.consume(parseBytesPrecompiled(BYTES_AND_PATHS[i]));
	}
}