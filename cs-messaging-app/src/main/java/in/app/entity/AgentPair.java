package in.app.entity;

import java.util.ArrayList;

import lombok.Data;

@Data
public class AgentPair 
{
	public Integer uid;
	public  ArrayList<String> messages;
	
	public AgentPair(Integer uid){
		   this.uid = uid;
		   messages = new ArrayList<>();
	   }
}

