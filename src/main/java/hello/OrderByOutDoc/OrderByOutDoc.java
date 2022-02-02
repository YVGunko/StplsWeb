package hello.OrderByOutDoc;


public class OrderByOutDoc {
	
	public OrderByOutDoc(Long id, String order, String nomenklature, String customer, int ordered_box, int ordered_pair,
			int done_box, int done_pair, int rest_box, int rest_pair, int last_box, int last_pair, int lastBoxQuantitySum, int lastPairQuantitySum) {
		super();
		this.id = id;
		this.order = order;
		this.nomenklature = nomenklature;
		this.customer = customer;
		this.ordered_box = ordered_box;
		this.ordered_pair = ordered_pair;
		this.done_box = done_box;
		this.done_pair = done_pair;
		this.rest_box = rest_box;
		this.rest_pair = rest_pair;
		this.last_box = last_box;
		this.last_pair = last_pair;
		this.lastPairQuantitySum = lastPairQuantitySum;
		this.lastBoxQuantitySum = lastBoxQuantitySum;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getNomenklature() {
		return nomenklature;
	}
	public void setNomenklature(String nomenklature) {
		this.nomenklature = nomenklature;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public int getOrdered_box() {
		return ordered_box;
	}
	public void setOrdered_box(int ordered_box) {
		this.ordered_box = ordered_box;
	}
	public int getOrdered_pair() {
		return ordered_pair;
	}
	public void setOrdered_pair(int ordered_pair) {
		this.ordered_pair = ordered_pair;
	}
	public int getDone_box() {
		return done_box;
	}
	public void setDone_box(int done_box) {
		this.done_box = done_box;
	}
	public int getDone_pair() {
		return done_pair;
	}
	public void setDone_pair(int done_pair) {
		this.done_pair = done_pair;
	}
	public int getRest_box() {
		return rest_box;
	}
	public void setRest_box(int rest_box) {
		this.rest_box = rest_box;
	}
	public int getRest_pair() {
		return rest_pair;
	}
	public void setRest_pair(int rest_pair) {
		this.rest_pair = rest_pair;
	}
	public int getLast_box() {
		return last_box;
	}
	public void setLast_box(int last_box) {
		this.last_box = last_box;
	}
	public int getLast_pair() {
		return last_pair;
	}
	public void setLast_pair(int last_pair) {
		this.last_pair = last_pair;
	}
	public Long id;
	public String order;
	public String nomenklature;
	public String customer ;
	public int ordered_box;
	public int ordered_pair;
	public int done_box;
	public int done_pair;
	public int rest_box;
	public int rest_pair;
	public int last_box;
	public int last_pair;

	public String outDocId;

	public String getOutDocId() {
		return outDocId;
	}
	public void setOutDocId(String outDocId) {
		this.outDocId = outDocId;
	}

	public int lastPairQuantitySum;
	public int getLastPairQuantitySum() {
		return lastPairQuantitySum;
	}
	public void setLastPairQuantitySum(int lastPairQuantitySum) {
		this.lastPairQuantitySum = lastPairQuantitySum;
	}
	
	public int lastBoxQuantitySum;
	public int getLastBoxQuantitySum() {
		return lastBoxQuantitySum;
	}
	public void setLastBoxQuantitySum(int lastBoxQuantitySum) {
		this.lastBoxQuantitySum = lastBoxQuantitySum;
	}
	
	public OrderByOutDoc(int lastPairQuantitySum) {
		super();
		this.lastPairQuantitySum = lastPairQuantitySum;
	}
	
}
