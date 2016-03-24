package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Software")
public class Software {
	
	@Id
	@JoinColumn
	@Column(name="softwareName", length = 45)
	private String softwareName;

	@Column(length = 16, nullable = false)
	@Enumerated(EnumType.STRING)
	private SoftwareType type;

	@Column(length = 255)
	private String downloadURL;
	
	@Column(length = 255)
	private String description;
	
	@ManyToMany(mappedBy="softwares")
	private List<Subject> subjects;
	
	//Constructor required for JPA framework
	Software() {}

	public Software(String softwareName, int type, String downloadURL, String description) {
		super();
		this.softwareName = softwareName;
		if(type == 1){
			this.type = SoftwareType.OPERATIVE_SYSTEM;
		} else {
			this.type = SoftwareType.PROGRAM;
		}
		this.downloadURL = downloadURL;
		this.description = description;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public SoftwareType getType() {
		return type;
	}

	public void setType(SoftwareType type) {
		this.type = type;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
