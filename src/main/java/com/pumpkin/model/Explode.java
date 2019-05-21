package com.pumpkin.model;

import com.pumpkin.client.TankClient;

import java.awt.*;


public class Explode {
	//��ը���ڵ�λ��
	private int x;
	private int y;
	
	//��ըͼƬ��ֱ��
	private int[] diameter={6,20,40,60,20,7};
	
	//��ʶ��ը�����Ƿ��������
	private boolean live =true;
	
	public boolean isLive() {
		return live;
	}

	//��ʶ��ը��ʾ���ڼ�����
	private int step = 0;
	
	private TankClient tc;
	
	public Explode(int x ,int y , TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	/*
	 * �ñ�ը��ʾ����
	 * */
	public void draw(Graphics g){
		if(!live) return;
		//�ж���ʾ���ڼ����ˣ����ȫ����ʾ���ˣ���˶�������������
		if(step>=diameter.length){
			live = false;
			step = 0;
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
	}
	
	
}
