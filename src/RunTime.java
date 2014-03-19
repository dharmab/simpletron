import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RunTime
{
	public static void main(String[] args)
	{
		menu();
	}
	
	private static void menu()
	{
		System.out.println("*** Welcome to Simpletron! ***");
		System.out.println("*** For help, refer to pages 304-309 ***");
		System.out.println("*** of Java: How to Program 9th ed. ***");

		Scanner menuInput = new Scanner (System.in);
		Computer myComputer = new Computer(100);
		int choice;
		
		do 
		{
			System.out.println("*** Enter 1 to manually enter a program ***");
			System.out.println("*** Enter 2 to load a program from a file ***");
			System.out.println("*** Enter 0 to quit ***");

			try
			{
				choice = menuInput.nextInt();
			}
			catch (InputMismatchException e)
			{
				choice = -1;
			}
			menuInput.nextLine(); //eat next line to prevent errors
			
			switch (choice)
			{
				case 0:
					System.out.println("*** Exiting... ***");
					
					break;
				case 1:
					System.out.println("*** Please enter your program one instruction ***");
					System.out.println("*** (or data word) at a time. I will display ***");
					System.out.println("*** the location number and a question mark (?) ***");
					System.out.println("*** You then type the word for that location. ***");
					System.out.println("*** Type -99999 to stop entering your program. ***\n");
					
					programFromManualEntry(myComputer);
					
					System.out.println("*** Program loading completed ***");
					System.out.println("*** Program execution begins  ***");
					
					myComputer.run();
					break;
				case 2:
					System.out.println("*** Please enter the relative path of a file ***");
					System.out.println("*** containing a valid Simpletron program ***");
					System.out.println("*** with each instruction separated by whitespace. ***");
					System.out.println("*** Filename may not contain spaces. ***");
					
					File programFile = new File (menuInput.nextLine());
					try
					{
						programFromFile(programFile, myComputer);
					}
					catch (FileNotFoundException e)
					{
						System.out.println("*** File not found! Returning to main menu ***");
						break;
					}
					catch (InputMismatchException e)
					{
						System.out.println("*** File contains invalid content! Returning to main menu ***");
					}

					System.out.println("*** Program loading completed ***");
					System.out.println("*** Program execution begins  ***");
					
					myComputer.run();
					break;
				default:
					System.out.println("*** Invalid choice ***");
					break;
			}
		} while (choice != 0);
		
	}
	
	private static void programFromManualEntry(Computer computer)
	{
		Scanner entryScanner = new Scanner (System.in);
		
		programEntry:
		for (int i = 0; i <= computer.getAmountOfMemory();i++)
		{
			System.out.printf("%02d ? ", i);
			int word = entryScanner.nextInt();
			
			if (word != -99999)
			{
				computer.putInMemory(word, i);
			}
			else
			{
				break programEntry;
			}
		}
	}
	
	private static void programFromFile(File file, Computer computer) throws FileNotFoundException, InputMismatchException
	{
		Scanner fileScanner = new Scanner(file);
		int i = 0;
		
		while (fileScanner.hasNextInt())
		{
			computer.putInMemory(fileScanner.nextInt(), i);
			i++;
		}
	}
}