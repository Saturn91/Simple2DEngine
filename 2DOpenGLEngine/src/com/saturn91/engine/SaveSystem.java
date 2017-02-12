package com.saturn91.engine;
/**
 * Savesystem.class
 * 
 * Author: Manuel Geissberger/Saturn91
 * 
 * this class handels the writing and reading of a textFile 
 * 
 */



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.saturn91.engine.logger.Log;


public class SaveSystem {
	
	private StringBuilder buffer;
	private String path;
	private String[] loadBuffer;
	private String loadpath;
	private boolean testing = false;
	
	public SaveSystem(){
		init();
	}

	private void init() {
		buffer = new StringBuilder();
	}
	
	
	//******************************Save Methodes **********************************
	
	
	/**
	 * add String to the TextBuffer (to the current line)
	 * @param addString
	 */
	public void addToBuffer(String addString){
		if(testing){
			Log.printLn("added \"" + addString + "\"" + addString + "\"", getClass().getName(), 3);
		}
		buffer.append(addString);
	}
	
	
	/**
	 * add a Line to the TextBuffer
	 * @param addString
	 */
	public void addBufferLine(String addString){
		if(testing){
			Log.printLn("added Line \"" + addString + "\"", getClass().getName(), 3);
		}
		buffer.append(addString + "\n");
	}
	
	/**
	 * save the current Buffer to a File "name" with the ending and path decleared in constructor
	 * @param path
	 */
	public void save(String path){
		String saveData = buffer.toString();
		if(testing){
			Log.printLn("************saving:*******************", getClass().getName(), 3);
			if(Log.getDebugModus() >= 3) {System.out.println(saveData);}
			Log.printLn("************End of saving ***********", getClass().getName(), 3);
		}
		
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.print(saveData);
			writer.close();
		} catch (Exception e) {
			Log.printErrorLn("error while saving" + path, getClass().getName(), 1);
		}
				
		//clear String to get PLace on the Heap:
		saveData = "";
	}
	
	/**
	 * clear the Buffer to beginn again with writing the code
	 */
	public void clearBuffer(){
		buffer = new StringBuilder();
	}	
	
	//**************************************Load Methodes*********************
	
	/**
	 * clear the actual LoadBuffer
	 */
	public void clearLoadBuffer(){
		loadBuffer = null;
	}
	
	/**
	 * read the File at loadpath with the ending decleared in the constructor
	 * @param loadpath -> for Example "./saves/FILE_NAME"
	 */
	public boolean readFile(String loadpath){
		clearLoadBuffer();
		this.loadpath = loadpath;
		
		String loadString ="";
		try {
			loadString = readTextFile();
			if(loadString.contains("\n")){
				loadBuffer = loadString.split("\n");
			}else{
				loadBuffer = new String[1];
				loadBuffer[0] = loadString;
			}			
			return true;
		} catch (IOException e) {
			Log.printErrorLn("coudn't load " + loadpath, getClass().getName(), 1);
			return false;
		}
	}
	
	/**
	 * 
	 * @return String readed from file "loadpath"
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String readTextFile() throws FileNotFoundException, IOException{
		try(BufferedReader br = new BufferedReader(new FileReader(loadpath))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    return sb.toString();
		}		
	}
	
	/**
	 * returns the whole text of Buffer form the TextFile at loadpath 
	 * with the ending specified in Constructor
	 * @param loadpath -> for Example "./saves/FILE_NAME"
	 * @return
	 */
	public String load(String loadpath){
		this.loadpath = loadpath;
		readFile(loadpath);
		return loadBuffer.toString();
	}
	
	/**
	 * returns the whole text of the actual LoadBuffer
	 * @return
	 */
	public String load(){
		return loadBuffer.toString();
	}
	
	/**
	 * Load the String in Line X of the actual File
	 * you have to read the file first with readFile(PATH_NAME);
	 * @param line {0... loadBuffer.lenght();} 
	 * @return String at Line X of loadBuffer
	 */
	public String loadLine(int line){
		try {
			return loadBuffer[line];
		} catch (Exception e) {
			Log.printErrorLn("coudn't load Line " + line + " from file ", getClass().getName(), 1);
			Log.printErrorLn("Max Line of " + loadpath +" = " + (loadBuffer.length-1)+ "!", getClass().getName(), 1);
			return "LOADING ERROR";
		}
	}
	
	/**
	 * Loads the Line after the first apperance of the insert Prefix of the actual file
	 * You have to load a file first with readFile(PATH_NAME);
	 * @param prefix
	 * @return line after prefix
	 */
	public String loadPrefixLine(String prefix){
		String output = null;
		for(int i = 0; i < loadBuffer.length; i++){
			if(loadBuffer[i].startsWith(prefix)){
				if(testing){
					Log.printLn("found Prefix \"" + prefix + "\" at Line: " + i, getClass().getName(), 3);
				}
				StringBuilder sb = new StringBuilder(); 
				sb.append(loadBuffer[i].substring(prefix.length()+1));
				output = sb.deleteCharAt(sb.length()-1).toString();
			}
			if(output != null){
				break;
			}
		}
		if(output == null){
			Log.printErrorLn("no prefix: \"" + prefix + "\" exists in File " + loadpath, getClass().getName(), 1);
		}
		return output;
	}
	
	/**
	 * Loads the Line after the last apperance of the insert Prefix of the actual file
	 * You have to load a file first with readFile(PATH_NAME);
	 * @param prefix
	 * @return line after prefix
	 */
	public String getLastPrefix(String prefix){
		String output = null;
		for(int i = loadBuffer.length-1; i > -1; i--){
			if(loadBuffer[i].startsWith(prefix)){
				if(testing){
					Log.printLn("found Prefix \"" + prefix + "\" at Line: " + i, getClass().getName(), 3);
				}
				StringBuilder sb = new StringBuilder(); 
				sb.append(loadBuffer[i].substring(prefix.length()));
				output = sb.deleteCharAt(sb.length()-1).toString();
			}
			if(output != null){
				break;
			}
		}
		if(output == null){
			Log.printErrorLn("getLastPrefix(): no prefix: \"" + prefix + "\" exists in File " + loadpath, getClass().getName(), 1);
		}
		return output;
	}
	
	/**
	 * returns a int[] with all linenumbers where the entered prefix appears
	 * @param prefix
	 * @return int[]
	 */
	public int[] getPrefixLinePositions(String prefix){
		boolean list[] = new boolean[loadBuffer.length];
		int lenght = 0;
		for(int i = 0; i < loadBuffer.length; i++){
			list[i] = false;
			if(loadBuffer[i].startsWith(prefix)){
				if(testing){
					Log.printLn("found Prefix \"" + prefix + "\" at Line: " + i, getClass().getName(), 3);
				}
				list[i]= true;
				lenght++;
			}
		}
		if(lenght > 0){
			int[] output = new int[lenght];
			int counter = 0;
			for(int i = 0; i < loadBuffer.length; i++){
				if(list[i]){
					output[counter] = i;
					counter++;
				}
			}
			
			return output;
		}else{
			//System.err.println("getPrefixLinePosition: no prefix: \"" + prefix + "\" exists in File " + loadpath + "." + ending);
			return new int[0];
		}
	}
	
	/**
	 * returns how many lines exists Attention empty lines are not counted!
	 */
	public int getLineNum(){
		return loadBuffer.length;
	}
	
	//*********************************** other Methodes *******************************************
	
	/**
	 * get the standartpath defined in Constructor
	 * @return
	 */
	public String getPath(){
		return path;
	}
	
	/**
	 * if testing = true you get all the Sysout's of this class, might help during debugging
	 * @param testing
	 */
	public void setTesting(boolean testing){
		this.testing = testing;
		if(testing){
			Log.printLn("enabled Testing, to see debug messages: Log.setDebugModus( >= 3)", getClass().getName(), 1);
		}else{
			Log.printLn("disabled Testing", getClass().getName(), 1);
		}
		
	}
}
