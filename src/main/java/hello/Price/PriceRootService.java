package hello.Price;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.Defaults.DefaultsRepository;

@Service
public class PriceRootService {
	@Autowired
	DefaultsRepository defaultsRepository;
	@Autowired
	PriceRepository repositoryPrice;
	@Autowired
	PriceService servicePrice;
	@Autowired
	PriceRootService servicePR;
	@Autowired
	PriceTypeRepository repositoryPT;
	@Autowired
	PriceRootRepository repository;
	@Autowired
	PriceType2CrudeRepository repositoryPT2C;
	@Autowired
	PriceColumnRepository priceColumnRepository;
	@Autowired
	PriceColumnService priceColumnService;

//TODO нужно изменить priceRoot. Связать с priceType и делать изменения для каждого Crude
	public void save(@Valid PriceRoot newPriceRoot, int i) throws Exception {
		// Выбрать предыдущий прайс для тех же разделов прайса. Может быть разный для разных разделов.
		PriceRoot oldPriceRoot = new PriceRoot();
		// Для PriceType ищем PriceRoot с самой новой датой.
		oldPriceRoot = repository.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(i, newPriceRoot.getSample())
				.orElseThrow(() -> new NoSuchElementException("PriceRoot not found exception when trying to obtain a PriceRoot."));

		List<Price> oldPrice = new ArrayList<>();
    		// Сохранить, чтобы было на что ссылаться.
		newPriceRoot.setPriceType(repositoryPT.getOne(i));
		repository.save(newPriceRoot);
    		// Скопировать все Price из выбранных разделов со ссылкой на PriceRoot и PriceColumn с увеличением цен

		if (oldPriceRoot != null) {
			//get price's rows as source  
			oldPrice = repositoryPrice.findByPriceTypeIdAndPriceRootIdOrderByName(i, ((oldPriceRoot.getId() == newPriceRoot.id) ? 0 : oldPriceRoot.getId()));
			if (oldPrice != null) {
				for (Price price : oldPrice) servicePrice.copyOne(price, newPriceRoot);
			} else {
				System.out.println("ERROR: PriceRootService save: Не найден исходный Price для PriceType & PriceRoot: " + i+", "+oldPriceRoot.getId());
			}
		} else {
			System.out.println("ERROR: PriceRootService save: Не найден исходный PriceRoot для PriceType: " + i);
		}		
	}

	public PriceRoot findActualPriceRootByPriceTypeIdAndSample (PriceType priceType, 
			Integer prevPriceTypeId,
			PriceRoot priceRoot,
			Integer prevPriceRootId,
			Boolean sample,
			Boolean prevSample) {
		
		PriceRoot responce = new PriceRoot();
		//предыдущего значения нет, значит первый раз загружается страница || тип прайса изменился
		if (prevPriceTypeId == null || prevPriceTypeId != priceType.getId()) {
			responce = repository.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc
					(priceType.getId(), sample)
					.orElseThrow(() -> new NoSuchElementException("PriceRoot not found exception when trying to obtain a PriceRoot."));
		} else {
			if (prevPriceTypeId == priceType.getId() & !checkForNull(priceRoot)) {			//тип прайса не менялся & priceRoot not null
				if (prevSample == sample) {						//образец не менялся
					responce = repository.findOneById(priceRoot.getId());
				}else { 			//Образец изменился, получите максимальную дату прайса
					responce = repository.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc
							(priceType.getId(), sample)
								.orElseThrow(() -> new NoSuchElementException("PriceRoot not found exception when trying to obtain a PriceRoot."));
					}	
				}
			}
		if (responce == null) responce = new PriceRoot();
		return responce;		
	}
	public Boolean checkForNull(PriceRoot ptOne)
	{
		Boolean responce = false; // 0 должен быть всегда.
		try {
			ptOne.getId().intValue();
		}catch (Exception ex) {
				System.out.println("PriceRoot. checkForNull. Null Exception: "+ex); 
				responce = true;
				}
		return responce;		
	}
	public PriceRoot checkForNullAndReplaceWithZero(PriceRoot ptOne)
	{
		PriceRoot responce = new PriceRoot(); // 0 должен быть всегда.
		if (ptOne != null) {
			try {
				responce = repository.findOneById(ptOne.getId());
			}catch (Exception ex) {
				System.out.println("Error. PriceRoot. checkForNullAndReplaceWithZero. Exception: "+ex); 
				responce = repository.findOneById(0);
				}
		}
		if (responce == null) responce = repository.findOneById(0); // 0 должен быть всегда.
		return responce;		
	}
	public List<PriceRoot> findActualListPriceRootByPriceTypeId (Integer pt, Boolean sample){
		List<PriceRoot> responce = new ArrayList<>();
		if (pt != null) {
			if (pt == 0) {
				responce.addAll(repository.findBySampleOrderByDateOfChangeDesc(sample));
			}else {
				responce.addAll(repository.findByPriceTypeIdAndSampleOrderByDateOfChangeDesc(pt, sample));
			}
		}
		//if (responce.isEmpty()) responce.add(repository.findOneById(0));
	    return responce;
	}
	public List<PriceRoot> getTopPriceRootAsListByPriceTypeId(Integer pt, Boolean sample){
		List<PriceRoot> responce = new ArrayList<>();
		PriceRoot responceOne = new PriceRoot();
		if (pt != null) {
			if (pt == 0) responce.addAll(repository.findBySampleOrderByDateOfChangeDesc(sample));
			else {
				responceOne = repository.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(pt, sample)
						.orElseThrow(() -> new NoSuchElementException("PriceRoot not found exception when trying to obtain a PriceRoot."));
				if (responceOne == null) {
					responceOne = new PriceRoot();
					responceOne.setId(0);
					responceOne.setDateOfChange(new Date());
				}
				responce.add(responceOne);
			}
		}
		//if (responce.isEmpty()) responce.add(repository.findOneById(0));
	    return responce;
	}
}

