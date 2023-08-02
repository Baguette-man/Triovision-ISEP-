package com.example.triovisioniseprtp.classes;


import java.util.ArrayList;
import java.util.Random;

public class Card {

    public Color pawnTop;

    public Color pawnMid;

    public Color getPawnTop() {
        return pawnTop;
    }

    public Color getPawnMid() {
        return pawnMid;
    }

    public Color getPawnBottom() {
        return pawnBottom;
    }


    public Color pawnBottom; //This one is offset

    public Card() {
        ArrayList<Color> lastPawns = new ArrayList<>();
        lastPawns.add(Color.GREEN);
        lastPawns.add(Color.GREEN);
        lastPawns.add(Color.RED);
        lastPawns.add(Color.RED);
        lastPawns.add(Color.YELLOW);
        lastPawns.add(Color.YELLOW);
        lastPawns.add(Color.BLUE);
        lastPawns.add(Color.BLUE);

        Random rd = new Random();
        int rng = rd.nextInt(lastPawns.size());
        pawnTop = lastPawns.get(rng);
        lastPawns.remove(rng);
        rng = rd.nextInt(lastPawns.size());
        pawnMid = lastPawns.get(rng);
        lastPawns.remove(rng);
        rng = rd.nextInt(lastPawns.size());
        pawnBottom = lastPawns.get(rng);
        lastPawns.remove(rng);
    }


}
