package spring.formation.model;

public interface Views {
	public static interface ViewNone {
	}
	
	public static interface ViewBasic {
	}
	
	public static interface ViewConsultation extends ViewBasic {
		
	}
	
	public static interface ViewIndividu extends ViewBasic {
	}
	
	public static interface ViewLieu extends ViewBasic {
	}
	
	public static interface ViewLieuDetail extends ViewLieu {
	}
	
	public static interface ViewPatient extends ViewIndividu {
	}
	
	public static interface ViewPatientDetail extends ViewPatient {
	}

	public static interface ViewUtilisateur extends ViewBasic {
	}

	public static interface ViewUtilisateurDetail extends ViewUtilisateur {
	}
}
