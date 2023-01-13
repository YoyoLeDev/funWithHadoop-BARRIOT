package org.edu.reduce.map.fun.david;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.edu.reduce.map.fun.GameWritable;
import org.edu.reduce.map.fun.GameWritables;

import java.io.IOException;

public class DavidOrGoliathMapper extends Mapper<LongWritable, GameWritable, Text, IntWritable> {

    public static final Text GOLIATH = new Text("Goliath");
    public static final Text DAVID = new Text("David");
    public static final Text BLACK = new Text("black");
    public static final Text WHITE = new Text("white");

    @Override
    protected void map(LongWritable key, GameWritable value, Mapper<LongWritable, GameWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        if(!GameWritables.EMPTY.equals(value))
            context.write(davidOrGoliath(value), new IntWritable(1));
    }

    private static Text davidOrGoliath(GameWritable value){
        if(goliathWon(value))
            return GOLIATH;
        else
            return DAVID;
    }

    protected static boolean goliathWon(GameWritable value){
        return (value.getBlackRating() > value.getWhiteRating() && BLACK.equals(value.getWinner())
                || value.getBlackRating() < value.getWhiteRating() && WHITE.equals(value.getWinner()));
    }
}
