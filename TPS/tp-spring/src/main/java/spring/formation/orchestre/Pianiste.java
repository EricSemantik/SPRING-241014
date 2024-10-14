package spring.formation.orchestre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Pianiste implements IMusicien {

	@Autowired
	@Qualifier("piano")
	private IInstrument instrument;
	@Value("Bouba mon petit ourson")
	private String morceau;

	public Pianiste() {
		super();
		System.out.println("Pianiste Constructeur : " + this.morceau);
	}

	public Pianiste(IInstrument instrument) {
		super();
		this.instrument = instrument;
	}


	public Pianiste(IInstrument instrument, String morceau) {
		super();
		this.instrument = instrument;
		this.morceau = morceau;
	}

	@Override
	public void jouer() {
		System.out.println("Le pianiste joue : " + this.morceau + "(" + this.instrument.son() + ")");

	}

	public IInstrument getInstrument() {
		return instrument;
	}

	
	public void setInstrument(IInstrument instrument) {
		this.instrument = instrument;
	}

	public String getMorceau() {
		return morceau;
	}

	public void setMorceau(String morceau) {
		this.morceau = morceau;
	}
	
}
