package hello.MasterData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqByQr {
	public ReqByQr(String division_code, String barcode, String attribute,
			String product_id, String sProduct, String size, String color_id, String sColor, String rant_id,
			String sLiner, String liner_id, String sRant, String shpalt_id, String sShpalt,
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
			Boolean prodir, Boolean difersize, Boolean tert, Boolean frez, Boolean sample, String other) {
		super();

		this.division_code = division_code;
		this.barcode = barcode;
		this.attribute = attribute;
		this.product_id = product_id;
		this.sProduct = sProduct;
		this.size = size;
		this.color_id = color_id;
		this.sColor = sColor;
		this.rant_id = rant_id;
		this.sLiner = sLiner;
		this.liner_id = liner_id;
		this.sRant = sRant;
		this.shpalt_id = shpalt_id;
		this.sShpalt = sShpalt;
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
		this.prodir = prodir;
		this.difersize = difersize;
		this.tert = tert;
		this.frez = frez;
		this.sample = sample;
		this.other = other;
	}

	public ReqByQr() {
		super();
	}
	
	@JsonProperty("division_code")
	public String division_code;
	
	@JsonProperty("barcode")
	public String barcode;
	
	@JsonProperty("attribute")
	public String attribute;
	
	@JsonProperty("product_id")
	public String product_id;
	@JsonProperty("sProduct")
	public String sProduct;
	
	@JsonProperty("size")
	public String size;
	
	@JsonProperty("color_id")
	public String color_id;
	@JsonProperty("sColor")
	public String sColor;

	@JsonProperty("rant_id")
	public String rant_id;
	@JsonProperty("sLiner")
	public String sLiner;
	
	@JsonProperty("liner_id")
	public String liner_id;
	@JsonProperty("sRant")
	public String sRant;
	
	@JsonProperty("shpalt_id")
	public String shpalt_id;
	@JsonProperty("sShpalt")
	public String sShpalt;
	
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
	public String sAshpalt ;
	
	@JsonProperty("other")
	public String other ;
	
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
}
