package es.uvigo.esei.infraestructura.entities;

import java.io.Serializable;

public class PetitionRowId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String consumable;
	private int printer;
	private int petition;

	public String getConsumable() {
		return consumable;
	}

	public void setConsumable(String consumable) {
		this.consumable = consumable;
	}

	public int getPrinter() {
		return printer;
	}

	public void setPrinter(int printer) {
		this.printer = printer;
	}

	public int getPetition() {
		return petition;
	}

	public void setPetition(int petition) {
		this.petition = petition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consumable == null) ? 0 : consumable.hashCode());
		result = prime * result + petition;
		result = prime * result + printer;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PetitionRowId other = (PetitionRowId) obj;
		if (consumable == null) {
			if (other.consumable != null)
				return false;
		} else if (!consumable.equals(other.consumable))
			return false;
		if (petition != other.petition)
			return false;
		if (printer != other.printer)
			return false;
		return true;
	}
}
