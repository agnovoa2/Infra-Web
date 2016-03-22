package es.uvigo.esei.infraestructura.exception;

public class RegisterException extends Exception
{
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegisterException() {}

      public RegisterException(String message)
      {
         super(message);
      }
 }