package io.gatling.benchmark.jsonpath;

import org.boon.json.JsonParser;
import org.boon.json.JsonParserFactory;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.logic.BlackHole;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.gatling.benchmark.jsonpath.GatlingJacksonBenchmark.BYTES_AND_PATHS;


@OutputTimeUnit ( TimeUnit.SECONDS)
@State
public class LazyBoonJsonPathBM {

    JsonParser jsonParser = new JsonParserFactory ().createLaxParser ();

    @State( Scope.Thread)
    public static class ThreadState {
        private int i = -1;

        public int next() {
            i++;
            if (i == BYTES_AND_PATHS.length)
                i = 0;
            return i;
        }
    }

    private Object parseCharsPrecompiled(GatlingJacksonBenchmark.BytesAndPath bytesAndPath) throws Exception {
        Object json =
                jsonParser.parse(Map.class, new String(bytesAndPath.bytes, StandardCharsets.UTF_8));
        return bytesAndPath.path.query(json);
    }

    @GenerateMicroBenchmark
    public void parseCharsPrecompiledRoundRobin(ThreadState state, BlackHole bh) throws Exception {
        int i = state.next();
        bh.consume(parseCharsPrecompiled(BYTES_AND_PATHS[i]));
    }

    private Object parseBytesPrecompiled(GatlingJacksonBenchmark.BytesAndPath bytesAndPath) throws Exception {
        Object json =
                jsonParser.parse ( Map.class, bytesAndPath.bytes );
        return bytesAndPath.path.query(json);
    }

    @GenerateMicroBenchmark
    public void parseBytesPrecompiledRoundRobin(ThreadState state, BlackHole bh) throws Exception {
        int i = state.next();
        bh.consume(parseBytesPrecompiled(BYTES_AND_PATHS[i]));
    }
}
