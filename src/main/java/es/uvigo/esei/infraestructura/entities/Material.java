package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Material")
@NamedQuery(name = "findAllMaterials", query = "select m from Material m")
public class Material {

	@Id
	@Column(name = "materialPetitionNumber")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int materialPetitionNumber;

	@Column(name = "materialName", length = 45)
	private String materialName;

	@Column(name = "materialType", length = 45)
	private String materialType;

	@Column(name = "material", length = 45)
	private MaterialType material;
	
	@Column(name = "capacity", length = 45)
	private String capacity;

	@Column(name = "tradeMark", length = 45)
	private String tradeMark;

	@Column(name = "model", length = 45)
	private String model;

	@Column(name = "speed", length = 45)
	private String speed;

	@Column(name = "destination", length = 45)
	private String destination;

	@Column(name = "size", length = 45)
	private String size;

	@Column(name = "resolution", length = 45)
	private String resolution;
	
	@Column(name = "proportion", length = 45)
	private String proportion;
	
	@Column(name = "quantity")
	private int quantity;
	
	@OneToMany(mappedBy="material")
	private List<MaterialPetitionRow> materialPetitionRows;

	Material() {
	}

	public Material(String materialName, String materialType, String capacity, String tradeMark, String model){
		this.materialName = materialName;
		this.material = MaterialType.HARD_DRIVE;
		this.materialType = materialType;
		this.capacity = capacity;
		this.tradeMark = tradeMark;
		this.model = model;
		this.quantity = 0;
	}
	
	public Material(String materialName, String materialType, String capacity, String speed, String destination, String tradeMark){
		this.materialName = materialName;
		this.material = MaterialType.RAM;
		this.materialType = materialType;
		this.capacity = capacity;
		this.speed = speed;
		this.destination = destination;
		this.tradeMark = tradeMark;
		this.quantity = 0;
	}
	
	public Material(String materialName, String size, String proportion, String resolution, String materialType, String tradeMark, String model){
		this.materialName = materialName;
		this.material = MaterialType.MONITOR;
		this.size = size;
		this.proportion = proportion;
		this.resolution = resolution;
		this.materialType = materialType;
		this.tradeMark = tradeMark;
		this.model = model;
		this.quantity = 0;
	}
	public int getMaterialPetitionNumber() {
		return materialPetitionNumber;
	}

	public void setMaterialPetitionNumber(int materialPetitionNumber) {
		this.materialPetitionNumber = materialPetitionNumber;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public MaterialType getMaterial() {
		return material;
	}

	public void setMaterial(MaterialType material) {
		this.material = material;
	}

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	public List<MaterialPetitionRow> getMaterialPetitionRows() {
		return materialPetitionRows;
	}

	public void setMaterialPetitionRows(List<MaterialPetitionRow> materialPetitionRows) {
		this.materialPetitionRows = materialPetitionRows;
	}
}