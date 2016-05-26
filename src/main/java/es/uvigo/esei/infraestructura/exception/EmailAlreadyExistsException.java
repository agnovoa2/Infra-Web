package es.uvigo.esei.infraestructura.exception;

public class EmailAlreadyExistsException extends Exception
{
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException() {}

      public EmailAlreadyExistsException(String message)
      {
         super(message);
      }
 }