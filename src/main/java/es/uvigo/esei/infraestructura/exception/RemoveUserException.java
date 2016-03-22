package es.uvigo.esei.infraestructura.exception;

public class RemoveUserException extends Exception
{
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoveUserException() {}

      public RemoveUserException(String message)
      {
         super(message);
      }
 }