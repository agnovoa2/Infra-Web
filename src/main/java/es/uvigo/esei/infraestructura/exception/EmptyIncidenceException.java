package es.uvigo.esei.infraestructura.exception;

public class EmptyIncidenceException extends Exception
{
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyIncidenceException() {}

      public EmptyIncidenceException(String message)
      {
         super(message);
      }
 }