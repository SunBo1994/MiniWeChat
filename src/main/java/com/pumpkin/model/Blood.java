package com.pumpkin.model;

import com.pumpkin.client.TankClient;

import java.awt.*;


public class Blood {

    private int x;
    private int y;
    private int w;
    private int h;

    private TankClient tc;

    private int[][] pos ={
            {300,400},{330,390},{340,370},{320,350},{300,370},{290,390}
    };

    private boolean live = true;

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }

    public Blood(int x, int y, int w, int h, TankClient tc) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tc = tc;
    }
    private int step = 0;

    public void draw(Graphics g){
        if(!live){
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.RED);
        if(step>=pos.length){
            step = 0;
        }
        int x = pos[step][0];
        int y = pos[step][1];
        step++;
        g.fillRect(x, y, w, h);

        g.setColor(c);

    }

    public Rectangle getRect(){
        return new Rectangle(x,y,w,h);
    }

}
