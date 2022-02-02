package hello.Client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Controller.OrderReq;
import hello.MasterData.MasterData;

@RestController
public class ClientController {
	@Autowired
	private ClientRepository cRepository;
	@Autowired
	private ClientService cService;
	
	@GetMapping("/clientid") 
	public List<Client> getClientById(@RequestParam(value="id", required=false) String id) throws RuntimeException{
		if (id==null)
			return cRepository.findAllByOrderByIdAsc();
		else
			return cRepository.findByid(id);
	}
	
	@GetMapping("/client1c") 
	public List<Client> client1c() throws RuntimeException{

			return cRepository.findById1cIsNull();
	}
	@PostMapping("/client1c") 
	public Client client1сSaveOrUpdate(@RequestBody Client client) throws Exception{
		if (client==null)
			return null;
		else
			return cService.saveOrUpdate(client);
	}	
	@GetMapping("/clientname") 
	public List<Client> getClientByName(@RequestParam(required=false) String name) throws RuntimeException{
		if (name==null)
			return cRepository.findAllByOrderByNameAsc();
		else
			return cRepository.findByNameOrderByName(name);
	}
	
	@CrossOrigin
	@PostMapping("/newClient") 
	public Client newClient(@RequestBody Client client) throws Exception{
		if (client==null)
			return null;
		else
			return cService.saveOrUpdate(client);
	}

	@CrossOrigin
	@GetMapping("/login/clients/getReqs") 
	public List<ClientReq> allClientOrdered() throws RuntimeException{
		List<ClientReq> result = new ArrayList<>();
		
		for (Client b : cRepository.findAllByOrderByNameAsc()) {
					ClientReq clientReq = new ClientReq(b.getId(), b.getName(), b.getEmail(), b.getPhone());
					result.add(clientReq);
			}
	
		return result;
	}
	
	/*@CrossOrigin
	@GetMapping("/login/clients/getReqs") 
	public List<ClientReq> allClientOrdered(@RequestParam(required=false) String name) throws RuntimeException{
		List<ClientReq> result = new ArrayList<>();
		
		if (name==null){
				for (Client b : cRepository.findAllByOrderByNameAsc()) {
					ClientReq clientReq = new ClientReq(b.getId(), b.getName(), b.getEmail(), b.getPhone());
					result.add(clientReq);
			}}
		else{
			for (Client b : cRepository.findTop50ByNameContainingOrderByName(name)) {
				ClientReq clientReq = new ClientReq(b.getId(), b.getName(), b.getEmail(), b.getPhone());
				result.add(clientReq);
		}}
	
		return result;
	}*/
	@GetMapping("/login/clients/getReq") 
	public Client clientById(@RequestParam(value="id", required=true) String id) throws RuntimeException{
			return cRepository.findOneByid(id);
	}
	@PostMapping("/login/clients/saveOrUpdate") 
	public Client clientSaveOrUpdate(@RequestBody Client client) throws Exception{
		if (client==null)
			return null;
		else
			return cService.saveOrUpdate(client);
	}
}
