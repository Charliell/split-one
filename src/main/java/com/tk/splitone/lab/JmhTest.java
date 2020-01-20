package com.tk.splitone.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author Chunlei.L
 * @date 2020/01/20
 */
@Warmup(iterations = 10,time = 1)
@Measurement(iterations = 10,time = 1)
public class JmhTest {

    static class Demo{
        int id;
        String name;
        public Demo(int id,String name){
            this.id=id;
            this.name=name;
        }
    }

    static List<Demo> demoList;
    static {
        demoList = new ArrayList<>();
        for(int i=0;i<10000;i++){
            demoList.add(new Demo(i,"test"));
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testExCloudInitValue(){
        Map<Integer,String> map = new HashMap<>();
        for(Demo demo:demoList){
            map.put(demo.id,demo.name);
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testIncloudInitValue(){
        Map<Integer,String> map = new HashMap<>((int)(demoList.size() / 0.75f) + 1);
        for(Demo demo:demoList){
            map.put(demo.id,demo.name);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(JmhTest.class.getSimpleName()).forks(
            1).build();
        new Runner(opt).run();
    }

}