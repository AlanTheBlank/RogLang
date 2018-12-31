package ie.AlanTheBlank.RogLang;

import java.io.*;
import java.util.Scanner;

public class RogLangInterp{
	
	String[] tokens;
	
	byte[] data;

	BufferedReader fileReader;
	
	FileOutputStream fos;
	
	InputStreamReader consoleIn;
	
	File f;
	
	Scanner in = new Scanner(System.in);
	
	int dataPointer = 0;
	int charPointer = 0;
	
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
	public void interpret(String s){
		System.out.println(s);
		char[] bfcode = translate(s);
		data = new byte[bfcode.length];
		String bf = "";
		for(int j = 0; j < bfcode.length; j++){
			bf += "" + bfcode[j];
		}
	
		for(; charPointer < bf.length(); charPointer++){
			interpret(bf.charAt(charPointer), bf.toCharArray());
		}
	}
	
	public char[] translate(String a){
		char[] bf = new char[getSize(f)];
		String[] b = a.split(" ");
		
		for(int i = 0; i < b.length; i++){
			if(b[i].equals("ooh")){
				bf[i] = '>';
			}
			else if(b[i].equals("ahh")){
				bf[i] = '<';
			}
			else if(b[i].equals("ooo")){
				bf[i] = '+';
			}
			else if(b[i].equals("aaa")){
				bf[i] = '-';
			}
			else if(b[i].equals("ooooo")){
				bf[i] = '[';
			}
			else if(b[i].equals("babe")){
				bf[i] = ']';
			}
			else if(b[i].equals("oooo")){
				bf[i] = ',';
			}
			else if(b[i].equals("aaaa")){
				bf[i] = '.';
			}
			else{}
		}
		return bf;
	}
	public int getSize(File f){
		int count = 0;
		try(Scanner s = new Scanner(new FileInputStream(f))){
			
			while(s.hasNext()){
				s.next();
				count++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return(count);
	}
	
	public void interpret(char s, char[] sa){
		switch(s){
			case '>':
				if((dataPointer + 1) > data.length){
					System.out.println("Error on line " + lineCount + "\n data pointer " + dataPointer + " on postion " + charPointer + " is out of range.");
					System.exit(1);
				}else{
					dataPointer++;
				}
				break;
			
			case '<':
				if((dataPointer - 1) < 0){
					System.out.println("Error on line " + lineCount + "\n data pointer " + dataPointer + " on postion " + charPointer + " is negative.");
					System.exit(1);
				}else{
					dataPointer--;
				}
				break;
			
			case '+':
				data[dataPointer]++;
				break;
				
			case '-':
				data[dataPointer]--;
				break;
			
			case '[':
				if(data[dataPointer] == 0){
					int i = 1;
					while(i > 0){
						char s2 = sa[++charPointer];
						if(s2 == '['){
							i++;
						}else if(s2 == ']'){
							i--;
						}
					}
				}
				break;
				
			case ']':
				int i = 1;
				while(i > 0){
					char s2 = sa[--charPointer];
					if(s2 == '['){
						i--;
					}else if(s2 == ']'){
						i++;
					}
				}
				charPointer--;
				break;
			
			case '.':
			try{
				fos.write((char) data[dataPointer]);
			}catch(Exception e){
				e.printStackTrace();
			}
				break;
				
			case ',':
				data[dataPointer] = (byte) in.next().charAt(0);
				break;
		}
	}
	
	public static void main(String[] args){
		if(args[0].contains(".uwu")){
			File f = new File(args[0]);
			RogLangInterp w = new RogLangInterp(f, f.getName());
		}else{
			System.out.println("Invalid file!  Please use a .uwu file!");
		}
	}
}