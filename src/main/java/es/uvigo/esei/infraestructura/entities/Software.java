package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Software")
@NamedQueries({
@NamedQuery(name = "findAllSoftware", query = "select s from Software s"),
@NamedQuery(name = "findAllSoftwareProgram", query = "select s from Software s where s.type = es.uvigo.esei.infraestructura.entities.SoftwareType.PROGRAM"),
@NamedQuery(name = "findAllSoftwareOperativeSystem", query = "select s from Software s where s.type = es.uvigo.esei.infraestructura.entities.SoftwareType.OPERATIVE_SYSTEM")
})
public class Software {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "softwareName", length = 255, unique=true, nullable=false)
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

	public void setType(int type) {
		if (type == 1) {
			this.type = SoftwareType.OPERATIVE_SYSTEM;
		} else {
			this.type = SoftwareType.PROGRAM;
		}
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
