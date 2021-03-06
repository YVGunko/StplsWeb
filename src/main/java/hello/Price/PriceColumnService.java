package hello.Price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceColumnService {
	@Autowired
	PriceRepository repository;
	@Autowired
	PriceTypeRepository repositoryPT;
	@Autowired
	PriceType2CrudeRepository repositoryPT2C;
	@Autowired
	PriceColumnRepository priceColumnRepository;
	
	public List<PriceColumn> getPriceColumns (Price p) {
		List<PriceColumn> result = new ArrayList<>();
				
		if (p != null) {
			//Выбираем все строки из таблицы столбцов прайса
			List<PriceColumn> pc = priceColumnRepository.findByPriceId(p.id);	
			if ((pc != null)&&(!pc.isEmpty())) {
				// Если изменили тип прайса
				if (pc.get(0).priceType2Crude.priceType.id != p.priceType.id) {
					priceColumnRepository.deleteByPriceId(p.id);
					pc = null;
				}
			}
			if ((pc != null)&&(!pc.isEmpty())) for (PriceColumn b : pc) {
				result.add(b);
			} else { //создать строки и вернуть
				//Выбрать столбцы именно подходящие по дате и типу прайса
				List<PriceType2Crude> pt2C = repositoryPT2C.findByPriceTypeId(p.priceType.id, p.getPriceRoot().getDateOfChange());
				if (pt2C != null) { 
					/*	public PriceColumn(Integer id, Date dateOfLastChange, @NotNull Price price, 
					 * @NotNull PriceType2Crude priceType2Crude,
						Double columnPrice, Double columnCosts)
					 * */
					for (PriceType2Crude pt : pt2C) 
						priceColumnRepository.save(new PriceColumn(0, new Date(), p, pt, (double)0, (double)0));

					
					result = calcAndSave (p.getId());
				}		
			}
		}
		if (result != null) for (PriceColumn b : result) {
			if (b.columnCosts == null) b.columnCosts = (double) 0;
			if (b.columnPrice == null) b.columnPrice = (double) 0;
			if (b.price.name.isEmpty()) b.price.name = "...";
		}
		return result;		
	}
	/* Расчет себестоимости и отпускной цены для сформированных ранее столбцов прайса.
	 * */
	List<PriceColumn> calcAndSave (Integer id){
		List<PriceColumn> pc = this.priceColumnRepository.findByPriceId(id);
		Price e = repository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Price not found exception when trying to obtain a PriceRoot."));
		int i = -1;
		Double tmpCalc = (double) 0;
		for (PriceColumn b : pc) {
			//Для случаев когда цена расчитывается как цена предыдущего материала + crudePlus
			i = pc.indexOf(b);
			if ((b.priceType2Crude.crude.crudeCost == 0)&(i > 0)) {
				b.columnCosts = pc.get(i-1).columnCosts + b.priceType2Crude.crude.crudePlus;
			} else {
				try {//calc with crude data
					// The costs should be def. as priceType.def_costs. if 0||null priceType2Crude.crude.directCosts
					tmpCalc = ((b.priceType2Crude.crude.directCosts == null)? 0 : b.priceType2Crude.crude.directCosts);
					tmpCalc += ((e.getPriceType().getDef_costs() == null)? 0 : e.getPriceType().getDef_costs()); 
					b.columnCosts = (double) Math.round(
						((b.priceType2Crude.crude.crudeCost == null)? 0 : b.priceType2Crude.crude.crudeCost)*
						((b.priceType2Crude.crude.crudeCurs == null)? 0 : b.priceType2Crude.crude.crudeCurs)*
						((e.weight == null)? 0 : e.weight)+
						tmpCalc+
						((e.paint == null)? 0 : e.paint)+
						(e.bRant ? ( (e.rant == null)? 0 : e.rant ) : 0)+
						(e.bLiner ? ( (e.shpalt == null)? 0 : e.shpalt ) : 0));
				}catch(NullPointerException ex) {
					ex.printStackTrace();
					System.out.println("NPE with calcAndSave, price.id="+e.getId()+", price.name="+e.getName());
				}
			}
			if (!b.columnCosts.equals((double) 0)) {
				tmpCalc = ((b.priceType2Crude.crude.crudeExtra == null)? 0 : b.priceType2Crude.crude.crudeExtra);
				tmpCalc += ((e.getPriceType().getDef_extra() == null)? 0 : e.getPriceType().getDef_extra());
				b.columnPrice = (double) Math.round(
					((b.columnCosts == null)? 0 : b.columnCosts)+tmpCalc);
			}
			else b.columnPrice = (double) 0;
	
		}
		return priceColumnRepository.saveAll(pc);
	}
	
	public List<String> getHeader(Price p) {
		List<PriceColumn> pt = new ArrayList<>();
		List<String> result = new ArrayList<>();
		
		if (p != null) {		
			pt = priceColumnRepository.findByPriceId(p.id);
			if (pt != null) for (PriceColumn b : pt) {
				result.add(b.priceType2Crude.crude.columnName);
			}	
		}
		return result;
	}


}

