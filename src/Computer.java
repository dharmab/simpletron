import java.util.Scanner;

public class Computer
{
	// fields
	private static final int READ = 10;
	private static final int WRITE = 11;
	private static final int LOAD = 20;
	private static final int STORE = 21;
	private static final int ADD = 30;
	private static final int SUBTRACT = 31;
	private static final int DIVIDE = 32;
	private static final int MULTIPLY = 33;
	private static final int BRANCH = 40;
	private static final int BRANCHNEG = 41;
	private static final int BRANCHZERO = 42;
	private static final int HALT = 43;
	private int accumulator;
	private int instructionCounter; //location in memory of instruction being performed
	private int instructionRegister; //value stored at location defined by instructionCounter
	private int operationCode;
	private int operand;
	private int[] memory;
	private boolean abnormalTermination;
	
	// constructors
	public Computer(int amountOfMemory)
	{
		memory = new int[amountOfMemory];
	}
	
	// methods
	public void setAccumulator(int value)
	{
		if (value >= -9999 && value <= 9999)
			accumulator = value;
		else error();
	}
	
	public int getAccumulator()
	{
		return accumulator;
	}
	
	public void setInstructionCounter(int value)
	{
		if (value >= -9999 && value <= 9999)
			instructionCounter = value;
		else error();
	}
	public int getInstructionCounter()
	{
		return instructionCounter;
	}
	
	public void setInstructionRegister(int value)
	{
		if (value >= -9999 && value <= 9999)
			instructionRegister = value;
		else error();	
	}
	
	public int getInstructionRegister()
	{
		return  instructionRegister;
	}
	
	public void setOperationCode(int value)
	{
		if (value >= -99 && value <= 99)
			operationCode = value;
		else error();
	}
	
	public int getOperationCode()
	{
		return operationCode;
	}
	
	public void setOperand(int value)
	{
		if (value < getAmountOfMemory() && value >= 0)
			operand = value;
		else error();
	}
	
	public int getOperand()
	{
		return operand;
	}
	
	public int getAmountOfMemory()
	{
		return memory.length;
	}
	
	public void putInMemory(int value, int location)
	{
		setAccumulator(value);
		store(location);
	}
	
	public void run()
	{
		setAccumulator(0);
		setInstructionCounter(0);
		setInstructionRegister(0);
		abnormalTermination = false;
		do
		{
			if (abnormalTermination == true)
			{
				System.out.println("*** ERROR: Program abnormally terminated ***");
				break;
			}
			setInstructionRegister(memory[getInstructionCounter()]);
			
			setOperationCode(getInstructionRegister() / 100);
			setOperand(getInstructionRegister() % 100);
			
			switch (getOperationCode())
			{
				case READ:
					read(getOperand());
					break;
				case WRITE:
					write(getOperand());
					break;
				case LOAD:
					load(getOperand());
					break;
				case STORE:
					store(getOperand());
					break;
				case ADD:
					add(getOperand());
					break;
				case SUBTRACT:
					subtract(getOperand());
					break;
				case DIVIDE:
					divide(getOperand());
					break;
				case MULTIPLY:
					multiply(getOperand());
					break;
				case BRANCH:
					branch(getOperand());
					break;
				case BRANCHNEG:
					branchNeg(getOperand());
					break;
				case BRANCHZERO:
					branchZero(getOperand());
					break;
				case HALT:
					break;
				default:
					error();
					break;
			}
		} while (getOperationCode() != HALT);
		System.out.println("\n*** Program execution completed ***");
		dump();
	}
	
	private void read(int location)
	{
		//Read a word from the keyboard into a specific location in memory.
		System.out.println("Enter an integer in range [-9999..9999]");
		Scanner input = new Scanner(System.in);
		int value = input.nextInt();
		
		if (value >= -9999 && value <= 9999) 
		{
			memory[location] = value;
			setInstructionCounter(getInstructionCounter() + 1);
		}
		else error();
	}
	
	private void write(int location)
	{
		//Write a word from a specific location in memory to the screen.
		System.out.println(memory[location]);
		setInstructionCounter(getInstructionCounter() + 1);
	}
	
	private void load(int location)
	{
		//Load a word from a specific location in memory into the accumulator.
		setAccumulator(memory[location]);
		setInstructionCounter(getInstructionCounter() + 1);
	}
	
	private void store(int location)
	{
		//Store a word from the accumulator into a specific location in memory.
		memory[location] = getAccumulator();
		setInstructionCounter(getInstructionCounter() + 1);
	}
	
	private void add(int location)
	{
		//Add a word from a specific location in memory to the word in the accumulator (leave the result in the accumulator).
		setAccumulator(memory[location] + getAccumulator());
		setInstructionCounter(getInstructionCounter() + 1);
	}
	
	private void subtract(int location)
	{
		//Subtract a word from a specific location in memory from the word in the accumulator (leave the result in the accumulator).
		setAccumulator(getAccumulator() - memory[location]);
		setInstructionCounter(getInstructionCounter() + 1);
	}
	
	private void divide(int location)
	{
		//Divide a word from a specific location in memory into the word in the accumulator (leave result in the accumulator).
		if (getAccumulator() != 0)
		{
			setAccumulator(memory[location] / getAccumulator());
			setInstructionCounter(getInstructionCounter() + 1);
		}
		else error();
	}
	
	private void multiply(int location)
	{
		//Multiply a word from a specific location in memory by the word in the accumulator (leave the result in the accumulator).
		setAccumulator(memory[location] * getAccumulator());
		setInstructionCounter(getInstructionCounter() + 1);
	}
	
	private void branch(int location)
	{
		//Branch to a specific location in memory.
		setInstructionCounter(location);
	}
	
	private void branchNeg(int location)
	{
		//Branch to a specific location in memory if the accumulator is negative.
		if(getAccumulator() < 0)
			setInstructionCounter(location);
		else
			setInstructionCounter(getInstructionCounter() + 1);
	}
	
	private void branchZero(int location)
	{
		//Branch to a specific location in memory if the accumulator is zero.
		if(getAccumulator() == 0)
			setInstructionCounter(location);
		else
			setInstructionCounter(getInstructionCounter() + 1);
	}
	
	private void error()
	{
		abnormalTermination = true;
	}
	
	private void dump()
	{
		System.out.println("REGISTERS:");
		System.out.printf("accumulator: %04d%n", getAccumulator());
		System.out.printf("instructionCounter: %04d%n", getInstructionCounter());
		System.out.printf("instructionRegister: %04d%n", getInstructionRegister());
		System.out.printf("operationCode: %02d%n", getOperationCode());
		System.out.printf("operand: %02d%n", getOperand());
		System.out.println("FLAGS:");
		System.out.printf("abnormalTermination: %b%n", abnormalTermination);
		System.out.println("MEMORY:");
		for (int i = 0; i < memory.length; i++) //Manual iteration for simpler array index getting
		{
			if (i % 10 == 0)
				System.out.println(); //Blocks of ten
			System.out.printf("%04d ", memory[i]);
		}
		System.out.println();
		System.out.println();
	}
}