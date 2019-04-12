package com.pumpkin.client;

import com.pumpkin.model.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * �˰汾�ĸĽ���̹���˶�ʱ���ܿ�Խ̹��
 *
 * */
public class TankClient extends Frame{
	
	public final static int GAME_WIDTH=600;
	public final static int GAME_HEIGHT=600;
	
	
	private Tank tk=new Tank(50,50,true, Tank.Direction.STOP,this);
	//��̹�˵�get��set����
	public Tank getTk() {
		return tk;
	}

	public void setTk(Tank tk) {
		this.tk = tk;
	}

	private Random r = new Random();
	//��Ŷ����з�̹��
	private List<Tank> enemyTanks = new ArrayList<Tank> ();
	//private Tank enemy = new Tank(100,100,false,this);
	
	/*
	 * Ϊÿ�������е�̹�����һ����ը����
	 * */
	private List<Explode> explodes = new ArrayList<Explode>();
	
	public List<Explode> getExplodes() {
		return explodes;
	}

	private List<Missile> missiles = new ArrayList<Missile> ();
	
	public List<Missile> getMissiles() {
		return missiles;
	}
	//����ǽ
	private Wall w1 = new  Wall(200, 200, 200, 10, this);
	private Wall w2 = new  Wall(100, 100, 10, 300, this);
	//һ��Ѫ��
	private Blood b = new Blood(300,400,10,10,this);
	
	private Image offScreenImage = null;
	
	public static void main(String[] args) {
		new TankClient().launchFrame();
	}
	
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics goffScreen = offScreenImage.getGraphics();// ���¶���һ�������������Ļ���//
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.darkGray);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	@Override
	public void paint(Graphics g) {
		//g.drawString("����A����",10 , 50);
		g.drawString("����S���Է������",10 , 50);
		g.drawString("��̹�˵�����ֵ��"+tk.getLife(),10 , 70);
		//��Ѫ��
		b.draw(g);
		//̹�˳�Ѫ��
		tk.eatBlood(b);
		
		//��ǽ
		w1.draw(g);
		w2.draw(g);
		
		/*
		 * ���������Լ���̹�ˣ������ж��Լ���̹���Ƿ��ǻ�ģ�����ǣ��򻭳���
		 * ��������ʾ Game  Over ,������100000����
		 * */
		if(tk.isLive()){
			tk.draw(g);	
			tk.tankHitWall(w1);
			tk.tankHitWall(w2);
		}
		else{
			g.drawString("Game Over,����A���Ը������",GAME_WIDTH/2 , GAME_HEIGHT/2);
		}	
		
		/*
		 * ���з�̹��Ҳ������,���û���˵з�̹�ˣ������һ�������ĵط�̹��
		 * */
		if(enemyTanks.size()==0){
			this.produceTank();
		}
		for(int i=0;i<enemyTanks.size();i++){
			Tank enemy = enemyTanks.get(i);
			//̹�˲��ܿ�Խ̹��
			enemy.notAcrossWithTanks(enemyTanks);
			if(!enemy.isLive()){
				enemyTanks.remove(enemy);
			}
			else{
				enemy.draw(g);
				enemy.tankHitWall(w1);
				enemy.tankHitWall(w2);
			}
			
		}
		
		//̹�˲��ܿ�Խ̹��
		tk.notAcrossWithTanks(enemyTanks);
		
		//ը������
		for(int i=0;i<explodes.size();i++){
			Explode e = explodes.get(i);
			if(!e.isLive()){
				explodes.remove(e);
			}
			else{
				e.draw(g);
			}
			
		}
		
		//���ӵ�
		for(int i=0;i<missiles.size();i++){
			Missile ms = missiles.get(i);			
			//�ж��ӵ��Ƿ񻹴���ڣ�������Ǵ��ģ����Ƴ�
			if(!ms.isLive()){
				missiles.remove(ms);
			}
			else{
				ms.hitTanks(enemyTanks);
				ms.draw(g);
				//���Լ���̹��Ҳ���뵽�ӵ����Թ����ķ�Χ
				ms.hitTank(tk);
				
				//�ӵ���ǽ
				ms.hitWall(w1);
				ms.hitWall(w2);
			}
			
		}
		tk.tankHitWall(w1);
		tk.tankHitWall(w2);
		
	}
	
	/*
	 * �������ܣ������з�̹��
	 * */
	public void produceTank(){
		
		int totalNum =r.nextInt(4)+3 ;
		
		for(int i=0;i<totalNum;i++){
			//λ��Ҳ���
			int x = (r.nextInt(10)+1)*40;
			int y = (r.nextInt(10)+1)*30;
			
			Tank.Direction[] dirs = Tank.Direction.values();
			int rn = r.nextInt(dirs.length);
			Tank.Direction dir = dirs[rn];
			Tank enemy = new Tank(x,y,false,dir,this);
			enemyTanks.add(enemy);
		}
	}
	public void launchFrame(){
		//��ʼ�������з�̹��
		this.produceTank();
		
		this.setTitle("̹�˴�ս");
		this.setLocation(300, 400);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.GRAY);
		//Ϊ�رմ��������Ӧ
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		//�����Ƿ������û��ı䴰�ڵĴ�С�����������£�������
		this.setResizable(false);
		this.setVisible(true);
		
		new Thread(new MyRepaint()).start();
		this.addKeyListener(new KeyMonitor());
		
	}
	
	private class MyRepaint implements Runnable{

		@Override
		public void run() {
			while(true){
				//ÿ50ms�ػ�һ��
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			tk.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			tk.keyReleased(e);
		}	
		
	}

}
