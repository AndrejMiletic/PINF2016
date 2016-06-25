package com.app.services;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.app.DTO.KifDTO;
import com.app.DTO.TableDTO;
import com.app.DTO.TableFieldDTO;
import com.app.DTO.TableRowDTO;
import com.app.constants.AppConstants.DataTypes;
import com.app.constants.TableNames;
import com.app.helpers.ConversionHelper;
import com.app.model.FakturaOtpremnica;
import com.app.model.PoreskaStopa;
import com.app.model.Porez;
import com.app.model.Preduzece;
import com.app.model.StavkeFaktureOtpremnice;
import com.app.model.StavkeNarudzbe;
import com.app.model.invoice.Faktura;
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
import com.app.repositories.XML_Repository;
import com.app.transformers.ArtikalTransformer;
import com.app.transformers.CenovnikTransformer;
import com.app.transformers.FakturaOtpremnicaToFaktura;
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
public class GenericServiceImpl implements IGenericService {

	@Autowired
	private XML_Repository xmlRepo;
	
	@Override
	public boolean create(TableRowDTO row) {
		try {
			CrudRepository repo = getTableRepo(row.getTableName());
			ITransformer transformer = getTransformer(row.getTableName());
			HashMap<String, Object> fks = getFKs(row);
			Object entity = transformer.transformFromDTO(row, fks);
			
			if(row.getTableName().equals(TableNames.STAVKE_NARUDZBE)) {
				calculateOrderItemValue((StavkeNarudzbe)entity);
			} else if(row.getTableName().equals(TableNames.STAVKE_FAKTURE_OTPREMNICE)) {
				calculateInvoiceItemValue((StavkeFaktureOtpremnice)entity);
			}
			repo.save(entity);
			
			if(row.getTableName().equals(TableNames.STAVKE_FAKTURE_OTPREMNICE)) {
				calculateInvoiceValue(((StavkeFaktureOtpremnice)entity).getFakturaOtpremnica().getIdFaktureOtpremnice(), true);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(TableRowDTO row) { 
		try {
			CrudRepository repo = getTableRepo(row.getTableName());
			ITransformer transformer = getTransformer(row.getTableName());
			HashMap<String, Object> fks = getFKs(row);
			Object entity = transformer.transformFromDTO(row, fks);
			
			if(row.getTableName().equals(TableNames.STAVKE_NARUDZBE)) {
				calculateOrderItemValue((StavkeNarudzbe)entity);
			} else if(row.getTableName().equals(TableNames.STAVKE_FAKTURE_OTPREMNICE)) {
				calculateInvoiceItemValue((StavkeFaktureOtpremnice)entity);
			}
			
			repo.save(entity);
			
			if(row.getTableName().equals(TableNames.STAVKE_FAKTURE_OTPREMNICE)) {
				calculateInvoiceValue(((StavkeFaktureOtpremnice)entity).getFakturaOtpremnica().getIdFaktureOtpremnice(), true);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Long id, String tableCode) {
		FakturaOtpremnica faktura = null;
		try {
			String tableName = ConversionHelper.getTableName(tableCode);
			CrudRepository repo = getTableRepo(tableName);
			

			if(tableName.equals(TableNames.STAVKE_FAKTURE_OTPREMNICE)) {
				faktura = calculateInvoiceValue(id, false);
			}
			
			repo.delete(id);
			

			if(tableName.equals(TableNames.STAVKE_FAKTURE_OTPREMNICE) && faktura != null) {
				update(getTransformer(TableNames.FAKTURA_OTPREMNICA).transformToDTO(faktura).getRows().get(0));
			}
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public TableDTO getById(Long id, String tableCode) {
		try {
			String tableName = ConversionHelper.getTableName(tableCode);
			CrudRepository repo = getTableRepo(tableName);
			Object result = repo.findOne(id);
			ITransformer tr = getTransformer(tableName);
			TableDTO dto = tr.transformToDTO(result);
			return dto;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TableDTO getAll(String tableCode) {
		try {
			String tableName = ConversionHelper.getTableName(tableCode);
			CrudRepository repo = getTableRepo(tableName);
			ArrayList<Object> rows = (ArrayList<Object>) repo.findAll();
			ITransformer tr = getTransformer(tableName);
			TableDTO dto = tr.transformToDTO(rows);
			return dto;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public HashMap<String, String> getEditableTables() {
		HashMap<String, String> tables = new HashMap<String, String>();

		for (String tableName : TableNames.getEditableTableNames()) {
			tables.put(ConversionHelper.getTableCode(tableName), tableName);
		}

		return tables;
	}

	@Override
	public TableDTO getTableByParent(String parentTableCode, String parentId) {
		try {
			Long parent = null;
			TableDTO retVal = new TableDTO();
			ArrayList<Object> rowsOfDocTable = new ArrayList<Object>();

			String tableName = ConversionHelper.getTableName(parentTableCode);
			CrudRepository repo = getTableRepo(tableName);
			parent = Long.parseLong(parentId);
			Object result = repo.findOne(parent);
			ITransformer tr = getTransformer(tableName);
			TableDTO dto = tr.transformToDTO(result);

			String child = dto.getDocumentChildName();
			CrudRepository repo1 = getTableRepo(child);
			ArrayList<Object> rows = (ArrayList<Object>) repo1.findAll();
			ITransformer tr1 = getTransformer(child);
			TableDTO dto1 = tr1.transformToDTO(rows);

			for (TableRowDTO row : dto1.getRows()) {
				if (row.getFields().containsKey(tableName)
						&& row.getFields().get(tableName).toString().equals(parentId)) {
					HashMap<String, Object> fks = getFKs(row);
					Object entity = tr1.transformFromDTO(row, fks);
					rowsOfDocTable.add(entity);
				}
			}
			
			if(rowsOfDocTable.size() != 0){
				 retVal = tr1.transformToDTO(rowsOfDocTable);
			}		
			return retVal;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TableDTO getFilteredTable(TableRowDTO filterRow) {
		try {
			
			String tableName = filterRow.getTableName();
			ArrayList<Object> filteredRows = new ArrayList<Object>();
			TableDTO retVal = null;
			boolean addToList = true;

			CrudRepository repo = getTableRepo(tableName);
			ArrayList<Object> rows = (ArrayList<Object>) repo.findAll();
			ITransformer tr = getTransformer(tableName);
			TableDTO dto = tr.transformToDTO(rows);			

			for (TableRowDTO row : dto.getRows()) {
				addToList = true;
				for (TableFieldDTO fieldName : dto.getFields()) {
					if (fieldName.getType() == DataTypes.NUMBER) {
						if(filterRow.getFields().get(fieldName.getName()) != null){
							BigDecimal value = new BigDecimal(row.getFields().get(fieldName.getName()).toString());
							BigDecimal filter = new BigDecimal(filterRow.getFields().get(fieldName.getName()).toString());
							if (value.compareTo(filter) == 0) {
							} else {
								addToList = false;
								break;
							}
						}
					} else if (fieldName.getType() == DataTypes.BOOLEAN) {
						if(filterRow.getFields().get(fieldName.getName()) != null){
							boolean value = new Boolean(row.getFields().get(fieldName.getName()).toString()).booleanValue();
							boolean filter = new Boolean(filterRow.getFields().get(fieldName.getName()).toString()).booleanValue();
							if (value == filter) {
							} else {
								addToList = false;
								break;
							}
						}
					} else if (fieldName.getType() == DataTypes.TEXT) {
						if(filterRow.getFields().get(fieldName.getName()) != null){
							String value = row.getFields().get(fieldName.getName()).toString();
							String filter = filterRow.getFields().get(fieldName.getName()).toString();
							if (value.contains(filter)) {
							} else {
								addToList = false;
								break;
							}
						}
					} else if (fieldName.getType() == DataTypes.DATE) {
						if(filterRow.getFields().get(fieldName.getName()) != null){
							Date value1;
							Date filter1;
							DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
							try {
								value1 = format.parse(row.getFields().get(fieldName.getName()).toString());
								filter1 = format.parse(filterRow.getFields().get(fieldName.getName()).toString());
								if (value1.equals(filter1)) {
								} else {
									addToList = false;
									break;
								}
							} catch (ParseException e) {
								e.printStackTrace();
								System.out.println("Datum nije prosao parsiranje: ");
								addToList = false;
								break;
							}
						}

					} else if (fieldName.getType() == DataTypes.CHAR) {
						if(filterRow.getFields().get(fieldName.getName()) != null){
							String value = row.getFields().get(fieldName.getName()).toString();
							String filter = filterRow.getFields().get(fieldName.getName()).toString();
							if (value.contains(filter)) {
							} else {
								addToList = false;
								break;
							}
						}
					}					
				}
				if(addToList){
					HashMap<String, Object> fks = getFKs(row);
					Object entity = tr.transformFromDTO(row, fks);
					filteredRows.add(entity);
				}
			}

			if (filteredRows.size() != 0) {
				retVal = tr.transformToDTO(filteredRows);
				return retVal;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public TableDTO getMetaData(String tableCode) {
		try {
			String tableName = ConversionHelper.getTableName(tableCode);
			TableDTO metaData = new TableDTO();
			ITransformer transformer = getTransformer(tableName);
			metaData = transformer.getMetaData();
			return metaData;
		} catch (Exception e) {
			return null;
		}
	}

	private HashMap<String, Object> getFKs(TableRowDTO row) {
		HashMap<String, Object> fks = new HashMap<String, Object>();
		ArrayList<String> tableNames = TableNames.getAllTableNames();
		Object fk;
		CrudRepository repo;
		Long id;

		for (String fieldName : row.getFields().keySet()) {
			if (tableNames.contains(fieldName) && !row.getFields().get(fieldName).toString().equals("")) {
				repo = getTableRepo(fieldName);
				id = Long.parseLong(row.getFields().get(fieldName).toString());
				fk = repo.findOne(id);
				if (fk != null) {
					fks.put(fieldName, fk);
				}
			}
		}

		return fks;
	}

	private CrudRepository getTableRepo(String tableName) {
		switch (tableName) {
		case TableNames.CENOVNIK:
			return cenovnikRepo;
		case TableNames.FAKTURA_OTPREMNICA:
			return fakturaRepo;
		case TableNames.GRUPA_PROIZVODA:
			return grupaProizvodaRepo;
		case TableNames.JEDINICE_MERE:
			return jediniceMereRepo;
		case TableNames.KATALOG_ROBE_I_USLUGA:
			return artikalRepo;
		case TableNames.NARUDZBA:
			return narudzbaRepo;
		case TableNames.PORESKA_STOPA:
			return poreskaStopaRepo;
		case TableNames.POREZ:
			return porezRepo;
		case TableNames.POSLOVNA_GODINA:
			return poslovnaGodinaRepo;
		case TableNames.POSLOVNI_PARTNER:
			return poslovniPartnerRepo;
		case TableNames.PREDUZECE:
			return preduzeceRepo;
		case TableNames.SIFRA_DELATNOSTI:
			return sifraDelatnostiRepo;
		case TableNames.STAVKE_CENOVNIKA:
			return stavkeCenovnikaRepo;
		case TableNames.STAVKE_FAKTURE_OTPREMNICE:
			return stavkeFaktureRepo;
		case TableNames.STAVKE_NARUDZBE:
			return stavkeNarudzbeRepo;
		case TableNames.PARTNER:
			return preduzeceRepo;
		}

		return null;
	}

	private ITransformer getTransformer(String tableName) {
		switch (tableName) {
		case TableNames.CENOVNIK:
			return new CenovnikTransformer();
		case TableNames.FAKTURA_OTPREMNICA:
			return new FakturaTransformer();
		case TableNames.GRUPA_PROIZVODA:
			return new GrupaProizvodaTransformer();
		case TableNames.JEDINICE_MERE:
			return new JediniceMereTransformer();
		case TableNames.KATALOG_ROBE_I_USLUGA:
			return new ArtikalTransformer();
		case TableNames.NARUDZBA:
			return new NarudzbaTransformer();
		case TableNames.PORESKA_STOPA:
			return new PoreskaStopaTransformer();
		case TableNames.POREZ:
			return new PorezTransformer();
		case TableNames.POSLOVNA_GODINA:
			return new PoslovnaGodinaTransformer();
		case TableNames.POSLOVNI_PARTNER:
			return new PoslovniPartnerTransformer();
		case TableNames.PREDUZECE:
			return new PreduzeceTransformer();
		case TableNames.SIFRA_DELATNOSTI:
			return new SifraDelatnostiTransformer();
		case TableNames.STAVKE_CENOVNIKA:
			return new StavkeCenovnikaTransformer();
		case TableNames.STAVKE_FAKTURE_OTPREMNICE:
			return new StavkeFaktureTransformer();
		case TableNames.STAVKE_NARUDZBE:
			return new StavkeNarudzbeTransformer();
		}

		return null;
	}

	private void calculateOrderItemValue(StavkeNarudzbe orderItem) {
		double iznos;
		
		iznos = orderItem.getCenaBezPdvAStavkeNarudzbenice().doubleValue() * orderItem.getKolicinaStavkeNarudzbenice();
		
		orderItem.setIznosStavkeNarudzbenice(new BigDecimal(iznos));
	}

	private void calculateInvoiceItemValue(StavkeFaktureOtpremnice invoiceItem) {
		double vrednost = invoiceItem.getJedinicnaCenaStavkeFakture().intValue() * invoiceItem.getKolicina().intValue();
		
		//ako postoji rabat, osnovica je kad se od vrednosti oduzme rabat
		if(invoiceItem.getRabat() != null && invoiceItem.getRabat().intValue() > 0) {
			double osnovicaPDV = vrednost * (1 - invoiceItem.getRabat().intValue()/100);
			invoiceItem.setOsnovicaPdv(new BigDecimal(osnovicaPDV));
		} else {	//ako nema rabat, PDV se racuna na cistu vrednost
			invoiceItem.setOsnovicaPdv(new BigDecimal(vrednost));
		}
	}
	
	private FakturaOtpremnica calculateInvoiceValue(Long id, boolean shouldUpdate) throws Exception {
		FakturaOtpremnica faktura;
		double ukupno = 0, iznos = 0, rabat = 0, porez = 0, jedCenaStavke, rabatStavke, kolicina, porezStavke;
		faktura = fakturaRepo.findOne(id);
		
		//to znaci da je delete zvao i da je prosledjeni ID ustvari ID stavke, a ne fakture
		if(!shouldUpdate) {
			if(stavkeFaktureRepo.findOne(id) != null) {
				faktura = stavkeFaktureRepo.findOne(id).getFakturaOtpremnica();
			} else {
				faktura = null;
			}
		}
		
		if(faktura != null) {
			for (StavkeFaktureOtpremnice stavka : faktura.getStavkeFaktureOtpremnices()) {
				//ako je brisanje, preskoci stavku koja treba da se brise - ako je brisanje i ako se ID poklapa
				if(!(!shouldUpdate && stavka.getIdStavkeFakture().equals(id))) {
					jedCenaStavke = stavka.getJedinicnaCenaStavkeFakture().doubleValue();
					kolicina = stavka.getKolicina().doubleValue();
					
					ukupno += jedCenaStavke * kolicina;
					
					rabatStavke = jedCenaStavke*kolicina*stavka.getRabat().doubleValue()/100;
					rabat += rabatStavke;
					
					porezStavke = getCurrentTaxAmmount(stavka) * stavka.getOsnovicaPdv().doubleValue()/100;
					porez += porezStavke;
				}
			}
			iznos = ukupno - rabat + porez;
			faktura.setFaIznos(new BigDecimal(iznos));
			faktura.setFaUkupno(new BigDecimal(ukupno));
			faktura.setFaRabat(new BigDecimal(rabat));
			faktura.setFaPorez(new BigDecimal(porez));
			if(shouldUpdate) {
				fakturaRepo.save(faktura);
			}
		} else {
			throw new Exception("Ne postoji faktura za preracunavanje vrednosti u odnosu na izmenu stavke!");
		}
		
		
		return faktura;
	}
	
	private double getCurrentTaxAmmount(StavkeFaktureOtpremnice stavka) {
		BigDecimal iznosPoreza = new BigDecimal(0);
		Date datum = new Date();
		Set<PoreskaStopa> stope = stavka.getKatalogRobeIUsluga().getGrupaProizvoda().getPorez().getPoreskaStopas();
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
	
	public boolean generateKIF(Connection connection, KifDTO info) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("ID_preduzeca", info.getCompanyId());
		params.put("Datum_od", ConversionHelper.convertToDate(info.getDateFrom()));
		params.put("Datum_do", ConversionHelper.convertToDate(info.getDateTo()));
		params.put("Naziv_preduzeca", info.getCompanyName());
		params.put("DTOd", info.getDateFrom());
		params.put("DTDo", info.getDateTo());
		
		try {
			
			String reportName = "src/main/resources/jasper_reports/KIF";
			String outputName = "src/main/webapp/downloads/KIF";

			// compiles jrxml
			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
			// fills compiled report with parameters and a connection
			JasperPrint print = JasperFillManager.fillReport(reportName + ".jasper", params, connection);
			// exports report to pdf
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(outputName + ".pdf")); // your output goes here
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
			
			exporter.exportReport();
			((FileOutputStream)(exporter.getParameter(JRExporterParameter.OUTPUT_STREAM))).close();

		} catch (Exception e) {
			throw new RuntimeException("It's not possible to generate the pdf report.", e);
		} finally {
		}		
		
		return true;
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

	@Override
	public TableDTO getCompaniesForKIF() {
		TableDTO dto = null;
		
		ArrayList<Preduzece> companies = new ArrayList<Preduzece>();
		companies.addAll(preduzeceRepo.getCompaniesForKIF());
		
		if(companies.size() > 0) {
			dto = new PreduzeceTransformer().transformToDTO(companies);
		}
		
		return dto;
	}
	
	public boolean generateXML(Long id,String filePath) {
		String tableCode="Faktura_i_otpremnica";
		Object result = null;
		try {
			String tableName = ConversionHelper.getTableName(tableCode);
			CrudRepository repo = getTableRepo(tableName);
			result = repo.findOne(id);
		} catch (Exception e) {
			return false;
		}
		if(result!=null && (result instanceof FakturaOtpremnica)){
			FakturaOtpremnica fo=(FakturaOtpremnica)result;
			Faktura invoice=FakturaOtpremnicaToFaktura.transform(fo);
			if(xmlRepo.generateXml(invoice, filePath))
				return true;
		}
		return false;
	}

	@Override
	public double getTax(String tableCode, Long id) {
		Object result = null;
		try {
			String tableName = ConversionHelper.getTableName(tableCode);
			CrudRepository repo = getTableRepo(tableName);
			result = repo.findOne(id);
		} catch (Exception e) {
			return -1;
		}
		if(result!=null && (result instanceof StavkeNarudzbe)){
			StavkeNarudzbe orderItem=(StavkeNarudzbe)result;
			Porez tax=orderItem.getKatalogRobeIUsluga().getGrupaProizvoda().getPorez();
			double taxAmount=getTaxAmmount(tax);
			return taxAmount;
		}
		return -1;
	}
	
	private static double getTaxAmmount(Porez porez) {
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
