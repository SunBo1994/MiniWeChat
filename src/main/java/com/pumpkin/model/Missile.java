package com.pumpkin.model;

import com.pumpkin.client.TankClient;

import java.awt.*;
import java.util.List;


public class Missile {

	//����������������ʾ�˶����ٶ�
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	//�ӵ����ڵ�λ��
	private int x;
	private int y;
	
	//̹�˵ĸ߶ȺͿ��
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	//�ӵ������з���
	private Tank.Direction dir;
	
	//�ӵ�����̹�˵�����
	private static final int POWER = 20;
	
	private boolean live = true;
	
	private boolean good =true;
	
	//ӵ��һ��TankClient���������
	private TankClient tc;
	public Missile(int x, int y, Tank.Direction dir, boolean good, TankClient tc){
		this(x,y,dir);
		this.good = good;
		this.tc = tc;
		
	}
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public void draw(Graphics g){
		//������ӵ����Ǵ��ģ��򲻽��л�ͼ
		if(!live){
			return ;
		}
		Color c = g.getColor();
		//�����ӵ��ĺû������ò�ͬ����ɫ
		if(this.good){
			g.setColor(Color.RED);
		}
		else{
			g.setColor(Color.BLUE);
		}
		
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}

	private void move() {
		if(dir== Tank.Direction.L){//L,LU,U,RU,R,RD,D,LD,STOP
			x -= XSPEED;
		}
		else if(dir== Tank.Direction.LU){
			x -= XSPEED;
			y -= YSPEED;
		}
		else if(dir== Tank.Direction.U){
			y -= YSPEED;
		}
		else if(dir== Tank.Direction.RU){
			x += XSPEED;
			y -= YSPEED;
		}
		else if(dir== Tank.Direction.R){
			x += XSPEED;
		}
		else if(dir== Tank.Direction.RD){
			x += XSPEED;
			y += YSPEED;
		}
		else if(dir== Tank.Direction.D){
			y += YSPEED;
		}
		else if(dir== Tank.Direction.LD){
			x -= XSPEED;
			y += YSPEED;
		}
		
		//�����ӵ����ڵ�λ��x,y���ж��ӵ��Ƿ񻹴����
		if(x<0||x>TankClient.GAME_WIDTH||y<0||y>TankClient.GAME_HEIGHT){
			live = false;
		}
	}
	public boolean isLive() {	
		return live;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		//���жϸ�̹���Ƿ��Ǵ�����Ѿ����ˣ��ӵ��Ͳ�������
		if(!t.isLive()){
			return false;
		}
		if(this.live&&this.good!=t.isGood()&&this.getRect().intersects(t.getRect())){//�ж��Ƿ�����ײ
			//��ײ֮���ӵ��͸�̹�˾�Ӧ�ö�����
			this.live = false;//�ӵ�����	
			/*
			 * ������ӵ�����������̹�ˣ�������ֵ�� 20������ǵз�̹�ˣ�ֱ�ӹҵ�
			 * */
			if(t.isGood()){
				int life = t.getLife() -POWER;
				if(life<=0){
					t.setLive(false);
				}			
				t.setLife(life);
				
			}
			else{
				t.setLive(false);//�з�̹������
			}		
			
			Explode e = new Explode(x,y,tc);
			tc.getExplodes().add(e);
			return true;
		}
		else{
			return false;
		}
	}
	/*
	 * һ���ӵ������̹��
	 * */
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	/*
	 * �����Ĺ��ܣ��ж��ӵ��Ƿ������ǽ
	 * */
	public boolean hitWall(Wall w){
		if(!this.live){
			return false;
		}
		
		if(this.live&&this.getRect().intersects(w.getRect())){
			this.live = false;
			return true;
		}
		else{
			return false;
		}		
		
	}
	/*
	 * �����Ĺ��ܣ��ж��ӵ��Ƿ������һϵ�е�ǽ
	 * */
	public boolean hitWalls(List<Wall> walls){
		for(int i=0;i<walls.size();i++){
			if(this.hitWall(walls.get(i))){
				return true;
			}
		}
		return false;
	}
}
