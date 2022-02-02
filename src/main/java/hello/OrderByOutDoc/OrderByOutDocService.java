package hello.OrderByOutDoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.Box.Box;
import hello.BoxMoves.BoxMove;
import hello.MasterData.MasterData;
import hello.PartBox.PartBox;
import hello.Box.BoxRepository;
import hello.Box.BoxService;
import hello.BoxMoves.BoxMovesRepository;
import hello.BoxMoves.BoxMovesService;
import hello.MasterData.MasterDataRepository;
import hello.MasterData.MasterDataService;
import hello.OrderByOutDoc.OrderByOutDoc;
import hello.OutDoc.OutDocService;

import hello.PartBox.PartBoxRepository;
import hello.PartBox.PartBoxService;

@Service
public class OrderByOutDocService {
	@Autowired
	MasterDataRepository masterDataRepository;
	@Autowired
	MasterDataService masterDataService;	
	@Autowired
	BoxMovesService boxMovesService;
	@Autowired
	private BoxMovesRepository boxMovesRepository;
	@Autowired
	PartBoxService partBoxService;
	@Autowired
	BoxService boxService;
	@Autowired
	BoxRepository boxRepository;
	@Autowired
	private PartBoxRepository partBoxRepository;


	@Autowired
	private OutDocService Service;
	
	public 	ArrayList<OrderByOutDoc> findByOutDocId (String outDocId){
		ArrayList<OrderByOutDoc> result = new ArrayList<>();
		
		if (!outDocId.isEmpty()) {
			//ВЫбираем  

			List<String> bmIdList = partBoxRepository.findByOutDocId(outDocId).stream().map(PartBox::getBoxMove).collect(Collectors.toList()).stream().map(BoxMove::getId).collect(Collectors.toList());	
			if (!bmIdList.isEmpty()) {
				Long OperationId = Service.selectOperationIdById(outDocId);
				List<String> bIdList = boxMovesRepository.findByOperationIdAndIdIn(OperationId,bmIdList).stream().map(BoxMove::getBox).collect(Collectors.toList()).stream().map(Box::getId).collect(Collectors.toList());
					if (!bIdList.isEmpty()) {
						List<Long> mIdList = boxRepository.findByIdIn(bIdList).stream().map(Box::getMasterData).collect(Collectors.toList()).stream().map(MasterData::getId).collect(Collectors.toList());
						if (!mIdList.isEmpty()) {
							List<MasterData> mList = masterDataRepository.findByIdInOrderByOrderTextAscNomenklatureAsc(mIdList);
							int lastPairQuantitySum = 0;
							int lastBoxQuantitySum = 0;
							for (MasterData m : mList) {
								//System.out.println("Номенклатура: " + m.getNomenklature()+((m.getAttrib() != null) ? ", "+m.getAttrib() : ""));
								//Посчитать сколько уже выработано по этому заказу
								// Идем в обратную сторону, выбрать все id коробок по этим заказам
								bIdList.clear();
								bIdList = boxRepository.findByMasterDataId(m.getId()).stream().map(Box::getId).collect(Collectors.toList());//id
								if (!bIdList.isEmpty()) {
									int boxCount = bIdList.size();//колво коробок уже принятых на производстве. Для других операций нужно считать по другому.
									
									bmIdList.clear();

									//выбираем Ид для последующего выбора партбоксов
									bmIdList = boxMovesRepository.findByOperationIdAndBoxIdIn(OperationId,bIdList).stream().map(BoxMove::getId).collect(Collectors.toList());
									if (OperationId != 1) boxCount = bmIdList.size();
									
									if (!bmIdList.isEmpty()) {

										int pairCount = partBoxRepository.findByBoxMoveIdIn(bmIdList).stream().mapToInt(PartBox::getQuantity).sum();
										int pairCountByDoc = partBoxRepository.findByOutDocIdAndBoxMoveIdIn(outDocId,bmIdList).stream().mapToInt(PartBox::getQuantity).sum();
										lastPairQuantitySum = lastPairQuantitySum + pairCountByDoc;
										int boxCountByDoc = (int)partBoxRepository.findByOutDocIdAndBoxMoveIdIn(outDocId,bmIdList).stream().count();
										lastBoxQuantitySum = lastBoxQuantitySum + boxCountByDoc;
										
										//public OrderByOutDoc(Long id, String order, String nomenklature, String customer, int ordered_box, int ordered_pair,
										//int done_box, int done_pair, int rest_box, int rest_pair)
										OrderByOutDoc e = new OrderByOutDoc (m.getId(), m.getOrderText(), m.getNomenklature()+((m.getAttrib() != null) ? ", "+m.getAttrib() : ""), m.getCustomer(), 
												(m.getCountBoxInOrder() < boxCount) ? boxCount : m.getCountBoxInOrder(), m.getCountProdInOrder(),
												boxCount,pairCount,
												(((m.getCountBoxInOrder() < boxCount) ? boxCount : m.getCountBoxInOrder())-boxCount),(m.getCountProdInOrder()-pairCount),
												boxCountByDoc, pairCountByDoc, 0, 0);
										result.add(e);
										
									}

								}
							}
							//OrderByOutDoc intRes = new OrderByOutDoc(lastPairQuantitySum);
							OrderByOutDoc e = result.get(0);

							//public OrderByOutDoc(Long id, String order, String nomenklature, String customer, int ordered_box, int ordered_pair,
							//		int done_box, int done_pair, int rest_box, int rest_pair, int last_box, int last_pair, int lastPairQuantitySum)
							
							result.set(0, new OrderByOutDoc(e.getId(), e.getOrder(), e.getNomenklature(), e.getCustomer(), e.getOrdered_box(), e.getOrdered_pair(),
									e.getDone_box(),e.getDone_pair(), e.getRest_box(), e.getRest_pair(),
									e.getLast_box(),e.getLast_pair(), 
									lastBoxQuantitySum, lastPairQuantitySum));
						}
					}
				}
				else {
					//(Long id, String order, String nomenklature, String customer, int ordered_box, int ordered_pair,
					//		int done_box, int done_pair, int rest_box, int rest_pair, int last_box, int last_pair, int lastPairQuantitySum)
					
					OrderByOutDoc e = new OrderByOutDoc ((long) (0), "", "", "", 0, 0,
									0, 0, 0, 0, 0, 0, 0, 0);
					result.add(e);
				}
			}


		return result;		
	}

	public String findNumberOutDoc(String id) {

		String numberOutDoc = Service.selectNumberById(id).toString() ;

		if (!(numberOutDoc).isEmpty())
			return numberOutDoc;
		else
			return "Номер накладной не найден.";
	}

	public Object getDepartmentOutDoc(String id) {

		// outdoc - user - employee - department
		String departmentName = Service.selectDepartmentById(id);
		if (!(departmentName).isEmpty())
			return departmentName;
		else
			return "Бригада не найдена.";
	}

	public Object getDateOutDoc(String id) {
		String dateOutDoc = Service.selectDateById(id);
		if (!(dateOutDoc).isEmpty())
			return dateOutDoc;
		else
			return "Дата не найдена.";
	}
	
	public 	ArrayList<OrderByOutDoc> findByOrderId (Long Id){
		//nomenklature, attrib, num_box, mov_date, mov_operation, quantity, deparment, outdoc_number, user, device
		ArrayList<OrderByOutDoc> result = new ArrayList<>();
		
		


		return result;		
	}

}
