package org.edu.reduce.map.fun.elo;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.edu.reduce.map.fun.GameWritable;
import org.edu.reduce.map.fun.GameWritables;

import java.io.IOException;

import static com.sun.prism.paint.Color.BLACK;
import static com.sun.prism.paint.Color.WHITE;

public class EloMapper extends Mapper<LongWritable, GameWritable, Text, WritableEloPair> {

    @Override
    protected void map(LongWritable key, GameWritable value, Mapper<LongWritable, GameWritable, Text, WritableEloPair>.Context context) throws IOException, InterruptedException {
        if (!GameWritables.EMPTY.equals(value)) {
            context.write(value.getGameId(), computeNewElo(value));
        }
    }

    protected static WritableEloPair computeNewElo(GameWritable gameWritable) {
        if (BLACK.equals(gameWritable.getWinner())) {
            return newElo(gameWritable.getBlack_id(), gameWritable.getBlackRating(), gameWritable.getWhite_id(), gameWritable.getWhiteRating());
        } else if (WHITE.equals(gameWritable.getWinner())) {
            return newElo(gameWritable.getWhite_id(), gameWritable.getWhiteRating(), gameWritable.getBlack_id(), gameWritable.getBlackRating());
        } else {
            return newEloDraw(gameWritable.getWhite_id(), gameWritable.getWhiteRating(), gameWritable.getBlack_id(), gameWritable.getBlackRating());
        }
    }

    protected static WritableEloPair newElo(Text winnerId, int winnerElo, Text loserId, int loserElo) {
        return new WritableEloPair(
                new EloWritable(winnerId, compute(winnerElo, 20, 1.0, gainProbability(winnerElo, loserElo))),
                new EloWritable(loserId, compute(loserElo, 20, 0.0, gainProbability(loserElo, winnerElo)))
        );
    }

    protected static int compute(int oldElo, int k, double w, double p) {
        return (int) (oldElo + k * (w - p));
    }

    protected static double gainProbability(int elo1, int elo2) {
        double diff = elo1 - elo2;
        return 1.0 / (1.0 + Math.pow(10.0, -diff / 400.0));
    }

    protected static WritableEloPair newEloDraw(Text player1Id, int player1Elo, Text player2Id, int player2Elo) {
        return new WritableEloPair(
                new EloWritable(player1Id, compute(player1Elo, 20, 0.5, gainProbability(player1Elo, player2Elo))),
                new EloWritable(player2Id, compute(player2Elo, 20, 0.5, gainProbability(player2Elo, player1Elo)))
        );
    }
}
