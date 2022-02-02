package hello.MasterData;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.Client.Client;
import hello.Cut.Cut;
import hello.Division.Division;
import hello.Glue.Glue;
import hello.Paint.Paint;

@Table(uniqueConstraints=@UniqueConstraint(columnNames={"orderId"}),indexes= {@Index(columnList = "archive"),
		@Index(name = "orderIdAndArchiveIndex", columnList="orderId,archive", unique = false)})
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})


@Entity
// from 1c
public class MasterData{
	public MasterData(Long id, @NotNull String orderId, @NotNull String orderText, @NotNull String customer,
			@NotNull String nomenklature, String attrib, @NotNull int countProdInOrder, @NotNull int countProdInBox,
			@NotNull int countBoxInOrder, @NotNull Date dt, @NotNull Boolean archive, @NotNull String id1c,
			@NotNull Client client, @NotNull Division division, Cut cut, Glue glue,
			Paint paint, Product product, String size, Color color, Color liner, Color rant, Color shpalt,
			Color vstavka, Color gelenok, Color guba, Color kabluk, Color matirovka, Color pechat, Color proshiv, Color pyatka, Color sled,
			Color spoyler, Color ashpalt, Boolean prodir, Boolean difersize, Boolean tert,
			Boolean frez, Boolean sample, String other, Date date1C, Color plastizol, Color plastizol2) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.orderText = orderText;
		this.customer = customer;
		this.nomenklature = nomenklature;
		this.attrib = attrib;
		this.countProdInOrder = countProdInOrder;
		this.countProdInBox = countProdInBox;
		this.countBoxInOrder = countBoxInOrder;
		this.dt = dt;
		this.date1C = date1C;
		this.archive = archive;
		this.id1C = id1c;
		this.client = client;
		this.division = division;
		this.cut = cut;
		this.glue = glue;
		this.paint = paint;
		this.product = product;
		this.size = size;
		this.color = color;
		this.liner = liner;
		this.rant = rant;
		this.shpalt = shpalt;
		this.vstavka = vstavka;
		this.gelenok = gelenok;
		this.guba = guba;
		this.kabluk = kabluk;
		this.matirovka = matirovka;
		this.pechat = pechat;
		this.proshiv = proshiv;
		this.pyatka = pyatka;
		this.sled = sled;
		this.spoyler = spoyler;
		this.ashpalt = ashpalt;
		this.prodir = prodir;
		this.difersize = difersize;
		this.tert = tert;
		this.frez = frez;
		this.sample = sample;
		this.other = other;
		this.plastizol = plastizol;
		this.plastizol2 = plastizol2;
	}
	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("_id")
	public Long id;
	@JsonProperty("_Ord_Id")
	@NotNull
	public String orderId;
	@JsonProperty("_Ord")
	@NotNull
	public String orderText;
	@JsonProperty("_Cust")
	@NotNull
	public String customer;
	@JsonProperty("_Nomen")
	@NotNull
	public String nomenklature;
	@JsonProperty("_Attrib")
	public String attrib; //dop info
	@JsonProperty("_Q_ord")
	@NotNull
	public int countProdInOrder;
	@JsonProperty("_Q_box")
	@NotNull
	public int countProdInBox;
	@JsonProperty("_N_box")
	@NotNull
	public int countBoxInOrder;	
	@JsonProperty("_DT")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Date dt = new Date();
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	public Date date1C;
	@JsonProperty("_archive")
	@NotNull
	public Boolean archive=false;
	@JsonProperty("_Zp_Id")
	@NotNull
	public String id1C;
	@NotNull
	@ManyToOne(optional = false)
	public Client client ;
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Division division ;

	public Cut getCut() {
		return cut;
	}
	public void setCut(Cut cut) {
		this.cut = cut;
	}
	public Glue getGlue() {
		return glue;
	}
	public void setGlue(Glue glue) {
		this.glue = glue;
	}
	public Paint getPaint() {
		return paint;
	}
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Cut cut ;
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Glue glue ;
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Paint paint ;

	public MasterData(String orderText, String customer, Date dt) {
		super();
		this.orderText = orderText;
		this.customer = customer;
		this.dt = dt;
	}
	public MasterData(Long id, String orderText, String customer, String nomenklature, String attrib, int countProdInOrder, Date dt) {
		super();
		this.id = id;
		this.orderText = orderText;
		this.customer = customer;
		this.nomenklature = nomenklature;
		this.attrib = attrib;
		this.countProdInOrder = countProdInOrder;
		this.dt = dt;
	}
	public MasterData(String customer, long countProdInOrder) {
		super();
		this.customer = customer;
		this.countProdInOrder = (int) countProdInOrder;
	}
	
	public MasterData(Long id, String orderId, String orderText, String customer, String nomenklature,
			String attrib, int countProdInOrder, int countBoxInOrder, int countProdInBox, Date dt, Boolean archive, 
			String id1C, Client client, Division division) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.orderText = orderText;
		this.customer = customer;
		this.nomenklature = nomenklature;
		this.attrib = attrib;
		this.countProdInOrder = countProdInOrder;
		this.countBoxInOrder = countBoxInOrder;
		this.countProdInBox = countProdInBox;
		this.dt = dt;
		this.archive = archive;
		this.id1C = id1C;
		this.client = client;
		this.division = division;
	}
	
	public MasterData(Long id, String orderId, String orderText, String customer, String nomenklature,
			String attrib, int countProdInOrder, int countBoxInOrder, int countProdInBox, Date dt, Boolean archive, 
			String id1C, Client client, Division division, Cut cut, Glue glue, Paint paint) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.orderText = orderText;
		this.customer = customer;
		this.nomenklature = nomenklature;
		this.attrib = attrib;
		this.countProdInOrder = countProdInOrder;
		this.countBoxInOrder = countBoxInOrder;
		this.countProdInBox = countProdInBox;
		this.dt = dt;
		this.archive = archive;
		this.id1C = id1C;
		this.client = client;
		this.division = division;
		this.cut = cut;
		this.glue = glue;
		this.paint = paint;
	}
	
	public MasterData(Long id,   int countProdInBox, Cut cut, Glue glue, Paint paint) {
		super();
		this.id = id;
		this.countProdInBox = countProdInBox;
		this.cut = cut;
		this.glue = glue;
		this.paint = paint;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getLiner() {
		return liner;
	}
	public void setLiner(Color liner) {
		this.liner = liner;
	}
	public Color getRant() {
		return rant;
	}
	public void setRant(Color rant) {
		this.rant = rant;
	}
	public Color getShpalt() {
		return shpalt;
	}
	public void setShpalt(Color shpalt) {
		this.shpalt = shpalt;
	}
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public MasterData() {
		super();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderText() {
		return orderText;
	}

	public void setOrderText(String orderText) {
		this.orderText = orderText;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getNomenklature() {
		return nomenklature;
	}

	public void setNomenklature(String nomenklature) {
		this.nomenklature = nomenklature;
	}

	public String getAttrib() {
		return attrib;
	}

	public void setAttrib(String attrib) {
		this.attrib = attrib;
	}

	public int getCountProdInOrder() {
		return countProdInOrder;
	}

	public void setCountProdInOrder(int countProdInOrder) {
		this.countProdInOrder = countProdInOrder;
	}

	public int getCountBoxInOrder() {
		return countBoxInOrder;
	}

	public void setCountBoxInOrder(int countBoxInOrder) {
		this.countBoxInOrder = countBoxInOrder;
	}

	public int getCountProdInBox() {
		return countProdInBox;
	}

	public void setCountProdInBox(int countProdInBox) {
		this.countProdInBox = countProdInBox;
	}

	public Date getDt() {
		return dt;
	}
	
	public Boolean getArchive() {
		return archive;
	}
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}
	public String getid1C() {

		return id1C;
	}
	public void setid1C(String id1C) {
		this.id1C = id1C;
	}
	public Date getDate1C() {
		return date1C;
	}
	public void setDate1C(Date date1c) {
		date1C = date1c;
	}

	//***Номенклатура
	@JsonProperty("product")
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Product product ;
	
	@JsonProperty("size")
	@ColumnDefault("0")
	public String size ;
	
	@JsonProperty("color")
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Color color ;
	
	@JsonProperty("liner")
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Color liner ;
	
	@JsonProperty("rant")
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Color rant ;
	
	@JsonProperty("shpalt")
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Color shpalt ;
	
	//***Атрибут
			@JsonProperty("vstavka")
			@ManyToOne(optional = false)
			@ColumnDefault("0")
			public Color vstavka ;
			
			@JsonProperty("gelenok")
			@ManyToOne(optional = false)
			@ColumnDefault("0")
			public Color gelenok ;
			
			@JsonProperty("guba")
			@ManyToOne(optional = false)
			@ColumnDefault("0")
			public Color guba ;
			
			@JsonProperty("kabluk")
			@ManyToOne(optional = false)
			@ColumnDefault("0")
			public Color kabluk ;
			
			@JsonProperty("matirovka")
			@ManyToOne(optional = false)
			@ColumnDefault("0")
			public Color matirovka ;
			
			@JsonProperty("pechat")
			@ManyToOne(optional = false)
			@ColumnDefault("0")
			public Color pechat ;
			
			@JsonProperty("proshiv")
			@ManyToOne(optional = false)
			@ColumnDefault("0")
			public Color proshiv ;
			
			@JsonProperty("pyatka")
			@ManyToOne(optional = false)
			public Color pyatka ;
			
			@JsonProperty("sled")
			@ManyToOne(optional = false)
			public Color sled ;
			
			@JsonProperty("spoyler")
			@ManyToOne(optional = false)
			public Color spoyler ;
			
			@JsonProperty("ashpalt")
			@ManyToOne(optional = false)
			public Color ashpalt ;
			
			@JsonProperty("plastizol")
			@ManyToOne(optional = false)
			public Color plastizol ;
			
			@JsonProperty("plastizol2")
			@ManyToOne(optional = false)
			public Color plastizol2 ;
			
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
			@JsonProperty("other")
			public String other;

			public String getOther() {
				return other;
			}
			public void setOther(String other) {
				this.other = other;
			}
			public Color getVstavka() {
				return vstavka;
			}
			public void setVstavka(Color vstavka) {
				this.vstavka = vstavka;
			}
			public Color getGelenok() {
				return gelenok;
			}
			public void setGelenok(Color gelenok) {
				this.gelenok = gelenok;
			}
			public Color getGuba() {
				return guba;
			}
			public void setGuba(Color guba) {
				this.guba = guba;
			}
			public Color getKabluk() {
				return kabluk;
			}
			public void setKabluk(Color kabluk) {
				this.kabluk = kabluk;
			}
			public Color getMatirovka() {
				return matirovka;
			}
			public void setMatirovka(Color matirovka) {
				this.matirovka = matirovka;
			}
			public Color getPechat() {
				return pechat;
			}
			public void setPechat(Color pechat) {
				this.pechat = pechat;
			}
			public Color getProshiv() {
				return proshiv;
			}
			public void setProshiv(Color proshiv) {
				this.proshiv = proshiv;
			}
			public Color getPyatka() {
				return pyatka;
			}
			public void setPyatka(Color pyatka) {
				this.pyatka = pyatka;
			}
			public Color getSled() {
				return sled;
			}
			public void setSled(Color sled) {
				this.sled = sled;
			}
			public Color getSpoyler() {
				return spoyler;
			}
			public void setSpoyler(Color spoyler) {
				this.spoyler = spoyler;
			}
			public Color getAshpalt() {
				return ashpalt;
			}
			public void setAshpalt(Color ashpalt) {
				this.ashpalt = ashpalt;
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
			public Color getPlastizol() {
				return plastizol;
			}
			public void setPlastizol(Color plastizol) {
				this.plastizol = plastizol;
			}
			public Color getPlastizol2() {
				return plastizol2;
			}
			public void setPlastizol2(Color plastizol2) {
				this.plastizol2 = plastizol2;
			}

}
