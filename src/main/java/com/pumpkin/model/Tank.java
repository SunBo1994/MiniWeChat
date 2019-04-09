package com.pumpkin.model;

import com.pumpkin.client.TankClient;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Tank {
    //̹�����ڵ�λ������
    private int x;
    private int y;

    //̹��ǰһ��ʱ�̵�λ��
    private int oldX;
    private int oldY;



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    //̹�˵ĸ߶ȺͿ��
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    //����������������ʾ�˶����ٶ�
    private static final int XSPEED = 5;
    private static final int YSPEED = 5;

    //�����ĸ��������ͱ�������¼���������,Ĭ��״̬��Ϊfalse����ʾû�м�����
    private boolean b_L,b_U,b_R,b_D;

    //���һ�����ԣ���ʾ��̹���Ǻû��ǻ�
    private boolean good;

    public boolean isGood() {
        return good;
    }

    //̹�˵�����ֵ

    private int life = 100;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    //������ʶ��̹�˶����Ƿ���
    private boolean live =true;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
    //����һ��ö����������ʾ���еķ���
    public enum Direction{
        L,LU,U,RU,R,RD,D,LD,STOP
    }
    //����һ����������ʾ̹��Ҫ���еķ��򣬳�ʼ״̬ΪSTOP
    private Direction dir = Direction.STOP;

    //��Ͳ����
    private Direction ptDir = Direction.D;

    private TankClient tc;

    public Tank(int x, int y,boolean good) {
        //�ճ�ʼ��ʱǰһ��ʱ�̵�λ�þ��ǵ�ǰ��λ��
        this.oldX = x;
        this.oldY = y;
        this.x = x;
        this.y = y;
        this.good = good;
    }

    public Tank(int x, int y,boolean good,Direction dir, TankClient tc) {
        this(x,y,good);
        this.dir = dir;
        this.tc = tc;
    }
    //̹��û��step���������һ������
    private Random r = new Random();
    private int step = r.nextInt(7)+3;

    public void draw(Graphics g){
        if(!live){		//�ж�̹���Ƿ��������ˣ��򲻻滭������ֱ�ӷ���
            return ;
        }
        if(!this.good){
            //���з�̹��������÷���
            if(step==0){
                Direction[] dirs = Direction.values();
                int rn = r.nextInt(dirs.length);
                this.dir = dirs[rn];
                step = r.nextInt(7)+3;
            }
        }
        //��ͬ��̹�˻��Ʋ�ͬ����ɫ
        Color c = g.getColor();
        if(good){
            g.setColor(Color.RED);
            //����̹�˻�Ѫ��
            bloodBar(g);
        }
        else{
            g.setColor(Color.BLUE);
        }

        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        //��һ����Ͳ
        drawGunBarrel(g);

        move();//���ݼ��̰����Ľ���ı�̹�����ڵ�λ��

        step--;

        //�з��ӵ�����
        if(!this.good&&r.nextInt(40)>38){
            this.tc.getMissiles().add(fire());
        }

    }

    private void bloodBar(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GREEN);
        g.drawRect(x, y-10, this.WIDTH, 10);
        int w = this.WIDTH * this.life/100;//��������ֵ��������ʾ�ĳ���
        g.fillRect(x, y-10, w, 10);
        g.setColor(c);
    }

    private void drawGunBarrel(Graphics g) {
        int centerX = this.x + this.WIDTH/2;
        int centerY = this.y + this.HEIGHT/2;

        if(ptDir== Direction.L){//L,LU,U,RU,R,RD,D,LD,STOP
            g.drawLine(centerX, centerY, x, y + HEIGHT/2);
        }
        else if(ptDir== Direction.LU){
            g.drawLine(centerX, centerY, x, y );
        }
        else if(ptDir== Direction.U){
            g.drawLine(centerX, centerY, x+ WIDTH/2, y );
        }
        else if(ptDir== Direction.RU){
            g.drawLine(centerX, centerY, x + WIDTH, y );
        }
        else if(ptDir== Direction.R){
            g.drawLine(centerX, centerY, x+ WIDTH, y + HEIGHT/2);
        }
        else if(ptDir== Direction.RD){
            g.drawLine(centerX, centerY, x+ WIDTH, y + HEIGHT);
        }
        else if(ptDir== Direction.D){
            g.drawLine(centerX, centerY, x+ WIDTH/2, y + HEIGHT);
        }
        else if(ptDir== Direction.LD){
            g.drawLine(centerX, centerY, x, y + HEIGHT);
        }

    }

    //��¼���̵İ������
    public void keyPressed(KeyEvent e){
        int key=e.getKeyCode();
        //System.out.println(key);
        switch(key){
//			case 17://������Ctrlһֱ���£�һֱ�����ӵ�����˽���һ���ܷ���keyReleased��������
//				tc.getMissiles().add(fire());
//				break;
        case KeyEvent.VK_LEFT:
            b_L=true;
            break;
        case KeyEvent.VK_UP:
            b_U=true;
            break;
        case KeyEvent.VK_RIGHT:
            b_R=true;
            break;
        case KeyEvent.VK_DOWN:
            b_D=true;
            break;
        }
        //��������İ��������ȷ��̹�˼���Ҫ���еķ���
        moveDirection();
    }

    //���̰�������ʱ��ҲҪ���м�¼
    public void keyReleased(KeyEvent e) {
        int key=e.getKeyCode();
        switch(key){
        case 17:
            tc.getMissiles().add(fire());
            break;
        case KeyEvent.VK_A:
            produceMainTank();
            break;
        case KeyEvent.VK_LEFT:
            b_L=false;
            break;
        case KeyEvent.VK_UP:
            b_U=false;
            break;
        case KeyEvent.VK_RIGHT:
            b_R=false;
            break;
        case KeyEvent.VK_DOWN:
            b_D=false;
            break;
        //�����
        case KeyEvent.VK_S:
            if(this.live&&this.good){
                tc.getMissiles().addAll(superFire());
            }
            break;
        }
    }

    private void produceMainTank() {
        Tank t=this.tc.getTk();
        if(!t.isLive()){
            int x = r.nextInt(100)+200;
            int y = r.nextInt(150)+300;
            Tank newTank =new Tank(x,y,true, Direction.STOP,this.tc);
            this.tc.setTk(newTank);
        }
    }

    //���ݼ��̵İ��������ȷ��̹�˵����з���
    private void moveDirection() {//L,LU,U,RU,R,RD,D,LD,STOP
        if(b_L&&!b_U&&!b_R&&!b_D){
            dir = Direction.L;
        }
        else if(b_L&&b_U&&!b_R&&!b_D){
            dir = Direction.LU;
        }
        else if(!b_L&&b_U&&!b_R&&!b_D){
            dir = Direction.U;
        }
        else if(!b_L&&b_U&&b_R&&!b_D){
            dir = Direction.RU;
        }
        else if(!b_L&&!b_U&&b_R&&!b_D){
            dir = Direction.R;
        }
        else if(!b_L&&!b_U&&b_R&&b_D){
            dir = Direction.RD;
        }
        else if(!b_L&&!b_U&&!b_R&&b_D){
            dir = Direction.D;
        }
        else if(b_L&&!b_U&&!b_R&&b_D){
            dir = Direction.LD;
        }
        else{//����������������ǲ���
            dir = Direction.STOP;
        }
        //��̹�˷���ֵ����Ͳ����
        if(dir!= Direction.STOP){
            ptDir = dir;
        }

    }

    //���������з��򣬵��ǻ�ȱ�پ��������ϸ�ڣ����磺�����ǰ������Ҽ�����Ӧ�ú�����x+=XSPEED;
    private void move(){
        //���˶�ǰ���Ƚ���ǰλ�ñ������һ��ʱ�̵�λ��
        oldX = x;
        oldY = y;

        if(dir== Direction.L){//L,LU,U,RU,R,RD,D,LD,STOP
            x -= XSPEED;
        }
        else if(dir== Direction.LU){
            x -= XSPEED;
            y -= YSPEED;
        }
        else if(dir== Direction.U){
            y -= YSPEED;
        }
        else if(dir== Direction.RU){
            x += XSPEED;
            y -= YSPEED;
        }
        else if(dir== Direction.R){
            x += XSPEED;
        }
        else if(dir== Direction.RD){
            x += XSPEED;
            y += YSPEED;
        }
        else if(dir== Direction.D){
            y += YSPEED;
        }
        else if(dir== Direction.LD){
            x -= XSPEED;
            y += YSPEED;
        }
        else if(dir== Direction.STOP){
            //... nothing
        }

        //����̹��Խ������
        dealTankBorder();
    }
    /*
     * �������ܣ�����̹��Խ������
     * */
    private void dealTankBorder() {
        if(x<0){
            x = 0;
        }
        else if(x > TankClient.GAME_WIDTH-this.WIDTH){
            x = TankClient.GAME_WIDTH-this.WIDTH ;
        }
        if(y<10){
             y = 10;
        }
        else if(y>TankClient.GAME_WIDTH - this.HEIGHT){
             y = TankClient.GAME_WIDTH - this.HEIGHT;
        }
    }

    public Missile fire(){
        //�����ӵ���λ��,��������Ͳ�ķ�����newһ���ӵ�����
        int x = this.x +(this.WIDTH)/2 - (Missile.WIDTH)/2;
        int y = this.y + (this.HEIGHT)/2 -(Missile.HEIGHT)/2;
        //����̹�˵�����(good)��new��֮��Ӧ���ӵ�����
        Missile ms = new Missile(x,y,this.ptDir,this.good,this.tc);
        return ms;
    }
    //���ݵ�ǰ�������ӵ�
    public Missile fire(Direction dir){
        //�����ӵ���λ��,��������Ͳ�ķ�����newһ���ӵ�����
        int x = this.x +(this.WIDTH)/2 - (Missile.WIDTH)/2;
        int y = this.y + (this.HEIGHT)/2 -(Missile.HEIGHT)/2;
        //����̹�˵�����(good)��new��֮��Ӧ���ӵ�����
        Missile ms = new Missile(x,y,dir,this.good,this.tc);
        return ms;
    }
    /*
     * �������ܣ�8�����������һ���ӵ�
     * */
    public List<Missile> superFire(){
        Direction[] dirs = Direction.values();
        List<Missile> missiles =new ArrayList<Missile>();
        /*
         * ������ͣ������ÿ�����򶼷���һ���ӵ�
         * */
        for(int i=0;i<dirs.length;i++){
            if(dirs[i]!= Direction.STOP){
                missiles.add(fire(dirs[i]));
            }

        }
        return missiles;
    }

//		/*
//		 * ������Ͳ��ǰ�������������ö�ӵ�
//		 * */
//
//		public List<Missile> multipleFire(){
//			List<Missile> missiles = new ArrayList<Missile>();
//			int totalNum=10;
//			for(int i=0;i<totalNum;i++){
//				missiles.add(fire());
//			}
//			return missiles;
//		}

    /*
     * �������ܣ��õ�̹������λ�õľ��ο�
     * */
    public Rectangle getRect(){
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    //̹��ײһ��ǽ
    public boolean tankHitWall(Wall w){
        if(!this.live){
            return false;
        }
        if(this.getRect().intersects(w.getRect())){
            this.x = oldX;
            this.y = oldY;
            return true;
        }
        else{
            return false;
        }
    }

    //̹��ײһϵ�е�ǽ
    public boolean tankHitWalls(List<Wall> walls){
        for(int i=0;i<walls.size();i++){
            if(tankHitWall(walls.get(i))){
                return true;
            }
        }

        return false;
    }

    //̹�˳�Ѫ��
    public boolean eatBlood(Blood blood){
        if(this.live&&this.good&&this.getRect().intersects(blood.getRect())){
            blood.setLive(false);
            //����̹����Ѫ
            this.life = 100;
            return true;
        }
        else{
            return false;
        }
    }

    /*
     * �����Ĺ��ܣ�̹�˲��ܿ�Խ̹��
     * */
    public boolean  notAcrossWithTank(Tank tank){
        if(this.live&&tank.isLive()){
            if(this.getRect().intersects(tank.getRect())){
                this.x = oldX;
                this.y = oldY;
                tank.setX(tank.getOldX());
                tank.setY(tank.getOldY());
                return true;

            }
        }

        return false;
    }

    /*
     * �����Ĺ��ܣ�̹�˲��ܿ�Խһϵ��̹��
     * */
    public boolean  notAcrossWithTanks(List<Tank> tanks){
        for(int i=0;i<tanks.size();i++){
            if(this!=tanks.get(i)&&notAcrossWithTank(tanks.get(i))){
                return true;
            }
        }
        return false;
    }

}
