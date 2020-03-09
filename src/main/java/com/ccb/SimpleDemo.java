package com.ccb;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SimpleDemo {
  private static final Logger LOG = LoggerFactory.getLogger(SimpleDemo.class);

  public static void main(String[] args) throws Exception {
//    ParameterTool parameter = ParameterTool.fromArgs(args);
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.enableCheckpointing(10000);
//    env.getConfig().setGlobalJobParameters(parameter);
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
    env.setParallelism(1);

    DataStreamSource<String> dataStreamSource = env.fromCollection(Arrays.asList("this_is_a_test", "this_is_another_test", "this_is_the_third_test"));
//    dataStreamSource.print();
    dataStreamSource.addSink(new SinkFunction<String>() {
      @Override
      public void invoke(String value, Context context) throws Exception {
        System.out.println(value);
      }
    });

    env.execute("test-none-args");
  }
}
