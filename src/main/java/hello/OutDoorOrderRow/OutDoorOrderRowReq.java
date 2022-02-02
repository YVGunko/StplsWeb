package hello.OutDoorOrderRow;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;


public class OutDoorOrderRowReq {
	/* OutDoorOrderRow((@NotNull String id, @NotNull OutDoorOrder outDoorOrder, String attribute,
	Integer number, String barcode, Product product, String size, 
	Color color, Color liner, Color rant, Color shpalt, 
	Color vstavka, 
	Color gelenok, 
	Color guba, 
	Color kabluk, 

	Color matirovka, 

	Color pechat, 
	Color proshiv, 
	Color pyatka, 
	Color sled,
	Color spoyler, 
	Color ashpalt, 
	Color other, 
	Boolean prodir, 

	Boolean difersize, 
	Boolean tert,
	Boolean frez, 
	Boolean sample)
	 */
	public OutDoorOrderRowReq(String id, String order_id, String attribute, Integer number, String barcode,
			String product_id, String sProduct, String size, 
			String color_id, String liner_id, String rant_id, String shpalt_id, 
			String sColor, String sLiner, String sRant, String sShpalt, 
			String vstavka_id, String sVstavka,
			String gelenok_id, String sGelenok, 
			String guba_id, String sGuba, 
			String kabluk_id, String sKabluk,
			String matirovka_id, String sMatirovka, 
			String pechat_id, String sPechat, 
			String proshiv_id, String sProshiv, 
			String pyatka_id, String sPyatka, 
			String sled_id,		String sSled, 
			String spoyler_id, String sSpoyler, 
			String ashpalt_id, String sAshpalt, 
			Boolean prodir, Boolean difersize, Boolean tert, Boolean frez, Boolean sample,
			String plastizol_id, String sPlastizol) {
		super();
		this.id = id;
		this.order_id = order_id;

		this.attribute = attribute;
		this.number = number;
		this.barcode = barcode;
		this.product_id = product_id;
		this.sProduct = sProduct;
		this.size = size;
		this.color_id = color_id;
		this.liner_id = liner_id;
		this.shpalt_id = shpalt_id;
		this.rant_id = rant_id;
		this.sColor = sColor;
		this.sLiner = sLiner;
		this.sShpalt = sShpalt;
		this.sRant = sRant;
		this.vstavka_id = vstavka_id;
		this.sVstavka = sVstavka;
		this.gelenok_id = gelenok_id;
		this.sGelenok = sGelenok;
		this.guba_id = guba_id;
		this.sGuba = sGuba;
		this.kabluk_id = kabluk_id;
		this.sKabluk = sKabluk;

		this.matirovka_id = matirovka_id;
		this.sMatirovka = sMatirovka;

		this.pechat_id = pechat_id;
		this.sPechat = sPechat;
		this.proshiv_id = proshiv_id;
		this.sProshiv = sProshiv;
		this.pyatka_id = pyatka_id;
		this.sPyatka = sPyatka;
		this.sled_id = sled_id;
		this.sSled = sSled;
		this.spoyler_id = spoyler_id;
		this.sSpoyler = sSpoyler;
		this.ashpalt_id = ashpalt_id;
		this.sAshpalt = sAshpalt;
		this.plastizol_id = plastizol_id;
		this.sPlastizol = sPlastizol;
		
		this.prodir = prodir;
		this.difersize = difersize;
		this.tert = tert;
		this.frez = frez;
		this.sample = sample;
	}
	public OutDoorOrderRowReq(String id, String order_id, Integer number, String barcode,
			String product_id, String sProduct, String size, 
			String color_id, String liner_id, String rant_id, String shpalt_id, 
			String vstavka_id, 
			String gelenok_id, 
			String guba_id, 
			String kabluk_id, 

			String matirovka_id, 

			String pechat_id,
			String proshiv_id, 
			String pyatka_id, 
			String sled_id,
			String spoyler_id, 
			String ashpalt_id, 

			Boolean prodir, Boolean difersize, Boolean tert, Boolean frez, Boolean sample, 
			String plastizol_id) {
		super();
		this.id = id;
		this.order_id = order_id;

		this.number = number;
		this.barcode = barcode;
		this.product_id = product_id;
		this.sProduct = sProduct;
		this.size = size;
		this.color_id = color_id;
		this.liner_id = liner_id;
		this.shpalt_id = shpalt_id;
		this.rant_id = rant_id;

		this.vstavka_id = vstavka_id;
		this.gelenok_id = gelenok_id;
		this.guba_id = guba_id;
		this.kabluk_id = kabluk_id;
		this.matirovka_id = matirovka_id;
		this.pechat_id = pechat_id;
		this.proshiv_id = proshiv_id;
		this.pyatka_id = pyatka_id;
		this.sled_id = sled_id;
		this.spoyler_id = spoyler_id;
		this.ashpalt_id = ashpalt_id;
		this.plastizol_id = plastizol_id;

		this.prodir = prodir;
		this.difersize = difersize;
		this.tert = tert;
		this.frez = frez;
		this.sample = sample;
	}

	public OutDoorOrderRowReq(String id, String order_id, String attribute, Integer number, String barcode,
			String product_id, String size, 
			String color_id, String liner_id, String rant_id, String shpalt_id, 
			String vstavka_id, 
			String gelenok_id,  
			String guba_id, 
			String kabluk_id, 
			String matirovka_id, 
			String pechat_id, 
			String proshiv_id,  
			String pyatka_id,  
			String sled_id,	 
			String spoyler_id, 
			String ashpalt_id, 
			Boolean prodir, Boolean difersize, Boolean tert, Boolean frez, Boolean sample,
			String plastizol_id) {
		super();
		this.id = id;
		this.order_id = order_id;

		this.attribute = attribute;
		this.number = number;
		this.barcode = barcode;
		this.product_id = product_id;
		this.size = size;
		this.color_id = color_id;
		this.liner_id = liner_id;
		this.shpalt_id = shpalt_id;
		this.rant_id = rant_id;
		this.vstavka_id = vstavka_id;
		this.gelenok_id = gelenok_id;
		this.guba_id = guba_id;
		this.kabluk_id = kabluk_id;
		this.matirovka_id = matirovka_id;
		this.pechat_id = pechat_id;
		this.proshiv_id = proshiv_id;
		this.pyatka_id = pyatka_id;
		this.sled_id = sled_id;
		this.spoyler_id = spoyler_id;
		this.ashpalt_id = ashpalt_id;
		this.plastizol_id = plastizol_id;
		
		this.prodir = prodir;
		this.difersize = difersize;
		this.tert = tert;
		this.frez = frez;
		this.sample = sample;
	}
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor_id() {
		return color_id;
	}

	public void setColor_id(String color_id) {
		this.color_id = color_id;
	}

	public String getLiner_id() {
		return liner_id;
	}

	public void setLiner_id(String liner_id) {
		this.liner_id = liner_id;
	}

	public String getShpalt_id() {
		return shpalt_id;
	}

	public void setShpalt_id(String shpalt_id) {
		this.shpalt_id = shpalt_id;
	}

	public String getRant_id() {
		return rant_id;
	}

	public void setRant_id(String rant_id) {
		this.rant_id = rant_id;
	}

	public String getsColor() {
		return sColor;
	}

	public void setsColor(String sColor) {
		this.sColor = sColor;
	}

	public String getsLiner() {
		return sLiner;
	}

	public void setsLiner(String sLiner) {
		this.sLiner = sLiner;
	}

	public String getsShpalt() {
		return sShpalt;
	}

	public void setsShpalt(String sShpalt) {
		this.sShpalt = sShpalt;
	}

	public String getsRant() {
		return sRant;
	}

	public void setsRant(String sRant) {
		this.sRant = sRant;
	}

	public String getVstavka_id() {
		return vstavka_id;
	}

	public void setVstavka_id(String vstavka_id) {
		this.vstavka_id = vstavka_id;
	}

	public String getsVstavka() {
		return sVstavka;
	}

	public void setsVstavka(String sVstavka) {
		this.sVstavka = sVstavka;
	}

	public String getGelenok_id() {
		return gelenok_id;
	}

	public void setGelenok_id(String gelenok_id) {
		this.gelenok_id = gelenok_id;
	}

	public String getsGelenok() {
		return sGelenok;
	}

	public void setsGelenok(String sGelenok) {
		this.sGelenok = sGelenok;
	}

	public String getGuba_id() {
		return guba_id;
	}

	public void setGuba_id(String guba_id) {
		this.guba_id = guba_id;
	}

	public String getsGuba() {
		return sGuba;
	}

	public void setsGuba(String sGuba) {
		this.sGuba = sGuba;
	}

	public String getKabluk_id() {
		return kabluk_id;
	}

	public void setKabluk_id(String kabluk_id) {
		this.kabluk_id = kabluk_id;
	}

	public String getsKabluk() {
		return sKabluk;
	}

	public void setsKabluk(String sKabluk) {
		this.sKabluk = sKabluk;
	}


	public String getMatirovka_id() {
		return matirovka_id;
	}

	public void setMatirovka_id(String matirovka_id) {
		this.matirovka_id = matirovka_id;
	}

	public String getsMatirovka() {
		return sMatirovka;
	}

	public void setsMatirovka(String sMatirovka) {
		this.sMatirovka = sMatirovka;
	}

	public String getPechat_id() {
		return pechat_id;
	}

	public void setPechat_id(String pechat_id) {
		this.pechat_id = pechat_id;
	}

	public String getsPechat() {
		return sPechat;
	}

	public void setsPechat(String sPechat) {
		this.sPechat = sPechat;
	}

	public String getProshiv_id() {
		return proshiv_id;
	}

	public void setProshiv_id(String proshiv_id) {
		this.proshiv_id = proshiv_id;
	}

	public String getsProshiv() {
		return sProshiv;
	}

	public void setsProshiv(String sProshiv) {
		this.sProshiv = sProshiv;
	}

	public String getPyatka_id() {
		return pyatka_id;
	}

	public void setPyatka_id(String pyatka_id) {
		this.pyatka_id = pyatka_id;
	}

	public String getsPyatka() {
		return sPyatka;
	}

	public void setsPyatka(String sPyatka) {
		this.sPyatka = sPyatka;
	}

	public String getSled_id() {
		return sled_id;
	}

	public void setSled_id(String sled_id) {
		this.sled_id = sled_id;
	}

	public String getsSled() {
		return sSled;
	}

	public void setsSled(String sSled) {
		this.sSled = sSled;
	}

	public String getSpoyler_id() {
		return spoyler_id;
	}

	public void setSpoyler_id(String spoyler_id) {
		this.spoyler_id = spoyler_id;
	}

	public String getsSpoyler() {
		return sSpoyler;
	}

	public void setsSpoyler(String sSpoyler) {
		this.sSpoyler = sSpoyler;
	}

	public String getAshpalt_id() {
		return ashpalt_id;
	}

	public void setAshpalt_id(String ashpalt_id) {
		this.ashpalt_id = ashpalt_id;
	}
	public String getsAshpalt() {
		return sAshpalt;
	}

	public void setsAshpalt(String sAshpalt) {
		this.sAshpalt = sAshpalt;
	}

	public Boolean getProdir() {
		return prodir;
	}

	public void setProdir(Boolean prodir) {
		this.prodir = prodir;
	}


	public Boolean getDifersize() {
		return difersize;
	}

	public void setDifersize(Boolean difersize) {
		this.difersize = difersize;
	}

	public Boolean getTert() {
		return tert;
	}

	public void setTert(Boolean tert) {
		this.tert = tert;
	}

	public Boolean getFrez() {
		return frez;
	}

	public void setFrez(Boolean frez) {
		this.frez = frez;
	}

	public Boolean getSample() {
		return sample;
	}

	public void setSample(Boolean sample) {
		this.sample = sample;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public OutDoorOrderRowReq(@NotNull String id, String order_id, String attribute, Integer number, String barcode, String product_id) {
		super();
		this.id = id;
		this.order_id = order_id;
		this.attribute = attribute;
		this.number = number;
		this.barcode = barcode;
		this.product_id = product_id;
	}

	@JsonProperty("id")
	public String id;	
	@JsonProperty("order_id")
	public String order_id;	
	@JsonProperty("attribute")
	public String attribute;	
	@JsonProperty("number")
	public Integer number;	
	@JsonProperty("barcode")
	public String barcode;
	
	@JsonProperty("product_id")
	public String product_id;
	@JsonProperty("sProduct")
	public String sProduct;
	public String getsProduct() {
		return sProduct;
	}
	public void setsProduct(String sProduct) {
		this.sProduct = sProduct;
	}

	@JsonProperty("size")
	public String size;
	@JsonProperty("color_id")
	public String color_id;
	@JsonProperty("liner_id")
	public String liner_id;
	@JsonProperty("shpalt_id")
	public String shpalt_id;
	@JsonProperty("rant_id")
	public String rant_id;
	@JsonProperty("sColor")
	public String sColor;
	@JsonProperty("sLiner")
	public String sLiner;
	@JsonProperty("sShpalt")
	public String sShpalt;
	@JsonProperty("sRant")
	public String sRant;
	//***Атрибут
	@JsonProperty("vstavka_id")
	public String vstavka_id ;
	@JsonProperty("sVstavka")
	public String sVstavka ;
	@JsonProperty("gelenok_id")
	public String gelenok_id ;
	@JsonProperty("sGelenok")
	public String sGelenok ;
	@JsonProperty("guba_id")
	public String guba_id ;
	@JsonProperty("sGuba")
	public String sGuba ;	
	@JsonProperty("kabluk_id")
	public String kabluk_id ;
	@JsonProperty("sKabluk")
	public String sKabluk ;	
	
	@JsonProperty("matirovka_id")
	public String matirovka_id ;
	@JsonProperty("sMatirovka")
	public String sMatirovka ;	

	@JsonProperty("pechat_id")
	public String pechat_id ;
	@JsonProperty("sPechat")
	public String sPechat ;		
	@JsonProperty("proshiv_id")
	public String proshiv_id ;
	@JsonProperty("sProshiv")
	public String sProshiv ;	
	@JsonProperty("pyatka_id")
	public String pyatka_id ;
	@JsonProperty("sPyatka")
	public String sPyatka ;	
	@JsonProperty("sled_id")
	public String sled_id ;
	@JsonProperty("sSled")
	public String sSled ;	
	@JsonProperty("spoyler_id")
	public String spoyler_id ;
	@JsonProperty("sSpoyler")
	public String sSpoyler ;	
	@JsonProperty("ashpalt_id")
	public String ashpalt_id ;
	@JsonProperty("sAshpalt")
	public String sAshpalt;
	@JsonProperty("plastizol_id")
	public String plastizol_id ;
	@JsonProperty("sPlastizol")
	public String sPlastizol;

	
	@JsonProperty("prodir")
	public Boolean prodir=false;

	@JsonProperty("difersize")
	public Boolean difersize=false;
	@JsonProperty("tert")
	public Boolean tert=false;
	@JsonProperty("frez")
	public Boolean frez=false;
	@JsonProperty("sample")
	public Boolean sample=false;
	
	public OutDoorOrderRowReq() {
		super();
	}
	public String getPlastizol_id() {
		return plastizol_id;
	}
	public void setPlastizol_id(String plastizol_id) {
		this.plastizol_id = plastizol_id;
	}
	public String getsPlastizol() {
		return sPlastizol;
	}
	public void setsPlastizol(String sPlastizol) {
		this.sPlastizol = sPlastizol;
	}



}
