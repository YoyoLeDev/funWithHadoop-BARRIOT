package org.edu.reduce.map.fun;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;
import org.apache.log4j.Logger;
public class ChessRecordReader extends RecordReader<LongWritable, GameWritable> {
    LineRecordReader lineRecordReader = new LineRecordReader();
    private static final Logger log = Logger.getLogger(ChessRecordReader.class);
    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        lineRecordReader.initialize(inputSplit,taskAttemptContext);
    }

    public boolean nextKeyValue() throws IOException, InterruptedException {
        return lineRecordReader.nextKeyValue();
    }

    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return lineRecordReader.getCurrentKey();
    }

    public GameWritable getCurrentValue() throws IOException, InterruptedException {
        return getGameWritableOrDefault(lineRecordReader.getCurrentValue().toString());
    }

    protected static GameWritable getGameWritableOrDefault(String line) {
        try {
            return GameWritables.fromColumns(line.split(","));
        } catch (Exception e) {
            log.error("Could not parse line :" + line, e);
            return new GameWritable();
        }
    }
    public float getProgress() throws IOException, InterruptedException {
        return lineRecordReader.getProgress();
    }

    public void close() throws IOException {
        lineRecordReader.close();
    }
}
