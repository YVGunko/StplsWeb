package hello.Price;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.EmailSenderService;
import hello.Mail;
import hello.Defaults.DefaultsRepository;

@Service
public class PriceService {
	@Autowired
	DefaultsRepository defaultsRepository;
	@Autowired
	PriceRepository repository;
	@Autowired
	PriceTypeRepository repositoryPT;
	@Autowired
	PriceTypeService servicePT;
	@Autowired
	PriceRootRepository repositoryPR;
	@Autowired
	PriceRootService servicePR;
	@Autowired
	PriceType2CrudeRepository repositoryPT2C;
	@Autowired
	PriceColumnRepository priceColumnRepository;
	@Autowired
	PriceColumnService priceColumnService;
	@Autowired
    private EmailSenderService emailService;

	public Boolean delete (Integer id) throws Exception {
		Boolean result = false;
		if (id != null) {
			try {
				priceColumnRepository.deleteByPriceId(id);
				repository.deleteById(id);
				result = true;
			} catch (Exception ex){
				System.out.println("Price delete Exception: " + ex);
            }
		}
		
		return result;	
	}
	public Boolean delete (Price p) throws Exception {
		Boolean result = false;
		if (p.id != null) {
			try {
				priceColumnRepository.deleteByPriceId(p.id);
				repository.delete(p);
				result = true;
			} catch (Exception ex){
				System.out.println("Price delete Exception: " + ex);
            }
		}
		
		return result;	
	}
	public Price save (Price p) throws Exception {
		Price result = new Price();
		
		if (p.id == null) {// Добавление строки
		//Сначала создаем строку прайса чтобы было на что ссылаться
			Optional<Price> optPr = repository.findOneByNameAndPriceTypeIdAndPriceRootId(p.name, p.priceType.id, p.priceRoot.id);
			if (optPr.isPresent()) {//Строка с такими параметрами не может повторяться
				result = optPr.get();
				}
			else {
				if (p.costs == null) p.costs=(double)0;
				if (p.paint == null) p.paint=(double)0;
				if (p.rant == null) p.rant=(double)0;
				if (p.shpalt == null) p.shpalt=(double)0;
				if (p.number_per_box == null) p.number_per_box=(double)0;
				if (p.bRant == null) p.bRant=false;
				if (p.bLiner == null) p.bLiner=false;
				result = repository.save(p); 
				}
		} else { //Сохраняем изменения
			//Прежде проверяем изменения
			Optional<Price> optPr = repository.findById(p.id);
			if (optPr.isPresent()) {//Строка с такими параметрами не может повторяться
				result = optPr.get();
				if (result.priceType.id != p.priceType.id) {
					Optional<PriceType> opt = repositoryPT.findById(p.priceType.id);
					if (opt.isPresent()) {//Строка с такими параметрами не может повторяться
						PriceType pt = opt.get();
						if (pt.def_costs != null) p.setCosts(pt.def_costs);
						if (pt.def_paint != null) p.setPaint(pt.def_paint);
						p.setRant(p.bRant ? ( (pt.def_rant == null)? p.rant : pt.def_rant ) : 0) ;
						p.setShpalt(p.bLiner ? ( (pt.def_shpalt == null)? p.shpalt : pt.def_shpalt ) : 0);	
					}
				}
			}
			result = repository.save(p);
		}
		
		List<PriceColumn> pc = priceColumnService.getPriceColumns (result);
		if (pc == null) throw new Exception("ERROR: ошибка при расчете цен.");
		
		return result;		
	}

	public void copyOne (Price p, PriceRoot newPr) throws Exception {
		/*Price(Integer id, String name, PriceType priceType, Double costs, Double paint,
			Double rant, Double shpalt, Double number_per_box, Double weight, Date dateOfLastChange, Boolean bRant,
			Boolean bLiner, String note, PriceRoot priceRoot)*/
		// Создаем копию объекта иначе не запишется в таблицу
		Price result = new Price(null, p.name, p.priceType, p.costs, p.paint, p.rant, p.shpalt, p.number_per_box, p.weight, p.dateOfLastChange, 
				p.bRant, p.bLiner, p.note, newPr);
		Price newPrice = repository.save(result);
		if (newPrice == null) throw new Exception("ERROR: Price copyOne. Price save error.");

		List<PriceColumn> oldPcList = priceColumnService.getPriceColumns (p);
		if (oldPcList == null) throw new Exception("ERROR: Price copyOne. priceColumnService.getPriceColumns (p) return null.");
		for (PriceColumn oldPcOne : oldPcList) {
			/*PriceColumn(Integer id, Date dateOfLastChange, @NotNull Price price, @NotNull PriceType2Crude priceType2Crude,
					Double columnPrice, Double columnCosts)*/
			// Создаем копию объекта иначе не запишется в таблицу
			PriceColumn newPcOne = new PriceColumn(null, oldPcOne.getDateOfLastChange(), newPrice, oldPcOne.getPriceType2Crude(), 
					oldPcOne.getColumnPrice()+newPr.plusValue, oldPcOne.getColumnCosts());
			priceColumnRepository.save(newPcOne);
		}
		//return result;
	}
	
    public void sendMail(@Valid Price e, String Subject) throws MessagingException, IOException { 
    	
    		Mail mail = new Mail();
	    mail.setFrom("stilplastservicemail@gmail.com");
	    
	    defaultsRepository.findById("directorsMail").ifPresent(eMail -> mail.setMailTo(eMail.name));
	    		if (mail.getMailTo().equals(null)) throw new MessagingException("eMail директора не задан.");
	    
	    mail.setSubject("Стиль-Пласт прайс. ".concat(Subject.concat(" "+e.name)));
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("priceType", repositoryPT.getOne(e.getPriceType().getId()).name);
	    model.put("costs", String.format("%1$,.2f", e.costs));
	    model.put("paint", String.format("%1$,.2f", e.paint));
	    model.put("bRant", e.bRant);
	    model.put("rant", String.format("%1$,.2f", e.rant));
	    model.put("bLiner", e.bLiner);
	    model.put("sample", e.priceRoot.getSample());
	    model.put("shpalt", String.format("%1$,.2f", e.shpalt));
	    model.put("weight", String.format("%1$,.3f", e.weight));
	    model.put("number_per_box", String.format("%1$,.0f", e.number_per_box));
	    
	    model.put("pcRows", priceColumnRepository.findByPriceId(e.id));
	    
	    model.put("date", e.dateOfLastChange);
	    model.put("name", e.name);
	    model.put("sign", "Ваш Стиль-Пласт.");
	    mail.setProps(model);
	    emailService.sendEmail(mail, "mailPrice");
    }
    public List<Price> getPriceByFilter (ViewPriceFilter priceFilter, Integer prevPriceTypeId, Boolean sample) {
    		List<Price> price = new ArrayList<>();
    		PriceRoot pr = new PriceRoot();
		if (priceFilter.getSample() == null) priceFilter.setSample(false);
		if (priceFilter.getName() == null) priceFilter.setName("");
		priceFilter.setPriceType(servicePT.checkForNullAndReplaceWithTop(priceFilter.getPriceType()));
		if (prevPriceTypeId == null) {//предыдущего значения нет, значит первый раз загружается страница.
			pr = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(priceFilter.getPriceType().getId(), sample);
			if (pr == null) priceFilter.setPriceRoot(repositoryPR.findOneById(0));
			else 	priceFilter.setPriceRoot(pr);
		}else {
			if (prevPriceTypeId == priceFilter.getPriceType().getId()) {//тип прайса не менялся
				if (servicePR.checkForNull(priceFilter.getPriceRoot())) priceFilter.setPriceRoot(repositoryPR.findOneById(0)); 
				else priceFilter.setPriceRoot(repositoryPR.findOneById(priceFilter.getPriceRoot().getId()));
			}else {
				pr = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(priceFilter.getPriceType().getId(), sample);
				if (pr == null) priceFilter.setPriceRoot(repositoryPR.findOneById(0));
				else 	priceFilter.setPriceRoot(pr);
			}
		}
		
		if (!priceFilter.getName().equals("")) 
			price = repository.findByPriceTypeIdAndPriceRootIdAndNameStartingWithOrderByName (priceFilter.getPriceType().getId(),
					priceFilter.getPriceRoot().getId(),
					priceFilter.getName());
		else 
			price = repository.findByPriceTypeIdAndPriceRootIdOrderByName (priceFilter.getPriceType().getId(),
					priceFilter.getPriceRoot().getId());
		return price;
    }
}

