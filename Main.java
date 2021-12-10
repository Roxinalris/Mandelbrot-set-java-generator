import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

public class Main extends JComponent implements ActionListener{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int ITERATIONS = 100;
    public static final float SCALE = 250;

    private float hueOffset = 0; 


    private BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Timer timer;
    
    public Main(){
  
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        timer = new Timer(1,  this);
        //renderMandelbrotSet();
        
        JFrame frame = new JFrame("Mandelbrot set");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void addNotify(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        timer.start();
    }

    public void renderMandelbrotSet(){
        for(int x= 0; x < WIDTH;x++ ){
            for (int y = 0; y < HEIGHT; y++ ){
                int color = calculatePoint((x - WIDTH/2f)/SCALE, (y - HEIGHT/2f )/SCALE);

                buffer.setRGB(x, y, color);
            }
        }
    }

    public int calculatePoint(float x, float y){
        float cx = x;
        float cy = y;

        int i = 0;
        
        for(;i<ITERATIONS; i++){
            float nx = x*x - y*y + cx; 
            float ny = 2 * x *y + cy; 
            x = nx;
            y = ny;

            if(x*x + y*y > 4 ) break;

        }

        if (i == ITERATIONS) return 0x00000000;
        return Color.HSBtoRGB(((float)i / ITERATIONS + hueOffset)%1f, 0.5f, 1);
    }
    
    public void paint(Graphics g){
        g.drawImage(buffer, 0,0,null);
    }


    public void actionPerformed(ActionEvent e){
        
        hueOffset += 0.3f;
        renderMandelbrotSet();
        repaint();
    }
    
    public static void main(String[] args) {
        new Main();       
    } 
}
