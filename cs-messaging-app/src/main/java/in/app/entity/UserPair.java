package in.app.entity;

import java.util.ArrayList;

import lombok.Data;

@Data
public class UserPair 
{
   public Integer uid;
   public ArrayList<String> messages;
   
   public UserPair(Integer uid){
	   this.uid = uid;
	   messages = new ArrayList<>();
   }

}
