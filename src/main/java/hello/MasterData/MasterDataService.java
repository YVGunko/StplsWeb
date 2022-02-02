package hello.MasterData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.Box.Box;
import hello.Box.BoxRepository;
import hello.Box.BoxService;
import hello.Cut.CutService;
import hello.Glue.GlueService;
import hello.Paint.PaintService;

@Service
public class MasterDataService {
	@Autowired
	private MasterDataRepository masterDataRepository;
	@Autowired
	BoxService boxService;
	
	@Autowired
	BoxRepository boxRepository;
	@Autowired
	CutService cutService;
	@Autowired
	GlueService glueService;
	@Autowired
	PaintService paintService;
	@Autowired
	ProductService productService;
	@Autowired
	ColorService colorService;
	
	@Transactional
    public MasterData saveOrUpdate(MasterData masterData) throws Exception {
    		if (!masterDataRepository.findByid1C(masterData.getid1C()).isEmpty()) {
    			MasterData oldMasterData = masterDataRepository.findByid1C(masterData.getid1C()).get(0);
    			if (masterDataRepository.findByid1C(masterData.getid1C()).size() > 1)
    				throw new Exception("ERROR: somehow there is more than 1 masterdatas with 1C Id : " + masterData.getid1C());
    			else
    				masterData.setId(oldMasterData.getId());
    		}
    		else {
        		if (!masterDataRepository.findByOrderId(masterData.getOrderId()).isEmpty()) {
        			MasterData oldMasterData = masterDataRepository.findByOrderId(masterData.getOrderId()).get(0);
        			if (masterDataRepository.findByOrderId(masterData.getOrderId()).size() > 1)
        				throw new Exception("ERROR: somehow there is more than 1 masterdatas with orderId : " + masterData.getOrderId());
        			else
        				masterData.setId(oldMasterData.getId());
        		}
    		}
    		if (masterData.getDate1C() == null) masterData.setDate1C(new Date());
    		if (!masterData.getCut().getId().isEmpty()) cutService.saveOrUpdate(masterData.getCut());
    		if (!masterData.getGlue().getId().isEmpty()) glueService.saveOrUpdate(masterData.getGlue());
    		if (!masterData.getPaint().getId().isEmpty()) paintService.saveOrUpdate(masterData.getPaint());
    		try {
    			if (masterData.getColor().getId() != null) colorService.saveOrUpdate(masterData.getColor());
    		} catch (Exception e) {
    			masterData.setColor(new Color("0",""));
    		}
    		try {
    			if (masterData.getLiner().getId() != null) colorService.saveOrUpdate(masterData.getLiner());
    		} catch (Exception e) {
    			masterData.setLiner(new Color("0",""));
    		}
    		try {
    			if (masterData.getRant().getId() != null) colorService.saveOrUpdate(masterData.getRant());
    		} catch (Exception e) {
    			masterData.setRant(new Color("0",""));
    		}
    		try {
    			if (masterData.getShpalt().getId() != null) colorService.saveOrUpdate(masterData.getShpalt());
    		} catch (Exception e) {
    			masterData.setShpalt(new Color("0",""));
    		}
    		try {
    			if (masterData.getSize() != null) masterData.setSize(masterData.getSize());
    		} catch (Exception e) {
    			masterData.setSize("0");
    		}
    		try {
    			if (masterData.getProduct().getId() != null) productService.saveOrUpdate(masterData.getProduct());
    		} catch (Exception e) {
    			masterData.setProduct(new Product("0","","0"));
    		}
    		try {
    			if (masterData.getVstavka().getId() != null) colorService.saveOrUpdate(masterData.getVstavka());
    		} catch (Exception e) {
    			masterData.setVstavka(new Color("0",""));
    		}   
    		try {
    			if (masterData.getGelenok().getId() != null) colorService.saveOrUpdate(masterData.getGelenok());
    		} catch (Exception e) {
    			masterData.setGelenok(new Color("0",""));
    		}  
    		try {
    			if (masterData.getGuba().getId() != null) colorService.saveOrUpdate(masterData.getGuba());
    		} catch (Exception e) {
    			masterData.setGuba(new Color("0",""));
    		}  
    		try {
    			if (masterData.getKabluk().getId() != null) colorService.saveOrUpdate(masterData.getKabluk());
    		} catch (Exception e) {
    			masterData.setKabluk(new Color("0",""));
    		}  
    		try {
    			if (masterData.getMatirovka().getId() != null) colorService.saveOrUpdate(masterData.getMatirovka());
    		} catch (Exception e) {
    			masterData.setMatirovka(new Color("0",""));
    		}  
    		try {
    			if (masterData.getPechat().getId() != null) colorService.saveOrUpdate(masterData.getPechat());
    		} catch (Exception e) {
    			masterData.setPechat(new Color("0",""));
    		}  
    		try {
    			if (masterData.getProshiv().getId() != null) colorService.saveOrUpdate(masterData.getProshiv());
    		} catch (Exception e) {
    			masterData.setProshiv(new Color("0",""));
    		}  
    		try {
    			if (masterData.getPyatka().getId() != null) colorService.saveOrUpdate(masterData.getPyatka());
    		} catch (Exception e) {
    			masterData.setPyatka(new Color("0",""));
    		}  
    		try {
    			if (masterData.getSled().getId() != null) colorService.saveOrUpdate(masterData.getSled());
    		} catch (Exception e) {
    			masterData.setSled(new Color("0",""));
    		}  
    		try {
    			if (masterData.getSpoyler().getId() != null) colorService.saveOrUpdate(masterData.getSpoyler());
    		} catch (Exception e) {
    			masterData.setSpoyler(new Color("0",""));
    		}  
    		try {
    			if (masterData.getAshpalt().getId() != null) colorService.saveOrUpdate(masterData.getAshpalt());
    		} catch (Exception e) {
    			masterData.setAshpalt(new Color("0",""));
    		}  
    		try {
    			if (masterData.getPlastizol().getId() != null) colorService.saveOrUpdate(masterData.getPlastizol());
    		} catch (Exception e) {
    			masterData.setPlastizol(new Color("0",""));
    		} 
    		try {
    			if (masterData.getPlastizol2().getId() != null) colorService.saveOrUpdate(masterData.getPlastizol2());
    		} catch (Exception e) {
    			masterData.setPlastizol2(new Color("0",""));
    		} 
		return masterDataRepository.save(masterData);
    }
	
	@Transactional
	public void delete(ArrayList<String> id1c) {
		if (id1c!=null & !(id1c.isEmpty())) {
			masterDataRepository.deleteByid1CIn(id1c);
		}
		//return null;
	} 
	
	@Transactional
	public void setArchiveByid1c(ArrayList<String> id1c, boolean state) {
		if (id1c!=null & !(id1c.isEmpty())) {
			for (int i = 0; i < id1c.size(); i = i + 1 ) { 
				MasterData md = masterDataRepository.getByid1C(id1c.get(i));
				if (md!=null) md.setArchive(state);
				//Коробки тоже пометить архивом
				List<Box> savedBoxList = boxRepository.findByMasterDataId(md.getId());
				for (Box b : savedBoxList) {
					if (b != null) boxService.updateArchive(b);
					}
				} 
			}
		//return null;
	}
}
