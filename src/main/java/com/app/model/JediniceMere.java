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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * JediniceMere generated by hbm2java
 */
@Entity
@Table(name = "jedinice_mere", catalog = "pinf2016")
public class JediniceMere implements java.io.Serializable {

	private Long idJediniceMere;
	private String oznakaJediniceMere;
	private String nazivJediniceMere;
	private Set<KatalogRobeIUsluga> katalogRobeIUslugas = new HashSet<KatalogRobeIUsluga>(0);
	private Set<StavkeNarudzbe> stavkeNarudzbes = new HashSet<StavkeNarudzbe>(0);

	public JediniceMere() {
	}

	public JediniceMere(String oznakaJediniceMere, String nazivJediniceMere) {
		this.oznakaJediniceMere = oznakaJediniceMere;
		this.nazivJediniceMere = nazivJediniceMere;
	}

	public JediniceMere(String oznakaJediniceMere, String nazivJediniceMere,
			Set<KatalogRobeIUsluga> katalogRobeIUslugas, Set<StavkeNarudzbe> stavkeNarudzbes) {
		this.oznakaJediniceMere = oznakaJediniceMere;
		this.nazivJediniceMere = nazivJediniceMere;
		this.katalogRobeIUslugas = katalogRobeIUslugas;
		this.stavkeNarudzbes = stavkeNarudzbes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID_JEDINICE_MERE", unique = true, nullable = false)
	public Long getIdJediniceMere() {
		return this.idJediniceMere;
	}

	public void setIdJediniceMere(Long idJediniceMere) {
		this.idJediniceMere = idJediniceMere;
	}

	@Column(name = "OZNAKA_JEDINICE_MERE", nullable = false, length = 3)
	public String getOznakaJediniceMere() {
		return this.oznakaJediniceMere;
	}

	public void setOznakaJediniceMere(String oznakaJediniceMere) {
		this.oznakaJediniceMere = oznakaJediniceMere;
	}

	@Column(name = "NAZIV_JEDINICE_MERE", nullable = false, length = 100)
	public String getNazivJediniceMere() {
		return this.nazivJediniceMere;
	}

	public void setNazivJediniceMere(String nazivJediniceMere) {
		this.nazivJediniceMere = nazivJediniceMere;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jediniceMere")
	public Set<KatalogRobeIUsluga> getKatalogRobeIUslugas() {
		return this.katalogRobeIUslugas;
	}

	public void setKatalogRobeIUslugas(Set<KatalogRobeIUsluga> katalogRobeIUslugas) {
		this.katalogRobeIUslugas = katalogRobeIUslugas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jediniceMere")
	public Set<StavkeNarudzbe> getStavkeNarudzbes() {
		return this.stavkeNarudzbes;
	}

	public void setStavkeNarudzbes(Set<StavkeNarudzbe> stavkeNarudzbes) {
		this.stavkeNarudzbes = stavkeNarudzbes;
	}

}
