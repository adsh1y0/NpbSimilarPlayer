package org.adsh1y0.tutorial.jubatus;

import us.jubat.common.Datum;
import us.jubat.recommender.IdWithScore;
import us.jubat.recommender.RecommenderClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adsh1y0 on 2014/03/11.
 */
public class Tutorial {

    protected static final String HOST = "127.0.0.1";
    protected static final int PORT = 9199;
    protected static final String NAME = "recommender_baseball";
    protected RecommenderClient recommender = null;

    public static void main(String[] args) {
        Tutorial tutorial = new Tutorial();
        List<String[]> lines = tutorial.readCsv("/baseball.csv");
        tutorial.update(lines);
        tutorial.analyze(lines);
    }

    public Tutorial() {
        try {
            recommender = new RecommenderClient(HOST, PORT, NAME, 60);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<String[]> readCsv(String filepath) {

        List<String[]> lines = new ArrayList<>();
        try {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(Tutorial.class.getClass().getResourceAsStream(filepath)))) {
                String line;
                while ((line = in.readLine()) != null) {
                    lines.add(line.split(","));
                }
            }
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }

        return lines;

    }

    protected void update(List<String[]> lines) {

        String[] columns = {"チーム", "打率", "試合数", "打席", "打数", "安打", "本塁打", "打点", "盗塁", "四球", "死球", "三振", "犠打", "併殺打", "長打率", "出塁率", "OPS", "RC27", "XR27"};
        for (String[] fullData : lines) {
            String player = fullData[0];
            String team = fullData[1];

            String[] data = Arrays.copyOfRange(fullData, 2, fullData.length);

            // python,rubyでチーム名だけを分けていたのは文字列と数値の学習設定メソッドが異なるから
            Datum datum = new Datum();
            datum.addString("チーム", team);

            for (int i = 0; i < data.length; i++) {
                System.out.print(columns[i] + " => " + data[i] + ", ");
                datum.addNumber(columns[i], Float.parseFloat(data[i]));
            }
            System.out.println();
            recommender.updateRow(player, datum);
        }

    }

    protected void analyze(List<String[]> lines) {

        for (String[] line : lines) {
            List<IdWithScore> sr = recommender.similarRowFromId(line[0], 4);

            System.out.print("player " + line[0] + " hi similar to : ");

            int max = 3;
            for (int i = 0; i < sr.size(); i++) {
                System.out.print(sr.get(i).id + " ");
                max++;
                if (max == i) break;
            }

            System.out.println();
        }

    }

}
