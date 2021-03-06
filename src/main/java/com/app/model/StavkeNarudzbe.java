package com.app.model;
// Generated Jun 20, 2016 4:41:29 PM by Hibernate Tools 5.1.0.Alpha1

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * StavkeNarudzbe generated by hbm2java
 */
@Entity
@Table(name = "stavke_narudzbe", catalog = "pinf2016")
public class StavkeNarudzbe implements java.io.Serializable {

	private Long idStavkeNarudzbe;
	private JediniceMere jediniceMere;
	private KatalogRobeIUsluga katalogRobeIUsluga;
	private Narudzba narudzba;
	private int kolicinaStavkeNarudzbenice;
	private BigDecimal cenaBezPdvAStavkeNarudzbenice;
	private BigDecimal iznosStavkeNarudzbenice;

	public StavkeNarudzbe() {
	}

	public StavkeNarudzbe(JediniceMere jediniceMere, KatalogRobeIUsluga katalogRobeIUsluga, Narudzba narudzba,
			int kolicinaStavkeNarudzbenice, BigDecimal cenaBezPdvAStavkeNarudzbenice,
			BigDecimal iznosStavkeNarudzbenice) {
		this.jediniceMere = jediniceMere;
		this.katalogRobeIUsluga = katalogRobeIUsluga;
		this.narudzba = narudzba;
		this.kolicinaStavkeNarudzbenice = kolicinaStavkeNarudzbenice;
		this.cenaBezPdvAStavkeNarudzbenice = cenaBezPdvAStavkeNarudzbenice;
		this.iznosStavkeNarudzbenice = iznosStavkeNarudzbenice;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID_STAVKE_NARUDZBE", unique = true, nullable = false)
	public Long getIdStavkeNarudzbe() {
		return this.idStavkeNarudzbe;
	}

	public void setIdStavkeNarudzbe(Long idStavkeNarudzbe) {
		this.idStavkeNarudzbe = idStavkeNarudzbe;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_JEDINICE_MERE", nullable = false)
	public JediniceMere getJediniceMere() {
		return this.jediniceMere;
	}

	public void setJediniceMere(JediniceMere jediniceMere) {
		this.jediniceMere = jediniceMere;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ARTIKLA", nullable = false)
	public KatalogRobeIUsluga getKatalogRobeIUsluga() {
		return this.katalogRobeIUsluga;
	}

	public void setKatalogRobeIUsluga(KatalogRobeIUsluga katalogRobeIUsluga) {
		this.katalogRobeIUsluga = katalogRobeIUsluga;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_NARUDZBE", nullable = false)
	public Narudzba getNarudzba() {
		return this.narudzba;
	}

	public void setNarudzba(Narudzba narudzba) {
		this.narudzba = narudzba;
	}

	@Column(name = "KOLICINA_STAVKE_NARUDZBENICE", nullable = false, precision = 6, scale = 0)
	public int getKolicinaStavkeNarudzbenice() {
		return this.kolicinaStavkeNarudzbenice;
	}

	public void setKolicinaStavkeNarudzbenice(int kolicinaStavkeNarudzbenice) {
		this.kolicinaStavkeNarudzbenice = kolicinaStavkeNarudzbenice;
	}

	@Column(name = "CENA_BEZ_PDV_A_STAVKE_NARUDZBENICE", nullable = false, precision = 15)
	public BigDecimal getCenaBezPdvAStavkeNarudzbenice() {
		return this.cenaBezPdvAStavkeNarudzbenice;
	}

	public void setCenaBezPdvAStavkeNarudzbenice(BigDecimal cenaBezPdvAStavkeNarudzbenice) {
		this.cenaBezPdvAStavkeNarudzbenice = cenaBezPdvAStavkeNarudzbenice;
	}

	@Column(name = "IZNOS_STAVKE_NARUDZBENICE", nullable = false, precision = 15)
	public BigDecimal getIznosStavkeNarudzbenice() {
		return this.iznosStavkeNarudzbenice;
	}

	public void setIznosStavkeNarudzbenice(BigDecimal iznosStavkeNarudzbenice) {
		this.iznosStavkeNarudzbenice = iznosStavkeNarudzbenice;
	}

}
