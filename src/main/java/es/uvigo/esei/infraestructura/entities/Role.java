package es.uvigo.esei.infraestructura.entities;

public enum Role{
	STUDENT {
		@Override
		public String toString() {
			return "Estudiante";
		}
	},INTERN {
		@Override
		public String toString() {
			return "Becario";
		}
	},PROFESSOR {
		@Override
		public String toString() {
			return "Profesor";
		}
	}
}