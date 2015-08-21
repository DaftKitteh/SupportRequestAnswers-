package source;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	// Creates hashmap to store all data 
	static LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
	
	// Creates copyText method for copying the hashmaps to clipboard
	public static void copyText(String s1) { 
		String myString = hm.get(s1); 
		myString = myString.toLowerCase();
		StringSelection selection = new StringSelection(myString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
	}
	
	public static void flushText(Integer sleepTime) throws InterruptedException { 

		// Sleep to allow the user to see stuff before it changes 
		Thread.sleep(sleepTime);
		// This clears the terminal 
		final String ANSI_CLS = "\u001b[2J";
		final String ANSI_HOME = "\u001b[H";
		System.out.print(ANSI_CLS + ANSI_HOME);
		System.out.flush();
	}
	public static void main(String args[]) throws InterruptedException, IOException{ 
		//Beginning test to see if I can import hashmaps via text file 
		//Attempting to grab hashmaps from the txt file 
		try {
			
		String fileName = "Phrases.txt";
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(fileName)));
		try {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] splitLine = line.split(","); 
		        String splitLine1 = splitLine[0];
		        String splitLine2 = splitLine[1];
		        hm.put(splitLine1, splitLine2); 
		        
		    }// ends while 
		} finally {
		    br.close();
		} // ends finally 
		// If file not found create file and write the format 
		} catch(FileNotFoundException e1) { 
			PrintWriter writer = new PrintWriter("Phrases.txt", "UTF-8");
			writer.println("command,value");
			writer.close();
		} // ends catch file not found 
		Scanner scn = new Scanner(System.in); 
		while(true) {
			//Prints all hashmaps 
			System.out.println("Printing all Keys, Values.");
			for (Map.Entry<String,String> entry : hm.entrySet()) {
				  String key = entry.getKey();
				  String value = entry.getValue();
				  System.out.println("|Command| " + key + " |Value| "  + value);
				}// ends for loop 
			
			System.out.println("Type the Command to copy the Value.");
			
			String userInput = scn.nextLine(); 
			try { 
				
				if(userInput.equalsIgnoreCase("quit")) {
					System.out.println("Closing program.");
					break; 
				}
				// this is where I'll add the /add command if any 
				
				else{ // Copies the text to the clipboard and clears the terminal 
					userInput = userInput.toLowerCase(); 
					userInput = userInput.trim(); 
					copyText(userInput); 
					
					//Clears the text field 
					flushText(500); 
					
					System.out.println("Key " + "|" + userInput + "|" + " has been copied to the clipboard~" );
				}// ends else
				
			} catch(Exception e1) { 
				flushText(0);
				System.out.println("That is not a valid command."
						+ "\nPlease ensure that you typed the command correctly."); 
			} // Ends exception catching 
		}// ends while true 
		System.out.println("Scanner has been closed.");
		scn.close(); // Closes scanner 
		System.exit(0);
	} // Ends public static void main
} // Ends main 
