package in.app.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.app.binding.Login;
import in.app.binding.Message;
import in.app.binding.Uid;
import in.app.entity.AgentPair;
import in.app.entity.UserPair;

@Controller
public class MainController {
	HashMap<Integer , Boolean> map = new HashMap<>();
    HashMap<UserPair, Boolean> users = new HashMap<>();
    HashMap<Integer, ArrayList<AgentPair>> agents = new HashMap<>();

    @GetMapping("/")
    public String loadPage(Model model) {
        model.addAttribute("login", new Login());
        return "index";
    }

    @GetMapping("/agent/{id}")
    public String loadAgent(@PathVariable("id") Integer id, Model model) {
    	
    	ArrayList<Integer> list = new ArrayList<>();
    	
    	ArrayList<Integer> list2 = new ArrayList<>();
    	
    	model.addAttribute("id", id);
    	
    	 model.addAttribute("num", new Uid());
    	 model.addAttribute("n", new Uid());
    	 
    	 for(Integer gh:map.keySet())
    	 {
    		 if(map.get(gh)==false) {
    			   list.add(gh);
    		 }
    	 }
    	 
    	 for(Integer df:agents.keySet())
    	 {
    		  if(df==id)
    		  {
    			  ArrayList<AgentPair> p = agents.get(df);
    			  for(AgentPair r:p) {
    				  list2.add(r.getUid());
    			  }
    			  break;
    		  }
    	 }
    	 model.addAttribute("list2", list2);
    	 
    	
    	model.addAttribute("list", list);
        return "agent";
    }
    
    
    @PostMapping("/agent/{id}")
    public String loadedAgent(@PathVariable("id") Integer id, @ModelAttribute("num") Uid num, Model model) {
    	model.addAttribute("n", new Uid());
        System.out.println("Received UID from form: " + num.getId());
        if (num.getId() != null) {
            for (Integer gh : map.keySet()) {
                if (gh.equals(num.getId())) {
                	
                	ArrayList<AgentPair> pairs = agents.get(id);
                	
                	pairs.add(new AgentPair(gh));
                	agents.put(id, pairs);
                    map.put(gh, true);
                }
            }
        } 
        System.out.println("Updated map: " + map);
        
       
        return "redirect:/agent/" + id;
    }
    
    
    
    
    @PostMapping("/agent/message/{id}")
    public String agentMessage(@PathVariable("id") Integer id, @ModelAttribute("n") Uid n, Model model) {
        model.addAttribute("u", new ArrayList<>());
        model.addAttribute("a", new ArrayList<>());
        model.addAttribute("don", new Message());
        model.addAttribute("ad", id); // Add 'ad' to the model
        model.addAttribute("ud", n.getId()); // Add 'ud' to the model
        
        for (UserPair user : users.keySet()) {
            if (user.getUid() == n.getId()) {
                model.addAttribute("u", user.getMessages());
            }
        }
        
        ArrayList<AgentPair> mm = agents.get(id);
        if (mm != null) {
            for (AgentPair d : mm) {
                if (d.getUid() == n.getId()) {
                    model.addAttribute("a", d.getMessages());
                }
            }
        }
        
        return "message";
    }

    
    
//    @PostMapping("/agent/message/{id}")
//    public String agentMessage(@PathVariable("id") Integer id, @ModelAttribute("n") Uid n,Model model)
//    {
//    	model.addAttribute("u", new ArrayList<>());
//    	model.addAttribute("a", new ArrayList<>());
//    	
//    	System.out.println("printed");
//    	
//    	model.addAttribute("don" , new Message());
//    	
//    	for(UserPair user:users.keySet())
//    	{
//    		if(user.getUid()==n.getId())
//    		{
//    			model.addAttribute("u", user.getMessages());
//    		}
//    	}
//    	
//    	
//    	
//    	System.out.println(id + " " + n);
//    	
//    	
//    	ArrayList<AgentPair> mm = agents.get(id);
//    	
//    	if (mm != null) {
//    	    for (AgentPair d : mm) {
//    	        if (d.getUid() == n.getId()) {
//    	            model.addAttribute("a", d.getMessages());
//    	        }
//    	    }
//    	} else {
//    	    model.addAttribute("a", new ArrayList<>()); // Ensure "a" is always added to the model
//    	}
//    	
//    	 model.addAttribute("ad", id);
//         model.addAttribute("ud", n.getId());
//    	return "message";
//    }
//    
//    
    
    @PostMapping("/message/{ad}/{ud}")
    public String handleMessages(@PathVariable("ad") Integer ad ,  @PathVariable("ud") Integer ud , @ModelAttribute("don") Message don ,Model model) 
    {
    	model.addAttribute("u", new ArrayList<>());
    	model.addAttribute("a", new ArrayList<>());
    	for(UserPair user:users.keySet())
    	{
    		if(user.getUid()==ud)
    		{
    			model.addAttribute("u", user.getMessages());
    		}
    	}
    	
    	
ArrayList<AgentPair> mm = agents.get(ad);
    	
    	if (mm != null) {
    	    for (AgentPair d : mm) {
    	        if (d.getUid() == ud) {
    	        	ArrayList<String> listed = d.getMessages();
    	        	listed.add(don.getData());
    	        	d.setMessages(listed);
    	            model.addAttribute("a", d.getMessages());
    	        }
    	    }
    	} else {
    	    model.addAttribute("a", new ArrayList<>()); // Ensure "a" is always added to the model
    	}
    	
    	
        return "message";	
    }
    
    
    
    
    

    @GetMapping("/user/{id}")
    public String loadUser(@PathVariable("id") Integer id, Model model) {

	model.addAttribute("u", new ArrayList<>());
	model.addAttribute("a", new ArrayList<>());
	model.addAttribute("id", id);

for(UserPair user:users.keySet())
{
	if(user.getUid()==id)
	{
		if(map.get(id)==false)
		{
			model.addAttribute("u", user.getMessages());
			System.out.println( "c:-"+user.getMessages().size());
			return "user";
		}
		else
		{
			model.addAttribute("u", user.getMessages());
			for(Integer i:agents.keySet())
			{
				ArrayList<AgentPair> agentss = agents.get(i);
				for(AgentPair p:agentss)
				{
					if(p.getUid()==id) {
						model.addAttribute("a", p.getMessages());
					}
				}
			}
			
			System.out.println( "c:-"+user.getMessages().size());
		}
	}
}
return "user";

    }
    
    
    
    
    
    
    @PostMapping("/user/{id}")
    public String loadedUser(@PathVariable("id") Integer id, @RequestParam("inputString") String inputString, Model model) {
        if (inputString != null) {
            for (UserPair p : users.keySet()) {
                if (p.getUid() == id) {
                    ArrayList<String> messages = p.getMessages();
                    messages.add(inputString);
                    p.setMessages(messages);
//                    users.put(p, users.get(p));
                    
                    System.out.println("Updated messages for user " + id + ": " + messages + "  " + users.get(p));
                    System.out.println(users);
                }
            }
        }
        // Redirect to prevent Thymeleaf error
        return "redirect:/user/" + id;
    }


    @PostMapping("/")
    public String loginPage(@ModelAttribute("login") Login login) {
        if (login.getRole().equals("Agent")) {
            boolean check = false;
            for (Integer i : agents.keySet()) {
                if (i.equals(login.getId())) {
                    check = true;
                    break;
                }
            }
            if (check==false) {
                boolean c = false;
                for (UserPair user : users.keySet()) {
                    if (user.getUid() == login.getId()) {
                        c = true;
                        break;
                    }
                }
                if (!c) {
                    agents.put(login.getId(), new ArrayList<>());
                } else {
                    return "redirect:/";
                }
            }
            return "redirect:/agent/" + login.getId();
        } else {
            boolean check = false;
            UserPair p = new UserPair(login.getId());
            for (UserPair user : users.keySet()) {
                if (user.getUid() == login.getId()) {
                    check = true;
                    p = user;
                    break;
                }
            }
            if (check==false) {
                boolean c = false;
                for (Integer i : agents.keySet()) {
                    if (i.equals(login.getId())) {
                        c = true;
                        break;
                    }
                }
                if (!c) {
                    users.put(p, false);
                    map.put(p.getUid() , false);
                } else {
                    return "redirect:/";
                }
            }
            return "redirect:/user/" + login.getId();
        }
    }
    
    
    
    
}
