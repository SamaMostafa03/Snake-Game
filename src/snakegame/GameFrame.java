/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 *
 * @author Sama
 */
public class GameFrame extends JFrame{
    GameFrame()
    {
        this.add(new GamePanel());
        this.setBounds(10, 10, 888, 700);
        this.setBackground(Color.DARK_GRAY);
        this.setVisible(true);
        this.setTitle("Snake Game");      
        ImageIcon frameImage= new ImageIcon("cuteSnake.jpg");
        this.setIconImage(frameImage.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);    
        this.setLocationRelativeTo(null);          
    }
}
