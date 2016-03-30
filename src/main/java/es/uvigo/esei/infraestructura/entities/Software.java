package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Software")
@NamedQuery(name = "findAllSoftware", query = "select s from Software s")
public class Software {

	@Id
	@JoinColumn
	@Column(name = "softwareName", length = 255)
	private String softwareName;

	@Column(length = 16, nullable = false)
	@Enumerated(EnumType.STRING)
	private SoftwareType type;

	@Column(length = 255)
	private String downloadURL;

	@ManyToMany(mappedBy = "softwares")
	private List<Subject> subjects;

	// Constructor required for JPA framework
	Software() {
	}

	public Software(String softwareName, int type, String downloadURL) {
		super();
		this.softwareName = softwareName;
		if (type == 1) {
			this.type = SoftwareType.OPERATIVE_SYSTEM;
		} else {
			this.type = SoftwareType.PROGRAM;
		}
		this.downloadURL = downloadURL;
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

	@Override
	public boolean equals(Object obj) {
		try {
			if (((Software) obj).getSoftwareName().equals(this.getSoftwareName()))
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
