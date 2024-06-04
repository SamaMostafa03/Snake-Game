/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Sama
 */
public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=825,SCREEN_HEIGHT=600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    ImageIcon faceU,faceD,faceL,faceR;
    final int x[] = new int[GAME_UNITS], y[] = new int[GAME_UNITS];
    int snakeBodyLength = 3, numberOfEatenApples, appleX, appleY;
    char direction = 'R';
    boolean start=false,running = false;
    static final int DELAY = 100;
    Timer timer;
    GamePanel()
    {
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    
    public void startGame()
    {
        x[0]=100; x[1]=75; x[2]=50;
        y[0]=100; y[1]=100; y[2]=100;
        addNewApple();
        timer = new Timer(DELAY,this);
        timer.start();
    }  
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
       if(running)
       {
           move();
           checkEatingApple();
           checkObstacles();
       }
       repaint();
    }
  
    @Override
    public void paintComponent(Graphics g) 
    {
	super.paintComponent(g);
	paint(g);
    }
    @Override
    public void paint(Graphics g)
    {
       if((!start&&!running)||(running))
        {
            //header for score
            g.setColor(Color.WHITE);
            g.drawRect(24,10 ,824 , 55);
            g.setColor( Color.decode("#B75030"));
            g.fillRect(25, 11,825 , 55);
            g.setColor(Color.WHITE);
            g.setFont( new Font("Times",Font.BOLD, 30));
            g.drawString("Score: "+this.numberOfEatenApples, 370,50);
            //game border
            g.setColor(Color.WHITE);
            g.drawRect(24, 74,824 , 552);
            g.setColor( Color.black);
            g.fillRect(25, 75,825 , 550); 
            // 2d matrics
            int rec=1;
            for(int i=25;i<=SCREEN_WIDTH;i+=UNIT_SIZE)
            {
                for(int j=75;j<=SCREEN_HEIGHT;j+=UNIT_SIZE)
                {
                    if(rec%2==0)
                    {
                        g.setColor(Color.decode("#FCBB4C"));
                        g.fillRect(i, j, UNIT_SIZE, UNIT_SIZE);
                    }
                    else 
                    { 
                        g.setColor(Color.decode("#FCCD7C"));
                        g.fillRect(i,j, UNIT_SIZE, UNIT_SIZE);
                    }
                    rec^=3;
                }
                rec^=3;
            }
            //apple
            ImageIcon image = new ImageIcon("Images/apple.png");
            g.drawImage(image.getImage(), appleX, appleY, UNIT_SIZE,UNIT_SIZE, this);        
            //snake
            for (int i=0;i<this.snakeBodyLength;i++) {
                if(i==0)//face
                {
                    switch(this.direction)
                    {
                        case('U') -> { 
                            faceU= new ImageIcon("Images/snakeFaceU.png");
                            g.drawImage(faceU.getImage(), x[i], y[i], UNIT_SIZE,UNIT_SIZE, this);
                        }
                        case('D') -> { 
                            faceD= new ImageIcon("Images/snakeFaceD.png");
                            g.drawImage(faceD.getImage(), x[i], y[i], UNIT_SIZE,UNIT_SIZE, this);
                        }
                        case('L') -> { 
                            faceL= new ImageIcon("Images/snakeFaceL.png");
                            g.drawImage(faceL.getImage(), x[i], y[i], UNIT_SIZE,UNIT_SIZE, this);
                        }
                        case('R') -> { 
                            faceR= new ImageIcon("Images/snakeFaceR.png");
                            g.drawImage(faceR.getImage(), x[i], y[i], UNIT_SIZE,UNIT_SIZE, this);
                        }        
                    }
                }
                else //body
                {
                   g.setColor(Color.WHITE);
                   g.drawOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                   g.setColor(Color.decode("#4C7CF4"));
                   g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            
        }
              
        if(start&&!running)
        {
           gameOver(g);
        }
        
    }
    
    public void addNewApple()
    {
        appleX=(new Random().nextInt((int)((SCREEN_WIDTH-25)))+25)/UNIT_SIZE*UNIT_SIZE;
        appleY=(new Random().nextInt((int)((SCREEN_HEIGHT-75)))+75)/UNIT_SIZE*UNIT_SIZE;
    }
    
    public void checkEatingApple()
    {
        if(x[0]==appleX&&y[0]==appleY)
        {
            
            this.numberOfEatenApples++;
            this.snakeBodyLength++;
            addNewApple();
        }
    }
    
    public void checkObstacles()
    {
        if(running)
        {
            for(int i=this.snakeBodyLength;i>0;i--)
            {
                if (x[i]==x[0] && y[i]==y[0]) {
                    running = false;  break;
                }
            }
            if (x[0]<25 || y[0]<75 || x[0]>SCREEN_WIDTH || y[0]>SCREEN_HEIGHT) {
                running = false;
            }
        }
        if(start&&!running)
        {
            timer.stop();
        }
    }
    
    public void gameOver(Graphics g)
    {
            g.setColor(Color.black);
            g.setFont( new Font("Times",Font.BOLD, 60));
            g.drawString("Game over", 280,350);
    }
    
    public void move()
    {
        for(int i=this.snakeBodyLength;i>0;i--)
        {
            x[i]=x[i-1]; y[i]=y[i-1];
        }
        switch(direction) {
	    case 'U' -> y[0] = y[0] - UNIT_SIZE;
	    case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
	    case 'R' -> x[0] = x[0] + UNIT_SIZE;
	}      
    }
    
    public class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) 
        {
            if(e.isActionKey()&&e.getKeyCode()!=KeyEvent.VK_LEFT){running=true; start=true;}
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> {if(direction != 'D') {direction = 'U';}}     
                case KeyEvent.VK_DOWN -> {if(direction != 'U') {direction = 'D';}}
                case KeyEvent.VK_LEFT -> {if(direction != 'R') {direction = 'L';}}
                case KeyEvent.VK_RIGHT-> {if(direction != 'L') {direction = 'R';}}  
            }
        }
    }


}
