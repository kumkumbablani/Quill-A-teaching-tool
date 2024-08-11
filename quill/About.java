package quill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class About extends JFrame implements ActionListener{
    About(){
         
        setBounds(500,300,450,450);
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("quill/About_us.jpg"));
        Image i2= i1.getImage().getScaledInstance(600, 60, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel headerIcon = new JLabel(i3);
        headerIcon.setBounds(40,40,400,100);
        add(headerIcon);
        
                
        
        JLabel text = new JLabel("<html>Something About The App..<br>Quill-A Teaching Tool<br>Version 0.1.0(OS Build Java)<br>All rights reserved<br>Quill is a teaching tool </html>");
        text.setBounds(100,60,600,250);
        add(text);
        setVisible(true);
    
        JButton b1 = new JButton("OK");
        b1.setBounds(580, 500, 80, 25);
        b1.addActionListener(this);
        add(b1);

}
    @Override
      public void actionPerformed(ActionEvent ae){
        this.setVisible(false);
    }
    public static void main(String[] args){
        new About();
}
}
