# JsonPath implementations microbenchmark for the JVM

## Use case

Input is byte arrays.

If the parser can't guess encoding by itself, we try to decode in the most efficient possible way, but encoding time is accounted for.

Paths are precompiled as it's the use case for Gatling (they are cached)

## tl;dr

* **Gatling implementation more than **TWICE FASTER than Jayway** (both running w/ Jackson)
* Boon now seems to be doing an incredible job for this use case (have to dig in implementation to know if there's any limitation though)
* If encoding is one included in the RFC (UTF8, UTF16... but not ISO), it can be efficient to let Jackson do the decoding

## How to

Build with `mvn clean package`

Run with `java -jar target/microbenchmarks.jar ".*" -wi 2 -i 10 -f 2 -t 8`

## Figures

Here are the results on my machine:

* OS X 10.9
* Hotspot 1.7.0_45
* Intel Core i7 2,7 GHz

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
