package Calculator;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Calculator_Test 
{
    public static void main (String [] args)
    {
        new Calculator("WizC4lc Destr0y3R");    // #styl3
    }
    
    
    static class Calculator extends JFrame
    {
        static DisplayPanel display = new DisplayPanel();
        static JPanel buttonPanel = new JPanel();
        static double op1 = 0;
        static double op2 = 0;
        static String operation = "";
        static Scanner opScanner;
        static String inputString = "0";
        static String outputString = "0";
        static boolean op1Full = false;      // indicates whether there's a value stored in the short term register
        static boolean opPressed = false;    // indicates whether there's an operation stored in the op register
        
        Calculator(String title)
        {
            setTitle(title);
            setSize(350, 400);  // width, height
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
                // We need to use the BoxLayout in order to have the requested pixel sizes
                // for our display and button panel respected. And whenever we use the 
                // BoxLayout, we must remember to attach it not to the JFrame itself, but to
                // the ContentPane attached to the JFrame
                
                // Now we set up our button panel
            buttonPanel.setLayout(new GridLayout(6,4));     // height, width
            buttonPanel.add(new opButton("%"));
            buttonPanel.add(new opButton("Sqrt"));
            buttonPanel.add(new opButton("Sqr"));
            buttonPanel.add(new opButton("1/x"));
            buttonPanel.add(new opButton("CE"));
            buttonPanel.add(new opButton("C"));
            buttonPanel.add(new opButton("Del"));
            buttonPanel.add(new opButton("/"));
            
            buttonPanel.add(new numButton("7"));
            buttonPanel.add(new numButton("8"));
            buttonPanel.add(new numButton("9"));
            
            buttonPanel.add(new opButton("x"));
            
            buttonPanel.add(new numButton("4"));
            buttonPanel.add(new numButton("5"));
            buttonPanel.add(new numButton("6"));
            
            buttonPanel.add(new opButton("-"));
           
            buttonPanel.add(new numButton("1"));
            buttonPanel.add(new numButton("2"));
            buttonPanel.add(new numButton("3"));
            
            buttonPanel.add(new opButton("+"));
            buttonPanel.add(new opButton("+/-"));
            
            buttonPanel.add(new numButton("0"));
            buttonPanel.add(new numButton("."));    // decimal point counts as a num button
            
            buttonPanel.add(new opButton("="));
                    // whew...
            buttonPanel.setSize(300,400);  // this size and that of the ButtonPanel get respected in a BoxLayout; ACTUAllY
                                            // they don't, the GUI appears coincidentally the way it does, need setPreferred...
            add(display);
            add(buttonPanel);
            setVisible(true);   // and there you have it,
                                // the complete GUI.
        }
        
        
        
        static double compute()       // the logic for computing the output from the operands
        {
            if (operation.equals("+")) return op1 + op2;
            if (operation.equals("-")) return op1 - op2;
            if (operation.equals("x")) return op1 * op2;
            if (operation.equals("/")) return op1 / op2;
            if (operation.equals("%")) return op1 % op2;
            if (operation.equals("=")) return op2;          // this last is simple beautiful logic that actually tripped me up
            return 0;
        }
    
        
        static class numButton extends JButton
        {
            JLabel label;
            numButton(String title)
            {
                label = new JLabel(title);
                add(label);
                addActionListener(new NumButtonListener());   
            }
        }
        
        static class opButton extends JButton
        {
            JLabel label;
            opButton(String title)
            {
                label = new JLabel(title);
                add(label);
                addActionListener(new OpButtonListener());
            }
        }
    
        
        
        
        static class NumButtonListener implements ActionListener        // all the num button does is concatenate the digit pressed onto the IS
        {
            public void actionPerformed(ActionEvent e)      // needs to be public
            {
                opPressed = false;
                numButton b = (numButton)(e.getSource());
                char c = b.label.getText().charAt(0);
                if (c == '.') 
                {
                    if (checkForDecimal(inputString)); else inputString += c;
                }
                else if (inputString.equals("0")) inputString = "" + c;
                else inputString += c;
                System.out.println("Input string: " + inputString);
                outputString = inputString;
                display.repaint();
            }
        }
        
        static boolean checkForDecimal(String s)
        {
            for (int i = 0; i < s.length(); ++i)
                        if (s.charAt(i) == '.') return true;
            return false;
        }
        
        
        
        
        
        
        
        
        
        static class OpButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                opScanner = new Scanner(inputString);
                
                opButton b = (opButton)(e.getSource());
                String s = b.label.getText();                
                System.out.println("Button pressed: " + s);
                
                if (s.equals("C"))              // base case: button is C, clear everything
                {
                    op1 = 0;
                    op2 = 0;
                    operation = "";
                    op1Full = false;
                    opPressed = false;
                   // op2Full = false;
                    System.out.println("Input string: " + inputString + "\tOp1: " + op1 + "\tOp2: " + op2 + "\tOpr: " + operation);
                    outputString = "0";
                    display.repaint();
                }
                
         
                
     
                
               // the following logic describes what happens when an op button other than C is pressed :
                else
                {
                    if (!opPressed)   // if the last button pressed was a number, not an op button; or an op button was the first pressed
                    {  
                        if (op1Full)   // if there is already a value stored in op1
                        {
                            op2 = opScanner.nextDouble();
                              outputString = op1 + " " + operation + " " + op2 + " = ";
                              System.out.print(outputString);
                              display.repaint();
                            op1 = compute();
                            op1Full = true;
                            operation = s;
                              outputString = op1 + "";
                              System.out.println(outputString);
                              display.repaint();
                        }
                        else     // if there is not yet a value stored in op1
                        {
                            op1 = opScanner.nextDouble();
                            op1Full = true;
                            operation = s;
                            outputString = op1 + " " + operation;
                            System.out.println(outputString);
                            display.repaint();
                        }
                    }
                    else if (!op1Full)  // the last button pressed was an op button, and there is no value in op1
                    {
                        op1 = 0;
                        op1Full = true;
                        operation = s;
                        outputString = op1 + " " + operation;
                        System.out.println(outputString);
                        display.repaint();
                    }
                    else                // the last button pressed was an op button, and there is a value in op1
                    {
                        operation = s;
                        outputString = op1 + " " + operation;
                        System.out.println(outputString);
                        display.repaint();
                    }
                    opPressed = true;
                }
                inputString = "0";   // the last thing we do upon pressing an operator button is reset the input string
            }
        }
        
        static class DisplayPanel extends JPanel        
        {
            DisplayPanel()
            {
                setSize(400, 50);
                repaint();
            }
            
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                g.setColor(Color.YELLOW);
                g.fillRoundRect(10, 10, 320, 90, 4, 4);
                g.setColor(Color.BLACK);
                g.drawString(outputString, 20, 80);
            }
        }  
    }  
}