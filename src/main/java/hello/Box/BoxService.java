package hello.Box;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.utils;
import hello.Controller.BoxReq;
import hello.MasterData.MasterData;
import hello.MasterData.MasterDataRepository;

@Service
public class BoxService {
	
	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private MasterDataRepository masterDataRepository;
	
	public List<Box> save(List<BoxReq> boxReqList, Date currentDate) throws Exception{
		List<Box> toBesavedBoxList = new ArrayList<>();
		List<Box> savedBoxList =new ArrayList<>();
		for (BoxReq boxReq: boxReqList) {
			if (!boxRepository.existsById(boxReq.id)) {
				Optional<MasterData> masterData = masterDataRepository.findById(boxReq.masterdataId);
				if (!masterData.isPresent())
					throw new Exception("Error while saving Box with id= " + boxReq.id + ", no masterData with id= " + boxReq.masterdataId);
				Box uniqMasterDataAndBox = boxRepository.findByMasterDataIdAndNumBox(boxReq.masterdataId, boxReq.numBox) ;
				if (uniqMasterDataAndBox == null) {
					Box box = new Box(boxReq.id, boxReq.quantityBox, boxReq.numBox, masterData.get(), 
						boxReq.date, currentDate, null, boxReq.archive);
					toBesavedBoxList.add(box);
				}
				else {//Коробка эта уже есть, с другого устройства приняли одновременно, в другую накладную, частично коробка была заполнена.
					System.out.println("boxService "+boxReq.id+" Exist such record in the Box table with id= " + boxReq.masterdataId+" and NumBox " + boxReq.numBox);	
					//throw new Exception("Error while saving Box with id= " + boxReq.id + ", duplicate masterData with id= " + boxReq.masterdataId+" and NumBox" + boxReq.numBox);
				}
			}
		}
		//long start = System.currentTimeMillis();
		savedBoxList = boxRepository.saveAll(toBesavedBoxList);
		//System.out.println("boxRepository.SaveAll " + (System.currentTimeMillis() - start));
		return savedBoxList;
		
	}
	
	public void updateArchive(Box box) {
		if (box.getId()!=null && boxRepository.existsById(box.getId()) && !boxRepository.getOne(box.getId()).getArchive()) {
			box.setArchive(true);
		}
	}
	
	public List<Box> selectBoxByDateAndDivision (Date date, String division_code){
		List<Box> result = new ArrayList<>();
		/*if (date==null) {
			if( utils.empty( division_code ) ) 
				result.addAll(boxRepository.findAll());
			else 
				for (Box b : boxRepository.findByDivision_code(division_code)) {
					BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
					result.add(boxReq);
				}
		}
		else {
			if( utils.empty( division_code ) ) 
				result.addAll(boxRepository.findByDateGreaterThan(date));
			else 
				for (Box b : boxRepository.findByDateGreaterThan(date)) {
					BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
					result.add(boxReq);
				}
		}*/
		return result;		
	}
}
