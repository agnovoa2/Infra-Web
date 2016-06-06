package es.uvigo.esei.infraestructura.exception;

public class EmptyPetitionException extends Exception
{
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyPetitionException() {}

      public EmptyPetitionException(String message)
      {
         super(message);
      }
 }