package com.saturn91.engine.game;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class Controler {
	private ArrayList<Command> commands;
	private ArrayList<String> usedCommands;
	
	public Controler(){
		commands = new ArrayList<>();
		usedCommands = new ArrayList<>();
	}
	
	public boolean addCommand(String name, int key){
		if(!usedCommands.contains(name)){
			usedCommands.add(name);
			commands.add(new Command(name, key));
			return true;
		}else{
			return false;
		}
	}
	
	public void tick(){
		for(Command c: commands){
			if(Keyboard.isKeyDown(c.key)){
				c.setActive();
			}else{
				c.deactivate();
			}
		}
	}
	
	public boolean isActive(String name){
		if(usedCommands.contains(name)){
			return commands.get(usedCommands.indexOf(name)).active;
		}else{
			System.err.println("Controler: unknown Command! " + name);
			return false;
		}
	}
	
	public boolean changeControl(String name, int key){
		if(usedCommands.contains(name)){
			commands.get(usedCommands.indexOf(name)).key = key;
			return true;
		}else{
			return false;
		}		
	}
	
	
	private class Command{
		private int key;
		private String name;
		private boolean active = false;
		
		public Command(String name, int key){
			this.name = name;
			this.key = key;
		}
		
		public void setActive(){
			this.active = true;
		}
		
		public void deactivate(){
			this.active = false;
		}
		
		public boolean getState(){
			return active;
		}
	}
}


