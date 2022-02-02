package hello.OutDoorOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.OutDoorOrder.OutDoorOrderService;
import hello.OutDoorOrderRow.OutDoorOrderRow;
import hello.OutDoorOrderRow.OutDoorOrderRowRepository;
import hello.EmailSenderService;
import hello.Mail;
import hello.utils;
import hello.Client.ClientRepository;
import hello.OutDoorOrder.OutDoorOrder;
import hello.OutDoorOrder.OutDoorOrderRepository;
import hello.OutDoorOrder.OutDoorOrderReq;
import hello.Defaults.DefaultsRepository;


@RestController
public class OutDoorOrderController {
	@Autowired
    private JavaMailSender javaMailSender;
	@Autowired
    private EmailSenderService emailService;
	
	@Autowired
	OutDoorOrderService service;
	@Autowired
	OutDoorOrderRepository repository;
	@Autowired
	OutDoorOrderRowRepository rowRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	DefaultsRepository defaultsRepository;
	
	@CrossOrigin
	@PostMapping("/login/outDoorOrders/saveOrUpdate") 
	public OutDoorOrderReq SaveOrUpdate(@RequestBody OutDoorOrderReq request) throws Exception{

		return service.saveOrUpdate(request);
	}
	
	@CrossOrigin
	@DeleteMapping(path="/login/outDoorOrders/delOrder")
    public @ResponseBody void delete(@RequestParam(value="id", required=true) String id) throws Exception {
    		service.delete(id);
    }
	
	@CrossOrigin
	@GetMapping("/login/outDoorOrders") 
	public List<OutDoorOrder> get() throws Exception{
		Iterable<OutDoorOrder> iterable = repository.findAll(Sort.by("date").ascending());
		List<OutDoorOrder> array = StreamSupport
			    .stream(iterable.spliterator(), false)
			    .collect(Collectors.toList());
        return array;
	}

	@GetMapping("/outDoorOrders/export1c") 
	public List<OutDoorOrderReq> export1c(@RequestParam(value="division_code", required=true) String division_code) throws Exception{
		List<OutDoorOrder> tmp = repository.findBySentToMasterDateIsNullAndDivision_code(division_code);
		List<OutDoorOrderReq> responce = new ArrayList<>();
		if (!tmp.isEmpty()) {
			for (OutDoorOrder b : tmp) {
				responce.add (new OutDoorOrderReq (b.id, b.comment, b.date, utils.toString(utils.toLocalDate(b.date)),
						b.division.getCode(), String.valueOf(b.user.getId()),
						b.client.id, b.client.name, b.client.id1c,
						b.sample));
			}
		}
		return responce;
	}
	@CrossOrigin
	@GetMapping("/login/outDoorOrders/getReqs") 
	public List<OutDoorOrderReq> getReqs(@RequestParam(value="client_id", required=true) String id) throws Exception{
		List<OutDoorOrder> tmp = repository.findByClientIdOrderByDateDesc(id);
		List<OutDoorOrderReq> responce = new ArrayList<>();
		if (!tmp.isEmpty()) {
			for (OutDoorOrder b : tmp) {
				responce.add (new OutDoorOrderReq (b.id, b.comment, b.date, utils.toStringOnlyDate(utils.toLocalDate(b.date)),
						b.division.getCode(),b.division.getName(), String.valueOf(b.user.getId()), b.client.id, b.sample));
			}
		}
		return responce;
	}
	@CrossOrigin
	@GetMapping("/login/outDoorOrders/getReqsByUser") 
	public List<OutDoorOrderReq> getReqsByUser(@RequestParam(value="user_id", required=true) Long id) throws Exception{
		List<OutDoorOrder> tmp = repository.findByUserIdOrderByDateDesc(id);
		List<OutDoorOrderReq> responce = new ArrayList<>();
		if (!tmp.isEmpty()) {
			for (OutDoorOrder b : tmp) {
				responce.add (new OutDoorOrderReq (b.id, b.comment, b.date, utils.toStringOnlyDate(utils.toLocalDate(b.date)),
						b.division.getCode(),b.division.getName(), String.valueOf(b.user.getId()), 
						b.client.id, b.client.getName(),b.client.getPhone(),b.client.getEmail(), 
						b.sample));
			}
		}
		return responce;
	}
	@CrossOrigin
	@GetMapping("/login/outDoorOrders/getReqsByUserAndDate") 
	public List<OutDoorOrderReq> getReqsByUserAndDate(@RequestParam(value="user_id", required=true) Long id,
			@RequestParam(value="day", required=false) Date day) throws Exception{
		List<OutDoorOrderReq> responce = new ArrayList<>();
		List<OutDoorOrder> tmp = new ArrayList<>();
		if (day == null) {
			tmp = repository.findByUserIdOrderByDateDesc(id);
			if (!tmp.isEmpty()) 
				for (OutDoorOrder b : tmp) 
					responce.add (new OutDoorOrderReq (b.id, b.comment, b.date, utils.toStringOnlyDate(utils.toLocalDate(b.date)),
							b.division.getCode(),b.division.getName(), String.valueOf(b.user.getId()), 
							b.client.id, b.client.getName(),b.client.getPhone(),b.client.getEmail(), 
							b.sample));
		} else {
			tmp = repository.findByUserIdAndDate(id, day);
			if (!tmp.isEmpty()) 
				for (OutDoorOrder b : tmp) 
					responce.add (new OutDoorOrderReq (b.id, b.comment, b.date, utils.toStringOnlyDate(utils.toLocalDate(b.date)),
							b.division.getCode(),b.division.getName(), String.valueOf(b.user.getId()), 
							b.client.id, b.client.getName(),b.client.getPhone(),b.client.getEmail(), 
							b.sample));
		}
		return responce;
	}
	@CrossOrigin
	@GetMapping("/login/outDoorOrders/getReq") 
	public OutDoorOrderReq getReq(@RequestParam(value="id", required=true) String id) throws Exception{
		OutDoorOrder tmp = repository.findOneById(id);
		OutDoorOrderReq responce = new OutDoorOrderReq();
		if (tmp != null) {
				responce = new OutDoorOrderReq (tmp.id, tmp.comment, tmp.date, utils.toStringOnlyDate(utils.toLocalDate(tmp.date)), 
						tmp.division.getCode(),tmp.division.getName(), String.valueOf(tmp.user.getId()),
						tmp.client.id, tmp.client.getName(), tmp.client.phone, tmp.client.email,
						tmp.sample);
			}
		return responce;
	}
	
	
	@RequestMapping(value = "/login/outDoorOrders/getNextOrderNumber", method = RequestMethod.GET, produces = "application/json")
	public Map<String, Object> getNextOrderNumber(@RequestParam(value="client_id", required=true) String client_id) throws Exception{
		Map<String, Object> rtn = new LinkedHashMap<>();
		rtn.put("nextNumber", service.getNextOrderNumber(client_id));
		return rtn;
	}
	
	@CrossOrigin
	@GetMapping("/login/outDoorOrders/htmlOrder/get") 
	public Map<String, Object> getHTMLOrder(@RequestParam(value="id", required=true) String id) throws Exception{
		Map<String, Object> rtn = new LinkedHashMap<>();
		rtn.put("html", "<!doctype html>\n" +
                "</html>\n");
		return rtn; 
	}
	
	@CrossOrigin
	@PostMapping("/login/outDoorOrders/htmlOrder/post") 
	public Map<String, Object> sendOrder(@RequestParam(value="id", required=true) String id) throws Exception{
		Map<String, Object> rtn = new LinkedHashMap<>();
		try {
			String clientMail = clientRepository.findOneByid(repository.findOneById(id).getClient().getId()).getEmail();
			String clientName = clientRepository.findOneByid(repository.findOneById(id).getClient().getId()).getName();
			String bccMail = repository.findOneById(id).getSample() 
					? defaultsRepository.findById("samplesMail").get().name 
							: defaultsRepository.findById("ordersMail").get().name ;
			if (clientMail == null) throw new Exception("Не указан eMail.");
        		//sendEmailWithAttachment(id, clientMail);
        		
			System.out.println("sendMail clientMail : "+clientMail);
			System.out.println("sendMail clientName : "+clientName);
			System.out.println("sendMail bccMail : "+bccMail);
        		sendMail(id, clientMail, clientName, bccMail);
        		rtn.put("responce", "Ok");
        } catch (MessagingException e) {
            e.printStackTrace();
            rtn.put("responce", "Ошибка отправки.");
        } catch (IOException e) {
            e.printStackTrace();
            rtn.put("responce", "Ошибка обмена.");
        } catch (Exception e) {
            e.printStackTrace();
            rtn.put("responce", "Не указан eMail.");
        }
		
		return rtn; 
	}
    void sendEmailWithAttachment(String id, String clientMail) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(clientMail);
        helper.setBcc("zakazstilplast@gmail.com");
        helper.setSubject("Заказ от СтильПласт №".concat(id));

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>Welcome <b>" + id + "</b></div>\n" +
                "\n" +
                "<div> Your username is <b>" + id + "</b></div>\n" +
                "</body>\n" +
                "</html>\n", true);

        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }
    void sendMail(String id, String clientMail, String clientName, String bccMail) throws MessagingException, IOException { 
	    Mail mail = new Mail();
	    mail.setFrom("stilplastservicemail@gmail.com");//replace with your desired email
	    mail.setMailTo(clientMail);//replace with your desired email
	    mail.setBcc(bccMail);
	    mail.setSubject(clientName.concat(" №").concat(id).concat(" от " + utils.toStringOnlyDate(utils.toLocalDate(repository.findOneById(id).date))));
	    Map<String, Object> model = new HashMap<String, Object>();
	    //model.put("name", clientName);
	    model.put("rows", service.findRows(id));
	    model.put("orderNumber", id);
	    model.put("orderDate", service.findDate(id));
	    model.put("orderSample", service.findSample(id));
	    //model.put("sign", "Ваш Стиль-Пласт.");
	    mail.setProps(model);
	    emailService.sendEmail(mail, "mailOrder");
    }
    
	@GetMapping("/fixDifDiv") 
	public List<String> fixDifDiv() throws Exception{
		List<String> result = new ArrayList();
		OutDoorOrder newRec = new OutDoorOrder();
		Iterable<OutDoorOrder> iterable = repository.fixDifDiv();
		for (OutDoorOrder itr:iterable) {
			//Берем код клиента и получаем следующий номер документа.
			String nextNumber = service.getNextOrderNumber(itr.client.id);
			//Создаем новый документ (String id, String comment, Date date,  
			//String division_code, Long idUser, String clientId, Boolean sample)
			if (nextNumber != null) {
				//if (itr.division.getCode().equals("00-000025") ? "00-000002" : "00-000025") 
				newRec = repository.save(new OutDoorOrder(nextNumber, itr.comment, itr.date, itr.division.getCode().equals("00-000025") ? "00-000002" : "00-000025", itr.user.getId(), itr.client.getId(), itr.sample));
			}
			//Выбираем строки текущего докумнта
			List<OutDoorOrderRow> tmp = rowRepository.findByOutDoorOrderIdOrderByDtAsc(itr.id);
			//result.add("#"+itr.id+", rows "+tmp.size());
			int newRowsCounter = 0;
			for (int i = 0; i < tmp.size(); i = i + 1 ) { 
				//нужно добавить строки с совпадающим кодом подразделения в новый документ.
				if (!tmp.get(i).product.getDivision().getCode().equals(itr.division.getCode())) {
					/*@NotNull String id, @NotNull OutDoorOrder outDoorOrder, String attribute,
						Integer number, String barcode, Product product, String size, Color color, Color liner, Color rant,
						Color shpalt, Color vstavka, Color gelenok, Color guba, Color kabluk, Color matirovka, Color pechat, Color proshiv, Color pyatka, Color sled,
						Color spoyler, Color ashpalt, Boolean prodir, Boolean difersize, Boolean tert,
						Boolean frez, Boolean sample
					 * */
					//Добавлаяем запись в новый док
					OutDoorOrderRow savedRow = rowRepository.save( new OutDoorOrderRow(UUID.randomUUID().toString(), newRec, tmp.get(i).attribute, tmp.get(i).number, tmp.get(i).barcode, tmp.get(i).product, tmp.get(i).size, tmp.get(i).color, 
							tmp.get(i).liner, tmp.get(i).rant, tmp.get(i).shpalt, tmp.get(i).vstavka, tmp.get(i).gelenok, tmp.get(i).guba, tmp.get(i).kabluk, tmp.get(i).matirovka,
							tmp.get(i).pechat, tmp.get(i).proshiv, tmp.get(i).pyatka, tmp.get(i).sled, tmp.get(i).spoyler, tmp.get(i).ashpalt, tmp.get(i).prodir,
							tmp.get(i).difersize, tmp.get(i).tert, tmp.get(i).frez, tmp.get(i).sample, tmp.get(i).plastizol));
					// log
					System.out.println("newRow id: " + savedRow.id);
					System.out.println("oldRow id: " + tmp.get(i).id);
					//удаляем запись в старом доке
					rowRepository.deleteById(tmp.get(i).id);
					newRowsCounter++;
				}	
			}
			result.add("#old "+itr.id+", rows "+tmp.size()+" and #new "+nextNumber+", rows "+newRowsCounter);
		}
        return result;
	}
}
