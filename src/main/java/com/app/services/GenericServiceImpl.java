package com.app.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.app.DTO.TableDTO;
import com.app.DTO.TableRowDTO;
import com.app.constants.TableNames;
import com.app.helpers.ConversionHelper;
import com.app.repositories.ICenovnikRepository;
import com.app.repositories.IFakturaOtpremnicaRepository;
import com.app.repositories.IGrupaProizvodaRepository;
import com.app.repositories.IJediniceMereRepository;
import com.app.repositories.IKatalogRobeIUslugaRepository;
import com.app.repositories.INarudzbaRepository;
import com.app.repositories.IPoreskaStopaRepository;
import com.app.repositories.IPorezRepository;
import com.app.repositories.IPoslovnaGodinaRepository;
import com.app.repositories.IPoslovniPartnerRepository;
import com.app.repositories.IPreduzeceRepository;
import com.app.repositories.ISifraDelatnostiRepository;
import com.app.repositories.IStavkeCenovnikaRepository;
import com.app.repositories.IStavkeFaktureOtpremniceRepository;
import com.app.repositories.IStavkeNarudzbeRepository;
import com.app.transformers.ArtikalTransformer;
import com.app.transformers.CenovnikTransformer;
import com.app.transformers.FakturaTransformer;
import com.app.transformers.GrupaProizvodaTransformer;
import com.app.transformers.ITransformer;
import com.app.transformers.JediniceMereTransformer;
import com.app.transformers.NarudzbaTransformer;
import com.app.transformers.PoreskaStopaTransformer;
import com.app.transformers.PorezTransformer;
import com.app.transformers.PoslovnaGodinaTransformer;
import com.app.transformers.PoslovniPartnerTransformer;
import com.app.transformers.PreduzeceTransformer;
import com.app.transformers.SifraDelatnostiTransformer;
import com.app.transformers.StavkeCenovnikaTransformer;
import com.app.transformers.StavkeFaktureTransformer;
import com.app.transformers.StavkeNarudzbeTransformer;
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class GenericServiceImpl implements IGenericService{
		
	@Override
	public boolean create(TableRowDTO row) {
		CrudRepository repo = getTableRepo(row.getTableName());
		ITransformer transformer = getTransformer(row.getTableName());
		HashMap<String, Object> fks = getFKs(row);
		Object entity = transformer.transformFromDTO(row, fks);
		repo.save(entity);
		return true;
	}

	@Override
	public boolean update(TableRowDTO row) {
		CrudRepository repo = getTableRepo(row.getTableName());
		ITransformer transformer = getTransformer(row.getTableName());
		HashMap<String, Object> fks = getFKs(row);
		Object entity = transformer.transformFromDTO(row, fks);
		repo.save(entity);
		return true;
	}

	@Override
	public boolean delete(Long id, String tableCode) {
		String tableName = ConversionHelper.getTableName(tableCode);
		CrudRepository repo = getTableRepo(tableName);
		repo.delete(id);
		return true;
	}

	@Override
	public TableDTO getById(Long id, String tableCode) {
		String tableName = ConversionHelper.getTableName(tableCode);
		CrudRepository repo = getTableRepo(tableName);
		Object result = repo.findOne(id);
		ITransformer tr = getTransformer(tableName);
		TableDTO dto = tr.transformToDTO(result); 
		return dto;
	}

	@Override
	public TableDTO getAll(String tableCode) {
		String tableName = ConversionHelper.getTableName(tableCode);
		CrudRepository repo = getTableRepo(tableName);
		ArrayList<Object> rows = (ArrayList<Object>) repo.findAll();
		ITransformer tr = getTransformer(tableName);
		TableDTO dto = tr.transformToDTO(rows);
		return dto;
	}
	
	private HashMap<String, Object> getFKs(TableRowDTO row) {
		HashMap<String, Object> fks = new HashMap<String, Object>();
		ArrayList<String> tableNames = TableNames.getAllTableNames();
		Object fk;
		CrudRepository repo;
		Long id;
		
		for (String fieldName : row.getFields().keySet()) {
			if(tableNames.contains(fieldName)) {
				repo = getTableRepo(fieldName);
				id = Long.parseLong(row.getFields().get(fieldName).toString());
				fk = repo.findOne(id);
				if(fk != null) {
					fks.put(fieldName, fk);
				}
			}
		}
		
		return fks;
	}
	
	private CrudRepository getTableRepo(String tableName) {
		switch(tableName) 
		{
			case TableNames.CENOVNIK: return cenovnikRepo;
			case TableNames.FAKTURA_OTPREMNICA: return fakturaRepo;
			case TableNames.GRUPA_PROIZVODA: return grupaProizvodaRepo;
			case TableNames.JEDINICE_MERE: return jediniceMereRepo;
			case TableNames.KATALOG_ROBE_I_USLUGA: return artikalRepo;
			case TableNames.NARUDZBA: return narudzbaRepo;
			case TableNames.PORESKA_STOPA: return poreskaStopaRepo;
			case TableNames.POREZ: return porezRepo;
			case TableNames.POSLOVNA_GODINA: return poslovnaGodinaRepo;
			case TableNames.POSLOVNI_PARTNER: return poslovniPartnerRepo;
			case TableNames.PREDUZECE: return preduzeceRepo;
			case TableNames.SIFRA_DELATNOSTI: return sifraDelatnostiRepo;
			case TableNames.STAVKE_CENOVNIKA: return stavkeCenovnikaRepo;
			case TableNames.STAVKE_FAKTURE_OTPREMNICE: return stavkeFaktureRepo;
			case TableNames.STAVKE_NARUDZBE: return stavkeNarudzbeRepo;
		}
		
		return null;
	}
	
	private ITransformer getTransformer(String tableName) {
		switch(tableName) 
		{
			case TableNames.CENOVNIK: return new CenovnikTransformer();
			case TableNames.FAKTURA_OTPREMNICA: return new FakturaTransformer();
			case TableNames.GRUPA_PROIZVODA: return new GrupaProizvodaTransformer();
			case TableNames.JEDINICE_MERE: return new JediniceMereTransformer();
			case TableNames.KATALOG_ROBE_I_USLUGA: return new ArtikalTransformer();
			case TableNames.NARUDZBA: return new NarudzbaTransformer();
			case TableNames.PORESKA_STOPA: return new PoreskaStopaTransformer();
			case TableNames.POREZ: return new PorezTransformer();
			case TableNames.POSLOVNA_GODINA: return new PoslovnaGodinaTransformer();
			case TableNames.POSLOVNI_PARTNER: return new PoslovniPartnerTransformer();
			case TableNames.PREDUZECE: return new PreduzeceTransformer();
			case TableNames.SIFRA_DELATNOSTI: return new SifraDelatnostiTransformer();
			case TableNames.STAVKE_CENOVNIKA: return new StavkeCenovnikaTransformer();
			case TableNames.STAVKE_FAKTURE_OTPREMNICE: return new StavkeFaktureTransformer();
			case TableNames.STAVKE_NARUDZBE: return new StavkeNarudzbeTransformer();
		}
		
		return null;
	}

	@Autowired
	private ICenovnikRepository cenovnikRepo;
	
	@Autowired
	private IFakturaOtpremnicaRepository fakturaRepo;
	
	@Autowired
	private IGrupaProizvodaRepository grupaProizvodaRepo;
	
	@Autowired
	private IJediniceMereRepository jediniceMereRepo;
	
	@Autowired
	private IKatalogRobeIUslugaRepository artikalRepo;
	
	@Autowired
	private INarudzbaRepository narudzbaRepo;
	
	@Autowired
	private IPoreskaStopaRepository poreskaStopaRepo;
	
	@Autowired
	private IPorezRepository porezRepo;
	
	@Autowired
	private IPoslovnaGodinaRepository poslovnaGodinaRepo;
	
	@Autowired
	private IPoslovniPartnerRepository poslovniPartnerRepo;
	
	@Autowired
	private IPreduzeceRepository preduzeceRepo;
	
	@Autowired
	private ISifraDelatnostiRepository sifraDelatnostiRepo;
	
	@Autowired
	private IStavkeCenovnikaRepository stavkeCenovnikaRepo;
	
	@Autowired
	private IStavkeFaktureOtpremniceRepository stavkeFaktureRepo;
	
	@Autowired
	private IStavkeNarudzbeRepository stavkeNarudzbeRepo;
	
}
