package com.app.transformers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.app.model.FakturaOtpremnica;
import com.app.model.PoreskaStopa;
import com.app.model.Porez;
import com.app.model.Preduzece;
import com.app.model.StavkeFaktureOtpremnice;
import com.app.model.invoice.Faktura;
import com.app.model.invoice.TPreduzece;
import com.app.model.invoice.TPrevoznik;
import com.app.model.invoice.TStavkaFakture;

public class FakturaOtpremnicaToFaktura {
	
	public static Faktura transform(FakturaOtpremnica fo){
		BigDecimal ukupanPDV=new BigDecimal(0);
		BigDecimal ukupanRabat=new BigDecimal(0);
		BigDecimal ukupnaPoreskaOsnova=new BigDecimal(0);
		Faktura faktura=new Faktura();
		faktura.setBroj(fo.getFaBroj()+"");
		faktura.setDatumIzdavanja(fo.getFaDatum().toString());
		faktura.setDatumValute(fo.getFaDatumValute().toString());
		faktura.setDodatneNapomene(fo.getDodatneNapomene());
		faktura.setIznos(fo.getFaIznos().setScale(2, RoundingMode.CEILING));
		faktura.setPodaciOIzdavaocu(transformPreduzeceIzdavac(fo));
		faktura.setPodaciOKupcu(transformPreduzeceKupac(fo));
		faktura.setPodaciOPrevozniku(transformPrevoznik(fo));
		faktura.setRacunIRobuIzdao(fo.getRobuIRacunIzdao());
		faktura.setRacunIRobuPrimio(fo.getRobuIRacunPreuzeo());
		faktura.setUkupnoZaUplatu(fo.getFaUkupno().setScale(2, RoundingMode.CEILING));
		
		//stavke
		List<TStavkaFakture> stavke=new ArrayList<>();
		for(StavkeFaktureOtpremnice foStavka:fo.getStavkeFaktureOtpremnices()){
			TStavkaFakture stavka=new TStavkaFakture();
			Porez porez=foStavka.getKatalogRobeIUsluga().getGrupaProizvoda().getPorez();
			BigDecimal stopaPDV=new BigDecimal(0);
			if(porez!=null){
				stopaPDV=new BigDecimal(getCurrentTaxAmmount(porez));
				stavka.setStopaPDV(stopaPDV);
			}
			//vrednost stavke=jedinicna cena* kolicina
			BigDecimal jedinicnaCena=foStavka.getJedinicnaCenaStavkeFakture();
			BigDecimal kolicina=foStavka.getKolicina();
			BigDecimal vrednostStavke=jedinicnaCena.multiply(kolicina);
			stavka.setVrednostStavke(vrednostStavke.setScale(2, RoundingMode.CEILING));
			//uracunaj pdv na vrednost 
			BigDecimal iznosPDV=vrednostStavke.multiply(stopaPDV.divide(new BigDecimal(100)));
			stavka.setIznosPDV(iznosPDV.setScale(2, RoundingMode.CEILING));
			ukupanPDV=ukupanPDV.add(iznosPDV);
			stavka.setJedinicaMere(foStavka.getKatalogRobeIUsluga().getJediniceMere().getNazivJediniceMere());
			stavka.setJedinicnaCena(foStavka.getJedinicnaCenaStavkeFakture().setScale(2, RoundingMode.CEILING));
			stavka.setKolicina(foStavka.getKolicina().setScale(2, RoundingMode.CEILING));
			stavka.setNazivRobe(foStavka.getKatalogRobeIUsluga().getNazivArtikla());
			//vrednost * (1 - rabat/100);
			BigDecimal rabat=foStavka.getRabat();
			BigDecimal poreskaOsnovica=vrednostStavke.multiply(((new BigDecimal(1)).subtract(rabat.divide(new BigDecimal(100)))));
			stavka.setPoreskaOsnovica(poreskaOsnovica.setScale(2, RoundingMode.CEILING));
			ukupnaPoreskaOsnova=ukupnaPoreskaOsnova.add(poreskaOsnovica);
			//prodajna v =vrednostStavke -iznos rabata+iznos pdv-a
			BigDecimal iznosRabata=(rabat.divide(new BigDecimal(100))).multiply(jedinicnaCena);
			ukupanRabat=ukupanRabat.add(iznosRabata);
			stavka.setProdajnaVrednost((((vrednostStavke).subtract(iznosRabata)).add(iznosPDV)).setScale(2, RoundingMode.CEILING));
			stavka.setRabat(rabat.setScale(2, RoundingMode.CEILING));
			stavka.setSifraRobe(foStavka.getKatalogRobeIUsluga().getSifraArtikla());
			stavke.add(stavka);
		}
		faktura.getStavkaFakture().addAll(stavke);
		faktura.setUkupanPDV(ukupanPDV.setScale(2, RoundingMode.CEILING));
		faktura.setUkupanRabat(ukupanRabat.setScale(2, RoundingMode.CEILING));
		faktura.setUkupnaPoreskaOsnova(ukupnaPoreskaOsnova.setScale(2, RoundingMode.CEILING));
		return faktura;
	}
	
	private static TPreduzece transformPreduzeceIzdavac(FakturaOtpremnica fo){
		TPreduzece preduzece=new TPreduzece();
		Preduzece pr=fo.getPoslovniPartner().getPreduzeceByIdPreduzeca();
		if(pr!=null){
			preduzece.setAdresa(pr.getAdresa());
			preduzece.setMaticniBroj(pr.getMaticniBroj());
			preduzece.setNaziv(pr.getNaziv());
			preduzece.setPIB(pr.getPib());
			preduzece.setTekuciRacun(pr.getTekuciRacun());
		}
		return preduzece;
	}
	
	private static TPreduzece transformPreduzeceKupac(FakturaOtpremnica fo){
		TPreduzece preduzece=new TPreduzece();
		Preduzece pr=fo.getPoslovniPartner().getPreduzeceByIdPartnera();
		if(pr!=null){
			preduzece.setAdresa(pr.getAdresa());
			preduzece.setMaticniBroj(pr.getMaticniBroj());
			preduzece.setNaziv(pr.getNaziv());
			preduzece.setPIB(pr.getPib());
			preduzece.setTekuciRacun(pr.getTekuciRacun());
		}
		return preduzece;
	}
	
	private static TPrevoznik transformPrevoznik(FakturaOtpremnica fo){
		TPrevoznik prevoznik=new TPrevoznik();
		prevoznik.setBrojKamiona(fo.getBrojKamiona());
		prevoznik.setNazivPrevoznika(fo.getPrevoznik());
		return prevoznik;
	}
	
	private static double getCurrentTaxAmmount(Porez porez) {
		BigDecimal iznosPoreza = new BigDecimal(0);
		Date datum = new Date();
		Set<PoreskaStopa> stope = porez.getPoreskaStopas();
		int i = 0;
		
		if(stope.size() > 0) {
			for (PoreskaStopa stopa : stope) {
				if(i == 0) {
					i++;
					iznosPoreza = stopa.getIznosStope();
					datum = stopa.getDatumVazenja();
				} else {
					if(stopa.getDatumVazenja().compareTo(datum) > 0) {
						iznosPoreza = stopa.getIznosStope();
					}
				}
			}
		}
		
		return iznosPoreza.doubleValue();
	}
	
}
