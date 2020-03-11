package com.example.demo.user.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.dao.UserRepository;
import com.example.demo.user.mail.EmailService;
import com.example.demo.user.pojo.User;



@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/UserPortal")
public class UserController {

	@Autowired
	private  UserRepository userRepository;
	@RequestMapping("/getAllUser")
	public Iterable<User>getAllUser()
	{
		return userRepository.findAll();
	}
	 @Autowired
	   private EmailService emailService;
	 @PostMapping("/saveUser")
	 public User saveUser(@RequestBody User user) {
	 System.out.println(user);
	 userRepository.save(user);
	 StringBuffer mailContain= new StringBuffer();
	 mailContain.append("Hi "+user.getuserName()+"\n");
	 mailContain.append("Please Click on Below Click to Confirm Your Email With Us\n");
	 mailContain.append("<a href='http://localhost:8896/Userssss/UserPortal/confirmEmail/"+user.getEmail()+">Click</a>\n");
	 mailContain.append("Thanks And Regards\n CTS Participant\n");
	 emailService.sendMail(user.getEmail(),"Email Confirmation", mailContain.toString());
	    //emailService.sendPreConfiguredMail("Ho ho ho");
	 return user;
	 }
	
	@PutMapping("/putuser/{id}")
	public User updateUser(@RequestBody User user,@PathVariable("id") String id)
	{
		
		user.setId(id);
		System.out.println(user);
		
		userRepository.save(user);
		return user;
	}
	@DeleteMapping("/deleteUser/{id}")
	public boolean deleteUser(@PathVariable("id") String id)
	{
		
	
		System.out.println(id);
		
		userRepository.deleteById(id);
		return true;
	}
	@GetMapping("/confirmEmail/{emailId}")

	 public User confirmEmail(@PathVariable("emailId") String email)

	 {
	 Optional<User> user= userRepository.findByEmail(email);
	 if(user!=null && user.get()!=null)
	 {
	  User u=user.get();
	  u.setConfirmed("Yes");
	  userRepository.save(u);
	  return u;
	 }
	 return user.get();
	 }
	@GetMapping("/find/{id}")
	public User findAllinOne(@PathVariable("id") String id)
	{
	
	Optional<User> user=userRepository.findById(id);
		return user.get();
	}
	@GetMapping("/findByUserNameAndPassword/{userName}/{password}")
	public User findByUserNameAndPassword(@PathVariable("userName") String userName,@PathVariable("password") String password)
	{
	
	User user=userRepository.findByUserNameAndPasswordAndConfirmed(userName, password,"yes");
		return user;
	}
	@GetMapping("/findByUserNameOrPassword/{userName}/{password}")
	public List<User> findByUserNameOrPassword(@PathVariable("userName") String userName,@PathVariable("password") String password)
	{
	
	List <User>user=userRepository.findByUserNameOrPassword(userName, password);
		return user;
	}
	@PostMapping("/uploadImage/{userId}")  
    public int handleFileUpload(@PathVariable int userId , @RequestParam("file") MultipartFile file, HttpSession session) {  
       
		Path rootLocation = Paths.get(session.getServletContext().getRealPath("/resources/images"));  
		 System.out.println("rootLocation  ==  " + rootLocation);
		 String nameExtension[] = file.getContentType().split("/");  
		 
		 String profileImage = System.currentTimeMillis() + "." + nameExtension[1];  
         
         System.out.println("ProfileImage  :: " + profileImage); 
         try {
			Files.copy(file.getInputStream(),rootLocation.resolve(profileImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
         
         
		return 1;
        
    }  
}
