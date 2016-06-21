package com.app.constants;

import java.util.ArrayList;

public class TableNames {

	public static final String CENOVNIK = "Cenovnik";
	public static final String FAKTURA_OTPREMNICA = "Faktura i otpremnica";
	public static final String GRUPA_PROIZVODA = "Grupa proizvoda";
	public static final String JEDINICE_MERE = "Jedinice mere";
	public static final String KATALOG_ROBE_I_USLUGA = "Katalog robe i usluga";
	public static final String NARUDZBA = "Narudžba";
	public static final String PORESKA_STOPA = "Poreska stopa";
	public static final String POREZ = "Porez";
	public static final String POSLOVNA_GODINA = "Poslovna godina";
	public static final String POSLOVNI_PARTNER = "Poslovni partner";
	public static final String PREDUZECE = "Preduzeće";
	public static final String SIFRA_DELATNOSTI = "Šifra delatnosti";
	public static final String STAVKE_CENOVNIKA = "Stavke cenovnika";
	public static final String STAVKE_FAKTURE_OTPREMNICE = "Stavke fakture i otpremnice";
	public static final String STAVKE_NARUDZBE = "Stavke narudžbe";
	
	public static ArrayList<String> getAllTableNames() {
		ArrayList<String> names = new ArrayList<String>();
		
		names.add(CENOVNIK);
		names.add(FAKTURA_OTPREMNICA);
		names.add(GRUPA_PROIZVODA);
		names.add(JEDINICE_MERE);
		names.add(KATALOG_ROBE_I_USLUGA);
		names.add(NARUDZBA);
		names.add(PORESKA_STOPA);
		names.add(POREZ);
		names.add(POSLOVNA_GODINA);
		names.add(POSLOVNI_PARTNER);
		names.add(PREDUZECE);
		names.add(SIFRA_DELATNOSTI);
		names.add(STAVKE_CENOVNIKA);
		names.add(STAVKE_FAKTURE_OTPREMNICE);
		names.add(STAVKE_NARUDZBE);
		
		return names;
	}
	
	public static ArrayList<String> getEditableTableNames() {
		ArrayList<String> names = new ArrayList<String>();
		
		names.add(CENOVNIK);
		names.add(FAKTURA_OTPREMNICA);
		names.add(GRUPA_PROIZVODA);
		names.add(JEDINICE_MERE);
		names.add(KATALOG_ROBE_I_USLUGA);
		names.add(NARUDZBA);
		names.add(PORESKA_STOPA);
		names.add(POREZ);
		names.add(POSLOVNA_GODINA);
		names.add(POSLOVNI_PARTNER);
		names.add(PREDUZECE);
		names.add(SIFRA_DELATNOSTI);
		
		return names;
	}
}
