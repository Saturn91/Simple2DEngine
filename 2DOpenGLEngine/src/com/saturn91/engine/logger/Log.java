package com.saturn91.engine.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	private static int debugMod;
	private static int allClassesDebugMode = -1;		//-1 means use normal debugModus
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:YYYY-hh:mm:ss");
	private final static String info = "[  INFO:  ]";
	private final static String error ="[ ERROR:  ]";
	
	private static StringBuilder sb = new StringBuilder();
	
	private static boolean printOnlyExact_DebugModus = false;
	
	/**
	 * Print the msg message on the debugMod Layer
	 * @param msg	Status Message
	 * @param debugMod
	 */
	public static void printLn(String msg, String className, int _debugMod){
		String msgLine = "[" + getDate() + "]: " + info + " " + className + ": " + msg;
		if(allClassesDebugMode == -1){
			if(printOnlyExact_DebugModus){
				if(debugMod == _debugMod){
					System.out.println(_debugMod + "| "+ msgLine);
					sb.append(_debugMod + "| "+msgLine + "\n");
				}
			}else{
				if(debugMod >= _debugMod){
					System.out.println(_debugMod + "| "+msgLine);
					sb.append(_debugMod + "| "+msgLine + "\n");
				}
			}
		}else{
			if(allClassesDebugMode >= _debugMod){
				System.out.println(_debugMod + "| "+msgLine);
				sb.append(_debugMod + "| "+msgLine + "\n");
			}
		}		
	}
	
	/**
	 * Print the msg errormessage on the debugMod Layer
	 * @param msg	Status Message
	 * @param debugMod
	 */	
	protected static void printErrorLn(String msg, String className, int _debugMod){
		String msgLine = "[" + getDate() + "]: " + error + " " + className + ": " + msg;
		if(allClassesDebugMode == -1){
			if(printOnlyExact_DebugModus){
				if(debugMod == _debugMod){
					System.err.println(_debugMod + "| "+msgLine);
					sb.append(_debugMod + "| "+msgLine + "\n");
				}
			}else{
				if(debugMod >= _debugMod){
					System.err.println(msgLine);
					sb.append(_debugMod + "| "+msgLine + "\n");
				}
			}
		}else{
			if(allClassesDebugMode >= _debugMod){
				System.err.println(_debugMod + "| "+msgLine);
				sb.append(_debugMod + "| "+msgLine + "\n");
			}
		}
	}
	
	/**
	 * Change the debug Mod so that other deeper or less deep msg gets trough
	 * @param debugMod
	 */
	public static void setDebugModus(int _debugMod){
		debugMod = _debugMod;
	}
	
	/**
	 * If active, only the one setted Leyer will be printed
	 * @param printOnlyExact_DebugModus
	 */
	public void setOnlyThisLayer(boolean _printOnlyExact_DebugModus){
		printOnlyExact_DebugModus = _printOnlyExact_DebugModus;
	}
	
	/**
	 * This Method provides every Textoutput with the last printed msg could be used for a log File
	 * @return
	 */
	public static StringBuilder getErrorStrings(){
		StringBuilder sb2 = sb;
		sb.setLength(0);
		return sb2;
	}
	
	/**
	 * This Methode changes the debugmodus for all classes
	 * @param debugModus
	 */
	public static void setDebugModusAllClasses(int debugModus){
		allClassesDebugMode = debugModus;
	}
	
	private static String getDate(){
		Date date = new Date(System.currentTimeMillis());
		return dateFormat.format(date).toString();
	}
}
