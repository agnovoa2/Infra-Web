package es.uvigo.esei.infraestructura.converter;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import es.uvigo.esei.infraestructura.ejb.ConsumableEJB;
import es.uvigo.esei.infraestructura.entities.Consumable;
@ManagedBean
@RequestScoped
public class ConsumableConverter implements Converter {

	@EJB
    private ConsumableEJB consumableEJB;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
            return null;
        }
		try {
            return consumableEJB.find(value);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid Consumable name", value)), e);
        }
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
            return "";
        }

        if (value instanceof Consumable) {
            return String.valueOf(((Consumable) value).getConsumableName());
        } else {
            return "";
        }
	}

}
