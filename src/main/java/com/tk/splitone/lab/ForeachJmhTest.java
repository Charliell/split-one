package com.tk.splitone.lab;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
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
@Warmup(iterations = 5,time = 1)
@Measurement(iterations = 5,time = 1)
@Fork(3)
public class ForeachJmhTest {

    static class Demo{
        Integer id;
        String name;

        public Demo(Integer id,String name){
            this.id=id;
            this.name=name;
        }
    }

    static List<Demo> demoList;
    static {
        demoList = new ArrayList<>();
        for(int i=0;i<10000;i++){
            demoList.add(new Demo(i,"for-each-test"));
        }
    }


    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void forEach1(){
        List<Demo> list = new ArrayList<>();
        Iterator<Demo> iter = demoList.iterator();
        while (iter.hasNext()){
            list.add(iter.next());
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void forEach2(){
        List<Demo> list = new ArrayList<>();
        for(Demo item:demoList){
            list.add(item);
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void forEach3(){
        List<Demo> list = new ArrayList<>();
        demoList.stream().forEach(item->list.add(item));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(ForeachJmhTest.class.getSimpleName())
            .forks(1).build();
        new Runner(opt).run();
    }

}
