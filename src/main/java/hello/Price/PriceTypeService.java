package hello.Price;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceTypeService {
	@Autowired
	PriceTypeRepository repository;
	@Autowired
	PriceType2CrudeRepository repository2;
	
	public List<PriceTypeWeb> getAllPriceTypeWeb() {
		List<PriceType> pt = new ArrayList<>();
		List<PriceTypeWeb> ptWeb = new ArrayList<>();
		List<PriceType2Crude> pt2C = new ArrayList<>();
		
		pt = repository.findByIdGreaterThanOrderByName(0);
		if (pt != null) for (PriceType b : pt) {
			pt2C = repository2.findByPriceTypeId(b.getId());
			String pt2C1 = "|";
			if (pt2C != null) for (PriceType2Crude p : pt2C) {
				pt2C1 = pt2C1 + " " + p.getCrude().getColumnName() +" |";
			}
			ptWeb.add(new PriceTypeWeb(b.getId(), b.getName(), pt2C1, 
					String.format("%1$,.2f", b.def_costs), String.format("%1$,.2f", b.def_paint), String.format("%1$,.2f", b.def_rant), String.format("%1$,.2f", b.def_shpalt)));
		}
			
		return ptWeb;
	}

	public PriceType checkForNullAndReplaceWithTop(PriceType ptOne)
	{
		PriceType responce = new PriceType();
		if (ptOne != null) {
			try {
				responce = repository.findOneById(ptOne.getId());
			}catch (Exception ex) {
				System.out.println("Exception. PriceType. checkForNullAndReplaceWithZero: "+ex); 
				responce = repository.findTopByIdGreaterThanOrderByName(0);
				}
		} else responce = repository.findTopByIdGreaterThanOrderByName(0);
		if (responce == null) responce = new PriceType();
		return responce;		
	}

}
