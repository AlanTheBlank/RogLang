import java.io.*;
import java.util.*;

/** RogLang Interpretator V2.0
	Author Alan O'Reilly
	
**/
public class RogLangInterp{
	
	String[] tokens;
	
	byte[] data;

	BufferedReader fileReader;
	
	FileOutputStream fos;
	
	InputStreamReader consoleIn;
	
	File f;
	File f_out;
	Scanner in = new Scanner(System.in);
	
	int dataPointer = 0;
	int entryPointer = 0;
	
	int lineCount = 0;
	
	String fileName;

	
	public RogLangInterp(File f, String Name){
		try{
			this.f = f;
			fileReader = new BufferedReader(new FileReader(f));
			String content = "";
			String line = "";
			int length = 0;
			File f_out = new File(Name + "_out.txt");
			f_out.createNewFile();
			fos = new FileOutputStream(f_out);
			while((line = fileReader.readLine()) != null){
				content += line + " ";
				lineCount++;
			}
			interpret(content);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public RogLangInterp(File f, File f_out){
		try{
			this.f = f;
			this.f_out = f_out;
			f_out.createNewFile();
			fileReader = new BufferedReader(new FileReader(f));
			String content = "";
			String line = "";
			fos = new FileOutputStream(f_out);
			while((line = fileReader.readLine()) != null){
				content += line;
				lineCount++;
			}
			interpret(content);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void interpret(String s){
		System.out.println(s);
		System.out.println("Tokenizing the input");
		String[] tokens = tokenizer(s);
		data = new byte[genCells(tokens)];
		for(; entryPointer < tokens.length; entryPointer++){
			interpret(tokens[entryPointer], tokens);
		}
	}
	
	public int genCells(String[] a){
		int count = 1;
		for(int i = 0; i < a.length; i++){
			if(a[i].equals("ooh")){
				count++;
			}else if(a[i].equals("aah")){
				count--;
			}
		}
		return count;
	}
	
	public String[] tokenizer(String s){
		String temp = "";
		System.out.println(s);
		System.out.println("String is " + s.length() + " characters long");
		ArrayList<String> tokens = new ArrayList<String>();
		for(int i = 0; i < s.length(); i++){
			temp += s.charAt(i);
			System.out.println(temp);
			if(temp.equals("ooh")){
				tokens.add(temp);
				System.out.println(temp);
				temp = "";
			}else if(temp.equals("aah")){
				tokens.add(temp);
				System.out.println(temp);
				temp = "";
			}else if(temp.equals("oooh")){
				tokens.add(temp);
				System.out.println(temp);
				temp = "";
			}else if(temp.equals("aaah")){
				tokens.add(temp);
				System.out.println(temp);
				temp = "";
			}else if(temp.equals("oooooh")){
				tokens.add(temp);
				System.out.println(temp);
				temp = "";
			}else if(temp.equals("babe")){
				tokens.add(temp);
				System.out.println(temp);
				temp = "";
			}else if(temp.equals("ooooh")){
				tokens.add(temp);
				System.out.println(temp);
				temp = "";
			}else if(temp.equals("aaaah")){
				tokens.add(temp);
				System.out.println(temp);
				temp = "";
			}else if(temp.equals(" ")){
				temp = "";
			}
		}
		
		System.out.println(tokens.size() + " entries in the array!");
		
		String[] complete = tokens.toArray(new String[tokens.size()]);
		for(int j = 0; j < complete.length; j++){
			System.out.print(complete[j] + " ");
		}
		return complete;
	}
	
	public void interpret(String s, String[] sa){
		System.out.println(s);
		switch(s){
			case "ooh":
				if((dataPointer + 1) > data.length){
					System.out.println("Error on line " + lineCount + "\n data pointer " + dataPointer + " on postion " + entryPointer + " is out of range.");
					System.exit(1);
				}else{
					dataPointer++;
				}
				break;
			
			case "aah":
				if((dataPointer - 1) < 0){
					System.out.println("Error on line " + lineCount + "\n data pointer " + dataPointer + " on postion " + entryPointer + " is negative.");
					System.exit(1);
				}else{
					dataPointer--;
				}
				break;
			
			case "oooh":
				data[dataPointer]++;
				break;
				
			case "aaah":
				data[dataPointer]--;
				break;
			
			case "oooooh":
				if(data[dataPointer] == 0){
					int i = 1;
					while(i > 0){
						String s2 = sa[++entryPointer];
						if(s2.equals("oooooh")){
							i++;
						}else if(s2.equals("babe")){
							i--;
						}
						System.out.println(i + "  " + s2);
					}
				}
				break;
				
			case "babe":
				int i = 1;
				while(i > 0){
					String s2 = sa[--entryPointer];
					if(s2.equals("oooooh")){
						i--;
					}else if(s2.equals("babe")){
						i++;
					}
					System.out.println(i + " " + s2);
				}
				entryPointer--;
				break;
			
			case "ooooh":
			try{
				fos.write((char) data[dataPointer]);
			}catch(Exception e){
				e.printStackTrace();
			}
				break;
				
			case "aaaah":
				data[dataPointer] = (byte) in.next().charAt(0);
				break;
		}
	}
	
	public static void main(String[] args){
		try{
			if(args[0].contains(".uwu")){
				if(args.length == 1){
					File f = new File(args[0]);
					RogLangInterp w = new RogLangInterp(f, f.getName());
				}else if(args.length == 2){
					File f = new File(args[0]);
					File f1 = new File(args[1]);
					RogLangInterp w = new RogLangInterp(f, f1);
				}					
			}else{
				System.out.println("Invalid file!  Please use a .uwu file!");
			}
		}catch(Exception e){
			System.out.println("Usage: java RogLangInterp <Input file> [output file]");
		}
	}
}