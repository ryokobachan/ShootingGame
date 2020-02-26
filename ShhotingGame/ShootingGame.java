import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Image;
public class ShootingGame extends JFrame {
    public void Shooting() {
        setSize(800, 500);
        setTitle("Game Example");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        MyJPanel myJPanel = new MyJPanel();
        Container c = getContentPane();
        c.add(myJPanel);
        setVisible(true);
    }
    public static void main(String[] args) {
    	ShootingGame q = new ShootingGame();
        q.Shooting();
    }
    public class MyJPanel extends JPanel
    implements ActionListener, MouseListener,
    MouseMotionListener {
        int my_x;
        int player_width, player_height;
        int enemy_width, enemy_height;
        int n;
        int enemy_x[];
        int enemy_y[];
        int enemy_move[];
        int enemy_alive[];
        int num_of_alive;
        int missile_num = 5;
        int my_missile_x[];
        int my_missile_y[];
        int enemy_missile_x[];
        int enemy_missile_y[];
        int enemy_missile_move[];
        int enemy_missile_flag[];
        int missile_flag[];
        public static final int MY_Y = 400;
        Image image, image2;
        Timer timer;
        public MyJPanel() {
            my_x = 250;
            missile_flag = new int[missile_num];
            my_missile_x = new int[missile_num];
            my_missile_y = new int[missile_num];
            int i;
            n = 14;
            num_of_alive = 14;
            for (i = 0; i < missile_num; i++) {
                missile_flag[i] = 0;
            }
            enemy_x = new int[n];
            enemy_y = new int[n];
            enemy_move = new int[n];
            enemy_alive = new int[n];
            enemy_missile_x = new int[n];
            enemy_missile_y = new int[n];
            enemy_missile_move = new int[n];
            enemy_missile_flag = new int[n];
            for (i = 0; i < 7; i++) {
                enemy_x[i] = 70 * (i + 1) - 50;
                enemy_y[i] = 50;
            }
            for (i = 7; i < n; i++) {
                enemy_x[i] = 70 * (i - 5) - 50;
                enemy_y[i] = 100;
            }
            for (i = 0; i < n; i++) {
                enemy_alive[i] = 1;
                enemy_move[i] = -10;
            }
            for(i=0;i<n;i++ ){
                enemy_missile_x[i] = enemy_x[i]+enemy_width/2;
                enemy_missile_y[i] = enemy_y[i]+enemy_height;
                enemy_missile_move[i] = 10 + (i%3);
                enemy_missile_flag[i] = 1;
            }
            ImageIcon icon = new ImageIcon("./img/player.jpg");
            image = icon.getImage();
            ImageIcon icon2 = new ImageIcon("./img/enemy.jpg");
            image2 = icon2.getImage();
            player_width = image.getWidth(this);
            player_height = image.getHeight(this);
            enemy_width = image2.getWidth(this);
            enemy_height = image2.getHeight(this);
            setBackground(Color.black);
            addMouseListener(this);
            addMouseMotionListener(this);
            timer = new Timer(50, this);
            timer.start();
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, my_x, 400, this);
            for (int i = 0; i < n; i++) {
                if (enemy_alive[i] == 1) {
                    g.drawImage(image2, enemy_x[i], enemy_y[i], this);
                }
            }
            for(int i=0;i<n;i++){
                if(enemy_missile_flag[i] == 1){
                    g.setColor(Color.white);
                    g.fillRect(enemy_missile_x[i],enemy_missile_y[i],2,5);
                }
            }
            for (int i = 0; i < missile_num; i++) {
                if (missile_flag[i] == 1) {
                    g.setColor(Color.white);
                    g.fillRect(my_missile_x[i], my_missile_y[i], 2, 5);
                }
            }
        }
        public void actionPerformed(ActionEvent e) {
            Dimension dim = getSize();
            for (int i = 0; i < n; i++) {
                enemy_x[i] += enemy_move[i];
                if ((enemy_x[i] < 0) || (enemy_x[i] > (dim.width - enemy_width))) {
                    enemy_move[i] = -enemy_move[i];
                }
                if(enemy_missile_flag[i]==1){
                    enemy_missile_y[i] += enemy_missile_move[i];
                    if (enemy_missile_y[i] > 500){
                        enemy_missile_flag[i] = 0; }
                }else{
                    enemy_missile_x[i] = enemy_x[i]+ enemy_width/2;
                    enemy_missile_y[i] = enemy_y[i];
                    enemy_missile_flag[i] = 1;
                }
                if( ((enemy_missile_x[i]+2) >= my_x) &&
                   ((my_x+player_width) > enemy_missile_x[i]) &&
                   ( (enemy_missile_y[i]+5) >= MY_Y ) &&
                   ((MY_Y + player_height) > enemy_missile_y[i]) ){
                    System.out.println("===Game End===");
                    System.exit(0); }
                
                
                
                
            }
            for (int i = 0; i < missile_num; i++) {
                if (missile_flag[i] == 1) {
                    my_missile_y[i] -= 15;
                    if (0 > my_missile_y[i]) {
                        missile_flag[i] = 0;
                    }
                }
                
                for(int j=0; j < n;j++){
                    if(enemy_alive[j] == 1){
                        if( (enemy_x[j] <= my_missile_x[i]) &&
                           ( my_missile_x[i] < (enemy_x[j]+enemy_width)) && ((enemy_y[j]+enemy_height) >= my_missile_y[i]) &&
                           enemy_y[j] < (my_missile_y[i]+5) ){
                            enemy_alive[j]=0; missile_flag[i] = 0; num_of_alive--;
                            if (num_of_alive == 0){
                                System.out.println("===Game Clear===");
                                System.exit(0); }
                        } }
                }
                
                
            }
            repaint();
        }
        public void mouseClicked(MouseEvent me) {}
        public void mousePressed(MouseEvent me) {
            
            if (missile_flag[0] == 0) {
                my_missile_x[0] = my_x + player_width / 2;
                my_missile_y[0] = MY_Y; //MY_Y=400
                missile_flag[0] = 1;
            } else if (missile_flag[1] == 0) {
                my_missile_x[1] = my_x + player_width / 2;
                my_missile_y[1] = MY_Y; //MY_Y=400
                missile_flag[1] = 1;
            }else if (missile_flag[2] == 0) {
                my_missile_x[2] = my_x + player_width / 2;
                my_missile_y[2] = MY_Y; //MY_Y=400
                missile_flag[2] = 1;
            }
            /*
             if (missile_flag[0] == 0) {
             my_missile_x[0] = my_x + player_width / 2;
             my_missile_y[0] = MY_Y-20; //MY_Y=400
             missile_flag[0] = 1;
             my_missile_x[1] = my_x + player_width / 2;
             my_missile_y[1] = MY_Y-10; //MY_Y=400
             missile_flag[1] = 1;
             my_missile_x[2] = my_x + player_width / 2;
             my_missile_y[2] = MY_Y; //MY_Y=400
             missile_flag[2] = 1;
             }
             */
            
        }
        public void mouseReleased(MouseEvent me) {}
        public void mouseExited(MouseEvent me) {}
        public void mouseEntered(MouseEvent me) {}
        public void mouseMoved(MouseEvent me) {
            my_x = me.getX();
        }
        public void mouseDragged(MouseEvent me) {}
    }
}


