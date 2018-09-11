
package AbstractMachine;

public class AbstractApp_FIRST_STEPS
{
    public static void main (String [] args)
    {
        Number4Bit Zero = new Number4Bit("0000");
        Number4Bit One = new Number4Bit("0001");
        Number4Bit Two = new Number4Bit("0010");
        Number4Bit Three = new Number4Bit("0011");
        Number4Bit Four = new Number4Bit("0100");
        Number4Bit Five = new Number4Bit("0101");
        Number4Bit Six = new Number4Bit("0110");
        Number4Bit Seven = new Number4Bit("0111");
        Number4Bit Eight = new Number4Bit("1000");
        Number4Bit Nine = new Number4Bit("1001");
        Number4Bit Ten = new Number4Bit("1010");
        Number4Bit Eleven = new Number4Bit("1011");
        Number4Bit Twelve = new Number4Bit("1100");
        Number4Bit Thirteen = new Number4Bit("1101");
        Number4Bit Fourteen = new Number4Bit("1110");
        Number4Bit Fifteen = new Number4Bit("1111");
    }
    
    static class Number4Bit
    {
        boolean R1;
        boolean R2;
        boolean R3;
        boolean R4;
        
        Number4Bit()
        {
            R1 = false; R2 = false; R3 = false; R4 = false;
        }
        
        Number4Bit(String inBinary)     // We use strings only for the purposes of demonstration and debugging
                {
                    System.out.println("Creating number from string ");
                    System.out.println("String : " + inBinary);
                    
                    if (inBinary.charAt(3) == '0') R1 = false; else R1 = true;
                    if (inBinary.charAt(2) == '0') R2 = false; else R2 = true;
                    if (inBinary.charAt(1) == '0') R3 = false; else R3 = true;
                    if (inBinary.charAt(0) == '0') R4 = false; else R4 = true;
                    
                    System.out.println("Number created : " + toString());
                    System.out.println(regToString());
                    System.out.println();
                }
        public String toString()
        {
            String s = "";
            if (R4) s += '1'; else s += '0';
            if (R3) s += '1'; else s += '0';
            if (R2) s += '1'; else s += '0';
            if (R1) s += '1'; else s += '0';
            return s;
        }
        String regToString()
        {
            String s = " R1 : ";
            if (R1) s += '1'; else s += '0';
            s += "\n R2 : ";
            if (R2) s += '1'; else s += '0';
            s += "\n R3 : ";
            if (R3) s += '1'; else s += '0';
            s += "\n R4 : ";
            if (R4) s += '1'; else s += '0';
            return s;
            
        }
        
        void Increment()        // How to add 1 to a natural number from 0 to 14 
                                // adding 1 to 15 switches it back to 0
        {
            if (NOR(R1,false)) R1 = NOT(R1);    // norring to false checks for falsehood i.e. 0
            else if (NOR(R2,false)) 
            {
                R1 = NOT(R1); R2 = NOT(R2);
            }
            else if (NOR(R3,false))
            {
                R1 = NOT(R1); R2 = NOT(R2); R3 = NOT(R3);
            }
            else        // The logic is the same for whether or not the leftmost bit is 0
                        // As such, incrementing 15 yields 0; If there were an extra bit on the left, it would be 1
            {
                R1 = NOT(R1); R2 = NOT(R2); R3 = NOT(R3); R4 = NOT(R4);
            }
        }
        void Decrement()
        {
            if (OR(R1,false)) R1 = NOT(R1);     // orring to false checks for truth i.e. 1
            else if (OR(R2,false)) 
            {
                R1 = NOT(R1); R2 = NOT(R2);
            }
            else if (OR(R3,false))
            {
                R1 = NOT(R1); R2 = NOT(R2); R3 = NOT(R3);
            }
            else
            {
                R1 = NOT(R1); R2 = NOT(R2); R3 = NOT(R3); R4 = NOT(R4);
            }
        }
    }
    
    static Number4Bit ADD(Number4Bit Register1, Number4Bit Register2)
    {
        Number4Bit sum = Register1;
        if (NOT(equalsZero(Register2)))
        {
            sum.Increment();
            Register2.Decrement();
            sum = ADD(sum, Register2);
        }
        return sum;   
    }
    
    static Number4Bit SUBTRACT(Number4Bit Register1, Number4Bit Register2)
    {
        Number4Bit difference = Register1;
        if (NOT(equalsZero(Register2)))
        {
            difference.Decrement();
            Register2.Decrement();
            difference = SUBTRACT(difference, Register2);
        }
        return difference;
    }
    
    static Number4Bit MULTIPLY (Number4Bit Register1, Number4Bit Register2)
    {
        Number4Bit product = Register1;
        if (NOT(equalsZero(Register2)))
        {
            product = ADD(product, product);
            Register2.Decrement();
            product = MULTIPLY(product, Register2);
        }
        return product;
    }
    static Number4Bit DIVIDE (Number4Bit Register1, Number4Bit Register2)
    {
        Number4Bit quotient = Register1;
        
    }
    
    static boolean equalsZero(Number4Bit n4b)
    {
        if (NOT(n4b.R1))
            if (NOT(n4b.R2))
                if (NOT(n4b.R3))
                    if (NOT(n4b.R4))
                        return true;
        return false;
    }
    
    static boolean NOT(boolean inBit)
    {
        if (inBit) return false; 
        return true;
    }
    static boolean NOR(boolean inBit1, boolean inBit2)  // equivalent to NOT(OR(boolean)) 
    {
        if (inBit1) return false;
        if (inBit2) return false;
        return true;
    }
    static boolean OR(boolean inBit1, boolean inBit2)
    {
        if (inBit1) return true;
        if (inBit2) return true;
        return false;
    }
}