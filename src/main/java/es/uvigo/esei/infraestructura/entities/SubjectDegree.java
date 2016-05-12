package es.uvigo.esei.infraestructura.entities;

public enum SubjectDegree {
	GRADE{
		@Override
		public String toString() {
			return "Grado";
		}
	},MASTER{
		@Override
		public String toString() {
			return "Máster";
		}
	}
}
