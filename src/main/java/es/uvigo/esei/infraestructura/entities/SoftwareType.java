package es.uvigo.esei.infraestructura.entities;

public enum SoftwareType {
	PROGRAM{
		@Override
		public String toString() {
			return "Programa";
		}
	},OPERATIVE_SYSTEM{
		@Override
		public String toString() {
			return "Sistema Operativo";
		}
	}
}
