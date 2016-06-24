//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.24 at 06:33:45 PM CEST 
//


package com.app.model.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Datum_izdavanja" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Podaci_o_izdavaocu" type="{}TPreduzece"/>
 *         &lt;element name="Podaci_o_kupcu" type="{}TPreduzece"/>
 *         &lt;element name="Stavka_fakture" type="{}TStavkaFakture" maxOccurs="unbounded"/>
 *         &lt;element name="Iznos">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Ukupan_rabat">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Ukupna_poreska_osnova">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Ukupan_PDV">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;fractionDigits value="2"/>
 *               &lt;totalDigits value="15"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Ukupno_za_uplatu">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="20"/>
 *               &lt;fractionDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Dodatne_napomene" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Racun_i_robu_izdao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Racun_i_robu_primio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Podaci_o_prevozniku" type="{}TPrevoznik" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Broj" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datumIzdavanja",
    "datumValute",
    "podaciOIzdavaocu",
    "podaciOKupcu",
    "stavkaFakture",
    "iznos",
    "ukupanRabat",
    "ukupnaPoreskaOsnova",
    "ukupanPDV",
    "ukupnoZaUplatu",
    "dodatneNapomene",
    "racunIRobuIzdao",
    "racunIRobuPrimio",
    "podaciOPrevozniku"
})
@XmlRootElement(name = "Faktura")
public class Faktura {

    @XmlElement(name = "Datum_izdavanja", required = true)
    protected String datumIzdavanja;
    @XmlElement(name = "Datum_valute", required = true)
    protected String datumValute;
    @XmlElement(name = "Podaci_o_izdavaocu", required = true)
    protected TPreduzece podaciOIzdavaocu;
    @XmlElement(name = "Podaci_o_kupcu", required = true)
    protected TPreduzece podaciOKupcu;
    @XmlElement(name = "Stavka_fakture", required = true)
    protected List<TStavkaFakture> stavkaFakture;
    @XmlElement(name = "Iznos", required = true)
    protected BigDecimal iznos;
    @XmlElement(name = "Ukupan_rabat", required = true)
    protected BigDecimal ukupanRabat;
    @XmlElement(name = "Ukupna_poreska_osnova", required = true)
    protected BigDecimal ukupnaPoreskaOsnova;
    @XmlElement(name = "Ukupan_PDV", required = true)
    protected BigDecimal ukupanPDV;
    @XmlElement(name = "Ukupno_za_uplatu", required = true)
    protected BigDecimal ukupnoZaUplatu;
    @XmlElement(name = "Dodatne_napomene", required = false)
    protected String dodatneNapomene;
    @XmlElement(name = "Racun_i_robu_izdao", required = false)
    protected String racunIRobuIzdao;
    @XmlElement(name = "Racun_i_robu_primio", required = false)
    protected String racunIRobuPrimio;
    @XmlElement(name = "Podaci_o_prevozniku", required = false)
    protected TPrevoznik podaciOPrevozniku;
    @XmlAttribute(name = "Broj", required = true)
    protected String broj;

    /**
     * Gets the value of the datumIzdavanja property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public String getDatumIzdavanja() {
        return datumIzdavanja;
    }

    /**
     * Sets the value of the datumIzdavanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumIzdavanja(String value) {
        this.datumIzdavanja = value;
    }

    /**
     * Gets the value of the datumValute property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public String getDatumValute() {
        return datumValute;
    }

    /**
     * Sets the value of the datumValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumValute(String value) {
        this.datumValute = value;
    }

    /**
     * Gets the value of the podaciOIzdavaocu property.
     * 
     * @return
     *     possible object is
     *     {@link TPreduzece }
     *     
     */
    public TPreduzece getPodaciOIzdavaocu() {
        return podaciOIzdavaocu;
    }

    /**
     * Sets the value of the podaciOIzdavaocu property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPreduzece }
     *     
     */
    public void setPodaciOIzdavaocu(TPreduzece value) {
        this.podaciOIzdavaocu = value;
    }

    /**
     * Gets the value of the podaciOKupcu property.
     * 
     * @return
     *     possible object is
     *     {@link TPreduzece }
     *     
     */
    public TPreduzece getPodaciOKupcu() {
        return podaciOKupcu;
    }

    /**
     * Sets the value of the podaciOKupcu property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPreduzece }
     *     
     */
    public void setPodaciOKupcu(TPreduzece value) {
        this.podaciOKupcu = value;
    }

    /**
     * Gets the value of the stavkaFakture property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stavkaFakture property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStavkaFakture().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TStavkaFakture }
     * 
     * 
     */
    public List<TStavkaFakture> getStavkaFakture() {
        if (stavkaFakture == null) {
            stavkaFakture = new ArrayList<TStavkaFakture>();
        }
        return this.stavkaFakture;
    }

    /**
     * Gets the value of the iznos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIznos() {
        return iznos;
    }

    /**
     * Sets the value of the iznos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIznos(BigDecimal value) {
        this.iznos = value;
    }

    /**
     * Gets the value of the ukupanRabat property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupanRabat() {
        return ukupanRabat;
    }

    /**
     * Sets the value of the ukupanRabat property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupanRabat(BigDecimal value) {
        this.ukupanRabat = value;
    }

    /**
     * Gets the value of the ukupnaPoreskaOsnova property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupnaPoreskaOsnova() {
        return ukupnaPoreskaOsnova;
    }

    /**
     * Sets the value of the ukupnaPoreskaOsnova property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupnaPoreskaOsnova(BigDecimal value) {
        this.ukupnaPoreskaOsnova = value;
    }

    /**
     * Gets the value of the ukupanPDV property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupanPDV() {
        return ukupanPDV;
    }

    /**
     * Sets the value of the ukupanPDV property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupanPDV(BigDecimal value) {
        this.ukupanPDV = value;
    }

    /**
     * Gets the value of the ukupnoZaUplatu property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupnoZaUplatu() {
        return ukupnoZaUplatu;
    }

    /**
     * Sets the value of the ukupnoZaUplatu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupnoZaUplatu(BigDecimal value) {
        this.ukupnoZaUplatu = value;
    }

    /**
     * Gets the value of the dodatneNapomene property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getDodatneNapomene() {
        return dodatneNapomene;
    }

    /**
     * Sets the value of the dodatneNapomene property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDodatneNapomene(String value) {
        this.dodatneNapomene = value;
    }

    /**
     * Gets the value of the racunIRobuIzdao property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getRacunIRobuIzdao() {
        return racunIRobuIzdao;
    }

    /**
     * Sets the value of the racunIRobuIzdao property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRacunIRobuIzdao(String value) {
        this.racunIRobuIzdao = value;
    }

    /**
     * Gets the value of the racunIRobuPrimio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getRacunIRobuPrimio() {
        return racunIRobuPrimio;
    }

    /**
     * Sets the value of the racunIRobuPrimio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRacunIRobuPrimio(String value) {
        this.racunIRobuPrimio = value;
    }

    /**
     * Gets the value of the podaciOPrevozniku property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TPrevoznik }{@code >}
     *     
     */
    public TPrevoznik getPodaciOPrevozniku() {
        return podaciOPrevozniku;
    }

    /**
     * Sets the value of the podaciOPrevozniku property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TPrevoznik }{@code >}
     *     
     */
    public void setPodaciOPrevozniku(TPrevoznik value) {
        this.podaciOPrevozniku = value;
    }

    /**
     * Gets the value of the broj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroj() {
        return broj;
    }

    /**
     * Sets the value of the broj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroj(String value) {
        this.broj = value;
    }

}
