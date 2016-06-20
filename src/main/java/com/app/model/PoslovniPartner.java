package com.app.model;
// Generated Jun 20, 2016 4:41:29 PM by Hibernate Tools 5.1.0.Alpha1

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

/**
 * PoslovniPartner generated by hbm2java
 */
@Entity
@Table(name = "poslovni_partner", catalog = "pinf2016")
public class PoslovniPartner implements java.io.Serializable {

	private Long idPartnerstva;
	private Preduzece preduzeceByIdPreduzeca;
	private Preduzece preduzeceByIdPartnera;
	private char vrsta;
	private Set<Narudzba> narudzbas = new HashSet<Narudzba>(0);
	private Set<FakturaOtpremnica> fakturaOtpremnicas = new HashSet<FakturaOtpremnica>(0);

	public PoslovniPartner() {
	}

	public PoslovniPartner(Preduzece preduzeceByIdPreduzeca, Preduzece preduzeceByIdPartnera, char vrsta) {
		this.preduzeceByIdPreduzeca = preduzeceByIdPreduzeca;
		this.preduzeceByIdPartnera = preduzeceByIdPartnera;
		this.vrsta = vrsta;
	}

	public PoslovniPartner(Preduzece preduzeceByIdPreduzeca, Preduzece preduzeceByIdPartnera, char vrsta,
			Set<Narudzba> narudzbas, Set<FakturaOtpremnica> fakturaOtpremnicas) {
		this.preduzeceByIdPreduzeca = preduzeceByIdPreduzeca;
		this.preduzeceByIdPartnera = preduzeceByIdPartnera;
		this.vrsta = vrsta;
		this.narudzbas = narudzbas;
		this.fakturaOtpremnicas = fakturaOtpremnicas;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID_PARTNERSTVA", unique = true, nullable = false)
	public Long getIdPartnerstva() {
		return this.idPartnerstva;
	}

	public void setIdPartnerstva(Long idPartnerstva) {
		this.idPartnerstva = idPartnerstva;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PREDUZECA", nullable = false)
	public Preduzece getPreduzeceByIdPreduzeca() {
		return this.preduzeceByIdPreduzeca;
	}

	public void setPreduzeceByIdPreduzeca(Preduzece preduzeceByIdPreduzeca) {
		this.preduzeceByIdPreduzeca = preduzeceByIdPreduzeca;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PARTNERA", nullable = false)
	public Preduzece getPreduzeceByIdPartnera() {
		return this.preduzeceByIdPartnera;
	}

	public void setPreduzeceByIdPartnera(Preduzece preduzeceByIdPartnera) {
		this.preduzeceByIdPartnera = preduzeceByIdPartnera;
	}

	@Column(name = "VRSTA", nullable = false, length = 1)
	public char getVrsta() {
		return this.vrsta;
	}

	public void setVrsta(char vrsta) {
		this.vrsta = vrsta;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "poslovniPartner")
	public Set<Narudzba> getNarudzbas() {
		return this.narudzbas;
	}

	public void setNarudzbas(Set<Narudzba> narudzbas) {
		this.narudzbas = narudzbas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "poslovniPartner")
	public Set<FakturaOtpremnica> getFakturaOtpremnicas() {
		return this.fakturaOtpremnicas;
	}

	public void setFakturaOtpremnicas(Set<FakturaOtpremnica> fakturaOtpremnicas) {
		this.fakturaOtpremnicas = fakturaOtpremnicas;
	}

}
