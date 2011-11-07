// Author: Martin Akolo Chiteri
// Date: 26th July, 2007 12:26 Hrs
// A program that implements a compiler  for the 
// low level Simpletron Machine Language (S.M.L)
import java.util.Scanner;

public class Simpletron 
{
	private int accumulator;	// a register to hold the current instruction being excecuted 
	private int [] memory;	// a placeholder for an SML program. Contains a maximum of 100 instructions
	private int instructionRegister;	// a temporary holder for the next instruction to be executed 
	private int instructionCounter;	// a variable with the location (number) of the current instruction being run
	private int operationCode;	// indicates the current operation in excecution
	private int operand;	// the rightmost two digits of the instruction being performed currently
	
	public Simpletron ( ) 
	{
		displayWelcomeMessage ();
		initialiseVariables ();
		// runSimulator ();
	}	// end constructor for class Sipletron

	// method for displaying the welcome message to the user
	public void displayWelcomeMessage ( ) 
	{
		System.out.printf ("\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s %s\n%s %s\n", 
			"*** Welcome to Simpletron! ***",
			"*** Please enter your program one instruction ***",
			"*** (or data word) at a time into the input   ***",
			"*** text field. I will display the location   ***",
			"*** number and a question mark (?). You then  ***",
			"*** type the word for that location. Press the***",
			"*** Done button to stop entering your program ***",
			" Loc", " Inst", "****", "*****");
	}	// end method displayWelcomeMessage

	// run the Simpletron simulator, which makes up our instruction execution cycle
	public void runSimulator () 
	{
		int submittedInstruction = 0;	// to hold the current instruction given by the user
		int memoryPointer = 0;	// to help us fill the contents of the memory well

		Scanner input = new Scanner ( System.in );

		do
		{
			System.out.printf ("%d %s  ", memoryPointer, "?" );
			submittedInstruction = input.nextInt ();
			if ( submittedInstruction != -99999 ) 
				memory [ memoryPointer ] = submittedInstruction;
			memoryPointer++;	// go to the next memory location
			
		} while ( submittedInstruction != -99999 );	// end do-while
		
	        System.out.printf ("\n%s%s", "*** Program loading completed ***\n", 
				"*** Program excecution begins  ***\n");	
		
		// loop through the hundred instructions loading and executing each at a time
		for ( int code : memory ) 
		{
			// System.out.println ("The value of memory location one is "+ code.getData () );
			// int sentinel = code.getData ();
			if ( code != 0 )	// skip all the null instructions 
			{
				load ();
				execute ( operand, operationCode );
			}
		}

	}	// end method runSimulator 
	
	public void initialiseVariables ( )
	{
		memory = new int [100];	// initialise the array to hold the program's data 
		instructionCounter = 0;	// make the instruction pointer referrence the first data item

	}	// end method initialiseVariables

	// a method to load the contents referrenced by the instruction pointer to the accumulator
	public void load ( ) 
	{
		
		// load the first instruction to the registers and begin execution
		operationCode = memory [ instructionCounter ] / 100;
		operand = memory [ instructionCounter ] % 100;

	}	// end method load 	

	// a method to be carried out immediately the program encounters the values -99999
	public void execute (int operands, int operation ) 
	{
		// determine which operation to carry out first
		switch ( operation ) 
		{
			case 10: // Read a word from the keyboard into a specific location in memory
				Scanner input = new Scanner ( System.in );
				System.out.print ( "Please Enter a whole number (positive or negative): " );
				memory [ operands ] = input.nextInt ();	// place the result in the specified memory location
				break;
			case 11:	// write a specific location in memory to the screen
				System.out.println ("The result of the operation is " + memory [ operands] );
				break;
			case 20: // load a word from a specific location into the accumulator 
				accumulator = memory [ operands ];
				break;
			case 21: 	// Store a word from the accumulator into a specific location in memory
				memory [ operands ] = accumulator;
				break;
			case 30: // add a word from a specific location in memory to the word 
				// in the accumulator ( leave the result in the accumulator 
				accumulator += memory [ operands ];
				break;
			case 31: // Subtract a word from a specific location in memory from the 
				// word in the accumulator ( leave the result in the accumulator )
				accumulator -= memory [ operands ];
				break;
			case 32:	// Divide a word from a specific location in memory into the 
			       // word in the accumulator ( leave the result in the accumulator )
				accumulator /=  memory [ operands ];
				break;
			case 33: // Multiply a word from a specific location in memory by the
			       // word in the  accumulator ( leave the result in the accumulator )
				accumulator *= memory [ operands ];
				break;
			case 40:	// branch to a specific location in memory
				instructionCounter = operands;
				break;
			case 41:	// branch to a specific location in memory if the accumulator is negative 
				if ( accumulator < 0 )
					instructionCounter = operands;
				break;
			case 42:	// branch to a specific location if the accumulator is zero
				if ( accumulator == 0 )
					instructionCounter = operands;
				break;
			case 43: 	// Halt. The program has completed its tasks
				dumpTheCore ();	// do a "core dump"
				System.out.printf ("\n%s\n", "The program has ended...");
				System.exit ( 0 );
				break;

		}	// end switch 

		instructionCounter++;	// move the instruction pointer to the next item
		// accumulator = memory [ instructionCounter ];	// load the next instruction to be excecuted
	}	// end method exceute 

	// a method to display the names and contents of the registers and memory
	public void dumpTheCore ( )
	{
		System.out.printf ("\n%30s\n%30s\t%s%4d\n%30s\t%2d\n%30s\t%2d\n%30s\t%2d\n%30s\t%2d\n\n%30s\n", "REGISTERS:", 
				"accumulator", "+", accumulator, "instruction counter", instructionCounter, "instruction register",
			       	instructionCounter, "operation code", operationCode, "operand", operand, "MEMORY:" );

		// display numbering for the memory cells in a horizontal order
		for ( int i = 0; i < 10; i++ )
		{
			System.out.printf ( "%6d", i);
		}

		System.out.println ();
		int counter = 0;	// counter to ensure that we go through the whole loop

		// display the memory cells themselves in both vertical and horizontal order
		for (int i = 0; i < 10; i++ ) 
		{
			if ( counter %10 == 0 )
				System.out.printf ("%2d ", counter);
			for (int j = 0; j < 10; j++) 
			{	
				// Lets apply some formatting to improve the display
				if ( memory [ counter ] == 0 )
					System.out.printf ( "%s%s", "+", "0000 ");
				else 
					System.out.printf ("%s%4d ", "+", memory [counter]);
				counter++;

			}	// end inner for
		       
		System.out.println ();	

		}	// end outer for 
	}	// end method dumpTheCore

}	// end class Simpletron
