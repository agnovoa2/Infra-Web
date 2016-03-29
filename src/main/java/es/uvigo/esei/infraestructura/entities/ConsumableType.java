package es.uvigo.esei.infraestructura.entities;

public enum ConsumableType {
	CARTRIDGE {
		@Override
		public String toString() {
			return "Cartucho";
		}
	},
	TONER {
		@Override
		public String toString() {
			return "Toner";
		}
	},
	DRUM {
		@Override
		public String toString() {
			return "Tambor";
		}
	},
	TRANSFER_KIT {
		@Override
		public String toString() {
			return "Kit de transferencia";
		}
	},
	BELT_UNIT {
		@Override
		public String toString() {
			return "Cinturón de arrastre";
		}
	},
	FUSER {
		@Override
		public String toString() {
			return "Fusor";
		}
	},
	GARBAGE_UNIT {
		@Override
		public String toString() {
			return "Recipiente de residuos";
		}
	}
}
