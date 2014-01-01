Independently verified. Boon is faster than Jackson.


```

Benchmark                                                              Mode Thr     Count  Sec         Mean   Mean error    Units
i.g.b.j.GatlingBoonBenchmark.parseCharsPrecompiledRoundRobin          thrpt   8        20    1    80346.155     6540.265    ops/s
i.g.b.j.GatlingBoonBenchmark.parseBytesPrecompiledRoundRobin          thrpt   8        20    1    77628.580     6633.424    ops/s
i.g.b.j.LazyBoonJsonPathBM.parseBytesPrecompiledRoundRobin            thrpt   8        20    1    63530.264     5220.353    ops/s
i.g.b.j.LazyBoonJsonPathBM.parseCharsPrecompiledRoundRobin            thrpt   8        20    1    67263.727     4400.878    ops/s
i.g.b.j.GatlingJacksonBenchmark.parseBytesPrecompiledRoundRobin       thrpt   8        20    1    41076.498     3031.684    ops/s
i.g.b.j.GatlingJacksonBenchmark.parseStringPrecompiledRoundRobin      thrpt   8        20    1    31354.957     3051.925    ops/s
i.g.b.j.GatlingJsonSmartBenchmark.parseStringPrecompiledRoundRobin    thrpt   8        20    1    29585.277     1801.637    ops/s
i.g.b.j.JaywayJacksonBenchmark.parseBytesPrecompiledRoundRobin        thrpt   8        20    1    17357.992     6662.890    ops/s
i.g.b.j.JaywayJacksonBenchmark.parseStringPrecompiledRoundRobin       thrpt   8        20    1    13665.135     5635.213    ops/s
```

```
Benchmark                                                              Mode Thr     Count  Sec         Mean   Mean error    Units
i.g.b.j.GatlingBoonBenchmark.parseBytesPrecompiledRoundRobin          thrpt   8         6    1   125093.867     2447.914    ops/s
i.g.b.j.GatlingBoonBenchmark.parseCharsPrecompiledRoundRobin          thrpt   8         6    1   117296.464     5713.760    ops/s
i.g.b.j.LazyBoonJsonPathBM.parseBytesPrecompiledRoundRobin            thrpt   8         6    1    93338.431    12747.054    ops/s
i.g.b.j.LazyBoonJsonPathBM.parseCharsPrecompiledRoundRobin            thrpt   8         6    1    96480.847     3097.341    ops/s
i.g.b.j.GatlingJacksonBenchmark.parseBytesPrecompiledRoundRobin       thrpt   8         6    1    83151.900     1084.591    ops/s
i.g.b.j.GatlingJacksonBenchmark.parseStringPrecompiledRoundRobin      thrpt   8         6    1    62074.683     1025.949    ops/s
i.g.b.j.GatlingJsonSmartBenchmark.parseStringPrecompiledRoundRobin    thrpt   8         6    1    58692.028     1583.224    ops/s
i.g.b.j.JaywayJacksonBenchmark.parseBytesPrecompiledRoundRobin        thrpt   8         6    1    23116.650    13294.959    ops/s
i.g.b.j.JaywayJacksonBenchmark.parseStringPrecompiledRoundRobin       thrpt   8         6    1    17084.089    12348.386    ops/s
$ java -jar target/microbenchmarks.jar ".*" -wi 3 -i 3 -f 2 -t 8

```

https://github.com/gatling/jsonpath-benchmark

https://github.com/gatling/jsonpath-benchmark/tree/boon04

"Update with latest new Boon parsers, rocking!"

"Boon now seems to be doing an incredible job for this use case"

```
Benchmark                                                                     Mode Thr     Count  Sec         Mean   Mean error    Units
GatlingBoonJsonParserForJsonPathBenchmark.parseBytesPrecompiledRoundRobin    thrpt   8        20    1   150867,743     8082,173    ops/s
GatlingBoonJsonParserForJsonPathBenchmark.parseCharsPrecompiledRoundRobin    thrpt   8        20    1   146406,116     8084,484    ops/s
GatlingBoonFastBenchmark.parseBytesPrecompiledRoundRobin                     thrpt   8        20    1   132918,858     6284,514    ops/s
GatlingBoonFastBenchmark.parseCharsPrecompiledRoundRobin                     thrpt   8        20    1   126100,990     4843,557    ops/s
GatlingJacksonBenchmark.parseBytesPrecompiledRoundRobin                      thrpt   8        20    1    98950,368     4735,560    ops/s
GatlingJacksonBenchmark.parseStringPrecompiledRoundRobin                     thrpt   8        20    1    76469,223     4301,819    ops/s
GatlingJsonSmartBenchmark.parseStringPrecompiledRoundRobin                   thrpt   8        20    1    74431,640     3237,084    ops/s
JaywayJacksonBenchmark.parseBytesPrecompiledRoundRobin                       thrpt   8        20    1    54236,658    16471,075    ops/s
JaywayJacksonBenchmark.parseStringPrecompiledRoundRobin                      thrpt   8        20    1    43793,581    13813,314    ops/s

```

# JsonPath implementations microbenchmark for the JVM

## Use case

Input is byte arrays.

If the parser can't guess encoding by itself, we try to decode in the most efficient possible way, but encoding time is accounted for.

Paths are precompiled as it's the use case for Gatling (they are cached)

## tl;dr

* **Gatling implementation is almost 3 TIMES FASTER than Jayway**
* Jackson currently perfoms better than Boon (~20% faster) on those samples
* If encoding is one included in the RFC (UTF8, UTF16... but not ISO), it can be efficient to let Jackson do the decoding

## How to

Build with `mvn clean package`

Run with `java -jar target/microbenchmarks.jar ".*" -wi 2 -i 10 -f 2 -t 8`

## Figures

Here are the results on my machine:

* OS X 10.9
* Hotspot 1.7.0_45
* Intel Core i7 2,7 GHz

Benchmark                                                      Mode Thr     Count  Sec         Mean   Mean error    Units
GatlingJacksonBenchmark.parseBytesPrecompiledRoundRobin       thrpt   8        20    1    93072,818     4120,802    ops/s
GatlingBoonBenchmark.parseBytesPrecompiledRoundRobin          thrpt   8        20    1    92354,272     7493,019    ops/s
GatlingBoonBenchmark.parseCharsPrecompiledRoundRobin          thrpt   8        20    1    84029,673     4189,809    ops/s
GatlingJsonSmartBenchmark.parseStringPrecompiledRoundRobin    thrpt   8        20    1    74342,808     3171,457    ops/s
GatlingJacksonBenchmark.parseStringPrecompiledRoundRobin      thrpt   8        20    1    73463,343     4708,547    ops/s
JaywayJacksonBenchmark.parseBytesPrecompiledRoundRobin        thrpt   8        20    1    53042,612    16835,683    ops/s
JaywayJacksonBenchmark.parseStringPrecompiledRoundRobin       thrpt   8        20    1    43823,404    14019,257    ops/s
