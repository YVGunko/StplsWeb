package hello.OutDoorOrder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.utils;
import hello.Client.Client;
import hello.Client.ClientRepository;
import hello.Division.Division;
import hello.Division.DivisionRepository;
import hello.OutDoorOrder.OutDoorOrderReq;
import hello.OutDoorOrderRow.OutDoorOrderRow;
import hello.OutDoorOrderRow.OutDoorOrderRowRepository;
import hello.User.User;

@Service
public class OutDoorOrderService {
	@Autowired
	OutDoorOrderRepository repository;
	@Autowired
	OutDoorOrderRowRepository rowRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	hello.User.UserRepository userRepository;
	@Autowired
	DivisionRepository divRepository;

	public OutDoorOrderReq saveOrUpdate(OutDoorOrderReq request)  throws Exception {

		Client client = clientRepository.findOneByid(request.client_id);
		if (client == null)
			throw new Exception("Error while saving OutDoorOrder with id= " + request.id + ", no Client with id= " + request.client_id);
		User user = userRepository.findOneById(Long.valueOf(request.user_id));
		if (user == null)
			throw new Exception("Error while saving OutDoorOrder with id= " + request.id + ", no user with id= " + request.user_id);
		Division division = divRepository.findOneBycode(request.division_code);
		if (division == null)
			throw new Exception("Error while saving OutDoorOrder with id= " + request.id + ", no Division with id= " + request.division_code);

		// String id, String comment, Date date, String division_code, Long idUser, String clientId
		OutDoorOrder tmp = repository.save(new OutDoorOrder(request.id, request.comment, 
				request.date, request.getDivision_code(), Long.valueOf(request.user_id), request.client_id, request.sample));	
		OutDoorOrderReq responce = new OutDoorOrderReq (tmp.id, tmp.comment, tmp.date, utils.toStringOnlyDate(utils.toLocalDate(tmp.date)), 
				tmp.division.getCode(), tmp.division.getName(), String.valueOf(tmp.user.getId()), tmp.client.id, tmp.sample);
		
	
		return responce;
	}
	
	public String getNextOrderNumber (String client_id)  throws Exception {
		String responce = null;
		if (client_id != null) {
			try {
					Long l = repository.getNextOrderNumber(client_id)+(long)1;
					responce = client_id+"-" + l;
			}catch (Exception e){
					responce = client_id+"-"+1;
			}
		}
		return responce;
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public List<OutDoorOrderRep> findRows(String id) {
		List<OutDoorOrderRep> orderRep = new ArrayList<>();
		List<OutDoorOrderRow> rowList =  rowRepository.findByOutDoorOrderIdOrderByDtAsc(id);
		Boolean div25 = repository.findById(id).get().getDivision().getCode().equals("00-000025");
		String tmpName ;
		if (!rowList.isEmpty()) {
	
			for (int i=0; i < rowList.size(); i++) {
				tmpName = 
					rowList.get(i).getProduct().getId() != null ? 
							(rowList.get(i).getProduct().id.equals("0") ? "" : rowList.get(i).getProduct().name) : "";
				tmpName =	tmpName.concat(rowList.get(i).size != null ? 
									(rowList.get(i).size.equals("") ? "" : " р. "+rowList.get(i).size) : "");
				tmpName =	tmpName.concat(rowList.get(i).getColor().getId() != null ? 
							(rowList.get(i).getColor().id.equals("0") ? "" : ", Цвет "+rowList.get(i).getColor().name) : "");
				tmpName =	tmpName.concat(rowList.get(i).getLiner().getId() != null ? 
							(rowList.get(i).getLiner().id.equals("0") ? "" : ", Подклада "+rowList.get(i).getLiner().name) : "");
				tmpName =	tmpName.concat(rowList.get(i).getRant().getId() != null ? 
							(rowList.get(i).getRant().id.equals("0") ? "" : ", Рант "+rowList.get(i).getRant().name) : "");
				tmpName =	tmpName.concat(rowList.get(i).getShpalt().getId() != null ? 
							(rowList.get(i).getShpalt().id.equals("0") ? "" : ", Шпальт "+rowList.get(i).getShpalt().name) : "");
				/*
				 * IF(repeated.current.sVstavka!="...","Вст."+repeated.current.sVstavka,"")+
				 * IF(repeated.current.sAshpalt!="..."," "+IF(params.ppDivisionCode==appVars.tepCode,"Шпал.","Крас.")+repeated.current.sAshpalt,"")+
				 * IF(repeated.current.sSpoyler!="..."," "+IF(params.ppDivisionCode==appVars.tepCode,"Спойл.","М1.")+repeated.current.sSpoyler,"")+
				 * IF(repeated.current.sGuba!="..."," "+IF(params.ppDivisionCode==appVars.tepCode,"Губа.","М2.")+repeated.current.sGuba,"")+
				 * IF(repeated.current.sKabluk!="..."," "+IF(params.ppDivisionCode==appVars.tepCode,"Кабл.","М3.")+repeated.current.sKabluk,"")+
				 * IF(repeated.current.sGelenok!="..."," "+"Гел."+repeated.current.sGelenok,"")+
				 * IF(repeated.current.sSled!="..."," "+"След."+repeated.current.sSled,"")+
				 * IF(repeated.current.sPyatka!="..."," "+"Пятк."+repeated.current.sPyatka,"")+
				 * IF(repeated.current.tert==true," "+"Терт.","")+
				 * IF(repeated.current.sMatirovka!="..."," "+"Мат."+repeated.current.sMatirovka,"")+
				 * IF(repeated.current.sPechat!="..."," "+"Печ."+repeated.current.sPechat,"")+
				 * IF(repeated.current.sProshiv!="..."," "+"Прош."+repeated.current.sProshiv,"")+
				 * IF(repeated.current.prodir==true," "+"Продир.","")+
				 * IF(repeated.current.frez==true," "+"Фрез.","")+
				 * IF(repeated.current.attribute!=""," "+"Доп."+repeated.current.attribute,"")
				 * */
				
				tmpName =	tmpName.concat(rowList.get(i).getVstavka().getId() != null ? 
						(rowList.get(i).getVstavka().id.equals("0") ? "" : ", Вставка "+rowList.get(i).getVstavka().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getAshpalt().getId() != null ? 
						(rowList.get(i).getAshpalt().id.equals("0") ? "" : ", "+(div25 ?"Шпальт" : "Прокрас")+" "+rowList.get(i).getAshpalt().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getSpoyler().getId() != null ? 
						(rowList.get(i).getSpoyler().id.equals("0") ? "" : ", "+(div25 ?"Спойлер" : "М1.")+" "+rowList.get(i).getSpoyler().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getGuba().getId() != null ? 
						(rowList.get(i).getGuba().id.equals("0") ? "" : ", "+(div25 ?"Губа" : "М2.")+" "+rowList.get(i).getGuba().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getKabluk().getId() != null ? 
						(rowList.get(i).getKabluk().id.equals("0") ? "" : ", "+(div25 ?"Каблук" : "М3.")+" "+rowList.get(i).getKabluk().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getGelenok().getId() != null ? 
						(rowList.get(i).getGelenok().id.equals("0") ? "" : ", Геленок "+rowList.get(i).getGelenok().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getSled().getId() != null ? 
						(rowList.get(i).getSled().id.equals("0") ? "" : ", След "+rowList.get(i).getSled().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getPyatka().getId() != null ? 
						(rowList.get(i).getPyatka().id.equals("0") ? "" : ", Пятка "+rowList.get(i).getPyatka().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).tert != null ? 
						(rowList.get(i).tert ? ", Терт. " : "") : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getMatirovka().getId() != null ? 
						(rowList.get(i).getMatirovka().id.equals("0") ? "" : ", Матировка "+rowList.get(i).getMatirovka().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getPechat().getId() != null ? 
						(rowList.get(i).getPechat().id.equals("0") ? "" : ", Печать "+rowList.get(i).getPechat().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getProshiv().getId() != null ? 
						(rowList.get(i).getProshiv().id.equals("0") ? "" : ", Прошив "+rowList.get(i).getProshiv().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).prodir != null ? 
						(rowList.get(i).prodir ? ", Продир. " : "") : "");
				
				tmpName =	tmpName.concat(rowList.get(i).frez != null ? 
						(rowList.get(i).frez ? ", Фрез. " : "") : "");
				
				tmpName =	tmpName.concat(rowList.get(i).getPlastizol().getId() != null ? 
						(rowList.get(i).getPlastizol().id.equals("0") ? "" : ", Пл. "+rowList.get(i).getPlastizol().name) : "");
				
				tmpName =	tmpName.concat(rowList.get(i).attribute != null ? 
						(rowList.get(i).attribute.equals("") ? "" : ", Доп. "+rowList.get(i).attribute) : "");
			
				
	    			orderRep.add(new OutDoorOrderRep(tmpName, 
	    					rowList.get(i).getNumber().toString()));	
	    			}
		}

		return orderRep;
	}

		public Object findDate(String id) {
			String dateOutDoc = utils.toStringOnlyDate(utils.toLocalDate(repository.findOneById(id).getDate()));
			if (!(dateOutDoc).isEmpty())
				return dateOutDoc;
			else
				return "Без даты";
		}
		public Object findSample(String id) {
			Boolean sample = repository.findOneById(id).getSample();
			if (!(sample).booleanValue())
				return "";
			else
				return "Образцы.";
		}
	
}
