package de.demimoranes.realestatecredit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.Before;
import org.junit.Test;

import de.demimoranes.realestatecredit.domain.AmountWithCurrency;
import de.demimoranes.realestatecredit.domain.Applicant;
import de.demimoranes.realestatecredit.domain.RealEstate;
import de.demimoranes.realestatecredit.dslifiers.AmountWithCurrencyDslifier;
import de.demimoranes.realestatecredit.dslifiers.ApplicantDslifier;
import de.demimoranes.realestatecredit.dslifiers.RealEstateDslifier;
import de.demimoranes.realestatecredit.placeholder.GroovyFormulaValuePlaceholder;
import de.demimoranes.realestatecredit.placeholder.SimpleValuePlaceholder;
import de.demimoranes.realestatecredit.placeholder.ValuePlaceholder;

public class JavaDslIntegrationTestCase {

	private static final String FORMEL = ".formel";

	private static final Currency EURO_CURRENCY = Currency.getInstance("EUR");
	
	@Before
	public void setUp() {
		new RealEstateDslifier().dslify();
		new AmountWithCurrencyDslifier().dslify();
		new ApplicantDslifier().dslify();
	}
	
	@Test
	public void testFormelIntegration() throws FileNotFoundException, IOException {
		Map<String, ValuePlaceholder> valuePlaceholders = new HashMap<String, ValuePlaceholder>();
		
		RealEstate realEstateToBuy = new RealEstate(false, new AmountWithCurrency(430.0, EURO_CURRENCY), 60.0);
		valuePlaceholders.put("Immobilie", new SimpleValuePlaceholder(realEstateToBuy, valuePlaceholders));
		
		Applicant applicant = new Applicant(
				new AmountWithCurrency(0, EURO_CURRENCY), 
				new RealEstate(false, 
						new AmountWithCurrency(650.0, EURO_CURRENCY), 75.0));
		valuePlaceholders.put("Antragsteller", new SimpleValuePlaceholder(applicant, valuePlaceholders));
		
		valuePlaceholders.put("monatlichesBruttoeinkommen", this.toAmountPlaceholder(2300.0));
		valuePlaceholders.put("Steuern", this.toAmountPlaceholder(210.0));
		valuePlaceholders.put("Sozialabgaben", this.toAmountPlaceholder(0.0));
		valuePlaceholders.put("sonstigeBelastungen", this.toAmountPlaceholder(460.0));
		valuePlaceholders.put("Kreditrate", this.toAmountPlaceholder(270.0));
		
		// Get all Formula file names with by using a FilenameFilter
		String[] formelFiles = new File("./").list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(FORMEL);
			}
		});
		
		CompilerConfiguration compilerConf = new CompilerConfiguration();
		compilerConf.setScriptBaseClass(DslDefinition.class.getName());
		
		for(String formelFile : formelFiles) {
			String code = IOUtils.toString(new FileInputStream(new File(formelFile)), "UTF-8");
			String formulaName = formelFile.replace(FORMEL, "");
			GroovyFormula formula = new GroovyFormula(formulaName, code, compilerConf, valuePlaceholders);
			valuePlaceholders.put(formulaName, new GroovyFormulaValuePlaceholder(formula));
		}
		
		for(int i = 0; i < 1000; i++) {
			valuePlaceholders.put("monatlichesBruttoeinkommen", this.toAmountPlaceholder(2300.0 + i));
			
			// Get formula from ValuePlaceholders Map
			ValuePlaceholder vp = valuePlaceholders.get("monatlichFreiVerfuegbarerBetrag");
			Object result = vp.getValue();
			
			assertEquals(new AmountWithCurrency(1140.0 + i, EURO_CURRENCY), result);
		}
		
	}
	
	private ValuePlaceholder toAmountPlaceholder(double d) {
		return new SimpleValuePlaceholder(new AmountWithCurrency(d, EURO_CURRENCY));
	}
	
}
