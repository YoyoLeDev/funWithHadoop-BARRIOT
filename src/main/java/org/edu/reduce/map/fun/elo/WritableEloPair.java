package org.edu.reduce.map.fun.elo;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class WritableEloPair implements Writable {
    EloWritable left;
    EloWritable right;

    public WritableEloPair(){
        this.left = new EloWritable();
        this.right = new EloWritable();
    }

    public WritableEloPair(EloWritable elo1, EloWritable elo2){
        this.left = elo1;
        this.right = elo2;
    }
    public void write(DataOutput out) throws IOException {
        this.left.write(out);
        this.right.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        this.left.readFields(in);
        this.right.readFields(in);
    }
}
