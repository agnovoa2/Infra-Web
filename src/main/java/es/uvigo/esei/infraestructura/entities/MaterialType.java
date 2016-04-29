package es.uvigo.esei.infraestructura.entities;

public enum MaterialType {
	HARD_DRIVE {
		@Override
		public String toString() {
			return "Disco duro";
		}
	},
	MONITOR {
		@Override
		public String toString() {
			return "Monitor";
		}
	},
	RAM {
		@Override
		public String toString() {
			return "RAM";
		}
	},
	OTHER {
		@Override
		public String toString() {
			return "Otros";
		}
	}
}
