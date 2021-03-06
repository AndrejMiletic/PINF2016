package com.app.model;
// Generated Jun 20, 2016 4:41:29 PM by Hibernate Tools 5.1.0.Alpha1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FakturaOtpremnica generated by hbm2java
 */
@Entity
@Table(name = "faktura_otpremnica", catalog = "pinf2016")
public class FakturaOtpremnica implements java.io.Serializable {

	private Long idFaktureOtpremnice;
	private Narudzba narudzba;
	private PoslovnaGodina poslovnaGodina;
	private PoslovniPartner poslovniPartner;
	private int faBroj;
	private char faTip;
	private Date faDatum;
	private Date faDatumValute;
	private Date datumObracuna;
	private BigDecimal faUkupno;
	private BigDecimal faRabat;
	private BigDecimal faPorez;
	private BigDecimal faIznos;
	private String faTekracun;
	private String faPoziv;
	private char statusFakture;
	private String dodatneNapomene;
	private String adresaIsporuke;
	private String brojKamiona;
	private String prevoznik;
	private String robuIRacunIzdao;
	private String robuIRacunPreuzeo;
	private Set<StavkeFaktureOtpremnice> stavkeFaktureOtpremnices = new HashSet<StavkeFaktureOtpremnice>(0);

	public FakturaOtpremnica() {
	}

	public FakturaOtpremnica(PoslovnaGodina poslovnaGodina, PoslovniPartner poslovniPartner, int faBroj, char faTip,
			Date faDatum, Date faDatumValute, BigDecimal faUkupno, BigDecimal faPorez, BigDecimal faIznos,
			String faTekracun, char statusFakture) {
		this.poslovnaGodina = poslovnaGodina;
		this.poslovniPartner = poslovniPartner;
		this.faBroj = faBroj;
		this.faTip = faTip;
		this.faDatum = faDatum;
		this.faDatumValute = faDatumValute;
		this.faUkupno = faUkupno;
		this.faPorez = faPorez;
		this.faIznos = faIznos;
		this.faTekracun = faTekracun;
		this.statusFakture = statusFakture;
	}

	public FakturaOtpremnica(Narudzba narudzba, PoslovnaGodina poslovnaGodina, PoslovniPartner poslovniPartner,
			int faBroj, char faTip, Date faDatum, Date faDatumValute, Date datumObracuna, BigDecimal faUkupno,
			BigDecimal faRabat, BigDecimal faPorez, BigDecimal faIznos, String faTekracun, String faPoziv,
			char statusFakture, String dodatneNapomene, String adresaIsporuke, String brojKamiona, String prevoznik,
			String robuIRacunIzdao, String robuIRacunPreuzeo, Set<StavkeFaktureOtpremnice> stavkeFaktureOtpremnices) {
		this.narudzba = narudzba;
		this.poslovnaGodina = poslovnaGodina;
		this.poslovniPartner = poslovniPartner;
		this.faBroj = faBroj;
		this.faTip = faTip;
		this.faDatum = faDatum;
		this.faDatumValute = faDatumValute;
		this.datumObracuna = datumObracuna;
		this.faUkupno = faUkupno;
		this.faRabat = faRabat;
		this.faPorez = faPorez;
		this.faIznos = faIznos;
		this.faTekracun = faTekracun;
		this.faPoziv = faPoziv;
		this.statusFakture = statusFakture;
		this.dodatneNapomene = dodatneNapomene;
		this.adresaIsporuke = adresaIsporuke;
		this.brojKamiona = brojKamiona;
		this.prevoznik = prevoznik;
		this.robuIRacunIzdao = robuIRacunIzdao;
		this.robuIRacunPreuzeo = robuIRacunPreuzeo;
		this.stavkeFaktureOtpremnices = stavkeFaktureOtpremnices;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID_FAKTURE_OTPREMNICE", unique = true, nullable = false)
	public Long getIdFaktureOtpremnice() {
		return this.idFaktureOtpremnice;
	}

	public void setIdFaktureOtpremnice(Long idFaktureOtpremnice) {
		this.idFaktureOtpremnice = idFaktureOtpremnice;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_NARUDZBE")
	public Narudzba getNarudzba() {
		return this.narudzba;
	}

	public void setNarudzba(Narudzba narudzba) {
		this.narudzba = narudzba;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_POSLOVNE_GODINE", nullable = false)
	public PoslovnaGodina getPoslovnaGodina() {
		return this.poslovnaGodina;
	}

	public void setPoslovnaGodina(PoslovnaGodina poslovnaGodina) {
		this.poslovnaGodina = poslovnaGodina;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PARTNERSTVA", nullable = false)
	public PoslovniPartner getPoslovniPartner() {
		return this.poslovniPartner;
	}

	public void setPoslovniPartner(PoslovniPartner poslovniPartner) {
		this.poslovniPartner = poslovniPartner;
	}

	@Column(name = "FA_BROJ", nullable = false, precision = 6, scale = 0)
	public int getFaBroj() {
		return this.faBroj;
	}

	public void setFaBroj(int faBroj) {
		this.faBroj = faBroj;
	}

	@Column(name = "FA_TIP", nullable = false, length = 1)
	public char getFaTip() {
		return this.faTip;
	}

	public void setFaTip(char faTip) {
		this.faTip = faTip;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FA_DATUM", nullable = false, length = 10)
	public Date getFaDatum() {
		return this.faDatum;
	}

	public void setFaDatum(Date faDatum) {
		this.faDatum = faDatum;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FA_DATUM_VALUTE", nullable = false, length = 10)
	public Date getFaDatumValute() {
		return this.faDatumValute;
	}

	public void setFaDatumValute(Date faDatumValute) {
		this.faDatumValute = faDatumValute;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATUM_OBRACUNA", length = 10)
	public Date getDatumObracuna() {
		return this.datumObracuna;
	}

	public void setDatumObracuna(Date datumObracuna) {
		this.datumObracuna = datumObracuna;
	}

	@Column(name = "FA_UKUPNO", nullable = false, precision = 15)
	public BigDecimal getFaUkupno() {
		return this.faUkupno;
	}

	public void setFaUkupno(BigDecimal faUkupno) {
		this.faUkupno = faUkupno;
	}

	@Column(name = "FA_RABAT", precision = 15)
	public BigDecimal getFaRabat() {
		return this.faRabat;
	}

	public void setFaRabat(BigDecimal faRabat) {
		this.faRabat = faRabat;
	}

	@Column(name = "FA_POREZ", nullable = false, precision = 15)
	public BigDecimal getFaPorez() {
		return this.faPorez;
	}

	public void setFaPorez(BigDecimal faPorez) {
		this.faPorez = faPorez;
	}

	@Column(name = "FA_IZNOS", nullable = false, precision = 15)
	public BigDecimal getFaIznos() {
		return this.faIznos;
	}

	public void setFaIznos(BigDecimal faIznos) {
		this.faIznos = faIznos;
	}

	@Column(name = "FA_TEKRACUN", nullable = false, length = 50)
	public String getFaTekracun() {
		return this.faTekracun;
	}

	public void setFaTekracun(String faTekracun) {
		this.faTekracun = faTekracun;
	}

	@Column(name = "FA_POZIV", length = 50)
	public String getFaPoziv() {
		return this.faPoziv;
	}

	public void setFaPoziv(String faPoziv) {
		this.faPoziv = faPoziv;
	}

	@Column(name = "STATUS_FAKTURE", nullable = false, length = 1)
	public char getStatusFakture() {
		return this.statusFakture;
	}

	public void setStatusFakture(char statusFakture) {
		this.statusFakture = statusFakture;
	}

	@Column(name = "DODATNE_NAPOMENE", length = 200)
	public String getDodatneNapomene() {
		return this.dodatneNapomene;
	}

	public void setDodatneNapomene(String dodatneNapomene) {
		this.dodatneNapomene = dodatneNapomene;
	}

	@Column(name = "ADRESA_ISPORUKE", length = 50)
	public String getAdresaIsporuke() {
		return this.adresaIsporuke;
	}

	public void setAdresaIsporuke(String adresaIsporuke) {
		this.adresaIsporuke = adresaIsporuke;
	}

	@Column(name = "BROJ_KAMIONA", length = 10)
	public String getBrojKamiona() {
		return this.brojKamiona;
	}

	public void setBrojKamiona(String brojKamiona) {
		this.brojKamiona = brojKamiona;
	}

	@Column(name = "PREVOZNIK", length = 50)
	public String getPrevoznik() {
		return this.prevoznik;
	}

	public void setPrevoznik(String prevoznik) {
		this.prevoznik = prevoznik;
	}

	@Column(name = "ROBU_I_RACUN_IZDAO", length = 30)
	public String getRobuIRacunIzdao() {
		return this.robuIRacunIzdao;
	}

	public void setRobuIRacunIzdao(String robuIRacunIzdao) {
		this.robuIRacunIzdao = robuIRacunIzdao;
	}

	@Column(name = "ROBU_I_RACUN_PREUZEO", length = 30)
	public String getRobuIRacunPreuzeo() {
		return this.robuIRacunPreuzeo;
	}

	public void setRobuIRacunPreuzeo(String robuIRacunPreuzeo) {
		this.robuIRacunPreuzeo = robuIRacunPreuzeo;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fakturaOtpremnica")
	public Set<StavkeFaktureOtpremnice> getStavkeFaktureOtpremnices() {
		return this.stavkeFaktureOtpremnices;
	}

	public void setStavkeFaktureOtpremnices(Set<StavkeFaktureOtpremnice> stavkeFaktureOtpremnices) {
		this.stavkeFaktureOtpremnices = stavkeFaktureOtpremnices;
	}

}
