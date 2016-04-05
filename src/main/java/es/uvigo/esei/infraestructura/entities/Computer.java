package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Computer")
@NamedQuery(name = "findAllComputers", query="select c from Computer c")
public class Computer {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="num", nullable=false)
	private int num;
	
	@Column(name="laboratory", nullable=false)
	private String laboratory;
	
	@Column(name="state", nullable=false)
	@Enumerated(EnumType.STRING)
	private State state;
	
	@Column(name="labelNum", unique=true, nullable=false)
	private int labelNum;
	
	@OneToMany(mappedBy="computer",cascade=CascadeType.REMOVE)
	private List<Incidence> incidences;
	
	Computer(){}
	
	public Computer(String laboratory, int num, int labelNum){
		this.laboratory = laboratory;
		this.num = num;
		this.state = State.OK;
		this.labelNum = labelNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getLaboratory() {
		return laboratory;
	}

	public void setLaboratory(String laboratory) {
		this.laboratory = laboratory;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getLabelNum() {
		return labelNum;
	}

	public void setLabelNum(int labelNum) {
		this.labelNum = labelNum;
	}

	public List<Incidence> getIncidences() {
		return incidences;
	}

	public void setIncidences(List<Incidence> incidences) {
		this.incidences = incidences;
	}
	
}
