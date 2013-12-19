package io.gatling.benchmark.jsonpath;

import static io.gatling.benchmark.jsonpath.GatlingJacksonBenchmark.*;
import io.gatling.benchmark.jsonpath.GatlingJacksonBenchmark.BytesAndPath;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import net.minidev.json.parser.JSONParser;

import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.logic.BlackHole;

@OutputTimeUnit(TimeUnit.SECONDS)
public class GatlingJsonSmartBenchmark {

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
		return bytesAndPath.path.query(new JSONParser(JSONParser.MODE_PERMISSIVE).parse(text));
	}

	@GenerateMicroBenchmark
	public void parseStringPrecompiledRoundRobin(ThreadState state, BlackHole bh) throws Exception {
		int i = state.next();
		bh.consume(parseStringPrecompiled(BYTES_AND_PATHS[i]));
	}
}
